FROM eclipse-temurin:17-focal

LABEL org.opencontainers.image.source="https://github.com/BitByeBit/CalendarParserCoreBE"

ENV SPRING_PORT=8081

WORKDIR /srv

COPY target/calendar-parser-core-be.jar calendar-parser-core-be.jar

EXPOSE ${SPRING_PORT}
