# API DE PLANETAS STAR WARS

**Autor**: Victor Vieira Paulo

## 1 - Endpoints disponíveis:

    - POST <host>/api/star-wars/planets
        - Request body: {"name": "<planet_name>", "climate": "<planet_climate>", "terrain": "<planet_terrain>"}
        - Response body: {"id:"<mongo_db_id>" ,"name": "<planet_name>", "climate": "<planet_climate>", "terrain": "<planet_terrain>", "numberOfFilmAppearences": <number>}
        - HTTP status code para sucesso: 201 (CREATED)

    - GET <host>/api/star-wars/planets
        - Query parameters opcionais para filtras planetas: "name": "<planet_name>", "climate": "<planet_climate>", "terrain": "<planet_terrain>"
        - Response body: {"id:"<mongo_db_id>" ,"name": "<planet_name>", "climate": "<planet_climate>", "terrain": "<planet_terrain>", "numberOfFilmAppearences": <number>}
        - HTTP status para code sucesso: 200 (OK)

    -GET <host>/api/star-wars/planets/{planet_mongodb_id}
        - Response body: {"id:"<mongo_db_id>" ,"name": "<planet_name>", "climate": "<planet_climate>", "terrain": "<planet_terrain>", "numberOfFilmAppearences": <number>}        
        - HTTP status code para sucesso: 200 (OK)
        - HTTP status code para planeta não existente (falha): 404 (NOT FOUND)

    -DELETE <host>/api/star-wars/planets/{planet_mongodb_id}
        - HTTP status code para sucesso: 204 (NO CONTENT)
        - HTTP status code para planeta não existente (falha): 404 (NOT FOUND)

## 2 - Como executar localmente:

Para rodar a aplicação localmente é necessário prover uma instância **MongoDB** para ser utilizada como base de dados.

Recomendo a execução através do **maven spring boot plugin**, mediante uso do seguinte script no diretório raíz do projeto:

    mvn spring-boot:run -Dspring-boot.run.arguments="--spring.data.mongodb.database=<database name> --spring.data.mongodb.host=<database host> --spring.data.mongodb.port=<database port>"

Caso a base dados requeira autenticação, também será necessário passar como parâmetro as informações de autenticação.

O script em questão foi testado na versão 3.6.3 do maven.

## 3 - Como rodar os testes:

Para rodar os testes basta executar `mvn test` no diretório raíz do projeto.

A aplicação contém os seguintes testes:
- **Testes unitários**, que espelham a estrutura de pacotes e o nome das classes de produção
- Uma **suite de testes end-to-end**. Estes testes rodam contra uma **instância MongoDB embedada**, e testam todos os endpoints da aplicação - incluindo a interação com a **StarWars API**.

## 4 - Descrição das opções tomadas:

Embora os requisitos permitissem uma solução simples, optei por tratar a aplicação como a primeira iteração de um projeto real que irá crescer e se complexificar.

Decidi então utilizar boas práticas de desenvolvimento para controlar o acoplamento e facilitar o crescimento futuro da aplicação. 

- Design de código:
    - Separação entre DTOS, modelos de domínio e modelos de persistência
    - Separação das camadas da aplicação.
    - Aplicação do princípio de **Inversão de Dependência** para desacoplar os Services da camada de Persistência e da api externa.
    - Aplicação do princípio de **Responsabilidade Única** para separar os endpoints e lógicas de service em classes diferentes.
    

- Testes:
  - Desenvolvimento com TDD. 
  - Testes unitários cobrem 98% das linhas de código.
  - testes end-to-end, que cobrem inclusive a integração com a API externa. Os testes end-to-end são interdependentes, e verificam os requisitos que foram colocados para o projeto. A abordagem mais comum seria criar testes de integração independentes para cada endpoint, mas não tive tempo para fazê-lo.