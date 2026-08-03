# Plataforma SaaS de Gestão Curricular

Uma plataforma web construída em **Java Spring Boot** para Formadores criarem e gerirem as suas próprias **Fichas de Trabalho** e **Bancos de Perguntas**.

## Funcionalidades (Fase 1 - MVP Base)
- **Registo e Autenticação Segura**: Encriptação de passwords com BCrypt.
- **Isolamento de Dados**: Cada formador apenas tem acesso às Fichas e Perguntas que ele próprio criou.
- **CRUD Completo**: Criar, Ler, Atualizar e Apagar Fichas de Trabalho e Perguntas.
- **Dashboard**: Painel de controlo principal e navegação simplificada.

---

## Como Executar e Testar Localmente

O projeto está configurado para ser incrivelmente fácil de testar, suportando duas abordagens de Base de Dados sem necessidade de alterar código.

### Opção 1: Teste Rápido (Modo de Memória - H2)
A forma mais rápida de testar a aplicação se não tiveres um servidor de Base de Dados instalado:
1. Abre o ficheiro `src/main/resources/application.properties`.
2. Comenta todas as linhas relacionadas com a ligação ao MySQL (linhas `spring.datasource.url`, `username`, `password`, `driver-class-name`).
3. Executa a aplicação. O Spring Boot detetará a ausência do MySQL e iniciará automaticamente a base de dados **H2 em memória**. As tabelas serão criadas instantaneamente.
4. Vai a `http://localhost:8080/` e começa a testar!

### Opção 2: Teste Real (Base de Dados MySQL)
A abordagem recomendada para manter os dados persistidos:
1. Certifica-te de que o teu servidor MySQL está a correr (ex: XAMPP, WAMP, ou serviço nativo) na porta `3306`.
2. As credenciais por defeito estão no `application.properties` (User: `root`, Password: `123456`).
3. O Spring Data JPA / Hibernate encarrega-se de criar a base de dados `saas_db` e todas as tabelas (graças à propriedade `ddl-auto=update`).
4. Executa o projeto no teu terminal:
   ```bash
   .\mvnw.cmd spring-boot:run
   ```

---

## Resolução de Problemas (Troubleshooting)

**Erro comum em Testes (Null/Transient Exception na Ficha/Pergunta):**
Foi introduzida uma melhoria no `SecurityUtils`. O utilizador agora é injetado como um *Bean* e os seus dados são recarregados diretamente da Base de Dados a cada chamada. Isto impede falhas nos Testes de Integração e Unitários ao usar Mocks (`@WithMockUser`), garantindo que o Hibernate deteta a entidade com o ID correto e não lança *PropertyValueException*.
