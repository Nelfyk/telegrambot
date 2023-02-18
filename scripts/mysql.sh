docker run --name test-mysql -p 7777:3306 \
-e MYSQL_ROOT_PASSWORD=bestuser \
-e MYSQL_USER=bestuser \
-e MYSQL_PASSWORD=bestuser \
-e MYSQL_DATABASE=telegram_bot \
-d mysql:latest