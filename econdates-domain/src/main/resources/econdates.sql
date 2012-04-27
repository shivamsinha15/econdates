CREATE TABLE ed_author (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
eda_url VARCHAR(255),
eda_blog_name VARCHAR(100),
eda_description VARCHAR (255)
)

CREATE TABLE ed_publication (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
edp_title VARCHAR(255),
edp_url VARCHAR(255),
edp_release_date TIMESTAMP,
edp_up_vote INT,
edp_down_vote INT,
edp_views INT
)

CREATE TABLE ed_country (
id INT NOT NULL PRIMARY KEY,
edc_name VARCHAR(50),
edc_currency_name VARCHAR(50),
edc_currency_code VARCHAR(3),
edc_mobile_ext VARCHAR(10),
edc_capital VARCHAR(100),
edc_map_reference VARCHAR(100),
edc_nationality_singular VARCHAR(255),
edc_nationality_plural VARCHAR(255),
edc_fips_104 VARCHAR(10),
edc_iso_2 VARCHAR(10),
edc_iso_3 VARCHAR(10)
)


CREATE TABLE ed_region (
id INT NOT NULL PRIMARY KEY,
edr_name VARCHAR(100),
edr_code VARCHAR(2),
edr_country_id INT,
FOREIGN KEY (edr_country_id) REFERENCES ed_country(id)
)


CREATE TABLE ed_city (
id INT NOT NULL PRIMARY KEY,
edci_name VARCHAR(100),
edci_latitude VARCHAR(100),
edci_longitude VARCHAR(100),
edci_time_zone VARCHAR(100),
edci_time_offset VARCHAR(100),
edci_code VARCHAR(100),
edci_daylight_saving  BOOLEAN,
edci_country_id INT NOT NULL,
edci_region_id INT NOT NULL,
FOREIGN KEY (edci_country_id) REFERENCES ed_country (id),
FOREIGN KEY (edci_region_id) REFERENCES ed_region (id)
)

CREATE TABLE ed_user (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
edu_firstname VARCHAR(50),
edu_lastname VARCHAR(50),
edu_mobile_number VARCHAR(50) NOT NULL,
edu_mobile_type VARCHAR(50),
edu_mobile_id VARCHAR(255),
edu_primary_email VARCHAR(100),
edu_secondary_email VARCHAR(100),
edu_password VARCHAR(50),
edu_created_date TIMESTAMP NOT NULL,
edu_last_login TIMESTAMP,
edu_ip_address VARCHAR(100),
edu_role VARCHAR(32),
edu_country_id INT,
edu_city_id INT,
FOREIGN KEY (edu_country_id) REFERENCES ed_country (id),
FOREIGN KEY (edu_city_id) REFERENCES ed_city (id)
)

CREATE TABLE ed_user_comment (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
educ_vote TINYINT SIGNED DEFAULT 0,
educ_favourite BOOLEAN,
educ_user_id INT NOT NULL,
FOREIGN KEY (educ_user_id) REFERENCES ed_user (id) 
)

CREATE TABLE ed_indicator (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
edi_name VARCHAR(255),
edi_importance ENUM("High", "Low", "Medium"),
edi_description TEXT,
edi_release_time TIME,
edi_release_frequency INT,
edi_release_day_of_week INT,
edi_release_day_of_month INT,
edi_release_url VARCHAR(255),
edi_release_page VARCHAR(255),
edi_source_report VARCHAR(255),
edi_last_updated TIMESTAMP NOT NULL,
edi_country_id INT,
FOREIGN KEY (edi_country_id) REFERENCES ed_country (id)
)

CREATE TABLE ed_history (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
edh_actual VARCHAR(50),
edh_consensus VARCHAR(50),
edh_revised VARCHAR(50),
edh_analysis TEXT, 
edh_release_date TIMESTAMP,
edh_indicator_id INT,
FOREIGN KEY (edh_indicator_id) REFERENCES ed_indicator (id) ON DELETE CASCADE
)


CREATE TABLE ed_indicator_publication (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
edip_edp_id INT,
edip_edi_id INT,
edip_created_date TIMESTAMP,
FOREIGN KEY (edip_edp_id) REFERENCES ed_publication (id),
FOREIGN KEY (edip_edi_id) REFERENCES ed_indicator (id)
)

CREATE TABLE ed_comment (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
edc_field TEXT NOT NULL,
edc_up_vote INT,
edc_down_vote INT,
edc_created_date TIMESTAMP,
edc_user_id INT NOT NULL,
edc_user_comment_id INT NOT NULL,
edc_publication_id INT NOT NULL,
CONSTRAINT FOREIGN KEY (edc_publication_id) REFERENCES ed_publication (id)
)

CREATE TABLE ed_user_publication (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
edup_user_id INT,
edup_publication_id INT,
edup_vote TINYINT SIGNED DEFAULT 0,
edup_favourite BOOLEAN,
CONSTRAINT FOREIGN KEY (edup_user_id) REFERENCES ed_user (id),
CONSTRAINT FOREIGN KEY (edup_publication_id) REFERENCES ed_publication (id)
)


CREATE TABLE ed_holiday (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
edh_name VARCHAR (100),
edh_date DATE, 
edh_market_close BOOLEAN not null default 1,
edh_market_name VARCHAR(255),
edh_country_id INT,
FOREIGN KEY (edh_country_id) REFERENCES ed_country (id)
)