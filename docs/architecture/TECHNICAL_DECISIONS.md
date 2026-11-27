# TECHNICAL_DECISIONS.md
# Registro de decisões técnicas da Nanalu


**Projeto:** Nanalu
**Versão:** 1.0
**Status:** Em avaliação
**Data:** 27/11/2026

---

## 1. Objetivo

Este documento registra as decisões tecnológicas da Nanalu.

Seu objetivo é comparar alternativas, justificar escolhas e manter um histórico das decisões que afetam a arquitetura, a implementação e a manutenção do aplicativo.

Nenhuma tecnologia deverá ser escolhida apenas por popularidade, preferência pessoal ou facilidade momentânea.

## 2. Princípios de seleção

- Compatibilidade real com Android.
- Confiabilidade e estabilidade.
- Desempenho em dispositivos modestos.
- Baixo consumo de bateria e memória.
- Funcionamento offline.
- Segurança e privacidade.
- Facilidade de manutenção.
- Qualidade da documentação.
- Disponibilidade de ferramentas de teste.
- Comunidade e continuidade do projeto.
- Compatibilidade com a arquitetura definida.
- Custo de desenvolvimento e distribuição.

## 3. Critérios de avaliação

Cada alternativa deverá ser avaliada considerando:

### Compatibilidade
Suporte ao Android, versões mínimas, dispositivos e recursos necessários.

### Desempenho
Tempo de inicialização, fluidez, consumo de memória e processamento.

### Confiabilidade
Maturidade, estabilidade, tratamento de falhas e previsibilidade.

### Manutenção
Complexidade, atualizações, dependências e facilidade de correção.

### Ecossistema
Documentação, comunidade, bibliotecas e ferramentas disponíveis.

### Segurança
Permissões, armazenamento, proteção de dados e riscos conhecidos.

### Adequação ao produto
Capacidade de atender aos requisitos da Nanalu sem complexidade desnecessária.

## 4. Processo de decisão

1. Identificar a necessidade técnica.
2. Levantar alternativas viáveis.
3. Consultar documentação oficial.
4. Comparar vantagens e limitações.
5. Verificar compatibilidade com os requisitos.
6. Realizar testes ou protótipos quando necessário.
7. Registrar a escolha e sua justificativa.
8. Documentar riscos e consequências.

## 5. Estados das decisões

- PROPOSED: alternativa proposta.
- UNDER_REVIEW: em avaliação.
- ACCEPTED: decisão aprovada.
- REJECTED: alternativa descartada.
- DEPRECATED: decisão anteriormente adotada, agora substituída.

Uma decisão em avaliação não deverá ser tratada como escolha definitiva.

## 6. Registro individual

Cada decisão deverá conter:
- Identificador.
- Título.
- Contexto e problema.
- Requisitos relacionados.
- Alternativas consideradas.
- Comparação técnica.
- Decisão adotada.
- Justificativa.
- Consequências positivas e negativas.
- Riscos conhecidos.
- Data e estado.
- Condições para revisão.


## 7. DEC-001: Tecnologia principal de desenvolvimento

**Estado:** APPROVED
**Prioridade:** Alta

### Contexto

A Nanalu será inicialmente um aplicativo Android. Precisa oferecer boa experiência visual, persistência local, funcionamento offline e possibilidade de integração futura com recursos nativos do sistema.

### Alternativas

#### A. Kotlin com Jetpack Compose
- Desenvolvimento nativo Android.
- Integração direta com APIs do sistema.
- Interface declarativa.
- Ecossistema oficial Android.
- Exige desenvolvimento específico para outras plataformas.

#### B. Flutter com Dart
- Desenvolvimento multiplataforma.
- Interface consistente entre dispositivos.
- Ecossistema próprio de componentes.
- Integração com recursos nativos por plugins e canais.
- Acrescenta uma camada tecnológica ao projeto.

#### C. React Native com TypeScript
- Desenvolvimento multiplataforma.
- Utiliza tecnologias do ecossistema JavaScript.
- Possui integração com recursos nativos.
- Depende da compatibilidade e manutenção de bibliotecas.
- Exige atenção à comunicação entre JavaScript e recursos nativos.

### Critérios específicos
- Qualidade da experiência Android.
- Integração com microfone e serviços do sistema.
- Desempenho em aparelhos modestos.
- Persistência local.
- Manutenção a longo prazo.
- Complexidade de implementação.
- Disponibilidade de bibliotecas confiáveis.

### Decisão

Kotlin é a linguagem principal do aplicativo Android.

---

## 8. DEC-002: Tecnologia de interface

**Estado:** APPROVED
**Prioridade:** Alta

### Contexto

A interface deverá ser acessível, responsiva, consistente e adequada ao uso cotidiano por cuidadores.

### Alternativas a investigar
- Jetpack Compose, caso Kotlin seja escolhido.
- Flutter Widgets, caso Flutter seja escolhido.
- React Native com bibliotecas de interface, caso React Native seja escolhido.

### Critérios específicos
- Acessibilidade.
- Facilidade de manutenção.
- Animações e transições.
- Personalização visual.
- Desempenho.
- Compatibilidade com recursos Android.

### Decisão

Jetpack Compose é a tecnologia principal de interface.


## 9. DEC-003: Banco de dados local

**Estado:** APPROVED
**Prioridade:** Crítica

### Contexto

A Nanalu precisa armazenar perfis, episódios de sono, eventos, configurações e informações necessárias para o funcionamento offline.

### Alternativas

#### A. SQLite
- Banco relacional leve.
- Suporte a transações.
- Integridade referencial.
- Amplamente utilizado em aplicações móveis.
- Exige definição de estratégia de acesso e migrações.

#### B. Room
- Biblioteca de persistência do ecossistema Android.
- Utiliza SQLite internamente.
- Oferece consultas verificadas e integração com Kotlin.
- É especialmente adequada a projetos Android nativos.
- Não é uma solução independente do ecossistema Android.

#### C. Drift
- Biblioteca de persistência para Dart e Flutter.
- Suporte a SQLite.
- Consultas tipadas e reatividade.
- Exige familiaridade com seu ecossistema.

#### D. SQLite com biblioteca de integração multiplataforma
- Permite utilizar SQLite em diferentes arquiteturas.
- Pode oferecer controle direto sobre consultas.
- A qualidade da integração depende da biblioteca escolhida.

### Critérios específicos
- Integridade transacional.
- Recuperação após interrupções.
- Migrações seguras.
- Consultas eficientes.
- Compatibilidade com a tecnologia principal.
- Facilidade de testes.
- Manutenção.

### Decisão

Room sobre SQLite é a solução de persistência estruturada local.

---

## 10. DEC-004: Gerenciamento de estado

**Estado:** APPROVED
**Prioridade:** Alta

### Contexto

A interface precisa refletir alterações nos registros sem confundir estado temporário de tela com dados persistidos.

### Alternativas a investigar
- ViewModel e StateFlow no Kotlin.
- Gerenciamento de estado nativo do Flutter.
- Provider, Riverpod ou Bloc no Flutter.
- Context API ou bibliotecas especializadas no React Native.

### Critérios específicos
- Previsibilidade.
- Facilidade de testes.
- Atualização reativa.
- Separação entre interface e domínio.
- Complexidade adequada ao tamanho do MVP.
- Facilidade de manutenção.

### Decisão

ViewModel e StateFlow são adotados para o gerenciamento de estado das telas.


## 11. DEC-005: Injeção de dependências

**Estado:** UNDER_REVIEW
**Prioridade:** Média

### Contexto

A arquitetura precisa permitir substituir implementações e testar componentes sem depender de serviços reais.

### Alternativas
- Hilt ou Koin para Kotlin.
- Injeção manual para projetos menores.
- Provider ou GetIt no Flutter.
- Soluções compatíveis com React Native.

### Critérios
- Simplicidade.
- Testabilidade.
- Integração com a tecnologia escolhida.
- Facilidade de manutenção.
- Baixo acoplamento.

### Decisão
Ainda não definida. A solução deverá ser proporcional à complexidade real do projeto.

---

## 12. DEC-006: Navegação

**Estado:** UNDER_REVIEW
**Prioridade:** Média

### Contexto

A Nanalu possui telas relacionadas entre si e precisa preservar uma navegação previsível.

### Alternativas
- Navigation Compose.
- Navegação nativa do Flutter.
- Bibliotecas de navegação do React Native.

### Critérios
- Compatibilidade com a tecnologia principal.
- Preservação do estado quando necessário.
- Facilidade de testes.
- Acessibilidade.
- Simplicidade.

### Decisão
Ainda não definida.

---

## 13. DEC-007: Estratégia de testes

**Estado:** UNDER_REVIEW
**Prioridade:** Alta

### Contexto

Os cálculos de sono e as regras temporais precisam ser verificáveis independentemente da interface.

### Categorias obrigatórias
- Testes unitários do domínio.
- Testes dos casos de uso.
- Testes de repositórios.
- Testes de integração.
- Testes de interface.
- Testes de migração.

### Critérios
- Execução reproduzível.
- Facilidade de manutenção.
- Compatibilidade com integração contínua.
- Cobertura dos cenários críticos.
- Capacidade de simular falhas.

### Decisão
Ainda não definida. As ferramentas serão escolhidas conforme a tecnologia principal.

---

## 14. DEC-008: Recursos em segundo plano

**Estado:** APPROVED
**Prioridade:** Média

### Contexto

Futuras funções poderão exigir tarefas agendadas e processamento em segundo plano.

### Alternativas a investigar
- WorkManager no Android.
- Serviços e mecanismos de execução do Flutter.
- APIs nativas integradas ao React Native.

### Critérios
- Respeito às restrições do Android.
- Consumo de bateria.
- Recuperação após interrupções.
- Necessidade real de execução persistente.
- Compatibilidade com políticas do sistema.

### Decisão
WorkManager será utilizado somente para tarefas adiáveis e persistentes compatíveis com seu modelo de execução.


## 15. DEC-009: Processamento de áudio

**Estado:** PROPOSED
**Prioridade:** Futura

### Contexto

O monitoramento acústico poderá identificar eventos sonoros relevantes durante sessões autorizadas.

### Alternativas a investigar
- APIs nativas de captura de áudio do Android.
- Processamento local com bibliotecas especializadas.
- Modelos de classificação executados no dispositivo.
- Bibliotecas multiplataforma com integração nativa.

### Critérios
- Processamento local.
- Precisão e limitações documentadas.
- Baixo consumo de bateria.
- Compatibilidade com diferentes aparelhos.
- Controle de permissões.
- Ausência de dependência obrigatória de servidores.
- Proteção e minimização dos dados de áudio.

### Restrições
- Não presumir que sons identificados confirmam despertar.
- Não presumir que silêncio confirma sono.
- Não anunciar detecção de apneia ou SMSL.
- Não substituir avaliação médica.

### Decisão

Adiada até que o MVP esteja funcional e exista evidência suficiente para justificar a pesquisa e implementação.

---

## 16. DEC-010: Segurança do armazenamento

**Estado:** UNDER_REVIEW
**Prioridade:** Alta

### Contexto

A aplicação armazenará informações relacionadas a crianças e seus cuidadores.

### Alternativas a investigar
- Armazenamento protegido pelo sandbox do Android.
- Criptografia de banco de dados.
- Proteção de chaves pelo Android Keystore.
- Soluções de criptografia compatíveis com a tecnologia principal.

### Critérios
- Proteção contra acesso não autorizado.
- Impacto no desempenho.
- Recuperação e integridade.
- Gerenciamento seguro de chaves.
- Compatibilidade com backup e exclusão.
- Adequação aos riscos identificados.

### Decisão
Ainda não definida. A solução deverá ser estabelecida após análise de ameaças e requisitos de privacidade.

---

## 17. DEC-011: Distribuição e atualização

**Estado:** UNDER_REVIEW
**Prioridade:** Média

### Alternativas
- Distribuição pela Google Play.
- Distribuição direta de APK.
- Outros canais oficiais de distribuição Android.

### Critérios
- Segurança da instalação.
- Atualizações confiáveis.
- Facilidade de acesso.
- Assinatura do aplicativo.
- Compatibilidade com versões Android.
- Custos e requisitos de publicação.

### Decisão
Ainda não definida.

---

## 18. DEC-012: Controle de versão e integração contínua

**Estado:** UNDER_REVIEW
**Prioridade:** Média

### Alternativas
- Git com GitHub.
- GitHub Actions para automação.
- Outras ferramentas compatíveis com o repositório escolhido.

### Critérios
- Histórico confiável.
- Revisão de alterações.
- Execução automatizada de testes.
- Geração reproduzível de builds.
- Proteção de credenciais.

### Decisão
Ainda não definida.


## 19. Matriz de avaliação

As tecnologias serão comparadas utilizando os seguintes pesos orientativos:

- Compatibilidade Android: 20%.
- Confiabilidade: 20%.
- Desempenho: 15%.
- Segurança e privacidade: 15%.
- Manutenção: 10%.
- Ecossistema e documentação: 10%.
- Adequação ao produto: 10%.

A pontuação poderá variar de 1 a 5.

A pontuação final não substituirá a análise técnica. Uma limitação crítica poderá eliminar uma alternativa mesmo que sua pontuação geral seja elevada.

## 20. Riscos tecnológicos

Os seguintes riscos deverão ser considerados durante a seleção:

- Escolher tecnologia incompatível com recursos necessários do Android.
- Acumular dependências sem manutenção.
- Adotar bibliotecas excessivamente complexas.
- Comprometer o funcionamento offline.
- Consumir bateria em excesso.
- Criar dependência desnecessária de serviços externos.
- Dificultar migrações e manutenção.
- Introduzir vulnerabilidades por dependências inseguras.
- Escolher soluções que dificultem testes.

## 21. Revisão das decisões

Uma decisão deverá ser reavaliada quando:

- Surgir incompatibilidade técnica.
- Uma dependência perder manutenção.
- Uma vulnerabilidade relevante for identificada.
- Os requisitos do produto mudarem.
- Testes demonstrarem limitações importantes.
- Uma alternativa oferecer benefício técnico significativo.

Decisões substituídas deverão permanecer documentadas no histórico.

## 22. Documentos relacionados

- PROJECT_CHARTER.md
- docs/architecture/SYSTEM_ARCHITECTURE.md
- docs/architecture/DATA_MODEL.md
- docs/architecture/NON_FUNCTIONAL_REQUIREMENTS.md
- docs/product/FUNCTIONAL_REQUIREMENTS.md
- docs/product/PRODUCT_RULES.md
- docs/privacy/
- docs/safety/
- docs/testing/

## 23. Situação atual

O registro inicial de avaliação foi atualizado pela ratificação da seção 24.

As decisões DEC-001, DEC-002 e DEC-003 foram aprovadas, assim como os complementos de arquitetura ali especificados para gerenciamento de estado e tarefas adiáveis.

As alternativas anteriores permanecem documentadas como histórico de avaliação, não como opções ainda abertas para a tecnologia principal.

As decisões não ratificadas continuam pendentes e deverão ser concluídas antes da implementação das funcionalidades correspondentes.
---

Fim do registro inicial de decisões técnicas.

## 24. Ratificação da tecnologia principal

Data: 27/11/2026
Status: APROVADO

Após a avaliação comparativa registrada em TECHNOLOGY_EVALUATION.md, ficam ratificadas as seguintes decisões:

- DEC-001: Kotlin como linguagem principal do aplicativo Android. APROVADO.
- DEC-002: Jetpack Compose como tecnologia principal de interface. APROVADO.
- DEC-003: Room sobre SQLite como persistência estruturada local. APROVADO.
- DEC-004: ViewModel e StateFlow para gerenciamento do estado das telas. APROVADO.
- DEC-008: WorkManager restrito a tarefas adiáveis e persistentes compatíveis com seu modelo de execução. APROVADO.

### Complementos da arquitetura
- Coroutines e Flow para operações assíncronas e comunicação entre camadas.
- DataStore para preferências e configurações simples.

### Decisões ainda pendentes
- Versões exatas das dependências e ferramentas.
- Versão mínima do Android suportada.
- Biblioteca e estratégia definitiva de navegação.
- Estratégia de injeção de dependências.
- Estratégia de criptografia do banco de dados e detalhes técnicos adicionais de proteção local.
- Estratégia de exportação e restauração, caso esses recursos sejam implementados futuramente.
- Viabilidade técnica do monitoramento acústico, fora do MVP.

### Condições de validação
- Construir e executar um protótipo Android real.
- Testar persistência, migrações e recuperação após encerramento do processo.
- Medir inicialização, memória, consumo de bateria e fluidez em aparelhos de capacidades diferentes.
- Verificar compatibilidade com as versões Android escolhidas.
- Não considerar o monitoramento acústico aprovado por esta decisão.

Esta ratificação substitui os estados anteriores de análise das cinco decisões registradas acima. As decisões pendentes permanecem em avaliação.

Referências: TECHNOLOGY_EVALUATION.md e documentação oficial do Android.
