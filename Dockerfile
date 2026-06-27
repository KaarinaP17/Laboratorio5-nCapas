FROM ubuntu:latest
LABEL authors="Kaar"

ENTRYPOINT ["top", "-b"]

# ---------- Etapa 1: build ----------
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Damos permisos de ejecución antes de usarlo
RUN chmod +x mvnw

# Ahora esto debería funcionar sin problemas
RUN ./mvnw dependency:go-offline -B

# Copiamos el resto del código y compilamos
COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# ---------- Etapa 2: runtime ----------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Crea un usuario sin privilegios para correr el proyecto
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copia el .jar que se obtuvo en la etapa 1 (build)
COPY --from=build /app/target/*.jar app.jar

#Exponemos el puerto
EXPOSE 8080

# Comando que se va a ejecutar cuando se levante el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]
