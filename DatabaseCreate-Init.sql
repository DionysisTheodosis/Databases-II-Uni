CREATE DATABASE attractionDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE attractionDB;

CREATE TABLE map (
	mapID INT AUTO_INCREMENT PRIMARY KEY, 
    embeded_Url TEXT NOT NULL ,
    attractionName VARCHAR(100) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE country (
    countryName VARCHAR(60) NOT NULL PRIMARY KEY
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE county (
    countyName VARCHAR(60) NOT NULL PRIMARY KEY,
    countryName VARCHAR(60) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE city (
    cityName VARCHAR(60) NOT NULL PRIMARY KEY,
    countyName VARCHAR(60) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE Day (
    dayName VARCHAR(15) NOT NULL PRIMARY KEY
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE operation_Time (
    open TIME,
    close TIME,
    attractionName VARCHAR(100) NOT NULL,
    dayName VARCHAR(15) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE attraction_type (
    typeName VARCHAR(255) NOT NULL PRIMARY KEY,
    attrTypeImage INT
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE picture (
	pictureID INT AUTO_INCREMENT PRIMARY KEY, 
    picture_url TEXT NOT NULL ,
    attractionName VARCHAR(100),
    username VARCHAR(20)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE review (
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    comment TEXT NOT NULL,
    rating  DECIMAL(2, 1) NOT NULL CHECK (rating IN (0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5)),
    attractionName VARCHAR(100) NOT NULL,
    username VARCHAR(20) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE user (
    username VARCHAR(20) NOT NULL PRIMARY KEY,
    surname VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    userProfilePic INT
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE attraction (
    attractionName VARCHAR(100) NOT NULL PRIMARY KEY,
    entrance_fee DECIMAL(10, 2) NOT NULL DEFAULT 0.0 CHECK (entrance_fee >= 0.0),
    website VARCHAR(255),
    attractionType VARCHAR(255) NOT NULL,
    attr_profilePic INT ,
    cityName VARCHAR(60) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE email_attraction (
    email VARCHAR(255) NOT NULL PRIMARY KEY CHECK (email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,7}$') ,
    attractionName VARCHAR(100) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE telephone_attraction (
    telephone CHAR(10) NOT NULL PRIMARY KEY CHECK (telephone REGEXP '^[0-9]+$'),
    attractionName VARCHAR(100) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

ALTER TABLE review
ADD CONSTRAINT PK_Review PRIMARY KEY (username,attractionName);

ALTER TABLE review
ADD CONSTRAINT FK_Attraction_Review 
FOREIGN KEY (attractionName) REFERENCES attraction(attractionName)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE review
ADD CONSTRAINT FK_User_Review 
FOREIGN KEY (username) REFERENCES user(username)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE user
ADD CONSTRAINT FK_User_Picture 
FOREIGN KEY (userProfilePic) REFERENCES picture(pictureID)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE attraction_type
ADD CONSTRAINT FK_AtrrType_Image 
FOREIGN KEY (attrTypeImage) REFERENCES picture(pictureID)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE map
ADD CONSTRAINT FK_Atrraction_Map 
FOREIGN KEY (attractionName) REFERENCES attraction(attractionName)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE operation_Time
ADD CONSTRAINT PK_operation_Time PRIMARY KEY (dayName,attractionName);

ALTER TABLE operation_Time
ADD CONSTRAINT FK_Operates_Attraction
FOREIGN KEY (attractionName) REFERENCES attraction(attractionName)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE operation_Time
ADD CONSTRAINT FK_Operates_Day
FOREIGN KEY (dayName) REFERENCES day(dayName)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE city
ADD CONSTRAINT FK_County_City 
FOREIGN KEY (countyName) REFERENCES county(countyName)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE county
ADD CONSTRAINT FK_County_Country
FOREIGN KEY (countryName) REFERENCES country(countryName)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE email_attraction
ADD CONSTRAINT FK_Email_Attraction 
FOREIGN KEY (attractionName) REFERENCES attraction(attractionName)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE telephone_attraction
ADD CONSTRAINT FK_Telephone_Attraction 
FOREIGN KEY (attractionName) REFERENCES attraction(attractionName)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE attraction
ADD CONSTRAINT FK_Attraction_Picture
FOREIGN KEY (attr_profilePic) REFERENCES picture(pictureID)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE attraction
ADD CONSTRAINT FK_Attraction_Type 
FOREIGN KEY (attractionType) REFERENCES attraction_type(typeName)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE attraction
ADD CONSTRAINT FK_Attraction_City 
FOREIGN KEY (cityName) REFERENCES city(cityName)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE picture
ADD CONSTRAINT FK_Picture_Review 
FOREIGN KEY (attractionName,username) REFERENCES review(attractionName,username)
ON DELETE CASCADE
ON UPDATE CASCADE;




