
-- //create schema whatsnew;
-- ////////////////////////////////////////drop table whatsnew.news; drop table whatsnew.business; drop table whatsnew.sport; drop table whatsnew.technology; drop table whatsnew.travel;drop table whatsnew.backupnews; ////////////////////////////////////////



create table whatsnew . news
(
Id					bigint			NOT NULL 	UNIQUE AUTO_INCREMENT,
Title				varchar(250)	NOT NULL,
Description			TEXT			NOT NULL,
Url					varchar(450)	NOT NULL,
ImageUrl			varchar(450)	NOT NULL,
type				varchar(50)		NOT NULL,
NewsType			varchar(20)		NOT NULL,

constraint PK_Users primary key
(
Id
)
);





create table whatsnew . business
(
Id					bigint			NOT NULL 	UNIQUE AUTO_INCREMENT,
Title				varchar(250)	NOT NULL ,
Description			TEXT			NOT NULL,
Url					varchar(450)	NOT NULL,
ImageUrl			varchar(450)	NOT NULL,
type				varchar(50)		NOT NULL,
NewsType			varchar(20)		NOT NULL,


constraint PK_Users primary key
(
Id
)
);






create table whatsnew . sport
(
Id					bigint			NOT NULL 	UNIQUE AUTO_INCREMENT,
Title				varchar(250)	NOT NULL ,
Description			TEXT			NOT NULL,
Url					varchar(450)	NOT NULL,
ImageUrl			varchar(450)	NOT NULL,
type				varchar(50)		NOT NULL,
NewsType			varchar(20)		NOT NULL,


constraint PK_Users primary key
(
Id
)
);







create table whatsnew . technology
(
Id					bigint			NOT NULL 	UNIQUE AUTO_INCREMENT,
Title				varchar(250)	NOT NULL ,
Description			TEXT			NOT NULL,
Url					varchar(450)	NOT NULL,
ImageUrl			TEXT			NOT NULL,
type				varchar(50)		NOT NULL,
NewsType			varchar(20)		NOT NULL,



constraint PK_Users primary key
(
Id
)
);




create table whatsnew . travel
(
Id					bigint			NOT NULL 	UNIQUE AUTO_INCREMENT,
Title				varchar(250)	NOT NULL ,
Description			TEXT			NOT NULL,
Url					varchar(450)	NOT NULL,
ImageUrl			varchar(450)	NOT NULL,
type				varchar(50)		NOT NULL,
NewsType			varchar(20)		NOT NULL,



constraint PK_Users primary key
(
Id
)
);



