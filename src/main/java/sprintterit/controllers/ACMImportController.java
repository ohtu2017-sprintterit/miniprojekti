package sprintterit.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import sprintterit.database.ArticleDao;
import sprintterit.database.BookDao;
import sprintterit.database.InproceedingDao;
import sprintterit.models.Pages;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ACMImportController implements Route {
    private ArticleDao articleDao;
    private BookDao bookDao;
    private InproceedingDao inproceedingDao;

    public ACMImportController(ArticleDao articleDao, BookDao bookDao, InproceedingDao inproceedingDao) {
        this.articleDao = articleDao;
        this.bookDao = bookDao;
        this.inproceedingDao = inproceedingDao;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        switch (request.requestMethod()) {
            case "GET":
                return get(request, response);
            case "POST":
                return post(request, response);
            default:
                return "";
        }
    }

    private Object get(Request request, Response response) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        return new ThymeleafTemplateEngine().render(new ModelAndView(map, "acmimport"));
    }

    private Object post(Request request, Response response) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<String> errors = new ArrayList<>();

        String url = request.queryParams("acmurl");
        if (url != null) {
            try {
                URLConnection conn = new URL(url).openConnection();
                // Set fake user agent because ACM blocks normal Java requests
                conn.setRequestProperty("User-Agent",  "browser");
                String responseBody = new Scanner(conn.getInputStream()).useDelimiter("\\A").next();

                Pattern exportPattern = Pattern.compile("exportformats.cfm\\?id=([0-9]+)&expformat=bibtex");
                Matcher m = exportPattern.matcher(responseBody);
                if (m.find()) {
                    String exportURL = "http://dl.acm.org/" + m.group();
                    conn = new URL(exportURL).openConnection();
                    conn.setRequestProperty("User-Agent",  "browser");
                    String reference = new Scanner(conn.getInputStream()).useDelimiter("\\A").next();
                    String referenceTitle = parseAndSaveReference(reference);

                    map.put("success", referenceTitle);
                } else {
                    errors.add("No export URL found in content");
                }
            } catch (IOException e) {
                errors.add("fetching content from URL failed");
            } catch (SQLException e) {
                errors.add("saving reference to database failed");
            } catch (IllegalArgumentException e) {
                errors.add("parsing reference failed: " + e.getMessage());
            }
        } else {
            errors.add("URL is required");
        }

        map.put("errors", errors);

        return new ThymeleafTemplateEngine().render(new ModelAndView(map, "acmimport"));
    }

    private String parseAndSaveReference(String input) throws SQLException {
        HashMap<String, String> fields = new HashMap<>();
        String refType;

        Pattern typeAndKeyPattern = Pattern.compile("@(?<reftype>[A-Za-z]+)\\{(?<key>.+),");
        Matcher typeAndKeyMatcher = typeAndKeyPattern.matcher(input);
        if (typeAndKeyMatcher.find()) {
            refType = typeAndKeyMatcher.group("reftype");
        } else {
            throw new IllegalArgumentException("Reference type not found");
        }

        Pattern fieldPattern = Pattern.compile("(?<fieldname>[a-z]+)\\s*=\\s*\\{(?<fieldvalue>.+)},");
        Matcher fieldMatcher = fieldPattern.matcher(input);
        while (fieldMatcher.find()) {
            fields.put(fieldMatcher.group("fieldname"), fieldMatcher.group("fieldvalue"));
        }

        if (fields.containsKey("pages")) {
            String[] pages = fields.get("pages").split("--");
            try {
                Integer.parseInt(pages[0]);
                Integer.parseInt(pages[1]);

                fields.put("startpage", pages[0]);
                fields.put("endpage", pages[1]);
            } catch (NumberFormatException e) {
            }
        }

        switch (refType.toLowerCase()) {
            case "article":
                saveArticle(fields);
                break;
            case "book":
                saveBook(fields);
                break;
            case "inproceedings":
                saveInproceedings(fields);
                break;
            default:
                throw new IllegalArgumentException("Reference type '" + refType + "' not supported");
        }

        return fields.get("title");
    }

    private void saveArticle(HashMap<String, String> fields) throws SQLException {
        InputValidator input = new InputValidator((name) -> fields.getOrDefault(name, null));

        String tags = input.getString("tags", "Tags", false);
        String authors = input.getString("author", "Authors", true);
        String title = input.getString("title", "Title", true);
        String journal = input.getString("journal", "Journal", true);
        Integer volume = input.getInteger("volume", "Volume", false);
        Integer number = input.getInteger("number", "Number", false);
        String month = input.getString("month", "Month", false);
        Integer year = input.getInteger("year", "Year", true);
        Pages pages = input.getPages("startpage", "Startpage", "endpage", "Endpage");
        String publisher = input.getString("publisher", "Publisher", false);
        String address = input.getString("address", "Address", false);
        String note = input.getString("note", "Note", false);
        String key = input.getString("key", "Key", false);

        if (input.isOk()) {
            articleDao.addArticle(tags, authors, title, year, journal, volume, month, number,
                    pages, publisher, address, note, key);
        } else {
            throw new IllegalArgumentException("missing or invalid fields in reference");
        }
    }

    private void saveBook(HashMap<String, String> fields) throws SQLException {
        InputValidator input = new InputValidator((name) -> fields.getOrDefault(name, null));

        String tags = input.getString("tags", "Tags", false);
        String authors = input.getString("author", "Authors", true);
        String title = input.getString("title", "Title", true);
        Integer year = input.getInteger("year", "Year", true);
        String month = input.getString("month", "Month", false);
        Integer volume = input.getInteger("volume", "Volume", false);
        String series = input.getString("series", "Series", false);
        String edition = input.getString("edition", "Edition", false);
        String publisher = input.getString("publisher", "Publisher", true);
        String address = input.getString("address", "Address", false);
        String note = input.getString("note", "Note", false);
        String key = input.getString("key", "Key", false);

        if (input.isOk()) {
            bookDao.addBook(tags, authors, title, year, publisher, address,
                    volume, series, edition, month, note, key);
        } else {
            throw new IllegalArgumentException("missing or invalid fields in reference");
        }
    }

    private void saveInproceedings(HashMap<String, String> fields) throws SQLException {
        InputValidator input = new InputValidator((name) -> fields.getOrDefault(name, null));

        String tags = input.getString("tags", "Tags", false);
        String authors = input.getString("author", "Authors", true);
        String title = input.getString("title", "Title", true);
        String booktitle = input.getString("booktitle", "Booktitle", true);
        String editor = input.getString("editor", "Editor", false);
        Integer volume = input.getInteger("volume", "Volume", false);
        String series = input.getString("series", "Series", false);
        String month = input.getString("month", "Month", false);
        Integer year = input.getInteger("year", "Year", true);
        Pages pages = input.getPages("startpage", "Startpage", "endpage", "Endpage");
        String organization = input.getString("organization", "Organization", false);
        String publisher = input.getString("publisher", "Publisher", false);
        String address = input.getString("address", "Address", false);
        String note = input.getString("note", "Note", false);
        String key = input.getString("key", "Key", false);

        if (input.isOk()) {
            inproceedingDao.addInproceeding(tags, authors, title, year, booktitle,
                    editor, volume, series, month, pages,
                    organization, publisher, address, note, key);
        } else {
            throw new IllegalArgumentException("missing or invalid fields in reference");
        }
    }
}
