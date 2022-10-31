##  Technical requirement:
Design and implement a REST API using Hibernate/Spring/SpringMVC (Spring-Boot preferred!) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote for a restaurant they want to have lunch at today
* Only one vote counted per user
* If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it (**better - link to Swagger**).

-----------------------------
P.S.: Make sure everything works with latest version that is on github :)  
P.P.S.: Assume that your API will be used by a frontend developer to build frontend on top of that.

## Техническое задание:
Разработайте и внедрите REST API с использованием Hibernate/Spring/SpringMVC (предпочтительнее Spring-Boot!) **без Frontend части**.

Задача состоит в том, чтобы:

Создать систему голосования для принятия решения о том, где пообедать.

* 2 типа пользователей: администратор и обычные пользователи
* Администратор может ввести ресторан и его обеденное меню на выбранный день (обычно 2-5 блюд, просто название блюда и стоимость).
* Меню меняется каждый день (администраторы делают обновления)
* Пользователи могут проголосовать за ресторан, в котором они хотят пообедать сегодня
* Для каждого пользователя учитывается только один голос
* Если пользователь проголосует снова в тот же день то:
  - Если это произойдет до 11:00, мы предполагаем, что он передумал.
  - Если это после 11:00, то уже слишком поздно, голосование изменить нельзя

Каждый ресторан предлагает новое меню каждый день.

В результате предоставьте ссылку на репозиторий github. Он должен содержать код, README.md с документацией API и парой команд curl для его тестирования (**лучше - ссылка на Swagger**).

-----------------------------
P.S.: Убедитесь, что все работает с последней версией, которая есть на github :)
P.P.S.: Предположим, что ваш API будет использоваться разработчиком интерфейса для создания интерфейса поверх этого.


