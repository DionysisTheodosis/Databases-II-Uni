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

CREATE TABLE area (
    areaName VARCHAR(60) NOT NULL PRIMARY KEY,
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
    areaName VARCHAR(60) NOT NULL
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

ALTER TABLE area
ADD CONSTRAINT FK_County_Area 
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
ADD CONSTRAINT FK_Attraction_Area 
FOREIGN KEY (areaName) REFERENCES area(areaName)
ON DELETE CASCADE
ON UPDATE CASCADE;

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

INSERT INTO county (countyName,countryName) VALUES
	('Ζακυνθος','Ελλάδα'),
    ('Χαλκιδική','Ελλάδα'),
    ('Άρτα','Ελλάδα'),
    ('Σάμος','Ελλάδα'),
    ('Αττική','Ελλάδα');
;

INSERT INTO area (areaName,countyName) VALUES
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
	('Photos\\user\\user2.jpg');
SET @userProfilePic :=LAST_INSERT_ID();
INSERT INTO user (username,surname,lastname,email,userProfilePic) VALUES   
    ('user3','Βαγγέλης','Παπαδόπουλος','papadopoylos@gmail.com',@userProfilePic);
INSERT INTO picture (picture_url) VALUES
	('Photos\\user\\user3.jpg');
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
	('<iframe src="https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d12581.48750617742!2d23.7285227!3d37.9684499!3m2!1i1024!2i768'
    '!4f13.1!3m3!1m2!1s0x14a1bd173f46e4e1%3A0xb00fb46a2c010a3c!2zzpzOv8-Fz4POtc6vzr8gzpHOus-Bz4zPgM6_zrvOt8-C!5e0!3m2!1sel!2sgr!4v170'
    '2303728375!5m2!1sel!2sgr" width="400" height="300" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer'
    '-when-downgrade"></iframe>',"Μουσείο Ακρόπολης");
INSERT INTO email_attraction(email,attractionName) VALUES
	('info@theacropolismuseum.gr',"Μουσείο Ακρόπολης");
INSERT INTO telephone_attraction(telephone,attractionName) VALUES
	('2109000900',"Μουσείο Ακρόπολης");

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
	("06:00:00", "06:00:00",'Παρθενώνας', "Δευτέρα"),     
	("06:00:00", "06:00:00",'Παρθενώνας', "Τρίτη"),     
	("06:00:00", "06:00:00",'Παρθενώνας', "Τετάρτη"),     
	("06:00:00", "06:00:00",'Παρθενώνας', "Πέμπτη"),     
	("06:00:00", "06:00:00",'Παρθενώνας', "Παρασκευή"),     
	("06:00:00", "06:00:00",'Παρθενώνας', "Σάββατο"),     
	("06:00:00", "06:00:00",'Παρθενώνας', "Κυριακή");
    
INSERT INTO review (rating,attractionName,username) VALUES  
	(4.5,'Παρθενώνας', "user1");
    
INSERT INTO review (comment,rating,attractionName,username) VALUES  
	("Ιστορία! Φανταστικό μέρος! Πρέπει να δείτε! Χαίρομαι που βλέπουμε την πρόοδο της ανοικοδόμησης αυτών των όμορφων κτιρίων! Μια ανάμνηση ζωής για κάθε επισκέπτη! Συνιστάται ιδιαίτερα να έρθετε και να δείτε αν βρίσκεστε στην Αθήνα. Αγοράστε το εισιτήριό σας ηλεκτρονικά και ελάτε νωρίς καθώς η ζέστη μπορεί να ανέβει ψηλά και το πλήθος του κόσμου",5,'Παρθενώνας', "user2");
INSERT INTO picture (picture_url,attractionName,userName) VALUES
	('Photos\\reviewPhotos\\Παρθενώνας1.jpg','Παρθενώνας','user2');
INSERT INTO picture (picture_url,attractionName,userName) VALUES
	('Photos\\reviewPhotos\\Παρθενώνας2.jpg','Παρθενώνας','user2');
    
INSERT INTO review (comment,rating,attractionName,username) VALUES  
	("Το γεφύρι της Άρτας δεσπόζει στον Άραχθο και είναι ορατό από τον περιφερειακό της πόλης.Είναι προσπελάσιμο και περπατήσιμο από το πλάι, μέσα από ένα πεζόδρομο. Μπορείτε να βγάλετε φωτογραφία με μέτωπο 3/4 από το παρακείμενο cafe.Είναι μακρύ, εντυπωσιακό, και μπορείτε να το περπατήσετε και να νιώσετε όπως οι ταξιδιώτες του 17ου αιώνα, πάνω από τις καμάρες, στην λιθόστρωτη επιφάνεια.",5,'Γεφύρι της Άρτας', "user2");
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
	('Πολύ όμορφη παραλία!Όποιος διαθέτει αμάξι τύπου τζιπ μπορεί να φθάσει αρκετά κοντά,κοντά στο φάρο. Εξοπλιστείτε με τα απαραίτητα και θα σας αποζημιώσει το περπάτημα. Εχει δυο πλευρές οπότε κάθεσαι ανάλογα με το που φυσάει. Βαθιά για μικρά παιδιά αλλά με τον απαραίτητο εξοπλισμό είναι μια ασφαλή επιλογή.',5,'Παραλία Ποσείδι', "user5");
INSERT INTO picture (picture_url,attractionName,userName) VALUES
	('Photos\\reviewPhotos\\Παραλία Ποσείδι2.jpg','Παραλία Ποσείδι','user5');





