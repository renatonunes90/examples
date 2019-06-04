# Imagem base
FROM node:8-alpine as angular-registry

# Pasta de trabalho
WORKDIR /usr/src/app

# Copia os arquivos para dentro do container
COPY package.json ./

# Install angular CLI
RUN npm install
RUN npm install -g @angular/cli@7.2.1

# Bundle app source
COPY . .

# Compila
RUN ng build --configuration=dsv --base-href="/registry/"
#RUN ng build --env=dsv

# Stage 2 - Publish nginx
FROM nginx:1.13.12-alpine

COPY --from=angular-registry /usr/src/app/dist /usr/share/nginx/html

COPY ./nginx.conf /etc/nginx/conf.d/default.conf