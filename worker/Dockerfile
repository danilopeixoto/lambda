FROM openjdk:11

LABEL description "Lambda worker."
LABEL version "1.0.0"
LABEL maintainer "Danilo Peixoto"

WORKDIR /usr/bin/worker/

COPY . .

RUN chmod +x gradlew && ./gradlew build

CMD ["java", "-jar", "-Dspring.profiles.active=production", "build/libs/worker-1.0.0.jar"]
