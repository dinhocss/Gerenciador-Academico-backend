# **Título e Sobre**: 

Gerenciador Acadêmico - Sistema para gestão de alunos e turmas da UFF.

## Tecnologias Utilizadas
* **Java 21**
* **Spring Boot 3** (Web, Data JPA, Validation)
* **Banco de Dados:** H2
* **Documentação:** Swagger
* **Arquitetura:** REST API com padrão DTO e Camada de Serviço
* **Testes:** JUnit 5 e Mockito

## Como rodar o Backend
1. Clone o repositório:
   ```bash
   git clone [https://github.com/SEU-USUARIO/gerenciador-academico.git](https://github.com/SEU-USUARIO/gerenciador-academico.git)

2. Acesse a pasta do projeto:
   ```
   cd gerenciador-academico
3. Execute a aplicação via Maven:
   ```
   mvn spring-boot:run
   
4. O servidor iniciará na porta 8080.

## Testes
Para rodar os testes unitários e de integração:
   ```
   mvn test
   ```

## Checklist de Desenvolvimento:
* [] Cadastro de Alunos
   
* [] Listagem de Turmas
 
* [] Login e Segurança

* [x] Documentação via Swagger

* [x] Testes
 
* [x] Configuração do Projeto
