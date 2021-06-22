BOOKMAKER APP
===

#### Frontend of Bookmaker App - a game for friends to predict the results of matches for football competitions. There is [backend](https://github.com/sitkositkowski/bookmaker-app).

#### Deployed version of application on Heroku:
[Backend](https://bookmaker-app-backend.herokuapp.com/) \
[Frontend](https://bookmaker-app.herokuapp.com/)

Table of Contents
---
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Endpoints](#endpoints)

General info
---
The Redudo is made for managing your books. After sign in via Google you can search for them in the database
(if there is not the book, app use [Google Books Api](https://developers.google.com/books "Google Books Api")).\
Then you can add the book to one of three lists:
* books to read (to **RE**ad)
* books already reading (**DU**ring)
* done books (**DO**ne)

Technologies
---
Project is created with:
* Java (version 11)
* Spring
* Hibernate
* RestApi

Setup
---
To run project you have to set environment variables:
* variables necessary to send automatic emails
    * MAIL_USERNAME
    * MAIL_PASSWORD

* variable necessary for OAuth2 authorization
    * CLIENT_ID (same as for the backend app)

To get it you have to create app in [Google Developer Console](https://console.cloud.google.com/apis/credentials)


Endpoints
---
###### Endpoint's API documentation is available at [Swaager](https://bookmaker-app-backend.herokuapp.com/swagger-ui.html).
