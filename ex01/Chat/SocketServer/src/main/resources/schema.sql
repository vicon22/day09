DROP TABLE users;
CREATE TABLE users(

id SERIAL,
name varchar(50) NOT NULL,
password varchar(250) NOT NULL
);

DROP TABLE messages;
CREATE TABLE messages(

    id SERIAL,
    text varchar(250) NOT NULL,
    timestamp TIMESTAMP NOT NULL
);

