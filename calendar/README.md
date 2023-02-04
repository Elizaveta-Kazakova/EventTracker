# Календарь
### **Требования**

1. Создание события в календаре. На 1 час может быть только 1 событие
2. Удаление события из календаря
3. Редактирование событий в календаре (наименование события, описание, время и т.д.)
4. Просмотр всех событий пользователя (прошедших, предстоящих) за определенный период времени с сортировкой по дате

# календарь

### **Требования**

1. Создание события в календаре. На 1 час может быть только 1 событие
2. Удаление события из календаря
3. Редактирование событий в календаре (наименование события, описание, время и т.д.)
4. Просмотр всех событий пользователя (прошедших, предстоящих) за определенный период времени с сортировкой по дате
5. Просмотр ближайших актуальных событий
6. Делиться событиями с другими пользователями (доступ на просмотр). Проверка наличия событий на заданное время у пользователя, с которым делишься событием (время должно быть свободно)

общая ошибка

HTTP status 500
```json
{

"code"       : "1",

"message" : "Внутренняя ошибка сервиса"

}
```
**POST /calendar/account - создание пользователькой учётной записи**

req
```json

{

“name”           : “Tom”

}
```
ans

HTTP status 200

{

“user_id” : “2”

}

**GET /calendar/events?sortType={DESC/ASC}- получить список всех событий с указанной сортировкой**

ans

HTTP status 200
```json

[
  {
    "id": 1,
    "name": "meeting with John",
    "date": "2018-10-09",
    "time": "10:00",
    "description": "meeting about our homework",
    "account": "Tom",
    "importanceOfEvent": "MEDIUM"
  }
]
```
**GET /calendar/events/in-period?startDate={start date}&startTime={start time}&endDate={end date}&endTime={end time}
&sort={DESC/ASC}- получить список всех событий за заданный период с start date по end date с указанной сортировкой**

ans

HTTP status 200
```json
[
  {
    "id": 1,
    "name": "meeting with John",
    "date": "2018-10-09",
    "time": "10:00",
    "description": "meeting about our homework",
    "account": "Tom",
    "importanceOfEvent": "MEDIUM"
  }
]
```

**POST  /calendar/events - создание события**

req
```json

{
"name" : "meeting with Mary",
"date" : "2018-10-09",
"time" : "12:00",
"description" : "meeting with my friend to hang out",
"account" : "Tom",
"importanceOfEvent" : "LIGHT"
}
```
ans

HTTP status 200
```json
{
  "id": 1
}
```

HTTP status 400
```json
{
  "code"       : "3",

  "message" : "Данное время уже занято другим событием"

}
```


**PUT /calendar/events/{id} - редактировать/обновить событие (замена указанного на новый)**

req
```json

{
"name" : "meeting with Mary",
"date" : "2018-10-09",
"time" : "12:00",
"description" : "meeting with my friend to hang out",
"account" : "Tom",
"importanceOfEvent" : "LIGHT"
}
```
ans

HTTP status 200 {}

HTTP status 404
```json

{

"code"       : "2",

"message" : "Событие не найдено"

}
```
HTTP status 400
```json

{

  "code"       : "3",

  "message"  : "Данное время уже занято другим событием"

}
```

**GET /calendar/accounts - получить список пользователей**

ans

HTTP status 200
```json

[
"Tom",
"John",
"Bob"
]
```

**DELETE /calendar/events/{id} - удалить событие по id**

ans

HTTP status 200 {}


**GET /calendar/events/{id} - получить событие по id**

ans

HTTP status 200
```json

{
"id": 1,
"name": "meeting with John",
"date": "2018-10-09",
"time": "10:00",
"description": "meeting about our homework",
"account": "Tom",
"importanceOfEvent": "MEDIUM"
}
```
HTTP status 404

{

“code”       : “2”,

“message” : “Событие не найдено”

}