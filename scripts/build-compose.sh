mvn clean package -DskipTests
docker-compose down
docker-compose up --build -d