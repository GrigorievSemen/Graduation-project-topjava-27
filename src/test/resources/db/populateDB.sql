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
VALUES ('Admin', 'admin@yandex.com',
        '$2a$10$9jxLMntUjcBgM4Rb.bMAzO3.nrd2ChpdAnVQdblF8gfYYPxl.UVc2'), -- 1 password admin
       ('User1', 'user1@yandex.com',
        '$2a$10$.NUmdJBMsx4OzbxZPnPpfeYSCw9kiek3lhvjp9nCjxugVG0ioVwf.'), -- 2 password test1
       ('User2', 'user2@yandex.com',
        '$2a$10$/yRivUr0N8X23Uz1ASSNxOrCzupdmBdO0o.xBp4GQKoLeli1vDcXi'), -- 3 password test2
       ('User3', 'user3@yandex.com',
        '$2a$10$OnGR.07a9JJxEdpKrHcU0OY1voZu//GW7IJ4vP1qp.lv8Ik3.15ee'); -- 4 password test3

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_ADMIN', 1),
       ('ROLE_USER', 2),
       ('ROLE_USER', 3),
       ('ROLE_USER', 4);

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
       (3, 3);

INSERT INTO menu(restaurant_id, dish_id, price, dat)
VALUES (1, 1, 200, now()),
       (1, 2, 159.99, now()),
       (1, 8, 100, now()),

       (2, 3, 160, now()),
       (2, 2, 140.5, now()),
       (2, 7, 50, now()),
       (2, 8, 120, now()),

       (3, 3, 240.99, now()),
       (3, 4, 365, now()),
       (3, 6, 150, now()),

       (4, 4, 299.5, now()),
       (4, 7, 70, now()),

       (5, 1, 180, now()),
       (5, 2, 175, now()),
       (5, 3, 199.99, now()),
       (5, 6, 90, now()),
       (5, 8, 140, now())



