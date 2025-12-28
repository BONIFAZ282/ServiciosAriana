# Etapa de construcción
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build

# Copiar el POM padre
COPY pom.xml ./pom.xml

# Copiar el POM del módulo config-server
COPY microservice-configServer/pom.xml ./microservice-configServer/pom.xml

# Copiar el código fuente del config-server
COPY microservice-configServer/src ./microservice-configServer/src

# IMPORTANTE: Instalar el POM padre primero
RUN mvn -N install -DskipTests

# Construir el módulo config-server
WORKDIR /build/microservice-configServer
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar el JAR generado
COPY --from=build /build/microservice-configServer/target/microservice-configServer-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto
EXPOSE 8888

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]