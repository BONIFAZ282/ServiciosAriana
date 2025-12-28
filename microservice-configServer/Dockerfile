# Etapa de construcción
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build

# Primero copiar el pom.xml del padre
COPY ../pom.xml ./pom.xml

# Copiar el pom.xml del config-server
COPY pom.xml ./microservice-configServer/pom.xml

# Copiar el código fuente
COPY src ./microservice-configServer/src

# Construir desde el directorio del módulo
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