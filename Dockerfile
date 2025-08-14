FROM gradle:jdk21-noble AS java-builder

WORKDIR /build
COPY . .
RUN ./gradlew
RUN ./gradlew build -x test

FROM node:24.4.1 AS webapp-builder

WORKDIR /build
COPY webapp/package.json webapp/package-lock.json* ./
RUN npm ci

COPY webapp/ ./
RUN npm run build

FROM eclipse-temurin:21-jre-jammy

RUN apt-get -y update && \
    apt-get -y upgrade && \
    apt-get install -y curl wget gnupg apt-transport-https ca-certificates software-properties-common && \
    rm -rf /var/lib/apt/lists/* && \
    curl -sL https://deb.nodesource.com/setup_24.x | bash - && \
    apt-get -y update && \
    apt-get -y install nodejs nginx

COPY ./relang/docker/nginx-prod/default.conf /etc/nginx/sites-available/default

RUN mkdir /app

COPY ./relang/start.sh /app/start.sh

#
# Webapp setup.
#

WORKDIR /app/webapp

COPY --from=webapp-builder /build/public ./public
COPY --from=webapp-builder /build/.next/standalone ./
COPY --from=webapp-builder  /build/.next/static ./.next/static

WORKDIR /app/server
COPY --from=java-builder /build/build/libs/app-0.0.1-SNAPSHOT.jar .

EXPOSE 9090

ENV HOSTNAME="0.0.0.0"

ENV NODE_ENV=production

WORKDIR /app/webapp
CMD []

ENTRYPOINT ["bash", "-c", "/app/start.sh"]

