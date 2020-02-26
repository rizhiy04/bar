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

```json
{ 
  "token" : "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRAZ21haWwuY29tIiwiZXhwIjoxNTgyMDkzMjQyLCJpYXQiOjE1ODIwNTcyNDJ9.G6oqbXxN5fu2QxxzzEaUkBNTcWi4nuMsh4xDP53EQHo" 
}
```

### B-2 Как "Клиент" я хочу войти в систему, и, если я зарегистрирован, вхожу.

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
  "token" : "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRAZ21haWwuY29tIiwiZXhwIjoxNTgyMDkzMjQyLCJpYXQiOjE1ODIwNTcyNDJ9.G6oqbXxN5fu2QxxzzEaUkBNTcWi4nuMsh4xDP53EQHo" 
}
```

### B-3 Как "Клиент" я хочу просмотреть информацию о своей карте клиента, и, если я зарегистрирован, то получаю информацию.

Request: 

`POST /bar/discount-cards`

Response: `200 OK`

```json
{
  "name" : "Денис",
  "email" : "client@gmail.com",
  "discount" : 10,
  "allSpentMoney" : 1250
}
```

### B-4 Как "Клиент" я хочу просмотреть все меню, и в результате получаю его.

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
  },
  {
       "id" : 12,
       "name" : "Пепперони",
       "category" : "pizza",
       "description" : "Колбаска пепперони, сыр. Пицца 30см",
       "price" : 15
  }
]
```

### B-5 Как "Клиент" я хочу просмотреть всю пиццу из меню, и в результате получаю его.

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


### B-6 Как "Клиент" я хочу заказать столик на вечер, и, если есть свободный, бронирую его.

Request: 

`POST /bar/reservations`

```json
{
  "name" : "Денис",
  "time" : "04-03-2020 19:00"
}
```

Response: `201 CREATED`

```json
{ 
  "response" : "№1" 
}
```

### B-7 Как "Клиент" я хочу посмотреть список мероприятий, проходящих в баре, и в результате получаю его.

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


### B-8 Как "Официант" я хочу просмотреть список забронированных столиков, и в результате получаю его.

Request: 

`GET /bar/reservations`

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

### B-9 Как "Официант" я хочу посмотреть список свободных столиков на ближайшие 2 часа, и в результате получаю его.

Request: 

`GET /bar/reservations/free/{hours}`

Response: `200 OK`

```json
{
  "tableNumbers" : [1, 3]
}
```

### B-10 Как "Официант" я хочу оформить заказ клиента, и в результате оформляю его.

Request: 

`POST /bar/orders`

```json
{
  "tableNumber" : 2,
  "order" : 
     [
      {
        "id" : 1,
        "count" : 5
      }
    ]
}
```

Response: `201 CREATED`

### B-11 Как "Официант" я хочу закрыть заказ клиента, и, если у него есть карта клиента, то использую ее, закрываю заказ.

Request: 

`PATCH /bar/orders`

```json
{
  "tableNumber" : 2,
  "clientId" : 1
}
```

Response: `200 OK`

```json
{
  "response" : "25.0р"
}
```

### B-12 Как "Администратор" я хочу получить количество инвентаря, и в результате получаю.

Request: 

`GET /bar/inventories`

Response: `200 OK`

```json
[
  {
    "id" : 1,
    "name" : "Рюмка 50 мл",
    "category" : "glass",
    "count" : 23
  },
  {
    "id" : 2,
    "name" : "Стол",
    "category" : "table",
    "count" : 3
  }
]
```

### B-13 Как "Администратор" я хочу изменить количество инвентаря, и в результате изменяю.

Request: 

`PATCH /bar/inventories`

```json
{
  "id" : 2,
  "count" : 5
}
```

Response: `200 OK`

### B-14 Как "Администратор" я хочу добавить новый инвентарь, и в результате добавляю.

Request:

`POST /bar/inventories`

```json
{
  "name" : "Бокал 500 мл",
  "category" : "wineglass",
  "count" : 30
}
```

Response: `201 CREATED`

### B-15 Как "Администратор" я хочу удалить старый инвентарь, и в результате удаляю.

Request:

`DELETE /bar/inventories/{inventoryId}`

Response: `200 OK`

### B-16 Как "Администратор" я хочу добавить новое мероприятие, и в результате добавляю.

Request: 

`POST /bar/events`

```json
{
   "eventName" : "StandUp вечер",
   "description" : "Много известных комиков",
   "date" : "14-03-2020 20:00" 
}
```

Response: `201 CREATED`

### B-17 Как "Администратор" я хочу удалить мероприятие, и в результате удаляю.

Request: 

`DELETE /bar/events/{eventId}`


Response: `200 OK`

### B-18 Как "Администратор" я хочу добавлить новую позицию в меню, и в результате добавляю.

Request: 

`POST /bar/menu`

```json
  {
    "name" : "Бургер",
    "category" : "burger",
    "description" : "Большой и вкусный",
    "price" : 5
  }
```

Response: `201 CREATED`

### B-19 Как "Администратор" я хочу удалить старую позицию в меню, и в результате удаляю.

Request: 

`DELETE /bar/menu/{productId}`

Response: `200 OK`


### B-20 Как "Администратор" я хочу просмотреть историю заказов, и в результате получаю её.

Request: 

`GET /bar/orders`

Response: `200 OK`

```json
[ 
   { 
      "id":1,
      "tableNumber":2,
      "timeOpen":"14-03-2020 20:00",
      "timeClose": null,
      "order":[ 
         { 
            "menuItem":{ 
               "id":1,
               "name":"Zatecky Gus",
               "category":"beer",
               "description":"Светлый лагер с легким традиционным вкусом",
               "price":5.0
            },
            "count":5
         }
      ]
   }
]
```

### B-21 Как "Администратор" я хочу просмотреть выручку с 14.03.2019, и в результате получаю её.

Request: 

`GET /bar/orders/revenue`

```json
{
  "date" : "14-03-2019 00:00"
}
```

Response: `200 OK`

```json
{
  "response" : "25.0р."
}
```