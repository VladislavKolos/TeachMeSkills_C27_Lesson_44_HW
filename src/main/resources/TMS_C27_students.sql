CREATE TABLE "students" (
	id SERIAL PRIMARY KEY,
	name VARCHAR(32) NOT NULL,
	email VARCHAR(256) NOT NULL UNIQUE,
	group VARCHAR(50) NOT NULL
);