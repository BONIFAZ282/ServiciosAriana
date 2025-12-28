# Etapa de construcción
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build

# Copiar archivos necesarios
COPY pom.xml .
COPY src ./src

# Construir el proyecto
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar el JAR generado
COPY --from=build /build/target/microservice-configServer-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto
EXPOSE 8888

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]