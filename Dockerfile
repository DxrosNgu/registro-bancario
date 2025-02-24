FROM amazoncorretto:21-alpine-jdk
WORKDIR /app

ADD ./target/registro-bancario-0.0.1-SNAPSHOT.jar registro-bancario.jar

ENTRYPOINT [ "java", "-jar", "registro-bancario.jar" ]
