# 🚀 Plataforma SaaS Curricular

Aplicação Spring Boot para gestão curricular e execução interativa de fichas de trabalho.

---

## 🛠️ Como Executar o Projeto

Existem duas formas de executar o projeto no ambiente local:

### 🔹 Opção 1: Usando MySQL (Recomendado)
1. Certifique-se de que o serviço **MySQL** está ativo na porta `3306`.
2. O Spring Boot criará a base de dados `saas_db` e as tabelas automaticamente ao iniciar.

---

### 🔹 Opção 2: Modo Demonstrativo com H2 (Sem necessidade de MySQL)
Para testar a aplicação em memória sem abrir o MySQL:

1. Abra o ficheiro `src/main/resources/application.properties`.
2. Substitua as configurações da base de dados por:

```properties
spring.datasource.url=jdbc:h2:mem:saas_db;DB_CLOSE_DELAY=-1
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# Cria e atualiza as tabelas automaticamente na H2
spring.jpa.hibernate.ddl-auto=update