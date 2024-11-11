# Étape de build : Utilisation de Maven pour construire l'application
FROM maven:3.8.1-openjdk-17-slim AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier pom.xml et les dépendances pour une construction optimale
COPY pom.xml /app/
RUN mvn dependency:go-offline

# Copier le code source et construire l'application
COPY src /app/src
RUN mvn clean package -DskipTests

# Étape de runtime : Utilisation d'OpenJDK 17 pour exécuter l'application
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR généré par Maven dans l'image finale
COPY --from=build /app/target/tp-foyer-5.0.0.jar /app/tp-foyer.jar

# Exposer le port que l'application va utiliser
EXPOSE 8082

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "tp-foyer.jar"]
