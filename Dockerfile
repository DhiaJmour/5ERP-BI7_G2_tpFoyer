# Utiliser l'image officielle de Java 17 comme base
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR de votre application dans le conteneur
COPY target/tp-foyer-5.0.0.jar /app/tp-foyer.jar

# Exposer le port sur lequel votre application écoute (par défaut 8080 pour Spring Boot)
EXPOSE 8082

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "tp-foyer.jar"]
