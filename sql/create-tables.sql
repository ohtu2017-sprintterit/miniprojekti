CREATE TABLE IF NOT EXISTS Reference (
    id integer PRIMARY KEY,
    authors varchar(200),
    title varchar(100),
    year integer
);

CREATE TABLE IF NOT EXISTS Article (
    journal varchar(100),
    volume integer,
    number integer,
    pages varchar(50),
    publisher varchar(100),
    address varchar(100),
    id integer,
    FOREIGN KEY(id) REFERENCES Reference(id)
);
