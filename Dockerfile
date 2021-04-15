FROM adoptopenjdk/openjdk11:jdk-11.0.10_9-alpine-slim as build
WORKDIR /workspace/app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
RUN ./gradlew dependencies

COPY src src
RUN ./gradlew build unpack -x test
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)

FROM adoptopenjdk/openjdk11:jre-11.0.10_9-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","net.gendercomics.api.GenderComicsApi"]
