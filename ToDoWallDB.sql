DROP DATABASE IF EXISTS ToDoWall;
CREATE DATABASE ToDoWall
CHARACTER SET utf8
DEFAULT CHARACTER SET utf8
COLLATE utf8_general_ci
DEFAULT COLLATE utf8_general_ci;

USE ToDoWall;

DROP TABLE IF EXISTS usercredentials;

CREATE TABLE usercredentials(
    username VARCHAR(50)  PRIMARY KEY,
    password VARCHAR(500)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE = utf8_general_ci;


DROP TABLE IF EXISTS savedjson;

CREATE TABLE savedjson(
    username VARCHAR(50)  PRIMARY KEY,
    saveddata  TEXT
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE = utf8_general_ci;


grant all privileges on ToDoWall.* to 'talosoft'@'localhost' identified by 'talos1029384756' with grant option;