### Как "Клиент" я хочу зарегистрироваться в системе, для получения скидочной карты клиента, и, если такого пользователя не найдено, регистрируюсь.

`POST /bar/sign-up`

```json
{
  "email" : "denis@gmail.com",
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

### Как "Клиент" я хочу войти в систему, и, если я зарегистрирован, вхожу.

Request: 

`POST /bar/sign-in`

```json
{
  "email" : "denis@gmail.com",
  "password" : "qwerty"
}
```

Response: `200 OK`

```json
{ 
  "token" : "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRAZ21haWwuY29tIiwiZXhwIjoxNTgyMDkzMjQyLCJpYXQiOjE1ODIwNTcyNDJ9.G6oqbXxN5fu2QxxzzEaUkBNTcWi4nuMsh4xDP53EQHo" 
}
```

### Как "Клиент" я хочу просмотреть информацию о своей карте клиента.

Request: 

`POST /bar/discount-cards`

Response: `200 OK`

```json
{
  "name" : "Денис",
  "email" : "denis@gmail.com",
  "discount" : 0,
  "allSpentMoney" : 0
}
```

### Как "Клиент" я хочу просмотреть все меню.

Request: 

`GET /bar/menu`

Response: `200 OK`

```json
[
   {
      "id":1,
      "name":"Zatecky Gus",
      "category":"beer",
      "description":"Светлый лагер с легким традиционным вкусом",
      "price":5.0
   
},
   {
      "id":2,
      "name":"Kozel",
      "category":"beer",
      "description":"Светлый лагер с легким традиционным вкусом",
      "price":5.0
   
},
   {
      "id":3,
      "name":"Heineken",
      "category":"beer",
      "description":"Светлый лагер с легким традиционным вкусом",
      "price":5.0
   
},
   {
      "id":4,
      "name":"Виски Jim Beam",
      "category":"whisky",
      "description":"Американский бурбон, номер 1 по продажам в мире. обладает янтарным цветом и приятным ароматом с ванильными и цветочными оттенками.",
      "price":7.3
   
},
   {
      "id":5,
      "name":"Gold Rum",
      "category":"rum",
      "description":"Классический пряный ром, идеальная основа для коктейлей",
      "price":4.4
   
},
   {
      "id":6,
      "name":"Silver Tequila",
      "category":"tequila",
      "description":"Традиционный мексиканский крепкоалкогольный напиток, приготовляемый из голубой агавы.",
      "price":5.5
   
},
   {
      "id":7,
      "name":"Tequila Sauza Silver",
      "category":"tequila",
      "description":"Традиционный мексиканский алкогольный напиток чистого и прозрачного цвета с нотками голубой агавы, дуба, перца и грейпфрута и терпким миндальным послевкусием.",
      "price":7.0
   
},
   {
      "id":8,
      "name":"London Dry Gin",
      "category":"gin",
      "description":"Традиционный джин, отличная основа для коктейлей.",
      "price":4.4
   
},
   {
      "id":9,
      "name":"Самбука",
      "category":"sambuca",
      "description":"Популярный итальянский ликер на основе вытяжки из звездчатого аниса и бузины.",
      "price":5.9
   
},
   {
      "id":10,
      "name":"Ликер Blue Curacao",
      "category":"liquor",
      "description":"Ликер голубого цвета на основе высушенных корок апельсинов особого сорта.",
      "price":6.6
   
},
   {
      "id":11,
      "name":"Ликер Егермайстер",
      "category":"liquor",
      "description":"Известный немецкий травяной ликер. В его состав входит 56 натуральных компонентов, среди которых разнообразные травы, пряности, фрукты.",
      "price":7.3
   
},
   {
      "id":12,
      "name":"Дисконтик",
      "category":"shots",
      "description":"Трехслойный коктейль с виски, вишневым сиропом и шампанским. Быстро и эффективно.",
      "price":2.6
   
},
   {
      "id":13,
      "name":"Отвертка шот",
      "category":"shots",
      "description":"Двухслойный коктейль на основе водки и апельсинного сока, для тех, кто не привык выдумывать сложных схем.",
      "price":2.6
   
},
   {
      "id":14,
      "name":"Лютый егерь",
      "category":"shots",
      "description":"Для любителей легендарного напитка Егермейстер с виски и вишневым послевкусием",
      "price":4.8
   
},
   {
      "id":15,
      "name":"Рыжая собака",
      "category":"shots",
      "description":"Острый двухслойный коктейль из анисового ликера Самбука и серебряной текилы. Остроту коктейлю придает дэш соуса табаско.",
      "price":5.1
   
},
   {
      "id":16,
      "name":"Опухоль мозга",
      "category":"shots",
      "description":"Устрашающе красивый коктейль, приготовленный из сухого вермута, сливочного ликера, традиционной водки и сиропа гренадин.",
      "price":5.1
   
},
   {
      "id":17,
      "name":"Coca-Cola",
      "category":"lemonade",
      "description":"розлив, 0.2л",
      "price":1.5
   
},
   {
      "id":18,
      "name":"Sprite",
      "category":"lemonade",
      "description":"розлив, 0.2л",
      "price":1.5
   
},
   {
      "id":19,
      "name":"Schweppes",
      "category":"lemonade",
      "description":"розлив, 0.2л",
      "price":1.5
   
},
   {
      "id":20,
      "name":"Сок апельсиновый",
      "category":"juice",
      "description":"0.2л",
      "price":1.9
   
},
   {
      "id":21,
      "name":"Сок ананасовый",
      "category":"juice",
      "description":"0.2л",
      "price":1.9
   
},
   {
      "id":22,
      "name":"Луковые кольца",
      "category":"snacks",
      "description":"Золотистые луковые колечки.",
      "price":2.9
   
},
   {
      "id":23,
      "name":"Гренки с чесноком",
      "category":"snacks",
      "description":"Обжаренные кусочки ржаного хлеба с чесноком",
      "price":2.9
   
},
   {
      "id":24,
      "name":"Пицца Пепперони",
      "category":"pizza",
      "description":"Пицца на тонком тесте с острой колбаской пепперони и маслинами под сыром моцарелла.",
      "price":9.5
   
},
   {
      "id":25,
      "name":"Пицца Маргарита",
      "category":"pizza",
      "description":"Классическая пицца из сочных томатов и нежной моцареллы на тонком тесте.",
      "price":7.3
   
},
   {
      "id":26,
      "name":"Пицца гавайская",
      "category":"pizza",
      "description":"Пикантная пицца с нежной ветчиной и сладким ананасом под сыром моцарелла.",
      "price":9.5
   
},
   {
      "id":27,
      "name":"Пицца с курицой и грибами",
      "category":"pizza",
      "description":"Пицца на тонком тесте с куриным филе и шампиньонами под сыром моцарелла.",
      "price":9.9
   
}
]
```

### Как "Клиент" я хочу просмотреть всю пиццу из меню.

Request: 

`GET /bar/menu/pizza`

Response: `200 OK`

```json
[
   {
      "id":24,
      "name":"Пицца Пепперони",
      "category":"pizza",
      "description":"Пицца на тонком тесте с острой колбаской пепперони и маслинами под сыром моцарелла.",
      "price":9.5
   
},
   {
      "id":25,
      "name":"Пицца Маргарита",
      "category":"pizza",
      "description":"Классическая пицца из сочных томатов и нежной моцареллы на тонком тесте.",
      "price":7.3
   
},
   {
      "id":26,
      "name":"Пицца гавайская",
      "category":"pizza",
      "description":"Пикантная пицца с нежной ветчиной и сладким ананасом под сыром моцарелла.",
      "price":9.5
   
},
   {
      "id":27,
      "name":"Пицца с курицой и грибами",
      "category":"pizza",
      "description":"Пицца на тонком тесте с куриным филе и шампиньонами под сыром моцарелла.",
      "price":9.9
   
}
]
```

### Как "Клиент" я хочу посмотреть список мероприятий, проходящих в баре.

Request: 

`GET /bar/events`

Response: `200 OK`

```json
[
   {
      "id":3,
      "eventName":"StandUp вечер",
      "description":"Много известных комиков",
      "date":"28-03-2020 20:00"
   
},
   {
      "id":4,
      "eventName":"Концерт какой-то группы",
      "description":"Живая музыка, ламповая атмосфера",
      "date":"03-04-2020 20:00"
   
},
   {
      "id":5,
      "eventName":"StandUp вечер",
      "description":"Много известных комиков",
      "date":"06-04-2020 20:00"
   
}
]
```

### Как "Клиент" я хочу заказать столик на вечер, и, если есть свободный, бронирую его.

Request: 

`POST /bar/reservations`

```json
{
  "name" : "Денис",
  "time" : "05-03-2020 19:00"
}
```

Response: `201 CREATED`

```json
{ 
  "response" : "№1" 
}
```

### Зарание подготовляна база данных с пользователями, поэтому я вхожу как "Официант".

Request: 

`POST /bar/sign-in`

```json
{
  "email" : "waiter@gmail.com",
  "password" : "qwerty"
}
```

Response: `200 OK`

```json
{ 
  "token" : "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRAZ21haWwuY29tIiwiZXhwIjoxNTgyMDkzMjQyLCJpYXQiOjE1ODIwNTcyNDJ9.G6oqbXxN5fu2QxxzzEaUkBNTcWi4nuMsh4xDP53EQHo" 
}
```

### Как "Официант" я хочу просмотреть список забронированных столиков, и в результате получаю его.

Request: 

`GET /bar/reservations`

Response: `200 OK`

```json
[
  {
    "id" : 1,
    "clientName" : "Денис",
    "reserveTime" : "05-03-2020 19:00",
    "tableNumber" : 1
  }
]
```

### Как "Официант" я хочу посмотреть список свободных столиков на ближайшие 2 часа.

Request: 

`GET /bar/reservations/free/{hours}`

Response: `200 OK`

```json
{
  "tableNumbers" : [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
}
```

### Как "Официант" я хочу оформить заказ клиента, и в результате оформляю его.

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

### Как "Официант" я хочу посмотреть список свободных столиков на ближайшие 2 часа, после оформления заказа.

Request: 

`GET /bar/reservations/free/{hours}`

Response: `200 OK`

```json
{
  "tableNumbers" : [1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
}
```

### Как "Официант" я хочу закрыть заказ клиента, и, если у него есть карта клиента, то использую ее, закрываю заказ.

Request: 

`PATCH /bar/orders`

```json
{
  "tableNumber" : 2,
  "clientId" : 4
}
```

Response: `200 OK`

```json
{
  "money" : "25.0",
  "currency" : "BYN"
}
```

### Как "Клиент" я хочу просмотреть информацию о своей карте клиента, после закрытия заказа.

Request: 

`POST /bar/discount-cards`

Response: `200 OK`

```json
{
  "name" : "Денис",
  "email" : "client@gmail.com",
  "discount" : 0,
  "allSpentMoney" : 25.00
}
```

### Зарание подготовляна база данных с пользователями, поэтому я вхожу как "Администратор".

Request: 

`POST /bar/sign-in`

```json
{
  "email" : "admin@gmail.com",
  "password" : "qwerty"
}
```

Response: `200 OK`

```json
{ 
  "token" : "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRAZ21haWwuY29tIiwiZXhwIjoxNTgyMDkzMjQyLCJpYXQiOjE1ODIwNTcyNDJ9.G6oqbXxN5fu2QxxzzEaUkBNTcWi4nuMsh4xDP53EQHo" 
}
```

### Как "Администратор" я хочу получить количество инвентаря.

Request: 

`GET /bar/inventories`

Response: `200 OK`

```json
[
   {
      "id":1,
      "name":"Рюмка 50 мл",
      "category":"glass",
      "count":45
   
},
   {
      "id":2,
      "name":"Стол",
      "category":"table",
      "count":15
   
},
   {
      "id":3,
      "name":"Бокал 500мл",
      "category":"glass",
      "count":20
   
},
   {
      "id":4,
      "name":"Баррель Zatecky Gus",
      "category":"alcohol",
      "count":10
   
},
   {
      "id":5,
      "name":"Баррель Kozel",
      "category":"alcohol",
      "count":10
   
},
   {
      "id":6,
      "name":"Баррель Heineken",
      "category":"alcohol",
      "count":3
   
},
   {
      "id":7,
      "name":"Виски Jim Beam 0,7л",
      "category":"alcohol",
      "count":5
   
},
   {
      "id":8,
      "name":"Gold Rum 0,7л",
      "category":"alcohol",
      "count":5
   
},
   {
      "id":9,
      "name":"Silver Tequila 0,7л",
      "category":"alcohol",
      "count":5
   
},
   {
      "id":10,
      "name":"Tequila Sauza Silver 0,7л",
      "category":"alcohol",
      "count":4
   
},
   {
      "id":11,
      "name":"London Dry Gin 0,7л",
      "category":"alcohol",
      "count":6
   
},
   {
      "id":12,
      "name":"Самбука 0,7л",
      "category":"alcohol",
      "count":6
   
},
   {
      "id":13,
      "name":"Ликер Blue Curacao 0,7л",
      "category":"alcohol",
      "count":6
   
},
   {
      "id":14,
      "name":"Ликер Егермайстер 0,7л",
      "category":"alcohol",
      "count":6
   
},
   {
      "id":15,
      "name":"Томаты 1кг",
      "category":"food",
      "count":10
   
},
   {
      "id":16,
      "name":"Сыр моцарелла 1кг",
      "category":"food",
      "count":5
   
},
   {
      "id":17,
      "name":"Колбаса Пепперони",
      "category":"food",
      "count":5
   
},
   {
      "id":18,
      "name":"Куриное филе 1кг",
      "category":"food",
      "count":10
   
}
]
```

### Как "Администратор" я хочу изменить количество инвентаря.

Request: 

`PATCH /bar/inventories`

```json
{
  "id" : 1,
  "count" : 50
}
```

Response: `200 OK`

### Как "Администратор" я хочу добавить новый инвентарь.

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

### Как "Администратор" я хочу удалить старый инвентарь.

Request:

`DELETE /bar/inventories/3`

Response: `200 OK`

### Как "Администратор" я хочу получить количество инвентаря, после его изменения.

Request: 

`GET /bar/inventories`

Response: `200 OK`

```json
[
   {
      "id":1,
      "name":"Рюмка 50 мл",
      "category":"glass",
      "count":50
   
},
   {
      "id":2,
      "name":"Стол",
      "category":"table",
      "count":15
   
},
   {
      "id":4,
      "name":"Баррель Zatecky Gus",
      "category":"alcohol",
      "count":10
   
},
   {
      "id":5,
      "name":"Баррель Kozel",
      "category":"alcohol",
      "count":10
   
},
   {
      "id":6,
      "name":"Баррель Heineken",
      "category":"alcohol",
      "count":3
   
},
   {
      "id":7,
      "name":"Виски Jim Beam 0,7л",
      "category":"alcohol",
      "count":5
   
},
   {
      "id":8,
      "name":"Gold Rum 0,7л",
      "category":"alcohol",
      "count":5
   
},
   {
      "id":9,
      "name":"Silver Tequila 0,7л",
      "category":"alcohol",
      "count":5
   
},
   {
      "id":10,
      "name":"Tequila Sauza Silver 0,7л",
      "category":"alcohol",
      "count":4
   
},
   {
      "id":11,
      "name":"London Dry Gin 0,7л",
      "category":"alcohol",
      "count":6
   
},
   {
      "id":12,
      "name":"Самбука 0,7л",
      "category":"alcohol",
      "count":6
   
},
   {
      "id":13,
      "name":"Ликер Blue Curacao 0,7л",
      "category":"alcohol",
      "count":6
   
},
   {
      "id":14,
      "name":"Ликер Егермайстер 0,7л",
      "category":"alcohol",
      "count":6
   
},
   {
      "id":15,
      "name":"Томаты 1кг",
      "category":"food",
      "count":10
   
},
   {
      "id":16,
      "name":"Сыр моцарелла 1кг",
      "category":"food",
      "count":5
   
},
   {
      "id":17,
      "name":"Колбаса Пепперони",
      "category":"food",
      "count":5
   
},
   {
      "id":18,
      "name":"Куриное филе 1кг",
      "category":"food",
      "count":10
   
},
   {
      "id":19,
      "name":"Бокал 500 мл",
      "category":"wineglass",
      "count":30
   
}
]
```

### Как "Администратор" я хочу добавить новое мероприятие, и в результате добавляю.

Request: 

`POST /bar/events`

```json
{
   "eventName" : "StandUp вечер",
   "description" : "Много известных комиков",
   "date" : "14-05-2020 20:00" 
}
```

Response: `201 CREATED`

### Как "Администратор" я хочу удалить мероприятие, и в результате удаляю.

Request: 

`DELETE /bar/events/5`

Response: `200 OK`

### Как "Клиент" я хочу посмотреть список мероприятий, проходящих в баре, после изменения.

Request: 

`GET /bar/events`

Response: `200 OK`

```json
[
   {
      "id":3,
      "eventName":"StandUp вечер",
      "description":"Много известных комиков",
      "date":"28-03-2020 20:00"
   
},
   {
      "id":4,
      "eventName":"Концерт какой-то группы",
      "description":"Живая музыка, ламповая атмосфера",
      "date":"03-04-2020 20:00"
   
},
   {
      "id":6,
      "eventName":"StandUp вечер",
      "description":"Много известных комиков",
      "date":"14-05-2020 20:00"
   
}
]
```

### Как "Администратор" я хочу добавить новую позицию в меню.

Request: 

`POST /bar/menu`

```json
  {
    "name" : "Бургер",
    "category" : "burger",
    "description" : "Большой и вкусный",
    "price" : 5.0
  }
```

Response: `201 CREATED`

### Как "Администратор" я хочу удалить старую позицию в меню.

Request: 

`DELETE /bar/menu/2`

Response: `200 OK`

### Как "Клиент" я хочу просмотреть все меню, после изменения.

Request: 

`GET /bar/menu`

Response: `200 OK`

```json
[
   {
      "id":1,
      "name":"Zatecky Gus",
      "category":"beer",
      "description":"Светлый лагер с легким традиционным вкусом",
      "price":5.0
   
},
   {
      "id":3,
      "name":"Heineken",
      "category":"beer",
      "description":"Светлый лагер с легким традиционным вкусом",
      "price":5.0
   
},
   {
      "id":4,
      "name":"Виски Jim Beam",
      "category":"whisky",
      "description":"Американский бурбон, номер 1 по продажам в мире. обладает янтарным цветом и приятным ароматом с ванильными и цветочными оттенками.",
      "price":7.3
   
},
   {
      "id":5,
      "name":"Gold Rum",
      "category":"rum",
      "description":"Классический пряный ром, идеальная основа для коктейлей",
      "price":4.4
   
},
   {
      "id":6,
      "name":"Silver Tequila",
      "category":"tequila",
      "description":"Традиционный мексиканский крепкоалкогольный напиток, приготовляемый из голубой агавы.",
      "price":5.5
   
},
   {
      "id":7,
      "name":"Tequila Sauza Silver",
      "category":"tequila",
      "description":"Традиционный мексиканский алкогольный напиток чистого и прозрачного цвета с нотками голубой агавы, дуба, перца и грейпфрута и терпким миндальным послевкусием.",
      "price":7.0
   
},
   {
      "id":8,
      "name":"London Dry Gin",
      "category":"gin",
      "description":"Традиционный джин, отличная основа для коктейлей.",
      "price":4.4
   
},
   {
      "id":9,
      "name":"Самбука",
      "category":"sambuca",
      "description":"Популярный итальянский ликер на основе вытяжки из звездчатого аниса и бузины.",
      "price":5.9
   
},
   {
      "id":10,
      "name":"Ликер Blue Curacao",
      "category":"liquor",
      "description":"Ликер голубого цвета на основе высушенных корок апельсинов особого сорта.",
      "price":6.6
   
},
   {
      "id":11,
      "name":"Ликер Егермайстер",
      "category":"liquor",
      "description":"Известный немецкий травяной ликер. В его состав входит 56 натуральных компонентов, среди которых разнообразные травы, пряности, фрукты.",
      "price":7.3
   
},
   {
      "id":12,
      "name":"Дисконтик",
      "category":"shots",
      "description":"Трехслойный коктейль с виски, вишневым сиропом и шампанским. Быстро и эффективно.",
      "price":2.6
   
},
   {
      "id":13,
      "name":"Отвертка шот",
      "category":"shots",
      "description":"Двухслойный коктейль на основе водки и апельсинного сока, для тех, кто не привык выдумывать сложных схем.",
      "price":2.6
   
},
   {
      "id":14,
      "name":"Лютый егерь",
      "category":"shots",
      "description":"Для любителей легендарного напитка Егермейстер с виски и вишневым послевкусием",
      "price":4.8
   
},
   {
      "id":15,
      "name":"Рыжая собака",
      "category":"shots",
      "description":"Острый двухслойный коктейль из анисового ликера Самбука и серебряной текилы. Остроту коктейлю придает дэш соуса табаско.",
      "price":5.1
   
},
   {
      "id":16,
      "name":"Опухоль мозга",
      "category":"shots",
      "description":"Устрашающе красивый коктейль, приготовленный из сухого вермута, сливочного ликера, традиционной водки и сиропа гренадин.",
      "price":5.1
   
},
   {
      "id":17,
      "name":"Coca-Cola",
      "category":"lemonade",
      "description":"розлив, 0.2л",
      "price":1.5
   
},
   {
      "id":18,
      "name":"Sprite",
      "category":"lemonade",
      "description":"розлив, 0.2л",
      "price":1.5
   
},
   {
      "id":19,
      "name":"Schweppes",
      "category":"lemonade",
      "description":"розлив, 0.2л",
      "price":1.5
   
},
   {
      "id":20,
      "name":"Сок апельсиновый",
      "category":"juice",
      "description":"0.2л",
      "price":1.9
   
},
   {
      "id":21,
      "name":"Сок ананасовый",
      "category":"juice",
      "description":"0.2л",
      "price":1.9
   
},
   {
      "id":22,
      "name":"Луковые кольца",
      "category":"snacks",
      "description":"Золотистые луковые колечки.",
      "price":2.9
   
},
   {
      "id":23,
      "name":"Гренки с чесноком",
      "category":"snacks",
      "description":"Обжаренные кусочки ржаного хлеба с чесноком",
      "price":2.9
   
},
   {
      "id":24,
      "name":"Пицца Пепперони",
      "category":"pizza",
      "description":"Пицца на тонком тесте с острой колбаской пепперони и маслинами под сыром моцарелла.",
      "price":9.5
   
},
   {
      "id":25,
      "name":"Пицца Маргарита",
      "category":"pizza",
      "description":"Классическая пицца из сочных томатов и нежной моцареллы на тонком тесте.",
      "price":7.3
   
},
   {
      "id":26,
      "name":"Пицца гавайская",
      "category":"pizza",
      "description":"Пикантная пицца с нежной ветчиной и сладким ананасом под сыром моцарелла.",
      "price":9.5
   
},
   {
      "id":27,
      "name":"Пицца с курицой и грибами",
      "category":"pizza",
      "description":"Пицца на тонком тесте с куриным филе и шампиньонами под сыром моцарелла.",
      "price":9.9
   
},
   {
      "id":28,
      "name":"Бургер",
      "category":"burger",
      "description":"Большой и вкусный",
      "price":5.0
   
}
]
```

### Как "Администратор" я хочу просмотреть историю заказов.

Request: 

`GET /bar/orders`

Response: `200 OK`

```json
[ 
   { 
      "id":1,
      "tableNumber":2,
      "timeOpen":"14-03-2020 20:00",
      "timeClose": "14-03-2020 21:00",
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

### Как "Администратор" я хочу просмотреть выручку с 14.03.2019, и в результате получаю её.

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
  "money" : "25.0",
  "currency" : "BYN"
}
```