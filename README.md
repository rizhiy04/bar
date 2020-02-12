# Bar

## Overview

Приложение для бара, в котором пользователи могут просмотреть всю интересующую их информацию, а работникам и менеджерам поможет автоматизировать их работу. 

## Сущности

Ниже перечисленный сущности в предметной области проекта и их поля.

### MenuItem (Позиция из меню)
Одина позиция из меню.

Поля:

* Наименование
* Категория меню
* Описание
* Цена

### Client (Клиент)
Зарегистрированный пользователь, который имеет скидочную карту клиента.

Поля:

* Номер карты клиента
* Email
* Имя
* Скидка
* Общая сумма потраченых денег

### Order (Заказ)
Заказ, который сделал клиент.

Поля:

* Номер стола
* Время открытия заказа
* Время закытия заказа

Связи:

* Позиции из меню("MenuItem")

### Reservation (Бронь)
Бронь столика на какое-то время.

Поля:

* Имя бронирующего
* Время
* Номер стола

### Event (Мероприятие)
Мероприятие, проходящее в баре.

Поля:

* Название
* Описание
* Время проведения

### Inventory (Инвентарь)
Инвентарь, который используется в баре.

Поля:

* Название
* Категория
* Количество

## User Stories

### B-1 Как "Клиент" я хочу зарегистрироваться в системе, для получения скидочной карты клиента, и, если такого пользователя не найдено, регистрируюсь.

`POST /bar/sign-up`

```json
{
  "email" : "client@gmail.com",
  "password" : "qwerty",
  "name" : "Денис"
}
```
Response: `201 CREATED`

### B-2 Как "Клиент" я хочу просмотреть информацию о своей карте клиента, и, если я зарегистрирован, то получаю информацию.

Request: 

`POST /bar/sign-in`

```json
{
  "email" : "client@gmail.com",
  "password" : "qwerty"
}
```

Response: `200 OK`

```json
{
  "name" : "Денис",
  "email" : "client@gmail.com",
  "discount" : 10,
  "allSpentMoney" : 1250
}
```

### B-3 Как "Клиент" я хочу просмотреть все меню, и в результате получаю его.

Request: 

`GET /bar/menu`

Response: `200 OK`

```json
[
  {
    "id" : 1,
    "name" : "Zatecky Gus",
    "category" : "beer",
    "description" : "Светлый лагер с легким традиционным вкусом",
    "price" : 5
  }
]
```

### B-4 Как "Клиент" я хочу просмотреть всю пиццу из меню, и в результате получаю его.

Request: 

`GET /bar/menu/{category}`

Response: `200 OK`

```json
[
  {
    "id" : 12,
    "name" : "Пепперони",
    "category" : "pizza",
    "description" : "Колбаска пепперони, сыр. Пицца 30см",
    "price" : 15
  }
]
```


### B-5 Как "Клиент" я хочу заказать столик на вечер, и, если есть свободный, бронирую его.

Request: 

`POST /bar/reserveTable`

```json
{
  "name" : "Денис",
  "time" : "04-03-2020 19:00"
}
```

Response: `201 CREATED`

```json
{ 
  "response" : "Ваш столик №2" 
}
```

### B-6 Как "Клиент" я хочу посмотреть список мероприятий, проходящих в баре, и в результате получаю его.

Request: 

`GET /bar/events`

Response: `200 OK`

```json
[
  {
    "id" : 1,
    "eventName" : "StandUp вечер",
    "description" : "Много известных комиков",
    "date" : "04-03-2020 20:00" 
  }
]
```


### B-7 Как "Официант" я хочу просмотреть список забронированных столиков, и в результате получаю его.

Request: 

`GET /bar/waiter/reservationEntity`

Response: `200 OK`

```json
[
  {
    "id" : 1,
    "clientName" : "Денис",
    "reserveTime" : "04-03-2020 19:00",
    "tableNumber" : 2
  }
]
```

### B-8 Как "Официант" я хочу посмотреть список свободных столиков на ближайшие 2 часа, и в результате получаю его.

Request: 

`GET /bar/waiter/freeTables/{hours}`

Response: `200 OK`

```json
{
  "tableNumbers" : [1, 3]
}
```

### B-9 Как "Официант" я хочу оформить заказ клиента, и в результате оформляю его.

Request: 

`POST /bar/waiter/makeOrder`

```json
{
  "tableNumber" : 2,
  "orderEntity" : 
     [
      {
        "id" : 1,
        "count" : 5
      }
    ]
}
```

Response: `201 CREATED`

```json
{
  "response" : "Заказ оформлен"
}
```

### B-10 Как "Официант" я хочу закрыть заказ клиента, и, если у него есть карта клиента, то использую ее, закрываю заказ.

Request: 

`POST /bar/waiter/closeOrder`

```json
{
  "tableNumber" : 2,
  "clientId" : 1
}
```

Response: `200 OK`

```json
{
  "response" : "Заказ закрыт, к оплате 25р"
}
```

### B-11 Как "Администратор" я хочу получить количество инвентаря, и в результате получаю.

Request: 

`GET /bar/admin/inventoryCount`

Response: `200 OK`

```json
[
  {
    "id" : 1,
    "name" : "Рюмка 50 мл",
    "category" : "glass",
    "count" : 23
  }
]
```

### B-12 Как "Администратор" я хочу изменить количество инвентаря, и в результате изменяю.

Request: 

`POST /bar/admin/inventoryCount`

```json
{
  "id" : 1,
  "count" : 50
}
```

Response: `200 OK`

```json
{
  "response" : "Сохранено"
}
```

### B-13 Как "Администратор" я хочу добавить новый инвентарь, и в результате добавляю.

Request:

`POST /bar/admin/newInventory`

```json
{
  "name" : "Бокал 500 мл",
  "category" : "wineglass",
  "count" : 30
}
```

Response: `201 CREATED`

```json
{
  "response" : "Инвентарь добавлен"
}
```

### B-14 Как "Администратор" я хочу удалить старый инвентарь, и в результате удаляю.

Request:

`DELETE /bar/admin/deleteInventory/{inventoryId}`

Response: `200 OK`

```json
{
  "response" : "Инвентарь удален"
}
```

### B-15 Как "Администратор" я хочу добавить новое мероприятие, и в результате добавляю.

Request: 

`POST /bar/admin/addNewEvent`

```json
{
   "eventName" : "StandUp вечер",
   "description" : "Много известных комиков",
   "date" : "14-03-2020 20:00" 
}
```

Response: `201 CREATED`

```json
{
  "response" : "Мероприятие добавлено"
}
```

### B-16 Как "Администратор" я хочу удалить мероприятие, и в результате удаляю.

Request: 

`DELETE /bar/admin/deleteEvent/{eventId}`


Response: `200 OK`

```json
{
  "response" : "Мероприятие удалено"
}
```

### B-17 Как "Администратор" я хочу добавлить новую позицию в меню, и в результате добавляю.

Request: 

`POST /bar/admin/addNewMenuItem`

```json
  {
    "name" : "Zatecky Gus",
    "category" : "beer",
    "description" : "Светлый лагер с легким традиционным вкусом",
    "price" : 5
  }
```

Response: `201 CREATED`

```json
{
  "response" : "Позиция добавлена"
}
```

### B-18 Как "Администратор" я хочу удалить старую позицию в меню, и в результате удаляю.

Request: 

`DELETE /bar/admin/deleteMenuItem/{productId}`

Response: `200 OK`

```json
{
  "response" : "Позиция удалена"
}
```
