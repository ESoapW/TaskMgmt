# Task Management System
This project is a simple RESTful web application built with DropWizard, JDBI3, React.js and PostgresQL. It also uses Junit, Assertj, Mockito, Jest, React-testing-library for testing and docker for containerization.

## Access via public address
This project has been deployed on [Oracle Cloud Infrastructure](https://www.oracle.com/cloud/)   
Go to [url](https://www.oracle.com/cloud/) to try out

## Set up and run on your local machine

### 0. Requirements
* docker >= 18.03
* docker-compose   

If you want to build and run without docker
* openjdk 11
* node >= 8.6.0
* npm >= 17

### 1. Set up and start PostgreSQL database
0. Make sure docker is running
1. Run the following commands to start the database
   ```
   cd compose-postgres 
   docker-compose up -d 
   ```
2. Go to `localhost:5050/browser`, password: `changeme`
3. Right-click `Servers`, click `Register` -> `Server`   
   Add a new server with properties:  
   Name: `TaskMgmt`, Host name/address: `postgres`, Port: `5432`
4. Right-click `Database`, click `Create` -> `Database`   
   Add a new database with properties:   
   Database: `TasksRepo`, Owner: `postgres`   
5. Right-click `TasksRepo`, select `Query Tool` to open the query tool   
   Copy and run the sql queries in `initData.sql` to initialize the database   
   The output messages should be "Query returned successfully"    

You have not successfully set up the database.   
After finishing using, return to this directory, run this command to stop it
```
docker-compose down 
```

### 2. Start backend service
Go back to the `TaskMgmt` directory: `cd ..`

#### Run with docker (Recommended)
```
docker build -t taskmgmt-backend:v1 . 
docker run -dit -p 8080:8080 -p 8081:8081 --name taskmgmt-backend-deploy --rm taskmgmt-backend:v1 
```

#### Run without docker
First, go to `config.yml`, change database url `host.docker.internal` to `localhost`   
```
mvn clean install 
java -jar target/TaskMgmt-1.0-SNAPSHOT.jar server config.yml 
```

#### Health Check
To see the application's health enter url `http://localhost:8081/healthcheck`

To check the application running enter url: `http://localhost:8080/tasks`   
You should see the initialized data   
<br/>
   
### 3. Start frontend service
Go to the `frontend` directory: `cd frontend`

#### Run with docker (Recommended)
```
docker build -t taskmgmt-frontend:v1 . 
docker run -dit -p 3000:3000 --name taskmgmt-frontend-deploy --rm taskmgmt-frontend:v1 
```

#### Run without docker
```
npm install 
npm run build 
npm test 
npm start 
```

To access the web app enter url: `http://localhost:3000`   
