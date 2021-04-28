FROM openjdk:11

LABEL description "Lambda web API."
LABEL version "1.0.0"
LABEL maintainer "Danilo Peixoto"

WORKDIR /usr/bin/api/

COPY . .

RUN ./gradlew build

EXPOSE 8080

CMD ["java", "-jar", "-Dspring.profiles.active=production", "build/libs/api-1.0.0.jar"]
