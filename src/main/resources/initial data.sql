CREATE TABLE item (
 id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
 itemName VARCHAR(255) NOT NULL,
 itemDescription VARCHAR(255) NOT NULL,
 price VARCHAR(255) NOT NULL,
 availability BOOLEAN NOT NULL,
 PRIMARY KEY (id)
);

CREATE TABLE attachment (
 id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
 filename VARCHAR(255) DEFAULT NULL,
 content_type VARCHAR(255) DEFAULT NULL,
 content BLOB DEFAULT NULL,
 item_id INTEGER DEFAULT NULL,
 PRIMARY KEY (id),
 FOREIGN KEY (item_id) REFERENCES item(id)
);

CREATE TABLE comment (
 id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
 content VARCHAR(255) NOT NULL,
 item_id INTEGER DEFAULT NULL,
 PRIMARY KEY (id),
 FOREIGN KEY (item_id) REFERENCES item(id)
);

CREATE TABLE users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    fullname VARCHAR(50) NOT NULL,
    phoneNumber VARCHAR(50) ,
    deliveryAddress VARCHAR(50),
    PRIMARY KEY (username)
);

CREATE TABLE user_roles (
    user_role_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL ,
    PRIMARY KEY (user_role_id),
    FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE user_orders (
    user_orders_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    item_id INTEGER NOT NULL ,
    checkout_date VARCHAR(50),
    PRIMARY KEY (user_orders_id)
);
--username and password--
INSERT INTO users VALUES ('admin', '{noop}adminpw','Keith','98765432','OUHK');
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_ADMIN');

INSERT INTO users VALUES ('user', '{noop}userpw','Kevin','23456789','OUHK');
INSERT INTO user_roles(username, role) VALUES ('user', 'ROLE_USER');

--Item--
INSERT INTO item(itemName, itemDescription,price,availability) VALUES ('Hamburger','From McDonald','30','true');
INSERT INTO item(itemName, itemDescription,price,availability) VALUES ('Fried Chicken','From KFC','50','true');
INSERT INTO item(itemName, itemDescription,price,availability) VALUES ('Hamburger','From Burger King','40','false');

--Comment--
INSERT INTO comment(content, item_id) VALUES ('decent burger 6/10', 1);
INSERT INTO comment(content, item_id) VALUES ('delicious and cheap',1);
INSERT INTO comment(content, item_id) VALUES ('dont worth that much', 2);
INSERT INTO comment(content, item_id) VALUES ('not available ',3);