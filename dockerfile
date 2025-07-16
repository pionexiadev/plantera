# Utiliser l’image Java 21 (compatible avec Spring Boot 3.2+)
FROM openjdk:21-jdk-slim

# Créer un dossier dans le conteneur
WORKDIR /app

# Copier le fichier jar généré par Maven
COPY target/agriculture-backend-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port 8080
EXPOSE 8080

# Lancer l’app
ENTRYPOINT ["java", "-jar", "app.jar"]
