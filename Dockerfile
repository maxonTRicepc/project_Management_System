FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /project-management-system
# Копируем все файлы в контейнер
COPY . .
# Собираем проект
RUN mvn -f /project-management-system/pom.xml clean package

FROM eclipse-temurin:17-jre-alpine
COPY --from=build /project-management-system/target/*.jar /project-management-system/*.jar
# Запускаем JAR-файл
ENTRYPOINT ["java", "-jar", "/project-management-system/*.jar"]