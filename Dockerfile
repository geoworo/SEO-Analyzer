FROM eclipse-temurin:19-jdk

WORKDIR /app

COPY /app

RUN gradle installDist

CMD ./build/install/app/bin/app