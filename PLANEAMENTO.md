# Planeamento do Projeto - Plataforma SaaS Curricular

Este documento descreve as 4 grandes Fases de desenvolvimento desenhadas para a execução deste projeto de estágio. 

---

## Fase 1: Análise e Espaço Pessoal (MVP Base)
*Objetivo: Estabelecer as fundações da plataforma, segurança e o espaço isolado (CRUD) para os formadores gerirem o seu próprio material.*

- **Dias 1 e 2:** Arquitetura Base. Configuração do Spring Boot, ligações à Base de Dados (H2/MySQL) e modelação inicial das Entidades.
- **Dias 3 e 4:** Segurança e Autenticação. Integração do Spring Security, BCrypt, e construção das páginas web de Login/Registo usando Thymeleaf. Implementação de utilitários de isolamento de dados.
- **Dias 5 a 7:** CRUD Pessoal. Programação das funcionalidades de gestão de Fichas de Trabalho e Bancos de Perguntas do lado do Backend (Serviços e Repositórios) e do Frontend (Controladores e UI).
- **Dia 8:** Refinamentos. Testes de integração, ajustes no grafismo (Dashboard) e finalização do MVP Base.

## Fase 2: Arquitetura SaaS e Camada Institucional
*Objetivo: Expandir a plataforma para suportar Múltiplas Instituições (Escolas) e permitir a colaboração/partilha entre formadores.*

- **Dias 9 e 10:** Multi-Tenancy (BD). Expansão do modelo relacional para criar `Instituição` e `Disciplina`, ligando os utilizadores em esquemas `Many-to-Many`.
- **Dias 11 e 12:** Gestão Institucional. Desenvolvimento dos ecrãs de Frontend para permitir aos formadores a criação de Instituições e a adesão rápida através de Códigos de Acesso únicos.
- **Dias 13 e 14:** Partilha e Clonagem. Implementação de mecanismos de *Deep Copy* (Clonagem) para permitir aos formadores publicarem fichas para a Escola e copiarem o trabalho dos colegas para o seu espaço pessoal.
- **Dias 15 e 16:** Auditoria. Testes focados em privacidade e isolamento de dados (assegurando que não existem quebras de visibilidade entre Escolas diferentes).

## Fase 3: Portal do Aluno e Execução Interativa
*Objetivo: Desenvolver o ambiente onde os alunos entram, executam as Fichas desenhadas pelos formadores e registam os seus resultados.*

- **Dias 17 a 19:** Autenticação Simplificada/Federada (SSO) para facilitar a entrada dos alunos na plataforma sem requerer o processo de registo complexo.
- **Dias 20 a 23:** Interface Split-Screen. Desenho de uma interface em ecrã dividido (material de estudo de um lado, guião/perguntas do outro) para máxima ergonomia.
- **Dias 24 e 25:** Motor de Execução. Lógica de *tracking* e cronómetro em tempo real para registar o progresso dos alunos nas atividades.
- **Dia 26:** Testes de usabilidade e experiência (UX/UI) específicos para o fluxo do Aluno.

## Fase 4: Automação, Exportação e Relatório
*Objetivo: Adicionar as ferramentas "premium" para o formador e concluir o embalamento final do projeto.*

- **Dias 27 e 28:** Segurança. Validador automático para varrer e garantir a integridade dos ficheiros carregados (.zip, imagens).
- **Dias 29 e 30:** Exportação. Motor de geração de ficheiros PDF dinâmicos para permitir imprimir as Fichas de Trabalho formatadas a partir da plataforma.
- **Dias 31 e 32:** Integração externa. Ferramenta para importação e leitura de bases de dados do Moodle.
- **Dia 33:** Testes globais finais da plataforma (Segurança, Usabilidade e *Bugs*).
- **Dias 34 e 35:** Conclusão. Redação do Relatório Final de Estágio baseado na documentação contínua recolhida.
