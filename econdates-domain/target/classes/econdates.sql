CREATE TABLE ed_indicator (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) NOT NULL,
importance ENUM('HIGH', 'MEDIUM', 'LOW'),
source_report VARCHAR (500),
release_page VARCHAR(500),
release_url_key VARCHAR(255),
release_url VARCHAR(500),
release_time TIMESTAMP NOT NULL,
release_day_of_month INT,
release_day_of_week INT,
release_frequency INT,
description TEXT,
country_id INT,
FOREIGN KEY (country_id) REFERENCES ed_country (id)
);

CREATE TABLE ed_indicator_history (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
date DATE,
actual varchar,
consensus varchar,
indicator_id INT,
FOREIGN KEY (indicator_id) REFERENCES ed_indicator (id)
);

CREATE TABLE ed_country (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name varchar(50), 
gmt TIMESTAMP,
currency varchar(3),
day_light_saving ENUM ("ON","OFF")
);

CREATE TABLE ed_holidays(
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50),
date DATE,
country_id INT,
FOREIGN KEY (country_id) REFERENCES ed_country (id)
);

CREATE TABLE ed_country_indicator_assoc(
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
indicator_id INT, 
country_id INT,
FOREIGN KEY (indicator_id) REFERENCES ed_indicator (id),
FOREIGN KEY (country_id) REFERENCES ed_country (id)
)