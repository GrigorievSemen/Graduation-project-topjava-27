DELETE
FROM menu;
DELETE
FROM user_roles;
DELETE
FROM vote;
DELETE
FROM users;
DELETE
FROM dish;
DELETE
FROM restaurant;

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@yandex.com', 'admin'), -- 0
       ('User1', 'user1@yandex.ru', 'user1'),  -- 1
       ('User2', 'user2@yandex.com', 'user2'), -- 2
       ('User3', 'user3@yandex.com', 'user3'); -- 3

INSERT INTO user_roles (role, user_id)
VALUES ('ADMIN', 1),
       ('USER', 2),
       ('USER', 3),
       ('USER', 4);

INSERT INTO dish (name)
VALUES ('Chicken'), -- 1
       ('Salad'),   -- 2
       ('Potato'),  -- 3
       ('Pasta'),   -- 4
       ('Soap'),    -- 5
       ('Juice'),   -- 6
       ('Tea'),     -- 7
       ('Coffee'); -- 8

INSERT INTO restaurant (name)
VALUES ('By sea'),      -- 1
       ('Fairy tale'),  -- 2
       ('Caucasus'),    -- 3
       ('Friday 13th'), -- 4
       ('Friends'); -- 5

INSERT INTO vote (user_id, restaurant_id)
VALUES (2, 1),
       (3, 3),
       (4, 4);

INSERT INTO menu(restaurant_id, dish_id, price)
VALUES (1, 1, 200),
       (1, 2, 159.99),
       (1, 8, 100),

       (2, 3, 160),
       (2, 2, 140.5),
       (2, 7, 50),
       (2, 8, 120),

       (3, 3, 240.99),
       (3, 4, 365),
       (3, 6, 150),

       (4, 4, 299.5),
       (4, 7, 70),

       (5, 1, 180),
       (5, 2, 175),
       (5, 3, 199.99),
       (5, 6, 90),
       (5, 8, 140);



