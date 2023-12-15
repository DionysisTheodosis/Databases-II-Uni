-- DHMIOURGIA THS VASHS
CREATE DATABASE attractionDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- XRHSH THS VASHS
USE attractionDB;

-- DHMIOURGIA TOU PINAKA XARTH
CREATE TABLE map (
	mapID INT AUTO_INCREMENT PRIMARY KEY, 
    embeded_Url TEXT NOT NULL ,
    attractionName VARCHAR(100) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- DHMIOURGIA TOU PINAKA XWRA
CREATE TABLE country (
    countryName VARCHAR(60) NOT NULL PRIMARY KEY
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- DHMIOURGIA TOU PINAKA GIA THN PERIFEREIA
CREATE TABLE region (
    regionName VARCHAR(60) NOT NULL PRIMARY KEY,
    countryName VARCHAR(60) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- DHMIOURGIA TOU PINAKA GIA THN PERIOXH
CREATE TABLE area (
    areaName VARCHAR(60) NOT NULL PRIMARY KEY,
    regionName VARCHAR(60) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- DHMIOURGIA TOU PINAKA GIA THN HMERA LEITOYRGIAS
CREATE TABLE Day (
    dayName VARCHAR(15) NOT NULL PRIMARY KEY
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- DHMIOURGIA TOU PINAKA GIA TO WRARIO LEITOURGIAS
CREATE TABLE operation_Time (
    open TIME,
    close TIME,
    attractionName VARCHAR(100) NOT NULL,
    dayName VARCHAR(15) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- DHMIOURGIA TOU PINAKA GIA TON TYPO AXIOTHEATOU
CREATE TABLE attraction_type (
    typeName VARCHAR(255) NOT NULL PRIMARY KEY,
    attrTypeImage INT
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- DHMIOURGIA TOU PINAKA GIA TIS FWTOGRAFIES
CREATE TABLE picture (
	pictureID INT AUTO_INCREMENT PRIMARY KEY, 
    picture_url TEXT NOT NULL ,
    attractionName VARCHAR(100),
    username VARCHAR(20)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- DHMIOURGIA TOU PINAKA GIA THN KRITIKH
CREATE TABLE review (
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    comment TEXT ,
    rating  DECIMAL(2, 1) NOT NULL CHECK (rating IN (0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5)),
    attractionName VARCHAR(100) NOT NULL,
    username VARCHAR(20) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- DHMIOURGIA TOU PINAKA XRHSTH
CREATE TABLE user (
    username VARCHAR(20) NOT NULL PRIMARY KEY,
    surname VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    userProfilePic INT
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- DHMIOURGIA TOU PINAKA GIA TO AXIOTHEATO
CREATE TABLE attraction (
    attractionName VARCHAR(100) NOT NULL PRIMARY KEY,
    entrance_fee DECIMAL(10, 2) NOT NULL DEFAULT 0.0 CHECK (entrance_fee >= 0.0),
    website VARCHAR(255),
    attractionType VARCHAR(255) NOT NULL,
    attr_profilePic INT ,
    areaName VARCHAR(60) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- DHMIOURGIA TOU PINAKA GIA TO EMAIL TOU AXIOTHEATOU
CREATE TABLE email_attraction (
    email VARCHAR(255) NOT NULL PRIMARY KEY CHECK (email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,7}$') ,
    attractionName VARCHAR(100) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- DHMIOURGIA TOU PINAKA GIA TO THLEFWNO TOU AXIOTHEATOU
CREATE TABLE telephone_attraction (
    telephone CHAR(10) NOT NULL PRIMARY KEY CHECK (telephone REGEXP '^[0-9]+$'),
    attractionName VARCHAR(100) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- EISAGWGH TOU KURIOU KLEIDIOU STON PINAKA THS KRITIKHS TO OPOIO EINAI APO SYNDIASMO
ALTER TABLE review
ADD CONSTRAINT PK_Review PRIMARY KEY (username,attractionName);

-- EISAGWGH XENOU KLEIDIOU GIA THN DIASYNDESH THS KRITIKHS ME TO AXIOTHEATO
ALTER TABLE review
ADD CONSTRAINT FK_Attraction_Review 
FOREIGN KEY (attractionName) REFERENCES attraction(attractionName)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- EISAGWGH XENOU KLEIDIOU GIA THN DIASYNDESH THS KRITIKHS ME TON XRHSTH
ALTER TABLE review
ADD CONSTRAINT FK_User_Review 
FOREIGN KEY (username) REFERENCES user(username)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- EISAGWGH XENOU KLEIDIOU GIA THN DIASYNDESH TOU XRHSTH ME TIS FWTOGRAFIES
ALTER TABLE user
ADD CONSTRAINT FK_User_Picture 
FOREIGN KEY (userProfilePic) REFERENCES picture(pictureID)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- DHMIOURGIA XENOUKLEIDIOU GIA THN DIASYNDESH TOU TYPOU TOU AXIOTHEATOU ME THN FWTOGRAFIA
ALTER TABLE attraction_type
ADD CONSTRAINT FK_AtrrType_Image 
FOREIGN KEY (attrTypeImage) REFERENCES picture(pictureID)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- DHMIOURGIA XENOU KLEIIDIOU GIA THN DIASYNDESH TOU XARTH ME TO AXIOTHEATO
ALTER TABLE map
ADD CONSTRAINT FK_Atrraction_Map 
FOREIGN KEY (attractionName) REFERENCES attraction(attractionName)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- DHMIOURGIA KYRIOU KLEIDIOU GIA TO WRARIO TO OPOIO THA APOTELEITAI APO THN HMERA KAI TO ONOMA TOY AXIOTHEATOU
ALTER TABLE operation_Time
ADD CONSTRAINT PK_operation_Time PRIMARY KEY (dayName,attractionName);

-- DHMIOURGIA XENOU KLEIDIOU GIA THN DIASYNDESH TOU WRARIOU ME TO AXIOTHEATO
ALTER TABLE operation_Time
ADD CONSTRAINT FK_Operates_Attraction
FOREIGN KEY (attractionName) REFERENCES attraction(attractionName)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- DHMIOURGIA XENOU KLEIDIOU GIA THN DIASYNDESH TOU ORARIOU ME THN HMERA
ALTER TABLE operation_Time
ADD CONSTRAINT FK_Operates_Day
FOREIGN KEY (dayName) REFERENCES day(dayName)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- DHMIOURGIA XENOU KLEIDIOU GIA THN DIASYNDESH THS PERIOXHS ME THN PERIFEREIA
ALTER TABLE area
ADD CONSTRAINT FK_Region_Area 
FOREIGN KEY (regionName) REFERENCES region(regionName)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- DHMIOURGIA XENOU KLEIDIOU GIA THN DIASYNDESH THS PERIFEREIAS ME THN XWRA
ALTER TABLE region
ADD CONSTRAINT FK_Region_Country
FOREIGN KEY (countryName) REFERENCES country(countryName)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- DHMIOURGIA XENOU KLEIDIOU GIA THN DIASYNDESH TOU EMAIL ME TO AXIOTHEATO
ALTER TABLE email_attraction
ADD CONSTRAINT FK_Email_Attraction 
FOREIGN KEY (attractionName) REFERENCES attraction(attractionName)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- DHMIOURGIA XENOU KLEIDIOU GIA THN DIASYNDESH TOU THLEFWNOU ME TO AXIOTHEATO
ALTER TABLE telephone_attraction
ADD CONSTRAINT FK_Telephone_Attraction 
FOREIGN KEY (attractionName) REFERENCES attraction(attractionName)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- DHMIOURGIA XENOU KLEIDIOU GIA THN DIASYNDESH TOU AXIOTHEATOU ME THN FWTOGRAFIA
ALTER TABLE attraction
ADD CONSTRAINT FK_Attraction_Picture
FOREIGN KEY (attr_profilePic) REFERENCES picture(pictureID)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- DHMIOURGIA XENOU KLEIDIOU GIA THN DIASYNDESH TOU AXIOTHEATOU ME TON TYPO TOU
ALTER TABLE attraction
ADD CONSTRAINT FK_Attraction_Type 
FOREIGN KEY (attractionType) REFERENCES attraction_type(typeName)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- DHMIOURGIA XENOU KLEIDIOU GIA THN DIASYNDESH TOU AXIOTHEATOU ME THN PERIOXH
ALTER TABLE attraction
ADD CONSTRAINT FK_Attraction_Area 
FOREIGN KEY (areaName) REFERENCES area(areaName)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- DHMIOURGIA XENOU KLEIDIOU GIA THN DIASYNDESH THS FWTOGRAFIAS ME THN KRITIKH
ALTER TABLE picture
ADD CONSTRAINT FK_Picture_Review 
FOREIGN KEY (attractionName,username) REFERENCES review(attractionName,username)
ON DELETE CASCADE
ON UPDATE CASCADE;

INSERT INTO day (dayName) VALUES
    ('Δευτέρα'),
    ('Τρίτη'),
    ('Τετάρτη'),
    ('Πέμπτη'),
    ('Παρασκευή'),
    ('Σάββατο'),
    ('Κυριακή');
    
INSERT INTO country (countryName) VALUES
	('Ελλάδα'),
    ('Ιταλία'),
    ('Ισπανία'),
    ('Γαλλία'),
    ('Αγγλία');
;

INSERT INTO region (regionName,countryName) VALUES
	('Ζακυνθος','Ελλάδα'),
    ('Χαλκιδική','Ελλάδα'),
    ('Άρτα','Ελλάδα'),
    ('Σάμος','Ελλάδα'),
    ('Αττική','Ελλάδα');
;

INSERT INTO area (areaName,regionName) VALUES
	('Αναφωνήτρια','Ζακυνθος'),
    ('Ποσείδι','Χαλκιδική'),
    ('Άρτα','Άρτα'),
    ('Καρλόβασι','Σάμος'),
    ('Αθήνα','Αττική');
;

INSERT INTO attraction_type (typeName) VALUES
	('Μουσεία'),
    ('Κάστρα'),
    ('Αρχαία Ερείπια - Μνημεία'),
    ('Παραλίες'),
    ('Θρησκευτικές Τοποθεσίες');
;

INSERT INTO picture (picture_url) VALUES
	('Photos\\attractionType\\Κάστρα.jpg'),
    ('Photos\\attractionType\\ΑρχαίαΕρείπια.jpg'),
    ('Photos\\attractionType\\ΘρησκευτικέςΤοποθεσίες.jpg'),
    ('Photos\\attractionType\\Παραλίες.jpg'),
    ('Photos\\attractionType\\Μουσεία.jpg');
;
UPDATE attraction_Type
SET attrTypeImage = (
    SELECT pictureID
    FROM picture
    WHERE picture_url = 'Photos\\attractionType\\Κάστρα.jpg'
)
WHERE typeName = 'Κάστρα';

UPDATE attraction_Type
SET attrTypeImage = (
    SELECT pictureID
    FROM picture
    WHERE picture_url = 'Photos\\attractionType\\ΑρχαίαΕρείπια.jpg'
)
WHERE typeName = 'Αρχαία Ερείπια - Μνημεία';

UPDATE attraction_Type
SET attrTypeImage = (
    SELECT pictureID
    FROM picture
    WHERE picture_url = 'Photos\\attractionType\\ΘρησκευτικέςΤοποθεσίες.jpg'
)
WHERE typeName = 'Θρησκευτικές Τοποθεσίες';

UPDATE attraction_Type
SET attrTypeImage = (
    SELECT pictureID
    FROM picture
    WHERE picture_url = 'Photos\\attractionType\\Μουσεία.jpg'
)
WHERE typeName = 'Μουσεία';

UPDATE attraction_Type
SET attrTypeImage = (
    SELECT pictureID
    FROM picture
    WHERE picture_url = 'Photos\\attractionType\\Παραλίες.jpg'
)
WHERE typeName = 'Παραλίες';

INSERT INTO picture (picture_url) VALUES
	('Photos\\user\\user1.jpg');
SET @userProfilePic :=LAST_INSERT_ID();
INSERT INTO user (username,surname,lastname,email,userProfilePic) VALUES
	('user1','Αναστάσης','Πετρόπουλος','petropoulos@gmail.com',@userProfilePic);
    
INSERT INTO picture (picture_url) VALUES
	('Photos\\user\\user3.jpg');
SET @userProfilePic :=LAST_INSERT_ID();
INSERT INTO user (username,surname,lastname,email,userProfilePic) VALUES   
    ('user3','Βαγγέλης','Παπαδόπουλος','papadopoylos@gmail.com',@userProfilePic);
    
INSERT INTO picture (picture_url) VALUES
	('Photos\\user\\user2jpg');
SET @userProfilePic :=LAST_INSERT_ID();
INSERT INTO user (username,surname,lastname,email,userProfilePic) VALUES
    ('user2','Μιχάλης','Γιαννακόπουλος','giannakopoulos@gmail.com',@userProfilePic);
    
INSERT INTO picture (picture_url) VALUES
	('Photos\\user\\user4.jpg');
SET @userProfilePic :=LAST_INSERT_ID();
INSERT INTO user (username,surname,lastname,email,userProfilePic) VALUES
    ('user4','Ελένη','Ιωάννου','ioannou@gmail.com',@userProfilePic);
    
INSERT INTO picture (picture_url) VALUES
	('Photos\\user\\user5.jpg');
SET @userProfilePic :=LAST_INSERT_ID();
INSERT INTO user (username,surname,lastname,email,userProfilePic) VALUES
    ('user5','Βασιλική','Πέτρου','petrou@gmail.com',@userProfilePic);



INSERT INTO picture (picture_url) VALUES
	('Photos\\attraction photos\\Ποσείδι.jpg');
SET @attr_profilePic := LAST_INSERT_ID();
INSERT INTO attraction (attractionName, entrance_fee, website, attractionType, attr_profilePic, areaName) VALUES
	('Παραλία Ποσείδι', 0.0, 'https://gohalkidiki.com/posidi/', 'Παραλίες', @attr_profilePic, 'Ποσείδι');
INSERT INTO map (embeded_Url,attractionName) VALUES
	('<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d12232.271429195527!2d23.364030374643736!3'
    'd39.96223554094451!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x14a7cd5639a3ffb3%3A0x9b6e699e60f9d27d!2'
    'zzqDOsc-BzrHOu86vzrEgzqDOv8-DzrXOr860zrk!5e0!3m2!1sel!2sgr!4v1702301764080!5m2!1sel!2sgr" width="400" height="300"'
    'style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>','Παραλία Ποσείδι');

INSERT INTO picture (picture_url) VALUES
	('Photos\\attraction photos\\Ναυάγιο Ζάκυνθος.jpg');
SET @attr_profilePic := LAST_INSERT_ID();
INSERT INTO attraction (attractionName, entrance_fee,attractionType, attr_profilePic, areaName) VALUES
	("Ναυάγιο", 0.0,'Παραλίες', @attr_profilePic, 'Αναφωνήτρια');
INSERT INTO map (embeded_Url,attractionName) VALUES
	('<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1113.7105495663247!2d20.624052963392906!3d3'
    '7.85914090282156!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x13676b8cc2540ae1%3A0x783ee88b75588aee!2zzqD'
    'Osc-BzrHOu86vzrEgzp3Osc-FzqzOs865zr8!5e0!3m2!1sel!2sgr!4v1702304061791!5m2!1sel!2sgr" width="400" height="300" style'
    '="border:0;"allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>','Ναυάγιο');

INSERT INTO picture (picture_url) VALUES
	('Photos\\attraction photos\\Μικρό σεϊτάνι.jpg');
SET @attr_profilePic := LAST_INSERT_ID();
INSERT INTO attraction (attractionName, entrance_fee, attractionType, attr_profilePic, areaName) VALUES
	("Μικρό Σεϊτάνι", 0.0, 'Παραλίες', @attr_profilePic, 'Καρλόβασι');  
INSERT INTO map (embeded_Url,attractionName) VALUES
	('<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d12614.229464046783!2d26.627311148149516!3d37.7'
    '7697575638339!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x14bc42f0f4bdc5cf%3A0xcef8bb339eea4db7!2zzqDOsc-Bzr'
    'HOu86vzrEgzpzOuc66z4HPjCDOo861z4rPhM6szr3OuQ!5e0!3m2!1sel!2sgr!4v1702304448417!5m2!1sel!2sgr" width="400" height="300"'
    'style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>',"Μικρό Σεϊτάνι");


INSERT INTO picture (picture_url) VALUES
	('Photos\\attraction photos\\ΓεφύριΤηςΆρτας.jpg');
SET @attr_profilePic := LAST_INSERT_ID();
INSERT INTO attraction (attractionName,entrance_fee,website, attractionType, attr_profilePic, areaName) VALUES
	("Γεφύρι της Άρτας", 0.0,'https://discoverarta.gr/en/destinations/historical-bridge-of-arta/','Αρχαία Ερείπια - Μνημεία', @attr_profilePic, 'Άρτα');  
INSERT INTO map (embeded_Url,attractionName) VALUES
	('<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3094.0102749947605!2d20.972010576646873!3d39.151747931655215'
    '!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x135c1b0ccdf0ec05%3A0xb00b80b525db12cf!2zzpPOtc-Gz43Pgc65IM-EzrfPgiDOhs-Bz4TO'
    'sc-C!5e0!3m2!1sel!2sgr!4v1702302526382!5m2!1sel!2sgr" width="400" height="300" style="border:0;" allowfullscreen="" loading="lazy" '
    'referrerpolicy="no-referrer-when-downgrade"></iframe>',"Γεφύρι της Άρτας");

INSERT INTO picture (picture_url) VALUES
	('Photos\\attraction photos\\Παρθενώνας.jpg');
SET @attr_profilePic := LAST_INSERT_ID();
INSERT INTO attraction (attractionName,entrance_fee,website, attractionType, attr_profilePic, areaName) VALUES
	("Παρθενώνας",20,'http://odysseus.culture.gr/h/3/eh355.jsp?obj_id=2384','Αρχαία Ερείπια - Μνημεία', @attr_profilePic, 'Αθήνα');  
INSERT INTO map (embeded_Url,attractionName) VALUES
	('<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3145.2397997151447!2d23.72414167660854!3d37.971532700800495!2m'
    '3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x14a1bd19ca39ee61%3A0x1b3fa079b878a218!2zzqDOsc-BzrjOtc69z47Ovc6xz4I!5e0!3m2!1sel!2'
    'sgr!4v1702303058409!5m2!1sel!2sgr" width="400" height="300" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-re'
    'ferrer-when-downgrade"></iframe>',"Παρθενώνας");
INSERT INTO email_attraction(email,attractionName) VALUES
	('efaath@culture.gr',"Παρθενώνας");
INSERT INTO telephone_attraction(telephone,attractionName) VALUES
	('2103214172',"Παρθενώνας"),
    ('2109238747',"Παρθενώνας");
    

INSERT INTO picture (picture_url) VALUES
	('Photos\\attraction photos\\Μουσείο Ακρόπολης.jpg');
SET @attr_profilePic := LAST_INSERT_ID();
INSERT INTO attraction (attractionName,entrance_fee,website, attractionType, attr_profilePic, areaName) VALUES
	("Μουσείο Ακρόπολης",15,'https://www.theacropolismuseum.gr/','Μουσεία', @attr_profilePic, 'Αθήνα');  
INSERT INTO map (embeded_Url,attractionName) VALUES
	('<iframe src="https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d12581.48750617742!2d23.7285227!3d37.9684499!3m2!1i1024!2i768
!4f13.1!3m3!1m2!1s0x14a1bd173f46e4e1%3A0xb00fb46a2c010a3c!2zzpzOv8-Fz4POtc6vzr8gzpHOus-Bz4zPgM6_zrvOt8-C!5e0!3m2!1sel!2sgr!4v170
2303728375!5m2!1sel!2sgr" width="400" height="300" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer
-when-downgrade"></iframe>',"Μουσείο Ακρόπολης");
INSERT INTO email_attraction(email,attractionName) VALUES
	('info@theacropolismuseum.gr','Μουσείο Ακρόπολης');
INSERT INTO telephone_attraction(telephone,attractionName) VALUES
	('2109000900','Μουσείο Ακρόπολης');

SELECT embeded_Url FROM map WHERE attractionName='Παρθενώνας';

INSERT INTO operation_time (open, close, attractionName, dayName) VALUES  
	("09:00:00", "17:00:00", "Μουσείο Ακρόπολης", "Δευτέρα"),     
	("09:00:00", "17:00:00", "Μουσείο Ακρόπολης", "Τρίτη"),     
	("09:00:00", "17:00:00", "Μουσείο Ακρόπολης", "Τετάρτη"),     
	("09:00:00", "17:00:00", "Μουσείο Ακρόπολης", "Πέμπτη"),     
	("09:00:00", "22:00:00", "Μουσείο Ακρόπολης", "Παρασκευή"),     
	("09:00:00", "20:00:00", "Μουσείο Ακρόπολης", "Σάββατο"),     
	("09:00:00", "20:00:00", "Μουσείο Ακρόπολης", "Κυριακή");

INSERT INTO operation_time (open, close, attractionName, dayName) VALUES  
	('06:00:00', '06:00:00','Παρθενώνας', 'Δευτέρα'),     
	('06:00:00', '06:00:00','Παρθενώνας', 'Τρίτη'),     
	('06:00:00', '06:00:00','Παρθενώνας', 'Τετάρτη'),     
	('06:00:00', '06:00:00','Παρθενώνας', 'Πέμπτη'),     
	('06:00:00', '06:00:00','Παρθενώνας', 'Παρασκευή'),     
	('06:00:00', '06:00:00','Παρθενώνας', 'Σάββατο'),     
	('06:00:00', '06:00:00','Παρθενώνας', 'Κυριακή');
    
INSERT INTO review (rating,attractionName,username) VALUES  
	(4.5,'Παρθενώνας', 'user1');
    
INSERT INTO review (comment,rating,attractionName,username) VALUES  
	('Ιστορία! Φανταστικό μέρος! Πρέπει να δείτε! Χαίρομαι που βλέπουμε την πρόοδο της
ανοικοδόμησης αυτών των όμορφων κτιρίων! Μια ανάμνηση ζωής για κάθε επισκέπτη! Συνιστάται 
ιδιαίτερα να έρθετε και να δείτε αν βρίσκεστε στην Αθήνα. Αγοράστε το εισιτήριό σας ηλεκτρονικά
και ελάτε νωρίς καθώς η ζέστη μπορεί να ανέβει ψηλά και το πλήθος του κόσμου',5,'Παρθενώνας', 'user2');
INSERT INTO picture (picture_url,attractionName,userName) VALUES
	('Photos\\reviewPhotos\\Παρθενώνας1.jpg','Παρθενώνας','user2');
INSERT INTO picture (picture_url,attractionName,userName) VALUES
	('Photos\\reviewPhotos\\Παρθενώνας2.jpg','Παρθενώνας','user2');
    
INSERT INTO review (comment,rating,attractionName,username) VALUES  
	("Το γεφύρι της Άρτας δεσπόζει στον Άραχθο και είναι ορατό από τον περιφερειακό της
πόλης.Είναι προσπελάσιμο και περπατήσιμο από το πλάι, μέσα από ένα πεζόδρομο. Μπορείτε
να βγάλετε φωτογραφία με μέτωπο 3/4 από το παρακείμενο cafe.Είναι μακρύ, εντυπωσιακό, 
και μπορείτε να το περπατήσετε και να νιώσετε όπως οι ταξιδιώτες του 17ου αιώνα, πάνω 
από τις καμάρες, στην λιθόστρωτη επιφάνεια.",5,'Γεφύρι της Άρτας', "user2");
INSERT INTO picture (picture_url,attractionName,userName) VALUES
	('Photos\\reviewPhotos\\Γεφύρι της Άρτας1.jpg','Γεφύρι της Άρτας','user2');
INSERT INTO picture (picture_url,attractionName,userName) VALUES
	('Photos\\reviewPhotos\\Γεφύρι της Άρτας2.jpg','Γεφύρι της Άρτας','user2');

INSERT INTO review (rating,attractionName,username) VALUES  
	(4.5,'Γεφύρι της Άρτας', "user1");

INSERT INTO review (rating,attractionName,username) VALUES  
	(5,'Παραλία Ποσείδι', "user4");
INSERT INTO picture (picture_url,attractionName,userName) VALUES
	('Photos\\reviewPhotos\\Παραλία Ποσείδι1.jpg','Παραλία Ποσείδι','user4');

INSERT INTO review (rating,attractionName,username) VALUES  
	(4.5,'Παραλία Ποσείδι', "user1");

INSERT INTO review (comment,rating,attractionName,username) VALUES  
	('Πολύ όμορφη παραλία!Όποιος διαθέτει αμάξι τύπου τζιπ μπορεί να φθάσει αρκετά
κοντά,κοντά στο φάρο. Εξοπλιστείτε με τα απαραίτητα και θα σας αποζημιώσει το περπάτημα.
 Εχει δυο πλευρές οπότε κάθεσαι ανάλογα με το που φυσάει. Βαθιά για μικρά παιδιά αλλά με
 τον απαραίτητο εξοπλισμό είναι μια ασφαλή επιλογή.',5,'Παραλία Ποσείδι', "user5");
INSERT INTO picture (picture_url,attractionName,userName) VALUES
	('Photos\\reviewPhotos\\Παραλία Ποσείδι2.jpg','Παραλία Ποσείδι','user5');

INSERT INTO review (rating,attractionName,username) VALUES  
	(4.5,'Ναυάγιο', 'user5');
INSERT INTO review (rating,attractionName,username) VALUES  
	(5,'Ναυάγιο','user3');
    
INSERT INTO review (comment,rating,attractionName,username) VALUES  
	('Αξίζει μια επίσκεψη με σκάφος για φωτογραφίες με background το ναυάγιο και
κολύμπι στις μπλε σπηλιες! Προτείνω να γίνει την ώρα του ηλιοβασιλεματος αφού ο ήλιος
δύει ακριβώς απέναντι από την παραλία του ναυαγίου',5,'Ναυάγιο', "user4");
INSERT INTO picture (picture_url,attractionName,userName) VALUES
	('Photos\\reviewPhotos\\Παραλία Ναυάγιο1.jpg','Ναυάγιο','user4');

INSERT INTO review (rating,attractionName,username) VALUES  
	(5,'Μουσείο Ακρόπολης', 'user2');
INSERT INTO review (rating,attractionName,username) VALUES  
	(5,'Μουσείο Ακρόπολης','user5');
INSERT INTO review (comment,rating,attractionName,username) VALUES  
	('Ένα από τα πιο ωραία μουσεία που έχω επισκεφτεί, όχι μόνο για τα εκθέματα του,
αλλά και ως χώρος. Απαραίτητη η επίσκεψη αφού πρώτα δει κανείς την Ακρόπολη των Αθηνών.
Το προσωπικό είναι πολύ εξυπηρετικό, ευγενέστατο και κατατοπιστικό.
Ο χώρος λιτός, μοντέρνος, λειτουργικός και πολύ έξυπνα δομημένος, σου δίνει την ευκαιρία για ένα
ταξίδι στο χρόνο σχετικά με την Ακρόπολη και την Αθήνα γενικότερα.
Αξίζει όσο τίποτα η επίσκεψη. Πραγματικά πρόκειται για ένα μουσείο αντάξιο του χώρου της Ακρόπολης
που εξυπηρετεί.',5,'Μουσείο Ακρόπολης', "user4");
INSERT INTO picture (picture_url,attractionName,userName) VALUES
	('Photos\\reviewPhotos\\Μουσείο Ακρόπολης1.jpg','Μουσείο Ακρόπολης','user4');
INSERT INTO review (comment,rating,attractionName,username) VALUES  
	('Έχω επισκεφτεί το μουσείο περισσότερες από 10 φορές! Κάθε φορά που φιλοξενώ κάποιον συγγενή
θα τον πάω να του δείξω τις ομορφιές της αρχαίας Ελλάδας! Κάθε φορά γνωρίζω και κάτι που την προηγούμενη
δεν είχα προσέξει! Είναι τόσο πλούσια η συλλογή που μια φορά δεν είναι αρκετή! Η θέα από την καφετέρια είναι 
μοναδική!',5,'Μουσείο Ακρόπολης', "user3");
INSERT INTO picture (picture_url,attractionName,userName) VALUES
	('Photos\\reviewPhotos\\Μουσείο Ακρόπολης2.jpg','Μουσείο Ακρόπολης','user3');
    

#TOP 5 AXIOTHEATA
SELECT 
    a.attractionName,
   ROUND(AVG(r.rating), 1) AS avgRating,
    COUNT(r.rating) AS numReviews,
    p.picture_url AS mainPhoto
FROM
    attraction a
JOIN
    review r ON a.attractionName = r.attractionName
JOIN
    picture p ON a.attr_profilePic = p.pictureID
GROUP BY
    a.attractionName
ORDER BY
    avgRating DESC, numReviews DESC
LIMIT 5;

-- EPISTREFEI OLA TA ONOMATA APO TA AXIOTHEATA VASH MIAS XWRAS KATHWS KAI TO MESO ORO VATHMOLOGIAS KAI FWTOGRAFIAS PROFILE
SET @country := 'Ελλάδα'COLLATE utf8mb4_unicode_ci;
SELECT 
    a.attractionName,
    ROUND(AVG(r.rating), 1) AS avgRating,
    COUNT(r.rating) AS numReviews,
    p.picture_url AS mainPhoto
FROM
    attraction a
JOIN
    review r ON a.attractionName = r.attractionName
JOIN
    picture p ON a.attr_profilePic = p.pictureID
JOIN
    area ar ON a.areaName = ar.areaName
JOIN
    region rg ON ar.regionName = rg.regionName
JOIN
    country c ON rg.countryName = c.countryName
WHERE
    c.countryName = @country
GROUP BY
    a.attractionName
ORDER BY
    avgRating DESC, numReviews DESC;
    
-- EPISTREFEI OLA TA ONOMATA APO TA AXIOTHEATA VASH MIAS PERIFEREIAS KATHWS KAI TO MESO ORO VATHMOLOGIAS KAI FWTOGRAFIAS PROFILE
SET @region := 'Αττική' COLLATE utf8mb4_unicode_ci;
SELECT 
    a.attractionName,
    ROUND(AVG(r.rating), 2) AS avgRating,
    COUNT(r.rating) AS numReviews,
    p.picture_url AS mainPhoto
FROM
    attraction a
JOIN
    review r ON a.attractionName = r.attractionName
JOIN
    picture p ON a.attr_profilePic = p.pictureID
JOIN
    area ar ON a.areaName = ar.areaName
JOIN
    region rg ON ar.regionName = rg.regionName
WHERE
    rg.regionName = @region
GROUP BY
    a.attractionName
ORDER BY
    avgRating DESC, numReviews DESC;
    
#EPISTREFEI OLA TA ONOMATA APO TA AXIOTHEATA VASH MIAS PERIOXHS KATHWS KAI TO MESO ORO VATHMOLOGIAS KAI FWTOGRAFIAS PROFILE
SET @area := 'Αθήνα' COLLATE utf8mb4_unicode_ci;
SELECT 
    a.attractionName,
    ROUND(AVG(r.rating), 1) AS avgRating,
    COUNT(r.rating) AS numReviews,
    p.picture_url AS mainPhoto
FROM
    attraction a
JOIN
    review r ON a.attractionName = r.attractionName
JOIN
    picture p ON a.attr_profilePic = p.pictureID
WHERE
    a.areaName = @area
GROUP BY
    a.attractionName
ORDER BY
    avgRating DESC, numReviews DESC;

-- EPISTREFEI TIS XWRES POU EXOUN AXIOTHEATA
SELECT DISTINCT c.countryName
FROM attraction a
JOIN region r ON a.areaName = r.regionName
JOIN country c ON r.countryName = c.countryName;

-- EPISTREFEI TIS PERIFEREIES POU EXOUN AXIOTHEATA
SELECT DISTINCT r.regionName
FROM attraction a
JOIN area ar ON a.areaName = ar.areaName
JOIN region r ON ar.regionName = r.regionName;

-- EPISTREFEI TIS PERIOXES POU EXOUN AXIOTHEATA
SELECT DISTINCT a.areaName
FROM attraction a;

-- EPISTREFEI TOUS TYPOUS AXIOTHEATWN MONO AN UPARXOUN AXIOTHEATA
SELECT DISTINCT at.typeName, p.picture_url
FROM attraction_type at
JOIN attraction a ON at.typeName = a.attractionType
JOIN picture p ON at.attrTypeImage = p.pictureID;


-- EPISTREFEI OLA TA ONOMATA APO TA AXIOTHEATA VASH TOU TYPOU TOUS KATHWS KAI TO MESO ORO VATHMOLOGIAS KAI FWTOGRAFIAS PROFILE
SET @attractionType := 'Παραλίες' COLLATE utf8mb4_unicode_ci;
SELECT 
    a.attractionName,
    ROUND(AVG(r.rating), 1) AS avgRating,
    COUNT(r.rating) AS numReviews,
    p.picture_url AS mainPhoto
FROM
    attraction a
JOIN
    review r ON a.attractionName = r.attractionName
JOIN
    picture p ON a.attr_profilePic = p.pictureID
WHERE
    a.attractionType =  @attractionType
GROUP BY
    a.attractionName
ORDER BY
    avgRating DESC, numReviews DESC;

-- #EPISTREFEI OLA TA ONOMATA APO TA AXIOTHEATA VASH MIAS ENOS ANW ORIOU STO TELOS EISODOU KATHWS KAI TO MESO ORO VATHMOLOGIAS KAI FWTOGRAFIAS PROFILE
SET @maxEntranceFee := 15 COLLATE utf8mb4_unicode_ci;
SELECT 
    a.attractionName,
    a.entrance_fee,
    ROUND(AVG(r.rating), 1) AS avgRating,
    COUNT(r.rating) AS numReviews,
    p.picture_url AS mainPhoto    
FROM
    attraction a
JOIN
    review r ON a.attractionName = r.attractionName
JOIN
    picture p ON a.attr_profilePic = p.pictureID
WHERE
    a.entrance_fee <= @maxEntranceFee
GROUP BY
    a.attractionName
ORDER BY
    avgRating DESC, numReviews DESC;
    
-- EPISTREFEI OLA TA ONOMATA APO TA AXIOTHEATA VASH AN H EISODOS EINAI DWREAN KATHWS KAI TO MESO ORO VATHMOLOGIAS KAI FWTOGRAFIAS PROFILE
SELECT 
    a.attractionName,
    a.entrance_fee,
    ROUND(AVG(r.rating), 1) AS avgRating,
	COUNT(r.rating) AS numReviews,
    p.picture_url AS mainPhoto    
FROM
    attraction a
JOIN
    review r ON a.attractionName = r.attractionName
JOIN
    picture p ON a.attr_profilePic = p.pictureID
WHERE
    a.entrance_fee = 0 
GROUP BY
    a.attractionName
ORDER BY
    avgRating DESC, numReviews DESC;
    
-- EPISTREFEI VASH TOU ONOMATOS TOU AXIOTHEATOU TA PARAKATW, TO ONOMA ,TO TELOS EISODOU,THN ISTOSELIDA,TO ONOMA THS PERIOXHS,TO ONOMA THS PERIFEREIAS,TO ONOMA THS XWRAS
-- TON MESO ORO VATHMOLOGIAS, TO SYNOLO TWN KRITIKWN,THN FWTOGRAFIA PROFILE,TO IFRAME GIA TO GOOGLE MAP
SET @attractionName := 'Μουσείο Ακρόπολης' COLLATE utf8mb4_unicode_ci;
SELECT 
    a.attractionName,
    a.entrance_fee,
    a.website,
    a.attractionType,
    a.areaName,
    rg.regionName,
    c.countryName,
    ROUND(AVG(r.rating), 1) AS avgRating,
    COUNT(r.rating) AS numReviews,
    p.picture_url AS mainPhoto,
    m.embeded_Url AS mapIframe
FROM
    attraction a
JOIN
    review r ON a.attractionName = r.attractionName
JOIN
    picture p ON a.attr_profilePic = p.pictureID
JOIN
    area ar ON a.areaName = ar.areaName
JOIN
    region rg ON ar.regionName = rg.regionName
JOIN
    country c ON rg.countryName = c.countryName
JOIN
    attraction_type at ON a.attractionType = at.typeName
JOIN 
	map m ON a.attractionName = m.attractionName
WHERE
    a.attractionName = @attractionName 
GROUP BY
    a.attractionName,
    m.embeded_Url
ORDER BY
    avgRating DESC, numReviews DESC;
    
-- EPISTREFEI OLES TIS FWTOGRAFIES APO OLES TIS KRITIKES GIA ENA AXIOTHEATO KATHWS KAI TO ONOMA THS EFARMOGHS TOU XRHSTH POU TIS ANEVASE 
SELECT picture_url ,username AS uploadFrom
FROM picture
WHERE (attractionName, username) IN (
    SELECT attractionName, username
    FROM review
    WHERE attractionName = @attractionName
);

-- EPISTREFEI TIS 3 POIO PROSFATES KRITIKES ENOS AXIOTHEATOY
SELECT
	r.username AS ReviewerUsername,
    p.picture_url AS ReviewerPicture,
    r.createdAt,
    r.comment,
    r.rating
FROM
    review r
JOIN user u ON u.username = r.username
JOIN picture p ON p.pictureID = u.userProfilePic
WHERE
    r.attractionName = @attractionName
ORDER BY
    r.createdAt DESC
LIMIT 3;

-- EPISTREFEI TIS FWTOGRAFIES MIAS KRITIKHS
SET @attractionName := 'Γεφύρι της Άρτας' COLLATE utf8mb4_unicode_ci;
SET @userName := 'user2' COLLATE utf8mb4_unicode_ci;
SELECT picture_url
FROM review r
JOIN picture p ON r.username=p.username && r.attractionName=p.attractionName
WHERE(r.username=@userName && r.attractionName= @attractionName);

-- EPISTREFEI TO WRARIO LEITOURGIAS
SET @attractionName := 'Μουσείο Ακρόπολης' COLLATE utf8mb4_unicode_ci;
SELECT 
	o.open AS opensAt,
    o.close AS closingAt,
    o.dayName AS day
FROM attraction a
JOIN operation_time o ON a.attractionName = o.attractionName
WHERE (a.attractionName= @attractionName);

-- EPISTREFEI TO H TA EMAIL POU EXEI ENA AXIOTHEATO
SET @attractionName := 'Παρθενώνας' COLLATE utf8mb4_unicode_ci;
SELECT e.email
FROM attraction a
JOIN email_attraction e ON a.attractionName= e.attractionName
WHERE (a.attractionName=@attractionName);
	
-- EPISTREFEI TO H TA THLEFWNA POU EXEI ENA AXIOTHEATO
SET @attractionName := 'Παρθενώνας' COLLATE utf8mb4_unicode_ci;
SELECT telephone
FROM telephone_attraction
WHERE attractionName = (
	SELECT attractionName
    FROM attraction
    WHERE(attractionName=@attractionName));

-- EMFANISH OLWN TWN KRITIKWN ENOS AXIOTHEATOU MAZI KAI THN FWTOGRAFIA PROFIL TOU KATHE XRHSTH
SET @userName := 'user2' COLLATE utf8mb4_unicode_ci;
SELECT r.*,p.picture_url
FROM attraction a
JOIN review r ON a.attractionName = r.attractionName
JOIN user u ON u.username = r.username
JOIN picture p ON p.pictureID = u.userProfilePic
WHERE r.attractionName = @attractionName;

-- EPISTROFH OLWN TWN KRITIKWN POU EXEI KANEI ENAS XRHSTHS
SET @userName := 'user2' COLLATE utf8mb4_unicode_ci;
SELECT 
	r.attractionName,
    r.rating,
    r.comment,
    r.createdAt
FROM user u
JOIN review r ON u.username = r.username
WHERE (u.username = @userName);

-- EPISTROFH TOU PROFILE TOU XRHSTH ME THN FWTOGRAFIA PROFILE KAI XWRIS TO EMAIL
SELECT 
	u.username,
    u.surname,
    u.lastname,
    p.picture_url
FROM user u
JOIN picture p ON u.userProfilePic = p.pictureID
WHERE ( u.username=@userName);
    