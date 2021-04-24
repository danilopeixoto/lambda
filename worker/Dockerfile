FROM openjdk:11

LABEL description "Lambda worker."
LABEL version "1.0.0"
LABEL maintainer "Danilo Peixoto"

WORKDIR /usr/bin/worker/

COPY build/worker.jar .

CMD ["java", "-jar", "-Dspring.profiles.active=production", "worker.jar"]
