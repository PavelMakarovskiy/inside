# Inside

## Запуск приложения

### Запуск из docker-образа

Выполнить команду:

```bash
docker-compose up -d
```

Все необходимые образы будут загружены и запущены, после чего приложение будет доступно по адресу: `http://localhost:8095`.

### Запуск из исходников

Перед запуском приложения необходимо запустить базу данных, выполнив:

```docker-compose up -d db```

Далее запустить само приложение с помощью Spring Boot:

```./mvnw spring-boot:run```

После чего приложение будет доступно по адресу: `http://localhost:8095`.

## Использование приложения

### Создать пользователя

Выполнить http запрос:

```http request
POST /auth HTTP/1.1
Host: localhost:8095
Content-Type: application/json

{
  "username": "Lesia",
  "password": "Perfect"
}
```
В случае успеха в ответ придёт:

```
User Lesia added successfully.
```

### Получить токен доступа

Выполнить http запрос:

```http request
POST /login HTTP/1.1
Host: localhost:8095
Content-Type: application/json

{
  "username": "Lesia",
  "password": "Perfect"
}
```

Если параметры авторизации верны, то в ответ придёт токен доступа:
```json
{
  "token": "<token>"
}
```

### Отправить или получить сообщения

Что бы отправить или получить сообщения необходимо сначала установить websocket соединение:

```http request
GET /message HTTP/1.1
Connection: Upgrade
Upgrade: websocket
Host: localhost:8095
Authorization: Bearer_<token>
Sec-WebSocket-Key: SGVsbG8sIHdvcmxkIQ==
Sec-WebSocket-Version: 13
```

Где в заголовок `Authorization` передается токен доступа с префиксом `Bearer_`.

После создания соединения можно выполнять обмен сообщениями:

#### Отправить сообщение

Передать следующее содержимое:

```json
{
  "username": "Pavel",
  "message": "Go ahead"
}
```

После чего `message` будет сохранено для пользователя `Pavel`.

#### Получить N последних сообщений

Передать следующее содержимое:

```json
{
  "username": "Pavel",
  "message": "history N"
}
```
Где `N` - количество последних сообщений пользователя.

В ответ сервер отправит список `N` последних сообщений пользователя:

```json
["Go ahead"]
```

## Запуск тестов

Для запуска тестов необходимо выполнить команду:

```bash
./mvnw test
```# inside
