FROM node:18

# add `node_modules/.bin` to $PATH
ENV PATH node_modules/.bin:$PATH

COPY package.json .
COPY package-lock.json .
RUN npm install

COPY . .
RUN npm run build
RUN npm test

EXPOSE 3000

ENTRYPOINT ["npm", "start"]