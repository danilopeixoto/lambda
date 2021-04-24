FROM openjdk:11

LABEL description "Lambda web API."
LABEL version "1.0.0"
LABEL maintainer "Danilo Peixoto"

WORKDIR /usr/bin/api/

COPY build/api.jar .

EXPOSE 8080

CMD ["java", "-jar", "-Dspring.profiles.active=production", "api.jar"]
