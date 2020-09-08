### STAGE 1: Build ###
FROM node:12.7-alpine AS build
WORKDIR /usr/src/app
COPY package.json ./
#RUN npm cache clean -f
#RUN npm clean
RUN npm install
COPY . .
ENV env=prod
RUN npm run build:${env}
### STAGE 2: Run ###
FROM nginx:1.17.1-alpine
COPY --from=build /usr/src/app/dist/monitor-view /usr/share/nginx/html/
