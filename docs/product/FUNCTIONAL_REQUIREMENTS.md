# FUNCTIONAL_REQUIREMENTS.md
# Requisitos funcionais da Nanalu

**Projeto:** Nanalu
**Versão:** 1.0
**Status:** Especificação inicial
**Data:** 27/11/2026

---

## 1. Objetivo

Este documento define os comportamentos que a Nanalu deverá implementar.

Cada requisito possui um identificador único para facilitar:

- Implementação.
- Testes.
- Revisões.
- Rastreamento de alterações.
- Comunicação com agentes de desenvolvimento.
- Verificação de conclusão.

Os requisitos estão classificados por prioridade:

- **P0:** obrigatório para o MVP.
- **P1:** importante, mas pode ser implementado após o núcleo inicial.
- **P2:** evolução futura.
- **EXP:** experimental, sujeito a estudos de viabilidade.

A prioridade não significa autorização para ignorar requisitos de segurança ou privacidade.

## 2. Regras gerais

### RG-001: Integridade dos registros

O aplicativo deverá preservar a consistência dos registros de sono.

### RG-002: Transparência

O aplicativo deverá identificar informações registradas, calculadas e estimadas.

### RG-003: Tolerância a dados incompletos

O sistema deverá lidar adequadamente com informações ausentes.

Não deverá inventar horários, durações ou eventos para preencher lacunas.

### RG-004: Correção de informações

O responsável deverá conseguir corrigir registros incorretos.

### RG-005: Exclusão controlada

A exclusão de registros deverá exigir uma ação explícita e apresentar confirmação quando houver risco de perda de informações.

### RG-006: Horário local

Os horários apresentados deverão respeitar a configuração temporal do dispositivo.

A arquitetura deverá considerar mudanças de fuso horário e horário de verão quando aplicáveis.

### RG-007: Idade da criança

A idade deverá ser calculada a partir da data de nascimento, considerando anos, meses e dias.

O sistema não deverá utilizar apenas uma quantidade aproximada de dias para determinar a idade em meses.

### RG-008: Ausência de conexão

As funcionalidades essenciais do MVP deverão funcionar sem internet.

### RG-009: Não dependência de monitoramento automático

O registro manual deverá permanecer plenamente funcional mesmo que recursos de áudio ou inteligência artificial não estejam disponíveis.

### RG-010: Comunicação não médica

O aplicativo não deverá apresentar estimativas ou observações como diagnóstico, avaliação clínica ou garantia de segurança.

---

# 3. Módulo: perfil da criança

## FR-001: Cadastro da criança
**Prioridade:** P0

O aplicativo deverá permitir cadastrar uma criança.

Campos:

- Nome ou apelido, opcional.
- Data de nascimento, obrigatória.

Critérios de aceitação:

1. O nome ou apelido, quando informado, deverá ser validado.
2. O cadastro deverá funcionar sem nome ou apelido.
3. A data de nascimento deverá ser válida.
4. Datas futuras deverão ser rejeitadas.
5. O cadastro deverá ser armazenado corretamente.
6. O usuário deverá receber confirmação do cadastro.

Foto não faz parte do escopo do MVP.

## FR-002: Consulta do perfil
**Prioridade:** P0

O usuário deverá visualizar as informações cadastradas.

Critérios de aceitação:

- Exibir nome ou apelido, quando informado.
- Exibir idade calculada.
- Exibir data de nascimento.
- Não exigir nome para apresentar o perfil.
- Não apresentar informações inexistentes como se fossem conhecidas.

## FR-003: Edição do perfil
**Prioridade:** P0

O usuário deverá conseguir editar as informações permitidas.

Critérios de aceitação:

- Alterações deverão ser persistidas.
- A idade deverá ser recalculada quando necessário.
- A alteração não deverá apagar os registros de sono.

## FR-004: Exclusão do perfil
**Prioridade:** P0

O usuário deverá conseguir solicitar a exclusão do perfil infantil.

Critérios de aceitação:

- Exigir confirmação explícita.
- Informar quais dados serão removidos.
- Remover efetivamente o perfil e todos os episódios de sono associados, além dos dados dependentes sob controle do aplicativo.
- Evitar exclusões acidentais.
- Respeitar obrigações legais de retenção, quando aplicáveis.

## FR-005: Idade dinâmica
**Prioridade:** P0

A idade deverá ser atualizada automaticamente conforme a data atual.

Critérios de aceitação:

- Apresentar idade em formato compreensível.
- Considerar meses e anos completos.
- Atualizar a classificação etária quando necessário.
- Não exigir edição manual do perfil.

---

# 4. Módulo: registro de sono

## FR-006: Iniciar episódio de sono
**Prioridade:** P0

O usuário deverá conseguir iniciar um episódio de sono.

O sistema deverá registrar:

- Identificador único.
- Criança relacionada.
- Horário de início.
- Origem do registro.
- Estado do episódio.

Critérios de aceitação:

1. O início deverá ser registrado com precisão compatível com o dispositivo.
2. O episódio deverá aparecer como ativo.
3. O usuário deverá visualizar que existe um registro em andamento.
4. O aplicativo deverá impedir dois episódios simultâneos incompatíveis para a mesma criança.
5. O registro deverá sobreviver ao fechamento e reabertura do aplicativo.

## FR-007: Finalizar episódio de sono
**Prioridade:** P0

O usuário deverá conseguir finalizar um episódio em andamento.

Critérios de aceitação:

- Registrar o horário de término.
- Calcular a duração.
- Alterar o estado para concluído.
- Atualizar o histórico.
- Atualizar as estatísticas relacionadas.
- Permitir que o responsável informe, opcionalmente, se observou o despertar naquele horário.
- Registrar essa confirmação em `wakeConfirmedAtEnd`, sem alterar o horário de término ou a duração do episódio.
- Permitir concluir o episódio sem fornecer a confirmação.
- Disponibilizar o horário de término como marco inicial de intervalo de vigília para estimativas somente quando `wakeConfirmedAtEnd = true`.

## FR-008: Registro retroativo
**Prioridade:** P0

O usuário deverá conseguir registrar um episódio ocorrido anteriormente.

Campos:

- Data.
- Horário de início.
- Horário de término.
- Confirmação opcional de que o horário de término corresponde ao despertar observado pelo responsável.

Critérios de aceitação:

- Aceitar episódios anteriores ao momento atual.
- Validar a ordem dos horários.
- Impedir duração negativa.
- Informar quando houver sobreposição com outro episódio.
- Permitir revisão antes de salvar.
- Permitir que o responsável deixe a confirmação do despertar como desconhecida.
- Registrar a confirmação em `wakeConfirmedAtEnd`.
- Não considerar como despertar confirmado um horário em que a criança apenas foi encontrada acordada posteriormente.
- Não exigir confirmação para salvar o episódio.
## FR-009: Edição de episódio
**Prioridade:** P0

O usuário deverá conseguir editar um registro existente.

Critérios de aceitação:

- Permitir corrigir início e término.
- Recalcular duração.
- Atualizar estatísticas.
- Preservar integridade do histórico.
- Identificar alterações relevantes quando houver auditoria de modificações.

## FR-010: Exclusão de episódio
**Prioridade:** P0

O usuário deverá conseguir excluir um episódio.

Critérios de aceitação:

- Solicitar confirmação.
- Remover efetivamente o episódio e seus dados dependentes do banco, utilizando transação e preservando a integridade referencial.
- Atualizar estatísticas.
- Evitar que registros excluídos continuem sendo contabilizados.

## FR-011: Identificação da origem
**Prioridade:** P0

Cada episódio deverá indicar sua origem.

Valores conceituais:

- Manual em tempo real.
- Manual retroativo.
- Automático.
- Importado.
- Sincronizado.

Critérios de aceitação:

- A origem deverá ser persistida.
- A interface deverá identificar registros automáticos quando existirem.
- A origem não deverá ser alterada silenciosamente durante uma edição.

## FR-012: Observações
**Prioridade:** P1

O usuário poderá adicionar observações a um episódio.

Exemplos:

- Dormiu no carrinho.
- Acordou chorando.
- Teve dificuldade para adormecer.
- Dormiu durante um passeio.

As observações serão informações fornecidas pelo usuário, não conclusões automáticas do aplicativo.

---

# 5. Módulo: classificação dos episódios

## FR-013: Classificação diurna e noturna
**Prioridade:** P0

O sistema deverá classificar os episódios para fins de organização.

Critérios de aceitação:

- Permitir separar sono diurno e noturno.
- Não assumir que todos os episódios iniciados à noite são necessariamente sono noturno.
- Permitir correção manual.
- Documentar a regra de classificação automática, caso seja utilizada.

## FR-014: Intervalos entre episódios
**Prioridade:** P0

O sistema deverá calcular o intervalo entre episódios consecutivos.

Critérios de aceitação:

- Utilizar horários válidos.
- Considerar a ordem cronológica.
- Não calcular intervalos quando faltarem dados necessários.
- Diferenciar tempo entre episódios de tempo efetivamente acordado.

## FR-015: Sobreposição de episódios
**Prioridade:** P0

O sistema deverá detectar episódios que se sobreponham temporalmente.

Critérios de aceitação:

- Identificar conflitos.
- Informar o usuário.
- Não somar períodos sobrepostos como se fossem tempos independentes.
- Permitir correção dos registros.

---

# 6. Módulo: painel principal

## FR-016: Estado atual
**Prioridade:** P0

O painel deverá apresentar o estado atual do acompanhamento.

Estados possíveis:

- Dormindo, com episódio ativo.
- Sem episódio ativo.
- Dados insuficientes.

O estado não deverá ser interpretado como detecção fisiológica quando depender exclusivamente de registros manuais.

## FR-017: Último episódio
**Prioridade:** P0

Exibir o episódio mais recente.

Informações:

- Horário.
- Duração, quando concluído.
- Classificação.
- Origem.

## FR-018: Resumo diário
**Prioridade:** P0

Exibir:

- Quantidade de episódios.
- Tempo total registrado.
- Quantidade de sonecas.
- Duração dos episódios.
- Estado de registros incompletos.

## FR-019: Acesso rápido
**Prioridade:** P0

As ações frequentes deverão estar disponíveis no painel.

Exemplos:

- Iniciar sono.
- Finalizar sono.
- Registrar episódio anterior.
- Consultar histórico.

---

# 7. Módulo: histórico

## FR-020: Histórico cronológico
**Prioridade:** P0

O aplicativo deverá apresentar os episódios em ordem cronológica.

Critérios de aceitação:

- Exibir data e horários.
- Diferenciar episódios ativos e concluídos.
- Identificar registros incompletos.
- Permitir consultar dias anteriores.

## FR-021: Consulta por período
**Prioridade:** P1

Permitir selecionar períodos de consulta.

Possibilidades:

- Dia.
- Semana.
- Mês.
- Intervalo personalizado.

## FR-022: Detalhes do episódio
**Prioridade:** P0

Ao selecionar um episódio, o usuário deverá visualizar seus detalhes.

## FR-023: Histórico sem registros
**Prioridade:** P0

Quando não houver registros, o aplicativo deverá apresentar uma orientação clara para começar.

Não deverá exibir estatísticas artificiais ou valores presumidos.

---

# 8. Módulo: estatísticas

## FR-024: Duração individual
**Prioridade:** P0

Calcular a duração de cada episódio concluído.

## FR-025: Total diário
**Prioridade:** P0

Calcular o total de sono registrado em um dia.

Critérios de aceitação:

- Não contar episódios duplicados.
- Tratar sobreposições.
- Não incluir episódios inválidos.
- Informar limitações quando existirem registros incompletos.

## FR-026: Média de duração
**Prioridade:** P1

Calcular médias de duração dos episódios em um período.

A interface deverá informar o período considerado.

## FR-027: Quantidade de sonecas
**Prioridade:** P0

Apresentar a quantidade de episódios classificados como sono diurno.

## FR-028: Tendências
**Prioridade:** P1

Permitir comparar padrões entre períodos.

Exemplos:

- Variação da duração das sonecas.
- Mudanças nos horários.
- Alteração na quantidade de episódios.
- Evolução do sono registrado.

Tendências não deverão ser apresentadas como diagnóstico ou prova de desenvolvimento adequado.

## FR-029: Dados insuficientes
**Prioridade:** P0

Quando a quantidade ou qualidade dos registros for insuficiente, o aplicativo deverá informar essa limitação.

Não deverá produzir conclusões com aparência de precisão sem suporte nos dados.

---

# 9. Módulo: estimativa da próxima soneca

## FR-030: Estimativa inicial
**Prioridade:** P0

O aplicativo deverá estimar uma janela provável para o próximo período de descanso.

A estimativa poderá considerar:

- Idade.
- Horário do último despertar.
- Duração da última soneca.
- Intervalos individuais.
- Histórico recente.
- Quantidade de sonecas.

## FR-031: Apresentação da estimativa
**Prioridade:** P0

A estimativa deverá ser apresentada como intervalo ou janela temporal.

Exemplo ilustrativo:

"Próxima janela provável de descanso: entre 13h30 e 14h15."

Os horários acima são apenas um exemplo de apresentação, não uma recomendação universal.

## FR-032: Confiança da estimativa
**Prioridade:** P1

O sistema deverá comunicar o nível de confiança da estimativa.

A confiança deverá ser baseada em critérios documentados, como quantidade e consistência dos registros.

Não deverá utilizar porcentagens arbitrárias.

## FR-033: Atualização da estimativa
**Prioridade:** P1

A estimativa deverá ser recalculada quando novos registros relevantes forem adicionados ou corrigidos.

## FR-034: Ausência de dados
**Prioridade:** P0

Quando não existirem dados suficientes, o aplicativo deverá informar que ainda não consegue gerar uma estimativa individual confiável.

## FR-035: Contextualização etária
**Prioridade:** P1

A estimativa deverá considerar referências adequadas à faixa etária.

As referências deverão ser tratadas como contexto populacional, não como regra obrigatória.

---

# 10. Módulo: conteúdo educativo

## FR-036: Conteúdo educativo básico
**Prioridade:** P0

O MVP deverá disponibilizar um conjunto inicial de conteúdo educativo sobre sono infantil. A biblioteca abrangente e sua expansão poderão ocorrer em versões posteriores.

Categorias previstas:

- Desenvolvimento.
- Necessidades de sono.
- Sonecas.
- Transições.
- Ciclos.
- Despertares.
- Higiene do sono.
- Sono seguro.

## FR-037: Conteúdo por idade
**Prioridade:** P1

Sempre que possível, apresentar conteúdos relacionados à idade da criança.

## FR-038: Referências
**Prioridade:** P0

Conteúdos científicos deverão possuir referências verificáveis.

## FR-039: Atualização de conteúdo
**Prioridade:** P2

A arquitetura deverá permitir atualizar conteúdos sem necessariamente alterar toda a aplicação.

## FR-040: Limites educativos
**Prioridade:** P0

O conteúdo deverá deixar claro que informações gerais não substituem avaliação profissional individual.

---

# 11. Módulo: notificações

## FR-041: Lembretes configuráveis
**Prioridade:** P2

O aplicativo poderá oferecer lembretes configuráveis.

Exemplos:

- Registrar um episódio.
- Finalizar um registro esquecido.
- Consultar o histórico.

## FR-042: Controle de notificações
**Prioridade:** P2

O usuário deverá conseguir ativar e desativar notificações.

## FR-043: Notificações não coercitivas
**Prioridade:** P2

Esta regra será obrigatória caso notificações sejam implementadas. Notificações não fazem parte do MVP.

As notificações não deverão utilizar linguagem alarmista, culpabilizante ou que apresente estimativas como obrigações.

---

# 12. Módulo: monitoramento automático

Este módulo é experimental e não constitui requisito do MVP.

## FR-044: Pesquisa de viabilidade acústica
**Prioridade:** EXP

Investigar a possibilidade de identificar eventos acústicos relevantes.

Exemplos:

- Choro.
- Vocalizações.
- Ruídos ambientais.
- Eventos semelhantes a possíveis despertares.

## FR-045: Identificação de eventos
**Prioridade:** EXP

Caso implementado, o sistema deverá classificar eventos com indicação de incerteza.

## FR-046: Revisão humana
**Prioridade:** EXP

O responsável deverá poder revisar eventos detectados e corrigir classificações.

## FR-047: Transparência do monitoramento
**Prioridade:** EXP

Condição obrigatória antes de qualquer disponibilização futura do monitoramento acústico. Não constitui requisito do MVP.

O aplicativo deverá explicar:

- Quando o microfone está ativo.
- Qual dado está sendo processado.
- Se existe gravação.
- Se existe armazenamento.
- Se há transmissão.
- Como interromper o monitoramento.

## FR-048: Limites de detecção
**Prioridade:** EXP

Condição obrigatória antes de qualquer disponibilização futura do monitoramento acústico. Não constitui requisito do MVP.

O sistema não deverá afirmar que detecta com segurança:

- SMSI.
- Apneia.
- Sufocamento.
- Parada respiratória.
- Condições médicas.
- Estágios fisiológicos do sono por áudio isolado.

## FR-049: Funcionamento em segundo plano
**Prioridade:** EXP

Investigar as limitações do Android para monitoramento contínuo.

Considerar:

- Permissões.
- Serviços em primeiro plano.
- Notificações obrigatórias.
- Consumo de bateria.
- Restrições de execução.
- Interrupções do sistema.
- Compatibilidade entre fabricantes.

A implementação deverá respeitar as políticas da plataforma.

---

# 13. Módulo: armazenamento e funcionamento local

## FR-050: Persistência local
**Prioridade:** P0

Os dados essenciais deverão ser armazenados localmente.

## FR-051: Recuperação após encerramento
**Prioridade:** P0

Os registros deverão permanecer disponíveis após:

- Fechamento do aplicativo.
- Reinicialização do dispositivo.
- Interrupção inesperada do processo.

## FR-052: Integridade dos dados
**Prioridade:** P0

O sistema deverá utilizar mecanismos adequados para evitar corrupção e inconsistência.

## FR-053: Exportação
**Prioridade:** P2

Avaliar exportação de registros em formato legível e estruturado.

## FR-054: Exclusão de dados
**Prioridade:** P0

O usuário deverá conseguir solicitar a exclusão dos dados armazenados. No MVP, a exclusão deverá remover efetivamente os registros e perfis abrangidos pela solicitação, conforme a política de privacidade e o modelo de dados. Cópias externas não controladas pelo aplicativo deverão ser tratadas conforme as informações fornecidas ao responsável.

## FR-055: Ausência de internet
**Prioridade:** P0

O cadastro, os registros, o histórico e os cálculos essenciais deverão funcionar offline.

---

# 14. Módulo: múltiplos usuários e compartilhamento

Este módulo não será obrigatório no MVP.

## FR-056: Compartilhamento de perfil
**Prioridade:** P2

Permitir que responsáveis autorizados acessem o mesmo perfil infantil.

## FR-057: Controle de permissões
**Prioridade:** P2

Permitir definir níveis de acesso.

## FR-058: Identificação de autoria
**Prioridade:** P2

Identificar quem criou ou modificou cada registro.

## FR-059: Sincronização
**Prioridade:** P2

Sincronizar informações entre dispositivos autorizados.

## FR-060: Tratamento de conflitos
**Prioridade:** P2

Detectar alterações conflitantes e aplicar regras documentadas de resolução.

---

# 15. Configurações

## FR-061: Preferências básicas
**Prioridade:** P0

Permitir configurar preferências essenciais.

Exemplos:

- Formato de horário.
- Preferências de apresentação.
- Configurações disponíveis de acessibilidade.

## FR-062: Informações sobre o aplicativo
**Prioridade:** P0

Apresentar:

- Nome.
- Versão.
- Informações de privacidade.
- Contato de suporte, quando disponível.
- Créditos e referências aplicáveis.

---

# 16. Regras de cálculo

## RC-001: Duração

Duração = horário final - horário inicial.

O cálculo deverá considerar corretamente a data associada a cada horário.

## RC-002: Total diário

O total diário deverá considerar a união dos intervalos válidos, sem contar sobreposições duas vezes.

## RC-003: Média

A média deverá utilizar somente episódios válidos para o indicador calculado.

A quantidade de amostras deverá ser informada quando relevante.

## RC-004: Idade

A idade deverá ser calculada a partir da data de nascimento e da data de referência.

## RC-005: Estimativas

Estimativas deverão utilizar dados disponíveis e regras documentadas.

Não poderão ser apresentadas como previsões exatas.

## RC-006: Registros incompletos

Episódios sem horário final não deverão ser incluídos em cálculos que dependam de duração concluída.

## RC-007: Fusos horários

A representação temporal deverá evitar erros em mudanças de fuso e transições de horário.

A estratégia de armazenamento deverá ser definida na arquitetura.

---

# 17. Requisitos de experiência

## UX-001: Registro rápido
**Prioridade:** P0

Iniciar e finalizar um episódio deverá exigir o mínimo possível de interações.

## UX-002: Feedback

Ações importantes deverão apresentar confirmação visual.

## UX-003: Erros compreensíveis

Mensagens de erro deverão explicar o problema e, quando possível, como resolvê-lo.

## UX-004: Estados vazios

Telas sem dados deverão orientar o usuário sem parecer que o aplicativo falhou.

## UX-005: Consistência

Componentes e comportamentos deverão seguir padrões consistentes.

## UX-006: Acessibilidade

A interface deverá respeitar recursos de acessibilidade do Android.

## UX-007: Linguagem

A comunicação deverá ser acolhedora, objetiva e não culpabilizante.

---

# 18. Requisitos de segurança e privacidade

## SEC-001: Permissões justificadas
**Prioridade:** P0

Solicitar somente permissões necessárias para funções utilizadas.

## SEC-002: Transparência

Explicar o motivo de cada permissão sensível.

## SEC-003: Proteção local

Avaliar mecanismos de proteção adequados para os dados armazenados.

## SEC-004: Dados infantis

Tratar os dados da criança com proteção reforçada e observar a LGPD.

## SEC-005: Áudio

Não armazenar nem transmitir áudio sem necessidade técnica justificada e informação clara ao usuário.

## SEC-006: Exclusão

Implementar mecanismos para exclusão dos dados conforme a política de retenção e as obrigações legais.

## SEC-007: Ausência de publicidade comportamental infantil

Não utilizar dados de sono da criança para publicidade comportamental.

---

# 19. Requisitos não funcionais relacionados

Os requisitos não funcionais completos serão definidos em documento próprio.

Entretanto, as funcionalidades deverão considerar:

- Desempenho.
- Confiabilidade.
- Segurança.
- Privacidade.
- Acessibilidade.
- Compatibilidade Android.
- Consumo de bateria.
- Manutenibilidade.
- Testabilidade.
- Funcionamento offline.

---

# 20. Critérios gerais de conclusão

Uma funcionalidade somente poderá ser considerada concluída quando:

1. O comportamento estiver implementado.
2. Os critérios de aceitação estiverem atendidos.
3. Os casos de erro estiverem tratados.
4. Os dados estiverem sendo persistidos corretamente, quando aplicável.
5. Os testes relevantes tiverem sido executados.
6. Não existirem regressões conhecidas sem documentação.
7. A implementação respeitar os requisitos de privacidade e segurança.
8. A documentação estiver atualizada.

Uma implementação parcial não deverá ser apresentada como funcionalidade concluída.

---

# 21. Estratégia de rastreabilidade

Cada requisito deverá estar associado a testes.

Identificadores de teste poderão seguir o padrão:

- TC-FR-001.
- TC-FR-002.
- TC-FR-003.

Os testes deverão verificar tanto o comportamento esperado quanto situações inválidas.

Exemplo:

**Requisito:** FR-007, finalizar episódio.

**Testes possíveis:**

- TC-FR-007-01: finalizar episódio válido.
- TC-FR-007-02: tentar finalizar sem episódio ativo.
- TC-FR-007-03: verificar persistência após reinicialização.
- TC-FR-007-04: verificar atualização do histórico.
- TC-FR-007-05: verificar cálculo de duração.

---

# 22. Observação final

Este documento define o comportamento funcional esperado, mas não determina sozinho a arquitetura ou a tecnologia.

Decisões sobre banco de dados, framework, serviços Android, sincronização e algoritmos deverão ser documentadas separadamente.

Requisitos experimentais não deverão ser implementados automaticamente apenas porque estão descritos aqui.

A prioridade inicial é construir um núcleo confiável de registro, histórico e acompanhamento.

**A Nanalu deverá funcionar corretamente antes de tentar ser inteligente.**
