FROM gradle:7.2.0-jdk17 as builder

COPY ./ /app/
WORKDIR /app
RUN gradle bootJar

FROM arm64v8/amazoncorretto:17-alpine as extractor
COPY --from=builder /app/build/libs/otel-app-one.jar ./
COPY --from=builder /app/otel.yaml ./
RUN java -Djarmode=layertools -jar otel-app-one.jar list
RUN java -Djarmode=layertools -jar otel-app-one.jar extract

FROM arm64v8/amazoncorretto:17-alpine
COPY --from=extractor dependencies/ ./
COPY --from=extractor snapshot-dependencies/ ./
COPY --from=extractor spring-boot-loader/ ./
COPY --from=extractor application/ ./
COPY --from=extractor otel-app-one.jar ./
COPY --from=extractor otel.yaml ./

RUN apk update
RUN apk --no-cache add curl

RUN curl -L https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.29.0/opentelemetry-javaagent.jar --output opentelemetry-javaagent-all.jar

ENTRYPOINT [ "java", "-javaagent:opentelemetry-javaagent-all.jar", "-Dotel.javaagent.configuration-file=otel.yaml","-jar", "otel-app-one.jar" ]
