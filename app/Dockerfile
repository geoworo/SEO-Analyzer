FROM eclipse-temurin:19-jdk

WORKDIR ./

COPY ./ .

RUN gradle installDist

CMD ./build/install/app/bin/app
