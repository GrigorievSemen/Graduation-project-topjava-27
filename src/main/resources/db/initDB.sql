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
    created_at TIMESTAMP(0) DEFAULT now()    NOT NULL,
    updated_at TIMESTAMP(0) DEFAULT now()    NOT NULL,
    status     VARCHAR(255) DEFAULT 'ACTIVE' NOT NULL
);
CREATE INDEX ON users (name);

CREATE TABLE user_roles
(
    user_id INTEGER      NOT NULL,
    role    VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE INDEX ON user_roles (role);

CREATE TABLE dish
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name       VARCHAR(255) UNIQUE        NOT NULL,
    created_at TIMESTAMP(0) DEFAULT now() NOT NULL,
    updated_at TIMESTAMP(0) DEFAULT now() NOT NULL
);
CREATE INDEX ON dish (name);

CREATE TABLE restaurant
(
    id         INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name       VARCHAR(255) UNIQUE        NOT NULL,
    created_at TIMESTAMP(0) DEFAULT now() NOT NULL,
    updated_at TIMESTAMP(0) DEFAULT now() NOT NULL
);
CREATE INDEX ON restaurant (name);

CREATE TABLE vote
(
    id            INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id       INTEGER                    NOT NULL,
    restaurant_id INTEGER                    NOT NULL,
    created_at    TIMESTAMP(0) DEFAULT now() NOT NULL,
    updated_at    TIMESTAMP(0) DEFAULT now() NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);
CREATE INDEX ON vote (user_id);

CREATE TABLE menu
(
    id            INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    restaurant_id INTEGER                    NOT NULL,
    dish_id       INTEGER                    NOT NULL,
    price         DOUBLE PRECISION           NOT NULL,
    created_at    TIMESTAMP(0) DEFAULT now() NOT NULL,
    updated_at    TIMESTAMP(0) DEFAULT now() NOT NULL,
    day_menu      DATE         DEFAULT now() NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE,
    FOREIGN KEY (dish_id) REFERENCES dish (id) ON DELETE CASCADE
);
CREATE INDEX ON menu (restaurant_id);


