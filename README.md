# LiterAlura - Catálogo de Livros

![Status](https://img.shields.io/badge/status-conclu%C3%ADdo-brightgreen)

## Sobre o Projeto

LiterAlura é uma aplicação de console desenvolvida em Java com o framework Spring Boot. O projeto consiste em um catálogo de livros interativo que consome a API pública [Gutendex](https://gutendex.com/) para buscar informações sobre livros e autores, e persiste esses dados em um banco de dados PostgreSQL.

Este projeto foi desenvolvido como parte do desafio de programação do programa Alura ONE (Oracle Next Education).

## Funcionalidades

A aplicação oferece uma interface de texto via console com as seguintes opções:

1. Buscar livro pelo título: Pesquisa por um livro na API Gutendex e salva o livro e seu autor no banco de dados local, evitando duplicatas.

2. Listar livros registrados: Exibe todos os livros que foram salvos no banco de dados.

3. Listar autores registrados: Mostra todos os autores salvos, junto com seus anos de nascimento/falecimento e os livros de sua autoria registrados no banco.

4. Listar autores vivos em um determinado ano: Permite ao usuário inserir um ano e exibe uma lista de autores que estavam vivos naquele período.

5. Listar livros em um determinado idioma: Filtra e exibe os livros registrados em um idioma específico (ex: português, inglês, etc.).

## Tecnologias Utilizadas

- Linguagem: Java 17

- Framework: Spring Boot 3

- Persistência de Dados: Spring Data JPA / Hibernate

- Banco de Dados: PostgreSQL

- Cliente HTTP: Java 11+ HTTP Client

- Manipulação de JSON: Jackson Databind

- Gerenciador de Dependências: Maven

## Como Executar o Projeto

Siga os passos abaixo para configurar e executar o projeto em seu ambiente local.

**Pré-requisitos**
- [Java Development Kit (JDK) 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) ou superior.
- Apache Maven instalado.
- Um servidor de banco de dados [PostgreSQL](https://www.postgresql.org/download/ ativo.
- Sua IDE de preferência (IntelliJ, Eclipse, VS Code).

**Passos para Configuração**
1. **Clone o repositório:**
2. ```Bash
   git clone https://github.com/Tavobrandao/Challenge-LiterAlura.git
   cd literalura
   ```
   
2. **Crie o banco de dados:**
   Acesse seu cliente PostgreSQL (como o `psql` ou DBeaver) e crie um novo banco de dados para a aplicação.
    ```sql
    CREATE DATABASE literalura_db;
    ```

3. **Configure as credenciais do banco:**
    Abra o arquivo `src/main/resources/application.properties` e altere as seguintes propriedades com as suas informações de usuário e senha do PostgreSQL:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
    spring.datasource.username=seu_usuario_aqui
    spring.datasource.password=sua_senha_aqui
    ```
    O Spring Data JPA, com a configuração `spring.jpa.hibernate.ddl-auto=update`, criará as tabelas automaticamente na primeira vez que a aplicação for executada.

4. **Execute a aplicação:**
    Você pode executar o projeto de duas maneiras:

    * **Pela sua IDE:** Encontre a classe `LiteraluraApplication.java` e execute o método `main`.
    * **Via linha de comando com Maven:**
        ```bash
        mvn spring-boot:run
        ```

## Como Usar

Após iniciar a aplicação, um menu interativo aparecerá no console. Basta digitar o número da opção desejada e pressionar `Enter` para interagir com o catálogo.

LiterAlura - Escolha uma opção:
1- Buscar livro pelo título
2- Listar livros registrados
3- Listar autores registrados
4- Listar autores vivos em um determinado ano
5- Listar livros em um determinado idioma
0- Sair


## Autor

Desenvolvido por **Gustavo Brandão**

[![LinkedIn](https://img.shields.io/badge/linkedin-%230077B5.svg?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/gustavobrandaobr/)
