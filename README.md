Product Board assignment

-Application is deployed on Heroku, you can find swagger here: https://product-board-yehor.herokuapp.com/swagger-ui/index.html
-As a DB I'm using MongoDB. DB is deployed on Atlas. 
-Source code has empty application.properties so you won't be able to run it against 'prod' DB.
-Dummy GitHub account was used to have more API calls
-Data are updated every hour
-Expected Date format is "2050-12-01T22:00:00.000Z", you can also find it in tests


Some TODOs which I didn't do in this demo project
-Only small part of code is covered with tests, also mocks should be used
-Test are running agains “prod” DB, test profiles must be used 
-Right now max 100 projects in repo are supported, pages must be used with GitHub api to fetch more
-Scheduler delay and TimeUnit must be configurable via application.properties 

P.S. feel free to contact me on email if you would like to recieve full application.properties:)
