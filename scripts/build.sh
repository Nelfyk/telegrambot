mvn clean package -DskipTests
docker rm nelf_telegram_bot
docker build -t nelf_telegram_bot .
docker run --name nelf_telegram_bot --network=nelf-network -it -p 8080:8080 nelf_telegram_bot