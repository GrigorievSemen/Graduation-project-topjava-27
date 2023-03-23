Курс TopJava27
=
Выпускной проект
=

## Техническое задание

## Техническое задание:

Разработайте и внедрите REST API с использованием Hibernate/Spring/SpringMVC (предпочтительнее Spring-Boot!) **без
Frontend части**.

Задача состоит в том, чтобы:

Создать систему голосования для принятия решения о том, где пообедать.

* 2 типа пользователей: администратор и обычные пользователи
* Администратор может ввести ресторан и его обеденное меню на выбранный день (обычно 2-5 блюд, просто название блюда и
  стоимость).
* Меню меняется каждый день (администраторы делают обновления)
* Пользователи могут проголосовать за ресторан, в котором они хотят пообедать сегодня
* Для каждого пользователя учитывается только один голос
* Если пользователь проголосует снова в тот же день то:
  - Если это произойдет до 11:00, мы предполагаем, что он передумал.
  - Если это после 11:00, то уже слишком поздно, голосование изменить нельзя

Каждый ресторан предлагает новое меню каждый день.

В результате предоставьте ссылку на репозиторий github. Он должен содержать код, README.md с документацией API и парой
команд curl для его тестирования (**лучше - ссылка на Swagger**).

-----------------------------
P.S.: Убедитесь, что все работает с последней версией, которая есть на github :)
P.P.S.: Предположим, что ваш API будет использоваться разработчиком интерфейса для создания интерфейса поверх этого.

## Сборка и запуск

Проект собирается из корня командой:

```bash
mvn clean install
```

После сборки проекта, запуск осуществляется командой:

```bash
java -jar target/project-top-java-27.jar
```
---

## Работа с приложением

Для локального запуска доступен Swagger по ссылке [http://localhost:8080/app/swagger-ui/](http://localhost:8080).

Для локального запуска доступна консоль H2 по ссылке [http://localhost:8080/app/h2-console/](http://localhost:8080/app/h2-console/).

### Дополнения к ТЗ.
В приложении реализована аутентификация на основе JWT токена, полученный токен после аутентификации, необходимо добавлять к каждому запросу

Пример ответа с токеном после аутентификации
```bash
{
"name": "Admin",
"token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNjc5NTg2MzM1LCJleHAiOjE2Nzk2MjIzMzV9._BJUUZBqSrqN0TnlHugJdwElffrsdj5rmCmrDuM30To"
}
```
Полученный токен необходимо добавлять с добавкой "Bearer_" в начале, пример/скрин ниже
![img.png](img.png)

Обычные пользователи не могут получать/обновлять данные чужого пользователя.

Обычным пользователя доступна: регистрация, логирование, получение списка меню, голосование.

Если админ делает обновление меню текущего дня, то все голоса с этого меню (ресторана) обнуляются.

Для удобства, список ресторанов с меню и голосами выводится строками.


## Примеры команд Postman

### Регистрация
ссылка - http://localhost:8080/app/api/v1/auth/registered

метод POST

Пример запроса 
```bash
{
"name": "User4",
"email": "user4@yandex.com",
"password": "test4"
}
```
Пример ответа
```bash
{
    "id": 5,
    "name": "User4",
    "email": "user4@yandex.com",
    "roles": [
        "ROLE_USER"
    ],
    "created_at": "23.03.2023 23:04",
    "updated_at": "23.03.2023 23:04"
}
```
---

### Аутентификация
ссылка - http://localhost:8080/app/api/v1/auth/login

метод POST

Пример запроса для Админа
```bash
{
"name": "Admin",
"password": "admin"
}
```
Пример запроса для Пользователя
```bash
{
"name": "User3",
"password": "test3"
}
```
### Методы работы с данными пользователя
Получить пользователя по id.

метод GET

могут использовать - Админ, Пользователь

ссылка - http://localhost:8080/app/api/v1/users/

Пример запроса в браузерной строке
```bash
http://localhost:8080/app/api/v1/users/2
```
Пример ответа
```bash
{
    "id": 2,
    "name": "User1",
    "email": "user1@yandex.com",
    "roles": [
        "ROLE_USER"
    ],
    "created_at": "23.03.2023 22:43",
    "updated_at": "23.03.2023 22:43"
}
```
---
Получить пользователя по name.

метод GET

могут использовать - Админ, Пользователь

ссылка - http://localhost:8080/app/api/v1/users/

Пример запроса в браузерной строке
```bash
http://localhost:8080/app/api/v1/users/?name=User1
```
Пример ответа
```bash
{
    "id": 2,
    "name": "User1",
    "email": "user1@yandex.com",
    "roles": [
        "ROLE_USER"
    ],
    "created_at": "23.03.2023 22:43",
    "updated_at": "23.03.2023 22:43"
}
```
---
Получить всех пользователей.

метод GET

могут использовать - Админ

ссылка - http://localhost:8080/app/api/v1/users/all

Пример запроса в браузерной строке
```bash
http://localhost:8080/app/api/v1/users/all
```
Пример ответа
```bash
[
    {
        "id": 1,
        "name": "Admin",
        "email": "admin@yandex.com",
        "roles": [
            "ROLE_ADMIN"
        ],
        "created_at": "23.03.2023 22:43",
        "updated_at": "23.03.2023 22:43"
    },
    {
        "id": 2,
        "name": "User1",
        "email": "user1@yandex.com",
```
---
Обновить данные пользователя.

метод POST

могут использовать - Админ,Пользователь

ссылка - http://localhost:8080/app/api/v1/users/update

Пример запроса
```bash
{
"old_name": "User1",
"old_password": "test1",
"new_name": "User7",
"new_password": "test7",
"email": "user1@yandex.com"
}
```
Пример ответа
```bash
{
    "id": 2,
    "name": "User7",
    "email": "user1@yandex.com",
    "roles": [
        "ROLE_USER"
    ],
    "created_at": "23.03.2023 22:43",
    "updated_at": "23.03.2023 23:45"
}
```
---
Удаление данных пользователя.

метод POST

могут использовать - Админ,Пользователь

ссылка - http://localhost:8080/app/api/v1/users/delete

Пример запроса
```bash
{
"id": 3,
"name": "User3",
"password": "test3"
}
```
Пример ответа
```bash
User User3 successfully deleted.
```
---
### Методы работы с едой
Сохранение новой еды.

метод POST

могут использовать - Админ

ссылка - http://localhost:8080/app/api/v1/admin/dish/save

Пример запроса
```bash
{
"name": "NewDish"
}
```
Пример ответа
```bash
{
    "name": "NewDish",
    "id": 9
}
```
---
Изменение еды.

метод POST

могут использовать - Админ

ссылка - http://localhost:8080/app/api/v1/admin/dish/update

Пример запроса
```bash
{
  "old_name": "Salad",
  "new_name": "NewSalad"
}
```
Пример ответа
```bash
{
    "name": "NewSalad",
    "id": 2
}
```
---
Получение конкретной еды по id.

метод GET

могут использовать - Админ

ссылка - http://localhost:8080/app/api/v1/admin/dish

Пример запроса в браузерной строке
```bash
http://localhost:8080/app/api/v1/admin/dish/1
```
Пример ответа
```bash
{
    "name": "Chicken",
    "id": 1
}
```
---
Получение конкретной еды по name.

метод GET

могут использовать - Админ

ссылка - http://localhost:8080/app/api/v1/admin/dish

Пример запроса в браузерной строке
```bash
http://localhost:8080/app/api/v1/admin/dish/?name=Chicken
```
Пример ответа
```bash
{
    "name": "Chicken",
    "id": 1
}
```
---
Получение всего списка еды.

метод GET

могут использовать - Админ

ссылка - http://localhost:8080/app/api/v1/admin/dish/all

Пример запроса в браузерной строке
```bash
http://localhost:8080/app/api/v1/admin/dish/all
```
Пример ответа
```bash
[
    {
        "name": "Chicken",
        "id": 1
    },
    {
        "name": "Coffee",
        "id": 8
    },
    {
        "name": "Juice",
        "id": 6
    },
```
---
Удаление еды по id.

метод POST

могут использовать - Админ

ссылка - http://localhost:8080/app/api/v1/admin/dish/delete/

Пример запроса в браузерной строке
```bash
http://localhost:8080/app/api/v1/admin/dish/delete/1
```
Пример ответа
```bash
Dish with id: 1, successfully deleted.
```
---
### Методы работы с рестораном
Сохранение новой ресторана.

метод POST

могут использовать - Админ

ссылка - http://localhost:8080/app/api/v1/admin/restaurants/save

Пример запроса
```bash
{
"name": "newRestaurant"
}
```
Пример ответа
```bash
{
    "name": "newRestaurant",
    "id": 6
}
```
---
Изменение ресторана.

метод POST

могут использовать - Админ

ссылка - http://localhost:8080/app/api/v1/admin/restaurants/update

Пример запроса
```bash
{
"old_name": "By sea",
"new_name": "By puddle"
}
```
Пример ответа
```bash
{
    "name": "By puddle",
    "id": 1
}
```
---
Получение конкретного ресторана по id.

метод GET

могут использовать - Админ

ссылка - http://localhost:8080/app/api/v1/admin/restaurants

Пример запроса в браузерной строке
```bash
http://localhost:8080/app/api/v1/admin/restaurants/3
```
Пример ответа
```bash
{
    "name": "Caucasus",
    "id": 3
}
```
---
Получение конкретного ресторана по name.

метод GET

могут использовать - Админ

ссылка - http://localhost:8080/app/api/v1/admin/restaurants

Пример запроса в браузерной строке
```bash
http://localhost:8080/app/api/v1/admin/dish/?name=Caucasus
```
Пример ответа
```bash
{
    "name": "Caucasus",
    "id": 3
}
```
---
Получение всего списка ресторанов.

метод GET

могут использовать - Админ

ссылка - http://localhost:8080/app/api/v1/admin/restaurants/all

Пример запроса в браузерной строке
```bash
http://localhost:8080/app/api/v1/admin/restaurants/all
```
Пример ответа
```bash
[
    {
        "name": "By puddle",
        "id": 1
    },
    {
        "name": "Caucasus",
        "id": 3
    },
    {
        "name": "Fairy tale",
        "id": 2
    },
```
---
Удаление ресторана по id.

метод POST

могут использовать - Админ

ссылка - http://localhost:8080/app/api/v1/admin/restaurants/delete

Пример запроса в браузерной строке
```bash
http://localhost:8080/app/api/v1/admin/restaurants/delete/1
```
Пример ответа
```bash
Restaurant with id: 1, successfully deleted.
```
---
### Методы работы с меню
Сохранение нового меню.

метод POST

могут использовать - Админ

ссылка - http://localhost:8080/app/api/v1/admin/menu/save

Пример запроса
```bash
[
  {
"restaurantId": "1",
"dishId": "1",
"price": "51",
"dayMenu": "2023-03-13"
  },
  {
"restaurantId": "2",
"dishId": "2",
"price": "66",
"dayMenu": "2023-03-13"
  }
 ]
```
Пример ответа
```bash
[
    {
        "id": 18,
        "restaurant": {
            "name": "By sea",
            "id": 1
        },
        "dish": {
            "name": "Chicken",
            "id": 1
        },
        "price": 51.0,
        "dayMenu": "2023-03-13"
    },
```
---
Изменение меню.

метод POST

могут использовать - Админ

ссылка - http://localhost:8080/app/api/v1/admin/menu/update

Пример запроса
```bash
[
 {
 "id": "1",
"restaurant_id": "1",
"dish_id": "1",
"price": "300"
 },
 {
"id": "17",
"restaurant_id": "2",
"dish_id": "2",
"price": "200"
 }
 ]
```
Пример ответа
```bash
[
    {
        "id": 1,
        "restaurant": {
            "name": "By sea",
            "id": 1
        },
        "dish": {
            "name": "Chicken",
            "id": 1
        },
        "price": 300.0,
        "dayMenu": "2023-03-23"
    },
```
---
Получение всего меню по указанной дате.

метод GET

могут использовать - Админ

ссылка - http://localhost:8080/app/api/v1/admin/menu/all

Пример запроса в браузерной строке, выведется меню на указанную дату
```bash
http://localhost:8080/app/api/v1/admin/menu/all?date=2023-03-23
```
Пример ответа
```bash
[
    {
        "id": 1,
        "restaurant": {
            "name": "By sea",
            "id": 1
        },
        "dish": {
            "name": "Chicken",
            "id": 1
        },
        "price": 300.0,
        "dayMenu": "2023-03-23"
    },
    {
        "id": 2,
```
---
Получение конкретного меню по id.

метод GET

могут использовать - Админ

ссылка - http://localhost:8080/app/api/v1/admin/menu

Пример запроса в браузерной строке
```bash
http://localhost:8080/app/api/v1/admin/menu/3
```
Пример ответа
```bash
{
    "id": 3,
    "restaurant": {
        "name": "By sea",
        "id": 1
    },
    "dish": {
        "name": "Coffee",
        "id": 8
    },
    "price": 100.0,
    "dayMenu": "2023-03-23"
}
```
---
Получение меню по id ресторана на конкретную дату.

метод GET

могут использовать - Админ

ссылка - http://localhost:8080/app/api/v1/admin/menu/restaurant

Пример запроса в браузерной строке
```bash
http://localhost:8080/app/api/v1/admin/menu/restaurant?id=1&date=2023-03-23
```
Пример ответа
```bash
[
    {
        "id": 1,
        "restaurant": {
            "name": "By sea",
            "id": 1
        },
        "dish": {
            "name": "Chicken",
            "id": 1
        },
        "price": 300.0,
        "dayMenu": "2023-03-23"
    },
    {
        "id": 2,
```
---
Удаление меню по id.

метод POST

могут использовать - Админ

ссылка - http://localhost:8080/app/api/v1/admin/menu/delete

Пример запроса в браузерной строке
```bash
http://localhost:8080/app/api/v1/admin/menu/delete/8
```
Пример ответа
```bash
Menu with id = 8 successfully deleted.
```
---
Удаление меню по id ресторана.

метод POST

могут использовать - Админ

ссылка - http://localhost:8080/app/api/v1/admin/menu/delete_by_restaurant

Пример запроса в браузерной строке
```bash
http://localhost:8080/app/api/v1/admin/menu/delete_by_restaurant
```
Пример ответа
```bash
Menu with restaurant_id = 1 successfully deleted.
```
---
### Методы работы с голосованием
Получение списка всех ресторанов с предлагаемым меню.

метод GET

могут использовать - Админ, Пользователь

ссылка - http://localhost:8080/app/api/v1/admin/menu/save

Пример запроса в браузерной строке
```bash
http://localhost:8080/app/api/v1/votes/allRestaurant
```
Пример ответа
```bash
Restaurant id = 1. Menu: Chicken = 200.0, Salad = 159.99, Coffee = 100.0. Votes = 1.
Restaurant id = 3. Menu: Potato = 240.99, Pasta = 365.0, Juice = 150.0. Votes = 1.
Restaurant id = 2. Menu: Potato = 160.0, Salad = 140.5, Tea = 50.0, Coffee = 120.0. Votes = 0.
Restaurant id = 4. Menu: Pasta = 299.5, Tea = 70.0. Votes = 0.
Restaurant id = 5. Menu: Chicken = 180.0, Salad = 175.0, Potato = 199.99, Juice = 90.0, Coffee = 140.0. Votes = 0.
```
---
Создание голоса за меню.

метод POST

могут использовать - Админ, Пользователь

ссылка - http://localhost:8080/app/api/v1/votes/save

Пример запроса в браузерной строке
```bash
http://localhost:8080/app/api/v1/votes/save/3
```
Пример ответа
```bash
Vote for restaurant with id = 3, name's restaurant = Caucasus, successfully create.
```
---
Обновление голоса за меню. Работает только до 11 дня.

метод POST

могут использовать - Админ, Пользователь

ссылка - http://localhost:8080/app/api/v1/votes/update

Пример запроса в браузерной строке
```bash
http://localhost:8080/app/api/v1/votes/update/3
```
Пример ответа
```bash
Vote for restaurant with id = 3, name's restaurant = Caucasus, successfully update.
```
---
