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

FROM ubuntu:jammy

RUN apt-get -y update && \
    apt-get -y upgrade && \
    apt-get install -y curl wget gnupg apt-transport-https ca-certificates software-properties-common && \
    rm -rf /var/lib/apt/lists/* && \
    curl -sL https://deb.nodesource.com/setup_24.x | bash - && \
    mkdir -p /etc/apt/keyrings && wget -O - https://packages.adoptium.net/artifactory/api/gpg/key/public | tee /etc/apt/keyrings/adoptium.asc && \
    echo "deb [signed-by=/etc/apt/keyrings/adoptium.asc] https://packages.adoptium.net/artifactory/deb $(awk -F= '/^VERSION_CODENAME/{print $2}' /etc/os-release) main" | tee /etc/apt/sources.list.d/adoptium.list && \
    apt-get -y update && \
    apt-get -y install temurin-21-jdk nodejs nginx

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

