# 🚀 Plataforma SaaS Curricular

Plataforma Web desenvolvida em **Spring Boot (Java 21)** focada na criação, gestão e resolução de Fichas/Testes Escolares de forma interativa. É pensada para Formadores (professores) e Alunos.

## ✨ Funcionalidades Principais

### Para Formadores
* **Gestão de Instituições e Disciplinas:** Criação de agrupamentos escolares e convite de outros professores (via código de acesso ou email para co-administradores).
* **Banco de Perguntas:** Gestão centralizada de perguntas reutilizáveis:
  * Suporte a **Texto Livre**, **Escolha Múltipla** (com gestão de alíneas certas/erradas) e **Verdadeiro/Falso**.
  * **Anexos Multimédia:** Upload de imagens e vídeos locais acoplados ao enunciado de cada pergunta.
* **Fichas de Trabalho:** Composição de testes selecionando perguntas do banco.
* **Integração Moodle:**
  * Importação de Perguntas a partir de ficheiros XML do Moodle.
  * Exportação de Fichas concluídas para formato XML compatível com Moodle.

### Para Alunos
* **Acesso Simples:** Os alunos podem aceder diretamente a uma ficha através de um Código de Acesso / Link único gerado pelo sistema (sem necessidade de criação de contas).
* **Portal de Resolução:** Ecrã dividido para leitura (texto de apoio) e respostas lado a lado.
* **Cronómetro:** Registo do tempo de execução do teste pelo aluno em tempo real.

---

## 🛠️ Stack Tecnológica
* **Backend:** Java 21, Spring Boot 3, Spring Security, Spring Data JPA, Hibernate.
* **Frontend:** Thymeleaf, HTML5, CSS3 Nativo (Sem frameworks JS externos pesados para UI).
* **Base de Dados:** MySQL (Produção) / H2 (Memória para testes).

---

## 🚀 Como Executar o Projeto

Existem duas formas de executar o projeto no ambiente local:

### 🔹 Opção 1: Usando MySQL (Recomendado)
1. Certifique-se de que o serviço **MySQL** está ativo na porta `3306`.
2. Confirme que as credenciais no `application.properties` (utilizador: `root`, password: *(vazio)*) correspondem ao seu sistema.
3. Execute o comando Maven na raiz do projeto (no terminal ou diretamente na interface do IDE):
   ```bash
   ./mvnw spring-boot:run
   ```
4. O Spring Boot criará a base de dados `saas_db` e todas as relações automaticamente ao iniciar. A aplicação arranca em `http://localhost:8080`.

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
   spring.jpa.hibernate.ddl-auto=update
   ```

### 📂 Armazenamento / Diretório de Uploads
A plataforma permite o upload de imagens e vídeos (anexos das perguntas). 
* Estes ficheiros são guardados localmente na pasta `uploads/perguntas/` na raiz do projeto.
* A pasta é criada de forma automática assim que for efetuado o primeiro upload através do sistema.

---

## 🧪 Testar o Circuito Completo
Para validar se todo o fluxo da plataforma está funcional, siga estes passos:
1. **Registo:** Aceda a `http://localhost:8080/registo` e crie uma conta com perfil Formador.
2. **Setup Base:** Vá ao separador *Instituições* e crie uma escola. Em seguida, aceda ao detalhe da escola e crie uma *Disciplina*.
3. **Gerir Perguntas:** Vá ao separador *Perguntas*, crie algumas do tipo *Escolha Múltipla*. Pressione o botão amarelo **"Opções"** na listagem para adicionar as alíneas. Pressione o botão azul **"Anexos"** para inserir ficheiros de media, se desejar.
4. **Criar a Ficha:** Aceda a *Fichas*, crie uma nova ficha definindo um texto de apoio longo. Adicione as perguntas recém-criadas e, por fim, clique em "Publicar Ficha" associando-a à Disciplina criada no Passo 2.
5. **Portal do Aluno:** No painel da disciplina, copie o link público da ficha (ícone `Copiar Link`). Faça logout na plataforma (ou abra o link em Navegação Anónima) para visualizar o Portal do Aluno, ver o cronómetro ativo, as opções formatadas em *Radio Buttons* e os anexos perfeitamente integrados no enunciado!

---
*(Desenvolvido com auxílio de Antigravity AI)*