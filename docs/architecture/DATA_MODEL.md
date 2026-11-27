# DATA_MODEL.md
# Modelo de dados da Nanalu

**Projeto:** Nanalu  
**Versão:** 1.0  
**Status:** Especificação inicial  
**Data:** 27/11/2026

---

## 1. Objetivo

Definir o modelo conceitual de dados da Nanalu, incluindo entidades, atributos, relacionamentos, identificadores, estados, integridade, dados derivados e estratégia inicial de persistência.

Este documento não determina uma tecnologia específica de banco de dados.

## 2. Princípios do modelo

### DM-001: Identificadores estáveis
Cada entidade persistente deverá possuir identificador único e estável, independente do nome da criança, horário ou posição do registro.

### DM-002: Origem dos dados
O sistema deverá distinguir:
- Dados informados pelo responsável.
- Dados observados.
- Dados calculados.
- Dados estimados.
- Dados provenientes de processamento automático.

### DM-003: Dados derivados
Sempre que possível, estatísticas deverão ser recalculáveis a partir dos registros de origem.

### DM-004: Integridade referencial
Uma entidade dependente não poderá apontar para uma entidade inexistente.

### DM-005: Histórico
Alterações deverão preservar a integridade dos dados e permitir reconstrução correta das estatísticas.

### DM-006: Privacidade
O modelo deverá evitar armazenar informações desnecessárias ao funcionamento do produto.

## 3. Entidades

Entidades principais do MVP:
1. ChildProfile
2. SleepEpisode
3. SleepEvent
4. AppSettings
5. EducationalContent
6. ContentReference

Entidades futuras:
7. Caregiver
8. Permission
9. ShareInvitation
10. AudioSession
11. AutomaticEvent
12. SyncMetadata
13. Estimate

Entidades futuras não deverão ser implementadas no MVP sem necessidade funcional real.

## 4. ChildProfile

Representa a criança acompanhada pelo aplicativo.

| Campo | Tipo conceitual | Obrigatório |
|---|---|---|
| id | UUID/String estável | Sim |
| name | String nullable | Não |
| birthDate | Data | Sim |
| createdAt | DateTime | Sim |
| updatedAt | DateTime | Sim |

Regras:
- id deverá ser único.
- name deverá possuir validação de tamanho.
- birthDate não poderá estar no futuro.
- createdAt será definido na criação.
- updatedAt será atualizado em alterações relevantes.

## 5. SleepEpisode

Representa um episódio de sono registrado.

| Campo | Tipo conceitual | Obrigatório |
|---|---|---|
| id | UUID/String estável | Sim |
| childId | UUID/String | Sim |
| startedAt | DateTime | Sim |
| endedAt | DateTime nullable | Não |
| wakeConfirmedAtEnd | Boolean nullable | Não |
| status | Enum | Sim |
| classification | Enum | Sim |
| source | Enum | Sim |

| createdAt | DateTime | Sim |
| updatedAt | DateTime | Sim |

### Estados

- ACTIVE: episódio em andamento.
- COMPLETED: episódio concluído.
- INVALID: registro preservado, mas excluído dos cálculos.
- DELETED: reservado exclusivamente para uma possível estratégia futura. Não será utilizado no MVP.

### Origem

- MANUAL: informado pelo responsável.
- AUTOMATIC: produzido por processamento automático futuro.
- IMPORTED: importado de outra fonte futura.

No MVP, MANUAL será a origem principal.

### Classificação

- DAYTIME: organizado como sono diurno.
- NIGHTTIME: organizado como sono noturno.
- UNCLASSIFIED: ainda não classificado.

A classificação representa organização do registro, não determinação fisiológica.

## 6. Integridade do SleepEpisode

- childId deverá apontar para uma criança existente.
- startedAt deverá existir.
- endedAt, quando existente, deverá ser posterior a startedAt.
- ACTIVE deverá representar episódio sem término.
- COMPLETED deverá possuir endedAt.
- wakeConfirmedAtEnd será opcional e indicará se o responsável confirma que endedAt corresponde ao despertar observado.
- wakeConfirmedAtEnd não substituirá endedAt nem alterará o cálculo da duração do sono.
- true: o responsável confirma que endedAt corresponde ao despertar observado.
- false: o responsável informa que não observou o despertar naquele horário.
- null: confirmação desconhecida ou não fornecida.
- Somente episódios com wakeConfirmedAtEnd = true poderão fornecer o marco inicial de um intervalo de vigília para estimativas.
- A confirmação representa um relato do responsável, não uma medição fisiológica do instante exato do despertar.
- INVALID não participará das estatísticas.
- DELETED não participará das estatísticas.
- Um episódio não poderá possuir estados conflitantes.

## 7. Cruzamento de meia-noite

Um episódio poderá começar em um dia e terminar no seguinte.

Exemplo:

Início: 2026-11-27 22:30  
Término: 2026-11-28 06:15  
Duração: 7h45

A duração será calculada pela diferença entre endedAt e startedAt.

Para estatísticas diárias, episódios que atravessam meia-noite deverão ser divididos logicamente entre os dias envolvidos, evitando atribuir toda a duração ao primeiro dia.

## 8. SleepEvent

Representa um evento associado a um episódio ou à linha temporal de sono.

| Campo | Tipo | Obrigatório |
|---|---|---|
| id | UUID/String | Sim |
| sleepEpisodeId | UUID/String nullable | Não |
| childId | UUID/String | Sim |
| timestamp | DateTime | Sim |
| type | Enum | Sim |
| source | Enum | Sim |
| confidence | Decimal nullable | Não |
| metadata | Estrutura controlada nullable | Não |
| createdAt | DateTime | Sim |

Tipos futuros possíveis:
- AWAKENING_CANDIDATE
- SOUND_DETECTED
- MOVEMENT_CANDIDATE
- SLEEP_START_CANDIDATE
- SLEEP_END_CANDIDATE

Eventos automáticos são evidências ou candidatos. Não deverão ser tratados automaticamente como confirmação de sono ou vigília.

## 9. AppSettings

Representa configurações locais do aplicativo.

Exemplos:
- Preferências de interface.
- Preferências de notificações.
- Configurações de privacidade.
- Configurações relacionadas ao monitoramento futuro.
- Preferências de conteúdo.

Configurações não deverão armazenar dados desnecessários da criança.

## 10. EducationalContent

Representa conteúdo educativo apresentado ao responsável.

| Campo | Tipo | Obrigatório |
|---|---|---|
| id | UUID/String | Sim |
| title | String | Sim |
| summary | String | Sim |
| body | Texto | Sim |
| category | Enum/String | Sim |
| ageRange | Estrutura controlada | Não |
| version | String | Sim |
| updatedAt | DateTime | Sim |
| isActive | Boolean | Sim |

Afirmações científicas ou institucionais deverão possuir referências de origem.

## 11. ContentReference

Representa uma fonte associada a conteúdo educativo.

| Campo | Tipo | Obrigatório |
|---|---|---|
| id | UUID/String | Sim |
| contentId | UUID/String | Sim |
| title | String | Sim |
| authors | String nullable | Não |
| year | Integer nullable | Não |
| identifier | String nullable | Não |
| url | String nullable | Não |
| sourceType | Enum | Sim |

Tipos de fonte poderão incluir artigo científico, diretriz, órgão público ou instituição de saúde.

## 12. Relacionamentos

### ChildProfile → SleepEpisode
Uma criança poderá possuir zero ou muitos episódios. Cada episódio deverá pertencer a exatamente uma criança.

### SleepEpisode → SleepEvent
Um episódio poderá possuir zero ou muitos eventos. Um evento poderá existir sem episódio específico quando representar evidência temporal ainda não associada.

### EducationalContent → ContentReference
Um conteúdo poderá possuir zero ou muitas referências.

## 13. Dados derivados

Os seguintes valores deverão ser recalculáveis a partir dos dados de origem:

- Duração do episódio.
- Total diário de sono.
- Total de sono noturno.
- Total de sono diurno.
- Quantidade de episódios.
- Médias e medianas.
- Tendências.
- Distribuição de horários.
- Indicadores de regularidade.

Caso exista cache para desempenho, seu conteúdo deverá ser reconstruível.

## 14. Dados estimados

Estimativas futuras deverão ser explicitamente diferenciadas de dados observados.

Quando aplicável, deverão registrar:
- Valor ou intervalo estimado.
- Método utilizado.
- Período analisado.
- Quantidade de dados utilizados.
- Nível ou faixa de confiança.
- Momento de geração.
- Versão do algoritmo.

Estimativas não deverão ser apresentadas como fatos clínicos ou previsões garantidas.

## 15. Idade e faixas de referência

A idade deverá ser calculada a partir de birthDate e da data de referência.

O sistema não deverá armazenar idade como fonte primária.

Para as categorias de referência AASM/WHO utilizadas no produto:
- 1–2 anos corresponde à categoria até 35 meses.
- 3–5 anos começa na categoria de referência aos 36 meses.

Essa mudança de categoria não representa uma mudança biológica abrupta.

## 16. Integridade temporal

- Datas deverão possuir representação consistente.
- O aplicativo deverá preservar corretamente o instante registrado.
- Episódios não poderão possuir término anterior ao início.
- Alterações temporais deverão atualizar os dados derivados afetados.
- Conflitos temporais deverão ser detectados pela camada de domínio quando relevantes.
- Sobreposições não poderão resultar em contagem duplicada de sono.

## 17. Intervalos de vigília

O modelo não deverá criar automaticamente um período de vigília apenas porque existe um intervalo entre dois episódios.

Exemplo:

- 22:00: início do sono.
- 01:00: fim do registro.
- 03:00: início do próximo registro.

O intervalo entre 01:00 e 03:00 não deverá ser tratado automaticamente como vigília confirmada.

## 18. Exclusão

### Exclusão de episódios no MVP
- A exclusão deverá remover efetivamente o episódio e seus dados dependentes.
- A operação deverá preservar a integridade referencial e utilizar transação.
- O episódio excluído não deverá permanecer nas consultas, telas ou estatísticas.

### Exclusão de perfil no MVP
- A exclusão do perfil deverá remover o ChildProfile e todos os SleepEpisode associados.
- Dados derivados armazenados, configurações vinculadas e arquivos temporários associados deverão ser removidos.
- A operação deverá manter a integridade do banco, inclusive diante de falhas durante a exclusão.

### Exclusão lógica futura
- Exclusão lógica não será utilizada como substituto da eliminação efetiva no MVP.
- Uma estratégia futura de exclusão lógica dependerá de finalidade legítima, retenção justificada e revisão de privacidade.

A exclusão deverá respeitar também a especificação de segurança, incluindo o tratamento de caches, arquivos auxiliares do banco e resíduos sob controle do aplicativo.
## 19. Offline-first

O modelo deverá funcionar localmente sem depender de internet para as funções essenciais.

Registros manuais deverão poder ser criados, consultados, editados e excluídos sem conexão.

Sincronização futura deverá ser uma camada adicional, nunca um requisito do funcionamento básico.

## 20. Segurança

- Dados da criança deverão permanecer protegidos contra acesso não autorizado.
- Credenciais, tokens e segredos não deverão ser armazenados junto aos registros de domínio.
- Dados sensíveis ou desnecessários não deverão ser coletados por conveniência técnica.
- O acesso aos dados deverá respeitar as regras de privacidade do produto.

## 21. Escopo do MVP

O MVP deverá permitir:
1. Cadastrar uma criança.
2. Registrar início do sono.
3. Registrar término do sono.
4. Registrar episódios retroativos.
5. Editar registros.
6. Excluir registros.
7. Visualizar histórico.
8. Calcular duração.
9. Calcular estatísticas básicas.
10. Apresentar referências educativas.

Monitoramento acústico, compartilhamento, sincronização e processamento automático ficam fora do funcionamento obrigatório do MVP.

## 22. Invariantes

- Todo SleepEpisode possui id único.
- Todo episódio pertence a uma criança válida.
- Episódio concluído possui início e término válidos.
- O término é posterior ao início.
- Registros inválidos não contaminam estatísticas.
- Exclusões não alteram indevidamente registros independentes.
- Dados derivados podem ser recalculados.
- Eventos automáticos não equivalem automaticamente a fatos confirmados.
- Dados de uma criança não podem ser associados acidentalmente a outra.

## 23. Migrações

Alterações futuras deverão utilizar estratégia de migração versionada.

Nenhuma alteração estrutural deverá apagar dados existentes sem política explícita.

Cada versão deverá documentar:
- Versão anterior.
- Versão nova.
- Alterações estruturais.
- Transformação de dados.
- Compatibilidade.
- Possibilidade de rollback, quando aplicável.

## 24. Testabilidade

O modelo deverá permitir testes para:
- Criação, edição e exclusão.
- Episódios ativos.
- Episódios atravessando meia-noite.
- Sobreposição.
- Cálculo de duração.
- Cálculo diário.
- Classificação.
- Idade.
- Estatísticas.
- Integridade referencial.
- Recuperação após encerramento inesperado.
- Persistência offline.

## 25. Critérios de aceitação

O modelo inicial será considerado aprovado quando:
- Todas as entidades do MVP estiverem definidas.
- Os relacionamentos estiverem documentados.
- Estados e enums principais estiverem definidos.
- Regras temporais estiverem documentadas.
- Dados derivados estiverem diferenciados dos dados de origem.
- Requisitos de privacidade estiverem contemplados.
- O modelo não exigir tecnologia específica.
- O modelo puder servir de base para a arquitetura técnica.
- As regras puderem ser transformadas em testes.

## 26. Documentos relacionados

- PROJECT_CHARTER.md
- docs/science/SCIENTIFIC_REFERENCES.md
- docs/product/PRODUCT_VISION_AND_SCOPE.md
- docs/product/FUNCTIONAL_REQUIREMENTS.md
- docs/product/PRODUCT_RULES.md
- docs/product/USER_FLOWS.md
- docs/product/SCREEN_MAP.md
- docs/architecture/NON_FUNCTIONAL_REQUIREMENTS.md

## 27. Princípio final

O modelo de dados da Nanalu deverá favorecer simplicidade, rastreabilidade, privacidade, consistência e capacidade de evolução.

Os dados registrados pelo responsável são a fonte principal. Cálculos e estimativas deverão permanecer distinguíveis dos fatos registrados, e funcionalidades futuras de processamento automático não deverão transformar incerteza em certeza.
## Decisões complementares de privacidade para o MVP

Esta seção complementa as definições anteriores e prevalece em caso de conflito para o escopo do MVP.

### ChildProfile
- name passa a ser opcional. O aplicativo deverá funcionar sem nome completo.
- birthDate será mantido para cálculo contínuo da idade e aplicação das referências etárias.
- birthDate deverá permanecer exclusivamente no armazenamento local do MVP.

### SleepEpisode
- notes não será implementado no MVP.
- O MVP utilizará registros manuais.
- wakeConfirmedAtEnd será opcional e indicará se o término do episódio corresponde ao despertar observado pelo responsável.
- A exclusão de um episódio deverá remover efetivamente o registro e seus dados dependentes.
- DELETED não será utilizado como substituto de exclusão efetiva no MVP.

### SleepEvent
- Entidade reservada para evolução futura.
- Não será implementada no MVP.
- confidence e metadata dependerão de definição formal de semântica, escala e propriedades permitidas antes da implementação.

### EducationalContent e ContentReference
- Conteúdo editorial será disponibilizado localmente.
- O MVP não manterá histórico individual de leitura.

### Datas e horários
- Instantes deverão possuir representação temporal inequívoca.
- A implementação deverá preservar o instante real e permitir apresentação no fuso horário local.
- Duração será calculada pela diferença entre instantes, nunca pela simples subtração de horários locais.

### Prevalência
Estas decisões complementam o modelo original e devem ser consideradas obrigatórias na implementação do MVP. Funcionalidades futuras permanecem fora do escopo inicial.
