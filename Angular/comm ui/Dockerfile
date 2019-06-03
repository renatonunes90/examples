# Imagem base
FROM node:8-alpine as angular-comm

# Pasta de trabalho
WORKDIR /usr/src/app

# Copia os arquivos para dentro do container
COPY package.json ./

# Install angular CLI
RUN npm install
RUN npm install -g @angular/cli@7.2.1

# Bundle app source
COPY . .

# Compila e cria o componente
RUN npm run package

# Aponta para o repositório do nexus
#RUN npm run set_reg

# Publica o componente
#RUN npm publish ./dist/comm-registry/comm-registry-0.0.2.tgz

# Compila casca
RUN ng build --configuration=dev --base-href="/comm/"

# Stage 2 - Publish nginx
FROM nginx:1.13.12-alpine

COPY --from=angular-comm /usr/src/app/dist /usr/share/nginx/html

COPY ./nginx.conf /etc/nginx/conf.d/default.conf
