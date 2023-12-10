FROM gradle:jdk20

WORKDIR /app

COPY / .

RUN ./gradlew installDist

EXPOSE 7070

CMD ./build/install/app/bin/app
