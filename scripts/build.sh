mvn clean package -DskipTests
docker rm nelf_telegram_bot
docker build -t nelf_telegram_bot .
docker run --name nelf_telegram_bot --network=nelf-network -it -p 127.0.0.1:7776:8080 nelf_telegram_bot