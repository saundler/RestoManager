# RestoManager

RestoManager — это веб-приложение для управления рестораном, разработанное на языке программирования Java с использованием фреймворка Spring Boot и базы данных PostgreSQL.

## Описание проекта

Приложение RestoManager предоставляет ресторанам возможность эффективного управления их меню, заказами и пользователями.

### Эндпоинты

#### AdminController

- **GET /statistics**: Возвращает статистику по всему ресторану.

#### DishController

- **GET /**: Возвращает меню.
- **GET /dish**: Возвращает список всех блюд.
- **GET /dish/{dishId}**: Возвращает информацию о конкретном блюде по его ID.
- **POST /dish/create**: Создает новое блюдо.
- **POST /dish/edit/{dishId}**: Обновляет информацию о блюде.
- **POST /dish/delete/{dishId}**: Удаляет блюдо по его ID.

#### OrderController

- **GET /order**: Возвращает список всех заказов.
- **GET /order/{orderId}**: Возвращает информацию о конкретном заказе по его ID.
- **POST /order/create**: Создает новый заказ.
- **POST /order/confirm/{id}**: Подтверждает заказ по его ID.
- **POST /order/delete/{orderId}**: Удаляет заказ по его ID.
- **POST /order/review/{id}**: Позволяет оставить отзыв о заказе.
- **POST /order/pay/{id}**: Осуществляет оплату заказа.

#### OrderItemController

- **GET /order/{orderId}/orderItem**: Возвращает список всех элементов определенного заказа.
- **GET /order/{orderId}/orderItem/{orderItemId}**: Возвращает информацию о конкретном элементе заказа.
- **POST /order/{orderId}/orderItem/create**: Добавляет новый элемент к заказу.
- **POST /order/{orderId}/orderItem/editQuantity/{orderItemId}**: Обновляет количество элемента в заказе.
- **POST /order/{orderId}/orderItem/delete/{orderItemId}**: Удаляет элемент из заказа.

#### UserController

- **GET /login**: Отображает страницу авторизации.
- **GET /registration**: Отображает страницу регистрации.
- **POST /registration**: Регистрирует нового пользователя.

### Система приоритетов для обработки заказов

Заказы обрабатываются согласно принципу очередности, отдавая приоритет тем, которые были сделаны ранее.