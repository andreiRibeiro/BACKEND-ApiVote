# BACKEND-ApiVote

Esta API tem como objetivo gerir o processo de votação (criar pauta, abrir sessão de votação, votar e computar os votos).

O serviço é executado em Spring e conta com endpoint's RestFull.

A API possui integração com validador de CPFs e utiliza banco dados Postgres.

Utiliza JPA para as operações com banco dados, e esta documentada com Swagger.

#### 1) Formas de execução do sistema e testes

   ##### Executar com Jar 
   ><code>java -jar icarros-0.0.1-SNAPSHOT.jar</code>

   ##### Executar testes 
   ><code>mvn test</code>

#### 2) Interface com exemplo de requisições (Postman)
><code>[PostmanCollection.json](Sicredi.postman_collection.json)</code>

#### 3) Swagger
><code>https://api-vote-sicredi.herokuapp.com/vote/api/v1/swagger-ui.html</code>

#### 4) Imagem DockerHub
><code>docker pull andreiaar/sicredi:latest</code>
