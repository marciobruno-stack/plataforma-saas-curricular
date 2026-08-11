# Documento de Especificação de Requisitos

Este documento descreve os **Requisitos Funcionais (RF)** e **Não Funcionais (RNF)** da Plataforma SaaS Curricular, alinhados com o planeamento atual e as fases desenvolvidas no estágio.

---

## 1. Requisitos Funcionais (RF)
*Os requisitos funcionais descrevem as funcionalidades que o sistema deve fornecer (o que o sistema faz).*

**Fase 1: Espaço Pessoal e Gestão Curricular**
* **RF01 - Gestão de Utilizadores:** O sistema deve permitir o registo de novos Formadores e a autenticação segura (Login/Logout).
* **RF02 - Isolamento de Dados:** Cada formador deve ter um espaço privado onde apenas ele pode visualizar, criar, editar e apagar os seus próprios conteúdos.
* **RF03 - Gestão de Fichas de Trabalho:** O formador deve conseguir gerir (CRUD) Fichas de Trabalho.
* **RF04 - Gestão do Banco de Perguntas:** O formador deve conseguir gerir (CRUD) perguntas individuais e associá-las às suas fichas.

**Fase 2: Arquitetura SaaS Institucional**
* **RF05 - Criação de Instituições:** O sistema deve permitir que um formador crie uma Escola (Instituição), gerando automaticamente um Código de Acesso único.
* **RF06 - Adesão de Colegas:** O sistema deve permitir que um formador entre numa Escola já existente inserindo o respetivo Código de Acesso.
* **RF07 - Gestão de Disciplinas:** Dentro de cada Instituição, os formadores devem conseguir criar Disciplinas (ex: "Matemática 10º Ano").
* **RF08 - Publicação e Partilha:** O formador deve poder publicar uma Ficha de Trabalho do seu espaço pessoal para uma Disciplina pública da sua Instituição.
* **RF09 - Clonagem de Fichas:** O formador deve poder clonar/copiar uma Ficha de Trabalho partilhada na Escola para o seu próprio espaço pessoal, permitindo editá-la sem afetar o original.

**Fase 3 & 4: Portal do Aluno e Ferramentas Extra (Próximas Fases)**
* **RF10 - Execução Interativa:** Os alunos devem poder aceder às Fichas e resolvê-las numa interface *Split-Screen* (metade com a pergunta, metade com material de apoio).
* **RF11 - Cronometragem de Execução:** O sistema deve registar o tempo que cada aluno demora a resolver a Ficha.
* **RF12 - Geração de PDFs:** O formador deve poder exportar facilmente uma Ficha de Trabalho e respetivas perguntas para um formato PDF formatado.
* **RF13 - Importação Moodle:** O sistema deve suportar a importação de ficheiros ZIP/XML compatíveis com a estrutura do Moodle.

---

## 2. Requisitos Não Funcionais (RNF)
*Os requisitos não funcionais definem os critérios de qualidade do sistema (como o sistema se comporta).*

**Segurança e Privacidade**
* **RNF01 - Encriptação:** Todas as palavras-passe dos utilizadores têm de ser encriptadas de forma unidirecional com o algoritmo BCrypt, impedindo leitura direta na base de dados.
* **RNF02 - Controlo de Acesso:** Apenas utilizadores devidamente autenticados pelo Spring Security podem aceder às rotas privadas e API da plataforma.
* **RNF03 - Multi-Tenancy (Isolamento Lógico):** Numa mesma tabela de Base de Dados, os dados da "Escola A" e da "Escola B" não se podem cruzar. Um formador da "Escola A" não tem permissão para aceder à "Escola B".

**Arquitetura e Tecnologias**
* **RNF04 - Padrão de Desenho:** O software tem de ser desenvolvido em Java sob a *framework* Spring Boot, obedecendo à arquitetura *Model-View-Controller* (MVC).
* **RNF05 - Flexibilidade da Base de Dados:** O sistema deve utilizar JPA / Hibernate como camada de abstração de dados (ORM), de forma a suportar tanto um motor robusto (MySQL) em produção como um motor embebido em memória (H2) para testes automatizados.
* **RNF06 - Server-Side Rendering:** As interfaces gráficas de utilizador (GUI) deverão ser desenhadas utilizando a *template engine* Thymeleaf e tecnologias web standard (HTML5, CSS3, Vanilla JS).

**Desempenho e Manutenibilidade**
* **RNF07 - Manutenibilidade de Código:** O código deve estar documentado, organizado por pacotes (`controller`, `model`, `service`, `repository`, `security`) e sem lógica de negócio embutida diretamente nos controladores.
* **RNF08 - Responsividade (UI):** O *layout* das páginas *frontend* deve adaptar-se perfeitamente aos redimensionamentos de ecrã (sejam *desktops* de sala de aula ou *laptops* dos formadores).
