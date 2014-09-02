# Todo Test Application

Application deployed on heroku: https://mashapetodo2.herokuapp.com.
Before using application, you have to verify your phone in Twilio system. We are using trial account and that is why you have to do that. Otherwise application will not be able send SMS notification. You can add & verify your number on this page https://www.twilio.com/user/account/phone-numbers/verified

## Rest API

* Main prefix for rest api is "/api". We have 2 services /api/session and /api/todo
* Some API are described below, but this list doesn't show all possibilities. Please look at the code. 
* This API requires cookies. Session token is passing through cookie and you have to make sure that you receiving cookies and sending them back

### /session

* I had to add simple sessions, just for be able send sms notifications to users. Session has only one key - phone. So it means that anyone can create as many sessions as he wants. We don't care.  
* There is validation for some API calls but not for all of them. 

#### Create new session

```
POST /api/session HTTP/1.1
Host: mashapetodo2.herokuapp.com
Content-Type: application/json
Cache-Control: no-cache

{ "phone":"+15122399903" }
```

#### Destroy session

```
DELETE /api/session HTTP/1.1
Host: mashapetodo2.herokuapp.com
Cache-Control: no-cache
```

### /todo

Todo is main entity in application. /todo service allows you make CRUD operations with todo. Also make search through searchbox.io. 

#### Add new todo
```
POST /api/todo HTTP/1.1
Host: mashapetodo2.herokuapp.com
Content-Type: application/json
Cache-Control: no-cache

{ "title":"titlea", "body":"bodya" }
```
#### Update todo
When you making update todo and status is changing from done=false to done=true, application sends SMS notification about that to the phone number from session.
```
PUT /api/todo HTTP/1.1
Host: mashapetodo2.herokuapp.com
Content-Type: application/json
Cache-Control: no-cache

{ "id": "5404da55e4b0de32d0ab0db0", "title": "titlea2", "body": "bodya2", "done": false }
```
#### Remove todo
```
DELETE /api/todo HTTP/1.1
Host: mashapetodo2.herokuapp.com
Content-Type: application/json
Cache-Control: no-cache

{ "id": "5404da55e4b0de32d0ab0db0", "phone": "+15122399902" }
```
#### Get todo list
```
GET /api/todo HTTP/1.1
Host: mashapetodo2.herokuapp.com
Accept: application/json
Cache-Control: no-cache
```
#### Search
You can add search query as url parameter to GET request
```
GET /api/todo?q=test HTTP/1.1
Host: mashapetodo2.herokuapp.com
Accept: application/json
Cache-Control: no-cache
```

## Front End

Front end has been built with ionic framework. Search on todo list doesn't use search from rest api. It is just UI filter.

![](https://dl.dropboxusercontent.com/u/22762275/todo/Screen%20Shot%202014-09-01%20at%203.49.11%20PM.png)
![](https://dl.dropboxusercontent.com/u/22762275/todo/Screen%20Shot%202014-09-01%20at%203.49.19%20PM.png)
![](https://dl.dropboxusercontent.com/u/22762275/todo/Screen%20Shot%202014-09-01%20at%203.49.30%20PM.png)
