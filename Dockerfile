FROM eclipse-temurin:17-jdk

WORKDIR /app
COPY . /app

# Ajouter les droits d'exécution sur le wrapper Maven
RUN chmod +x mvnw

# Compiler le projet
RUN ./mvnw clean package -DskipTests

# Exposer le port utilisé par Spring Boot
EXPOSE 8080

# Lancer l'application avec le bon nom du JAR
CMD java -jar target/ocpimport-0.0.1-SNAPSHOT.jar
