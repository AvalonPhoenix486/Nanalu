# SYSTEM_ARCHITECTURE.md
# Arquitetura do sistema Nanalu

## 1. Objetivo

Este documento define a arquitetura lógica da Nanalu, seus componentes, responsabilidades, limites de comunicação e critérios de evolução.

A arquitetura deverá permitir um MVP simples, confiável, testável e offline-first, preparado para futuras expansões.

## 2. Princípios arquiteturais

- Separação entre interface, aplicação, domínio e persistência.
- Independência das regras de negócio em relação à interface.
- Persistência independente de tecnologia específica.
- Funcionamento offline das funcionalidades essenciais.
- Privacidade por padrão e coleta mínima de dados.
- Evolução gradual, sem complexidade desnecessária.
- Testabilidade das regras de negócio.
- Transparência entre dados observados, calculados e estimados.
- Integridade e segurança dos registros.

## 3. Organização das camadas

A arquitetura será dividida em cinco camadas lógicas principais:

1. Presentation Layer: interface e interação com o usuário.
2. Application Layer: coordenação dos casos de uso.
3. Domain Layer: entidades, regras e cálculos de negócio.
4. Data Layer: repositórios e persistência.
5. Platform Services: recursos disponibilizados pelo Android.

Essas camadas representam responsabilidades. Não exigem projetos físicos separados.

## 4. Presentation Layer

Responsável pela apresentação e interação com o usuário.

### Responsabilidades
- Exibir telas e componentes.
- Receber entradas do usuário.
- Controlar navegação.
- Apresentar dados e resultados.
- Exibir carregamentos e erros.
- Solicitar confirmação para ações destrutivas.
- Atualizar a interface após alterações.

### Telas previstas
- Primeiro acesso e cadastro.
- Painel principal.
- Registro de sono.
- Histórico e detalhes.
- Estatísticas.
- Conteúdo educativo.
- Configurações e perfil.

### Limites
A interface não deverá implementar cálculos complexos, acessar diretamente o banco de dados ou duplicar regras de negócio.

Ela deverá encaminhar as solicitações à Application Layer.

## 5. Application Layer

Responsável por coordenar as operações da aplicação.

### Casos de uso previstos
- CreateChildProfile.
- UpdateChildProfile.
- StartSleepEpisode.
- CompleteSleepEpisode.
- CreateHistoricalSleepEpisode.
- UpdateSleepEpisode.
- DeleteSleepEpisode.
- GetSleepHistory.
- GetDailySleepSummary.
- GetSleepStatistics.
- GetEducationalContent.

### Responsabilidades
- Receber solicitações da interface.
- Coordenar operações do domínio.
- Consultar e atualizar repositórios.
- Controlar a sequência das operações.
- Retornar resultados e erros.
- Solicitar atualização dos dados apresentados.

Regras fundamentais de negócio deverão permanecer no Domain Layer, evitando concentrar toda a lógica na aplicação.

## 6. Domain Layer

Representa o núcleo das regras e conceitos da Nanalu.
Deverá funcionar independentemente da interface e da tecnologia de armazenamento.

### Elementos previstos
- ChildProfile.
- SleepEpisode.
- SleepEvent.
- SleepEpisodeStatus.
- SleepClassification.
- SleepSource.
- SleepDuration.
- SleepStatistics.
- SleepEstimate.

### Responsabilidades
- Validar episódios de sono.
- Controlar transições de estado.
- Calcular durações.
- Verificar inconsistências temporais.
- Aplicar regras de classificação.
- Evitar contagem duplicada.
- Calcular indicadores e estatísticas.
- Diferenciar dados observados, calculados e estimados.

### Regras obrigatórias
O domínio deverá respeitar as regras de docs/product/PRODUCT_RULES.md.
O modelo deverá respeitar docs/architecture/DATA_MODEL.md.
As referências científicas deverão respeitar a documentação de docs/science.

O domínio não deverá depender de componentes visuais, APIs externas ou bibliotecas específicas de armazenamento.

## 7. Data Layer

Responsável pela persistência e recuperação dos dados.

### Repositórios conceituais
- ChildProfileRepository.
- SleepEpisodeRepository.
- SleepEventRepository.
- AppSettingsRepository.
- EducationalContentRepository.

### Responsabilidades
- Criar, consultar, atualizar e excluir registros.
- Preservar relacionamentos e integridade.
- Recuperar dados após reinicialização.
- Executar migrações.
- Comunicar falhas de armazenamento.
- Evitar perda silenciosa de informações.

### Persistência
O MVP deverá utilizar armazenamento local persistente.
A persistência será implementada com Room sobre SQLite, conforme decisão técnica ratificada em TECHNICAL_DECISIONS.md.

A interface não deverá acessar diretamente o banco de dados. O acesso deverá ocorrer pelos contratos definidos na arquitetura.

## 8. Fluxo de criação de episódio

1. O usuário inicia um registro pela interface.
2. A Presentation Layer encaminha a solicitação.
3. StartSleepEpisode recebe a operação.
4. O domínio valida os dados e as regras aplicáveis.
5. O episódio é criado.
6. O repositório persiste o registro.
7. A aplicação retorna o resultado.
8. A interface atualiza o painel.

A interface somente poderá comunicar sucesso após a confirmação da persistência.
Falhas de armazenamento não poderão ser apresentadas como operações concluídas.

## 9. Fluxo de encerramento de episódio

1. O usuário solicita o encerramento.
2. CompleteSleepEpisode recebe a solicitação.
3. O episódio ativo é recuperado.
4. O domínio valida o horário final.
5. O estado e o horário final são atualizados.
6. O repositório persiste a alteração.
7. A aplicação retorna o resultado.
8. A interface atualiza o histórico e o painel.

O encerramento deverá respeitar as regras temporais e de integridade definidas em PRODUCT_RULES.md.

## 10. Fluxo de estatísticas

1. A interface solicita as estatísticas.
2. A aplicação consulta o repositório.
3. Os registros válidos são recuperados.
4. O domínio aplica os cálculos e as regras.
5. Os resultados são organizados.
6. A interface apresenta os indicadores.

### Requisitos
- Episódios inválidos ou excluídos não participam dos cálculos.
- Sobreposições não podem gerar contagem duplicada.
- Episódios que atravessam a meia-noite devem ser divididos corretamente por dia.
- Intervalos sem registros não representam vigília confirmada.
- Toda estatística deve identificar o período analisado.
- A quantidade e a qualidade dos dados devem ser consideradas.
- Estimativas devem ser identificadas como estimativas.

## 11. Estado da aplicação

O estado visual da interface deverá ser separado dos dados persistidos.

Estados temporários, como carregamento, seleção de filtros e navegação, não deverão substituir os registros armazenados.

Após reinicialização, os dados confirmados deverão ser recuperados da persistência local.

## 12. Platform Services

Camada responsável por intermediar o acesso aos recursos do sistema operacional.

### Serviços previstos
- ClockService: fornece horários e datas.
- LocalStorageService: disponibiliza recursos de armazenamento.
- NotificationService: gerencia notificações autorizadas.
- SchedulerService: agenda tarefas permitidas pelo sistema.
- PermissionService: verifica e solicita permissões.
- LifecycleService: acompanha o ciclo de vida da aplicação.
- AudioService: possível integração futura com recursos de áudio.

Os serviços deverão ser acessados por interfaces controladas, evitando dependência direta do domínio em APIs específicas do Android.

## 13. Background Processing

Processamentos em segundo plano deverão ser utilizados somente quando houver necessidade funcional real.

### Possíveis responsabilidades
- Atualização de indicadores derivados.
- Manutenção de tarefas agendadas.
- Preparação de notificações autorizadas.
- Processamento futuro de sessões acústicas.

### Restrições
- Respeitar os limites de execução do Android.
- Evitar consumo desnecessário de bateria.
- Não manter tarefas permanentes sem justificativa.
- Não comprometer o registro manual de sono.
- Não presumir execução contínua em segundo plano.
- Tratar interrupções e reinicializações de maneira segura.

## 14. Funcionamento offline

As funcionalidades essenciais deverão funcionar sem conexão.

### Disponibilidade offline
- Cadastro e edição do perfil.
- Criação e encerramento de episódios.
- Consulta do histórico.
- Estatísticas baseadas em dados locais.
- Configurações.
- Conteúdo educativo previamente armazenado.

### Regras
- Nenhuma operação essencial deverá depender de servidor remoto.
- A ausência de internet não poderá impedir o registro manual.
- Dados locais deverão permanecer disponíveis após reinicialização.
- Futuras integrações online deverão ser opcionais e isoladas.
- Eventual sincronização deverá possuir regras explícitas de conflito.

## 15. Notificações

As notificações serão opcionais e configuráveis.

Não deverão transmitir certeza sobre horários de sono, despertar ou necessidades da criança.

O usuário deverá poder controlar sua ativação e categorias.

A ausência de permissão para notificações não poderá comprometer as funções essenciais.

## 16. Subsistema acústico futuro

O monitoramento acústico não fará parte obrigatória do MVP.

Quando implementado, deverá constituir um subsistema isolado, sem modificar as regras fundamentais do registro manual.

### Componentes conceituais
- AudioCapture: aquisição de áudio mediante autorização.
- AudioSessionManager: gerenciamento das sessões.
- AudioProcessing: processamento dos sinais.
- EventDetection: identificação de possíveis eventos acústicos.
- EventReview: apresentação e revisão dos eventos.
- AudioPrivacyManager: controle de privacidade.

### Princípios
- Som não equivale a despertar confirmado.
- Silêncio não equivale a sono confirmado.
- Ruídos não devem ser classificados automaticamente como eventos de sono.
- Eventos automáticos devem permanecer distinguíveis dos registros manuais.
- O usuário deverá poder revisar e corrigir eventos.
- A indisponibilidade do recurso não poderá comprometer o restante do aplicativo.

### Restrições de segurança
- Não anunciar detecção confiável de apneia.
- Não anunciar prevenção ou detecção de SMSL.
- Não prometer monitoramento contínuo.
- Não apresentar o sistema como dispositivo médico.
- Não substituir supervisão ou cuidados responsáveis.

### Restrições de privacidade
- Solicitar autorização antes de utilizar o microfone.
- Explicar claramente a finalidade da captura.
- Minimizar a coleta e a retenção de áudio.
- Não manter gravações permanentemente por padrão.
- Avaliar processamento local como primeira opção.
- Permitir interromper o monitoramento.
- Documentar qualquer transmissão externa de dados.
- Avaliar consumo de bateria e limitações do Android.

## 17. Segurança e privacidade

A arquitetura deverá aplicar privacidade e segurança desde a concepção.

### Requisitos
- Coletar somente dados necessários.
- Restringir acesso aos dados da criança.
- Evitar exposição de informações em registros técnicos.
- Não inserir dados pessoais em mensagens de erro desnecessárias.
- Proteger dados armazenados conforme os riscos identificados.
- Revisar permissões solicitadas.
- Permitir exclusão dos dados conforme as regras do produto.
- Avaliar requisitos aplicáveis da LGPD.

### Separação de dados

Dados de perfil, episódios, configurações e conteúdo deverão possuir responsabilidades e estruturas distintas.

Futuras funcionalidades de compartilhamento deverão utilizar controle explícito de autorização, sem presumir que todos os usuários possuem os mesmos direitos.

## 18. Tratamento de erros

Os erros deverão ser tratados de maneira previsível e compreensível.

### Categorias
- ValidationError: dados inválidos.
- DomainError: violação de regra de negócio.
- PersistenceError: falha de armazenamento.
- PermissionError: permissão indisponível.
- PlatformError: falha de recurso do sistema.
- UnexpectedError: falha não prevista.

### Requisitos
- Não ocultar falhas importantes.
- Não apresentar operações não concluídas como sucesso.
- Preservar dados válidos sempre que possível.
- Apresentar mensagens compreensíveis.
- Evitar exposição de informações sensíveis.
- Registrar informações técnicas suficientes para investigação.

## 19. Observabilidade

A aplicação deverá permitir investigar falhas sem coletar informações pessoais desnecessárias.

### Eventos técnicos possíveis
- Falha de persistência.
- Falha de migração.
- Erro de validação inesperado.
- Falha em tarefa agendada.
- Interrupção de processamento.

Os registros técnicos não deverão conter áudio, conteúdo sensível ou dados identificáveis sem justificativa e proteção adequadas.

## 20. Estratégia de testes

Os testes deverão acompanhar cada camada.

### Testes de domínio
- Validação temporal.
- Cálculo de duração.
- Cruzamento de meia-noite.
- Sobreposições.
- Classificação de episódios.
- Integridade dos estados.
- Cálculos estatísticos.
- Regras de estimativa.

### Testes de aplicação
- Criação de perfil.
- Início e encerramento de episódios.
- Edição e exclusão.
- Recuperação de histórico.
- Tratamento de falhas.

### Testes de persistência
- Gravação e recuperação.
- Integridade referencial.
- Transações.
- Migrações.
- Recuperação após reinicialização.

### Testes de interface
- Navegação.
- Validação de entradas.
- Apresentação de erros.
- Estados vazios.
- Acessibilidade.
- Atualização após operações.

### Testes de integração

Deverão verificar a comunicação entre aplicação, domínio, persistência e serviços de plataforma.

## 21. Estratégia de evolução

A arquitetura deverá evoluir por etapas.

### Etapa 1: MVP
- Cadastro e edição do perfil de uma criança.
- Registro manual de episódios de sono.
- Classificação dos períodos entre sono diurno e noturno.
- Histórico com consulta e correção dos registros.
- Exclusão de episódios e do perfil infantil, incluindo os dados dependentes.
- Painel principal e estatísticas básicas.
- Estimativa inicial de sonecas, com limitações e incerteza claramente informadas.
- Persistência local e funcionamento offline.
- Conteúdo educativo inicial e configurações essenciais.

### Etapa 2: Refinamento
- Melhoria dos indicadores.
- Estimativas com critérios de confiança.
- Acessibilidade e experiência de uso.
- Otimização de desempenho.

### Etapa 3: Recursos avançados
- Monitoramento acústico experimental.
- Revisão de eventos automáticos.
- Notificações configuráveis.

### Etapa 4: Expansão
- Múltiplos cuidadores.
- Compartilhamento autorizado.
- Sincronização opcional.
- Exportação de relatórios.

Cada etapa deverá preservar a integridade dos dados e as restrições de segurança.

## 22. Decisões técnicas pendentes

As decisões principais de linguagem, interface, persistência e gerenciamento de estado já foram ratificadas em TECHNICAL_DECISIONS.md.

Permanecem pendentes, conforme aplicabilidade:

- Estratégia de injeção de dependências.
- Biblioteca e estratégia definitiva de navegação.
- Ferramentas e configuração da estratégia de testes.
- Estratégia detalhada de migração do banco Room.
- Versão mínima do Android suportada.
- Versões exatas das dependências e ferramentas.
- Estratégia de criptografia adicional e proteção do armazenamento.
- Viabilidade técnica do monitoramento acústico, exclusivamente como recurso futuro fora do MVP.

O uso do WorkManager permanece limitado às tarefas adiáveis e persistentes compatíveis com seu modelo de execução, conforme decisão ratificada.

As decisões deverão ser registradas em TECHNICAL_DECISIONS.md, incluindo justificativas, alternativas consideradas e consequências.
## 23. Critérios de aceitação arquitetural

A arquitetura será considerada suficientemente especificada para orientar o MVP quando:

- As responsabilidades das camadas estiverem documentadas.
- Os fluxos essenciais estiverem definidos.
- O domínio estiver separado da interface.
- A persistência possuir contratos claros.
- O funcionamento offline estiver contemplado.
- Os requisitos de segurança e privacidade estiverem considerados.
- As regras de negócio estiverem vinculadas à documentação correspondente.
- Os principais cenários de teste estiverem identificados.
- As decisões tecnológicas estiverem justificadas antes de sua adoção.
- As funcionalidades futuras estiverem isoladas do núcleo do MVP.

## 24. Documentos relacionados

- PROJECT_CHARTER.md
- docs/product/PRODUCT_VISION_AND_SCOPE.md
- docs/product/FUNCTIONAL_REQUIREMENTS.md
- docs/product/PRODUCT_RULES.md
- docs/product/USER_FLOWS.md
- docs/product/SCREEN_MAP.md
- docs/architecture/NON_FUNCTIONAL_REQUIREMENTS.md
- docs/architecture/DATA_MODEL.md
- docs/science/SCIENTIFIC_REFERENCES.md
- Documentos de segurança e privacidade.
- Documentos de testes e decisões técnicas.

## 25. Princípio final

A arquitetura da Nanalu deverá servir ao produto, não o contrário.

A prioridade será entregar uma experiência confiável, simples, segura e fundamentada, preservando a integridade dos registros e a transparência sobre os limites do aplicativo.

Qualquer complexidade adicional deverá possuir uma justificativa funcional, técnica ou de segurança.

---

Fim da especificação inicial de arquitetura do sistema.
