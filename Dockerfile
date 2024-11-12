# Utiliser une image de base Java
FROM openjdk:11-jdk-alpine

# Exposer le port de l'application
EXPOSE 8082

# Ajouter le fichier JAR de l'application
ADD target/5ERP-BI7_G2_tpFoyer-1.0.jar 5ERP-BI7_G2_tpFoyer-1.0.jar

# Définir le point d'entrée
ENTRYPOINT ["java", "-jar", "/5ERP-BI7_G2_tpFoyer-1.0.jar"]