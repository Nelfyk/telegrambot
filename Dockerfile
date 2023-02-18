FROM openjdk:17-jdk-alpine
MAINTAINER Burduzhan Ruslan
VOLUME /main-app
ADD target/telegramBot-0.0.1-SNAPSHOT.jar nelf_telegram_bot.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/nelf_telegram_bot.jar"]