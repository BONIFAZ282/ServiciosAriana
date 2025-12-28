# Etapa de construcción
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar pom.xml del padre
COPY ../pom.xml ./pom.xml

# Copiar el módulo config server
COPY pom.xml ./microservice-configServer/pom.xml
COPY src ./microservice-configServer/src

# Construir solo este módulo
WORKDIR /app/microservice-configServer
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/microservice-configServer/target/microservice-configServer-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "app.jar"]