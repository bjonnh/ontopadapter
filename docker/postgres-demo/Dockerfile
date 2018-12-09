# Source: https://github.com/docker-library/healthcheck/blob/master/postgres/Dockerfile.alpine
FROM postgres:11-alpine
RUN mkdir -p /docker-entrypoint-initdb.d
COPY demo.sql /docker-entrypoint-initdb.d

