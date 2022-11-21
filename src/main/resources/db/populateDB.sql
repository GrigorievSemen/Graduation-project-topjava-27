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
VALUES ('Admin', 'admin@yandex.com', '$2a$10$9jxLMntUjcBgM4Rb.bMAzO3.nrd2ChpdAnVQdblF8gfYYPxl.UVc2'), -- 0 password admin
       ('User1', 'user1@yandex.ru', '$2a$10$.NUmdJBMsx4OzbxZPnPpfeYSCw9kiek3lhvjp9nCjxugVG0ioVwf.'),  -- 1 password test1
       ('User2', 'user2@yandex.com', '$2a$10$/yRivUr0N8X23Uz1ASSNxOrCzupdmBdO0o.xBp4GQKoLeli1vDcXi'), -- 2 password test2
       ('User3', 'user3@yandex.com', '$2a$10$OnGR.07a9JJxEdpKrHcU0OY1voZu//GW7IJ4vP1qp.lv8Ik3.15ee'); -- 3 password test3

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



