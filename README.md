## User Stories

### B-1 Как "Клиент" я хочу зарегистрироваться в системе

Request:

`POST /bar/sign-up`

```json
{
  "email" : "client@gmail.com",
  "password" : "qwerty",
  "name" : "Денис"
}
```
Response: `200 OK`

### B-2 Как "Клиент" я хочу просмотреть все меню

Request: 

`GET /bar/menu`

Response: `200 OK`

```json
[
  {
    "id" : 1,
    "name" : "Zatecky Gus",
    "category" : "Пиво",
    "description" : "Светлый лагер с легким традиционным вкусом",
    "price" : 5
  }
]
```

### B-3 Как "Клиент" я хочу заказать столик на вечер

Request: 

`POST /bar/reserveTable`

```json
{
  "name" : "Денис",
  "time" : "04.03.2020 19:00"
}
```

Response: `200 OK`

```json
{ 
  "response" : "Ваш столик №2" 
}
```

### B-4 Как "Клиент" я хочу посмотреть список мероприятий, проходящих в баре

Request: 

`GET /bar/entertainment`

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

### B-5 Как "Клиент" я хочу просмотреть информацию о своей карте клиента, если я зарегистрирован

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
  "clientName" : "Денис",
  "clientDiscount" : 10,
  "allSpentMoney" : 1250
}
```

### B-6 Как "Официант" я хочу просмотреть забронированные столики

Request: 

`GET /bar/reservation`

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

### B-7 Как "Официант" я хочу оформить заказ клиента

Request: 

`POST /bar/order`

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

Response: `200 OK`

```json
{
  "response" : "Заказ оформлен"
}
```

### B-8 Как "Официант" я хочу закрыть заказ клиента

Request: 

`POST /bar/closeOrder`

```json
{
  "tableNumber" : 2,
  "clientId" : 1
}
```

Response: `200 OK`

```json
{
  "response" : "Заказ закрыт, к оплате 90р"
}
```

### B-9 Как "Администратор" я хочу проверить количество продукции

Request: 

`GET /bar/productsCount`

Response: `200 OK`

```json
[
  {
    "id" : 1,
    "name" : "Zatecky Gus",
    "count" : 23
  }
]
```

### B-10 Как "Администратор" я хочу добавлять информацию о новых мероприятиях

Request: 

`POST /bar/addNewEntertainment`

Response: `200 OK`

### B-11 Как "Администратор" я хочу добавлять новые позиции в меню

Request: 

`POST /bar/product/addNew`

```json
  {
    "name" : "Zatecky Gus",
    "category" : "Пиво",
    "description" : "Светлый лагер с легким традиционным вкусом",
    "price" : 5
  }
```

Response: `200 OK`

### B-12 Как "Администратор" я хочу удалять старые позиции в меню

Request: 

`POST /bar/product/delete/${productId}`

Response: `200 OK`
