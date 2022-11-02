DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS dishe;
DROP TABLE IF EXISTS restaurant;

DROP SEQUENCE IF EXISTS global_seq;
CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id         INTEGER DEFAULT nextval('global_seq') PRIMARY KEY,
    name       VARCHAR                           NOT NULL,
    email      VARCHAR                           NOT NULL,
    password   VARCHAR                           NOT NULL,
    enabled    BOOLEAN             DEFAULT TRUE  NOT NULL,
    registered TIMESTAMP           DEFAULT now() NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE dishe
(
    id        INTEGER DEFAULT nextval('global_seq') PRIMARY KEY,
    name      VARCHAR                           NOT NULL,
    date_time TIMESTAMP           DEFAULT now() NOT NULL
);
CREATE UNIQUE INDEX dishes_unique_datetime_idx ON dishe (date_time);

CREATE TABLE restaurant
(
    id        INTEGER DEFAULT nextval('global_seq') PRIMARY KEY,
    name      VARCHAR                           NOT NULL,
    date_time TIMESTAMP           DEFAULT now() NOT NULL
);
CREATE UNIQUE INDEX restaurant_unique_datetime_idx ON restaurant (date_time);

CREATE TABLE vote
(
    id            INTEGER DEFAULT nextval('global_seq') PRIMARY KEY,
    restaurant_id INTEGER                           NOT NULL,
    date_time     TIMESTAMP           DEFAULT now() NOT NULL,
    user_id       INTEGER                           NOT NULL
);
CREATE UNIQUE INDEX votes_unique_user_datetime_idx ON vote (user_id, date_time);

CREATE TABLE menus
(
    id            INTEGER DEFAULT nextval('global_seq') PRIMARY KEY,
    price         DOUBLE PRECISION                  NOT NULL,
    dish_id       INTEGER                           NOT NULL,
    restaurant_id INTEGER                           NOT NULL,
    date_time     TIMESTAMP           DEFAULT now() NOT NULL
);
CREATE UNIQUE INDEX menu_unique_restaurant_dish_datetime_idx ON menus (restaurant_id, dish_id, date_time);


