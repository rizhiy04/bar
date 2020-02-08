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

### B-1 Как "Клиент" я хочу зарегистрироваться в системе, для получения скидочной карты клиента.

Request:

`POST /bar/sign-up`

```json
{
  "email" : "client@gmail.com",
  "password" : "qwerty",
  "name" : "Денис"
}
```
Response: `201 CREATED`

### B-2 Как "Клиент" я хочу просмотреть информацию о своей карте клиента, если я зарегистрирован.

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

### B-3 Как "Клиент" я хочу просмотреть все меню

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

### B-4 Как "Клиент" я хочу просмотреть всю пиццу из меню 

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


### B-5 Как "Клиент" я хочу заказать столик на вечер

Request: 

`POST /bar/reserveTable`

```json
{
  "name" : "Денис",
  "time" : "04.03.2020 19:00"
}
```

Response: `201 CREATED`

```json
{ 
  "response" : "Ваш столик №2" 
}
```

### B-6 Как "Клиент" я хочу посмотреть список мероприятий, проходящих в баре

Request: 

`GET /bar/events`

Response: `200 OK`

```json
[
  {
    "id" : 1,
    "eventName" : "StandUp вечер",
    "description" : "Много известных комиков",
    "date" : "04.03.2020" 
  }
]
```


### B-7 Как "Официант" я хочу просмотреть забронированные столики

Request: 

`GET /bar/waiter/reservation`

Response: `200 OK`

```json
[
  {
    "id" : 1,
    "clientName" : "Денис",
    "reserveTime" : "04.03.2020 19:00",
    "tableNumber" : 2
  }
]
```

### B-8 Как "Официант" я хочу посмотреть, есть ли свободные столики на ближайшие 2 часа

Request: 

`GET /bar/waiter/freeTables/{hours}`

Response: `200 OK`

```json
[
  {
    "tableNumber" : 1
  }
]
```

### B-9 Как "Официант" я хочу оформить заказ клиента

Request: 

`POST /bar/waiter/makeOrder`

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

```json
{
  "response" : "Заказ оформлен"
}
```

### B-10 Как "Официант" я хочу закрыть заказ клиента

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

### B-11 Как "Администратор" я хочу проверить количество инвентаря

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

### B-12 Как "Администратор" я хочу изменить количество инвентаря

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

### B-13 Как "Администратор" я хочу добавлять новый инвентарь

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

### B-14 Как "Администратор" я хочу удалять старый инвентарь

Request:

`DELETE /bar/admin/deleteInventory/{inventoryId}`

Response: `200 OK`

```json
{
  "response" : "Инвентарь удален"
}
```

### B-15 Как "Администратор" я хочу добавлять новые мероприятия

Request: 

`POST /bar/admin/addNewEvent`

```json
{
   "eventName" : "StandUp вечер",
   "description" : "Много известных комиков",
   "date" : "14.03.2020" 
}
```

Response: `201 CREATED`

```json
{
  "response" : "Мероприятие добавлено"
}
```

### B-16 Как "Администратор" я хочу удалять мероприятия

Request: 

`DELETE /bar/admin/deleteEvent/{eventId}`


Response: `200 OK`

```json
{
  "response" : "Мероприятие удалено"
}
```

### B-17 Как "Администратор" я хочу добавлять новые позиции в меню

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

### B-18 Как "Администратор" я хочу удалять старые позиции в меню

Request: 

`DELETE /bar/admin/deleteMenuItem/{productId}`

Response: `200 OK`

```json
{
  "response" : "Позиция удалена"
}
```
