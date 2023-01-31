DROP SCHEMA IF EXISTS Checkers;

CREATE SCHEMA Checkers;
USE Checkers;

DROP TABLE IF EXISTS Games;

CREATE TABLE Games(
	id INT UNSIGNED AUTO_INCREMENT,
	enemy ENUM('bot', 'rival') NOT NULL,
	game_type VARCHAR(30) NOT NULL,
	course VARCHAR(1000) NOT NULL,
	winner ENUM('black', 'white', 'tie') NOT NULL,
	PRIMARY KEY(id)
);
