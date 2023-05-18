LABEL org.opencontainers.image.source=https://github.com/BitByeBit/CalendarParserCoreBE

FROM eclipse-temurin:17-focal

ENV SPRING_PORT=8081

WORKDIR /srv

COPY target/calendar-parser-core-be.jar calendar-parser-core-be.jar

EXPOSE ${SPRING_PORT}
