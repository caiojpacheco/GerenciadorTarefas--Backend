# Gerenciador de Eventos - Backend

## Visão Geral
Este é o backend do Gerenciador de Eventos, desenvolvido utilizando Spring Boot. Ele fornece serviços de autenticação, cadastro e gerenciamento de eventos, além de seguir boas práticas de segurança e desenvolvimento de APIs RESTful.

## Funcionalidades

### Autenticação de Administrador
- Verificação de credenciais (email e senha).
- Retorna um token JWT para autenticação nos serviços subsequentes.

### Cadastro de Administrador
- Recebe nome, email e senha.
- Armazena a senha de forma criptografada.

### Listagem de Eventos
- Retorna todos os eventos associados a um administrador especificado pelo seu ID.

### Cadastro de Evento
- Permite cadastrar eventos com nome, data, localização, imagem e ID do administrador.

### Atualização de Evento
- Permite atualizar a data ou localização de um evento especificado pelo seu ID.

### Exclusão de Evento
- Remove um evento com base no seu ID.

## Tecnologias Utilizadas
- **Java**: Linguagem principal.
- **Spring Boot**: Framework para desenvolvimento.
- **Spring Security com JWT**: Para autenticação e segurança.
- **Spring Data JPA**: Para interação com o banco de dados.
- **Swagger**: Para documentação da API.

## Configuração do Projeto

### Pré-requisitos
- Java 17 ou superior.
- Maven 3.8 ou superior.
- Banco de dados relacional (MySQL, PostgreSQL, etc.).
