package sprintterit.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.rules.ExternalResource;
import spark.Spark;
import sprintterit.Main;

public class ServerRule extends ExternalResource {

    private final int port;
    private final String database;

    public ServerRule(int port, String database) {
        this.port = port;
        this.database = database;
    }

    @Override
    protected void before() throws Throwable {
        deleteDatabaseFile();
        disableHtmlUnitDriverWarnings();
        setChromeDriverPath();

        String[] args = {String.valueOf(port), "jdbc:sqlite:" + database};
        Main.main(args);
    }

    private void deleteDatabaseFile() {
        try {
            Files.delete(Paths.get("./" + database));
        } catch (NoSuchFileException e) {
            // Do nothing
        } catch (IOException e) {
            throw new IllegalStateException("Unable to delete the database file", e);
        }
    }

    private void disableHtmlUnitDriverWarnings() throws SecurityException {
        Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
    }

    private void setChromeDriverPath() {
        System.setProperty("webdriver.chrome.driver", "./driver/chromedriver");
    }

    @Override
    protected void after() {
        Spark.stop();
    }

}
