CREATE DATABASE edishtv_db;

USE edishtv_db;


CREATE TABLE admin (
	id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(25) NOT NULL UNIQUE,
    password VARCHAR(25)
);

INSERT INTO admin (email, password) VALUES
('admin@iiht.com', 'iiht@1234'),
('admin@cts.com', 'cts@5678');

SELECT * FROM admin;

CREATE TABLE user (
	id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(25) NOT NULL UNIQUE,
    password VARCHAR(25),
    first_name VARCHAR(25),
    last_name VARCHAR(25)
);

CREATE TABLE wallet (
	user_id INT UNIQUE,
    amount INT NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE channel (
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(25) UNIQUE,
    channel_number INT NOT NULL UNIQUE,
    language VARCHAR(15),
    description VARCHAR(50),
    monthly_subscription_fee INT
);

CREATE TABLE subscription (
	id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    channel_id INT,
    cost INT,
    start_date DATE,
    expiry_date DATE,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (channel_id) REFERENCES channel(id)
);

CREATE TABLE transaction (
	id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    channel_id INT,
    price INT,
    transaction_date DATE,
    start_date DATE,
    expiry_date DATE,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (channel_id) REFERENCES channel(id)
);

#SELECT * FROM User;
#SELECT * FROM wallet;
#SELECT * FROM channel;
#SELECT * FROM subscription;