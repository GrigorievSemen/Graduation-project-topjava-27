DROP TABLE IF EXISTS menu;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS dish;
DROP TABLE IF EXISTS restaurant;

CREATE TABLE users
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name       VARCHAR(255) UNIQUE           NOT NULL,
    email      VARCHAR(255) UNIQUE           NOT NULL,
    password   VARCHAR(255)                  NOT NULL,
    enabled    BOOLEAN      DEFAULT TRUE     NOT NULL,
    created_at TIMESTAMP    DEFAULT now()    NOT NULL,
    updated_at TIMESTAMP    DEFAULT now()    NOT NULL,
    status     VARCHAR(255) DEFAULT 'ACTIVE' NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER      NOT NULL,
    role    VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX user_roles_idx ON user_roles (user_id);


CREATE TABLE dish
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name       VARCHAR(255) UNIQUE           NOT NULL,
    created_at TIMESTAMP    DEFAULT now()    NOT NULL,
    updated_at TIMESTAMP    DEFAULT now()    NOT NULL,
    status     VARCHAR(255) DEFAULT 'ACTIVE' NOT NULL
);
CREATE UNIQUE INDEX dish_unique_name_idx ON dish (name);

CREATE TABLE restaurant
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name       VARCHAR(255) UNIQUE           NOT NULL,
    created_at TIMESTAMP    DEFAULT now()    NOT NULL,
    updated_at TIMESTAMP    DEFAULT now()    NOT NULL,
    status     VARCHAR(255) DEFAULT 'ACTIVE' NOT NULL
);
CREATE UNIQUE INDEX restaurant_unique_name_idx ON restaurant (name);

CREATE TABLE vote
(
    id            INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id       INTEGER                       NOT NULL,
    restaurant_id INTEGER                       NOT NULL,
    created_at    TIMESTAMP    DEFAULT now()    NOT NULL,
    updated_at    TIMESTAMP    DEFAULT now()    NOT NULL,
    status        VARCHAR(255) DEFAULT 'ACTIVE' NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX vote_unique_user_idx ON vote (user_id);

CREATE TABLE menu
(
    id            INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    restaurant_id INTEGER                       NOT NULL,
    dish_id       INTEGER                       NOT NULL,
    price         DOUBLE PRECISION              NOT NULL,
    created_at    TIMESTAMP    DEFAULT now()    NOT NULL,
    updated_at    TIMESTAMP    DEFAULT now()    NOT NULL,
    status        VARCHAR(255) DEFAULT 'ACTIVE' NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX menu_unique_restaurant_dish_idx ON menu (restaurant_id, dish_id);


