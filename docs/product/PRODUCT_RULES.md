# PRODUCT_RULES.md
# Regras de negócio e comportamento da Nanalu

**Projeto:** Nanalu
**Versão:** 1.0
**Status:** Especificação inicial
**Data:** 27/11/2026

---

## 1. Objetivo

Este documento estabelece as regras que governam o comportamento da Nanalu.

As regras deverão ser utilizadas como referência para:

- Implementação dos cálculos.
- Classificação dos episódios.
- Validação de registros.
- Geração de estatísticas.
- Estimativas de sono.
- Apresentação de informações.
- Testes automatizados.
- Revisões futuras.

Nenhuma funcionalidade deverá adotar regras contraditórias com este documento sem uma alteração documentada.

## 2. Princípios de negócio

### RB-001: Dados reais têm prioridade

Informações registradas ou confirmadas pelo responsável deverão permanecer distintas de estimativas e inferências.

### RB-002: Não inventar informações

A ausência de um registro não autoriza o sistema a presumir que determinado evento aconteceu.

### RB-003: Transparência

Todo indicador deverá possuir uma definição compreensível.

### RB-004: Individualidade

Os padrões individuais deverão ter prioridade sobre médias populacionais na apresentação de tendências pessoais.

### RB-005: Contexto etário

A idade deverá ser calculada corretamente e utilizada conforme as faixas de referência documentadas.

### RB-006: Incerteza explícita

Quando os dados não sustentarem uma conclusão, o sistema deverá informar a limitação.

### RB-007: Segurança

Nenhuma regra de negócio deverá transformar a Nanalu em ferramenta de diagnóstico ou monitoramento médico.

---

# 3. Regras de identificação da criança

## RB-010: Perfil obrigatório

Todo registro de sono deverá estar associado a um perfil infantil.

## RB-011: Identificador único

Cada perfil deverá possuir um identificador interno único.

O nome não deverá ser utilizado como identificador principal.

## RB-012: Data de nascimento

A data de nascimento será utilizada para calcular a idade.

Não deverá ser alterada automaticamente com base em estatísticas ou registros.

## RB-013: Cálculo de idade

A idade deverá ser calculada em anos, meses e dias completos.

O cálculo deverá considerar o calendário, incluindo:

- Meses com quantidades diferentes de dias.
- Anos bissextos.
- Mudanças de mês.
- Mudanças de ano.

O sistema deverá utilizar uma implementação de calendário confiável, evitando aproximações como considerar todos os meses equivalentes a 30 dias.

## RB-014: Faixas etárias

As categorias etárias deverão ser configuráveis e possuir limites documentados.

Para apresentação de referências gerais de sono:

| Faixa | Referência utilizada |
|---|---|
| 0 a 3 meses | 0 a 3 meses |
| 4 a 11 meses | 4 a 11 meses |
| 1 a 2 anos | 12 a 35 meses |
| 3 a 5 anos | 36 a 71 meses |

Essas categorias refletem agrupamentos de diretrizes, não mudanças biológicas repentinas.

A Nanalu atende inicialmente crianças de 0 a 36 meses. A categoria de 3 a 5 anos poderá aparecer apenas quando necessária para contextualizar a transição de referência.

A ausência de uma recomendação consensual da AASM para menores de 4 meses deverá ser respeitada.

## RB-015: Mudança de faixa

A mudança de faixa deverá ocorrer conforme a idade real da criança.

O sistema não deverá alterar retrospectivamente registros históricos apenas porque a criança mudou de faixa etária.

---

# 4. Regras de registro de sono

## RB-020: Episódio de sono

Um episódio de sono representa um intervalo registrado pelo responsável ou por uma funcionalidade automática posteriormente validada.

## RB-021: Início

Todo episódio deverá possuir horário de início válido.

## RB-022: Término

Um episódio concluído deverá possuir horário de término válido.

## RB-023: Episódio em andamento

Um episódio sem horário de término poderá existir somente como registro em andamento.

Não deverá ser contabilizado como episódio concluído.

## RB-024: Duração

A duração será calculada pela diferença entre o horário final e o inicial.

\[
Duração = Término - Início
\]

O resultado deverá ser não negativo e compatível com os horários armazenados.

## RB-025: Precisão temporal

O sistema deverá preservar a precisão temporal disponível no dispositivo.

A interface poderá apresentar horários arredondados para facilitar a leitura, mas os cálculos internos não deverão perder precisão desnecessariamente.

## RB-026: Registro retroativo

Registros retroativos deverão manter a data e os horários do acontecimento, sem confundi-los com o momento em que foram cadastrados.

## RB-027: Data de criação

O sistema deverá preservar a data e hora em que o registro foi criado.

## RB-028: Data de modificação

Quando um registro for editado, o sistema deverá atualizar sua informação de modificação.

---

# 5. Regras de classificação

## RB-030: Sono diurno e noturno

A classificação diurna ou noturna será utilizada para organização e análise.

Não deverá ser tratada como uma identificação fisiológica.

## RB-031: Classificação manual

O responsável deverá conseguir corrigir uma classificação inadequada.

## RB-032: Classificação automática

Se houver classificação automática, sua regra deverá ser documentada.

A classificação automática deverá ser revisável.

## RB-033: Horário não determina sozinho a natureza do sono

Um episódio ocorrido durante a noite não deverá ser classificado automaticamente como sono noturno apenas pelo horário.

A classificação poderá considerar o contexto da rotina, mas deverá evitar pressupostos rígidos.

## RB-034: Soneca

Uma soneca será tratada como episódio de sono diurno registrado.

O aplicativo não deverá considerar todo período curto de sono como soneca sem avaliar a classificação e o contexto.

## RB-035: Despertar

Um despertar deverá ser registrado como evento ou informação específica.

Não deverá ser inferido automaticamente apenas pela existência de ruído.

---

# 6. Regras de intervalos temporais

## RB-040: Intervalo entre episódios

O intervalo entre dois episódios será calculado a partir do término do primeiro e do início do segundo, desde que ambos estejam concluídos ou possuam horários necessários válidos.

## RB-041: Tempo entre episódios

O tempo entre episódios não será automaticamente denominado tempo acordado.

O sistema não conhece necessariamente todos os períodos de vigília.

## RB-042: Intervalo negativo

Se o início de um episódio ocorrer antes do término do anterior, o sistema deverá identificar uma sobreposição.

Não deverá apresentar intervalo negativo como informação válida.

## RB-043: Cruzamento de meia-noite

Um episódio poderá começar em um dia e terminar no dia seguinte.

Exemplo:

- Início: 22h40 de segunda-feira.
- Término: 06h20 de terça-feira.
- Duração: 7 horas e 40 minutos.

O sistema deverá considerar a data completa, não apenas o horário do relógio.

## RB-044: Mudança de horário do dispositivo

O sistema deverá evitar cálculos incorretos quando o usuário alterar manualmente o relógio ou quando ocorrer mudança de fuso horário.

A estratégia técnica de armazenamento será definida na arquitetura.

## RB-045: Sobreposição

Dois episódios da mesma criança não deverão ser contabilizados como períodos independentes quando ocuparem o mesmo intervalo temporal.

O sistema deverá solicitar revisão quando uma sobreposição for identificada.

---

# 7. Regras de estatísticas

## RB-050: Total de sono diário

O total diário deverá representar a duração dos episódios válidos atribuídos ao dia analisado.

A regra de atribuição deverá ser consistente e documentada.

Para estatísticas por calendário, episódios que atravessarem a meia-noite deverão ser divididos contabilmente entre os dias correspondentes.

Exemplo:

Um episódio das 22h às 02h deverá contribuir com:

- 2 horas no primeiro dia.
- 2 horas no segundo dia.

A divisão será apenas estatística. O registro original continuará sendo um único episódio.

## RB-051: Intervalos sem registro

O sistema não deverá contabilizar automaticamente intervalos sem registro como vigília ou sono.

## RB-052: Episódios incompletos

Episódios sem término não deverão participar de médias de duração concluída.

## RB-053: Sobreposições

Os cálculos de total de sono deverão evitar contar o mesmo intervalo mais de uma vez.

## RB-054: Média de duração

A média deverá utilizar somente episódios válidos e concluídos.

A quantidade de episódios considerados deverá estar disponível para contextualização.

## RB-055: Média diária

A média diária deverá informar quantos dias foram considerados.

Dias sem registros não deverão ser tratados automaticamente como dias com zero horas de sono.

## RB-056: Distribuição

A distribuição diurna e noturna deverá utilizar as classificações dos episódios.

A interface deverá informar quando a classificação for incompleta ou incerta.

## RB-057: Comparações

Comparações entre períodos deverão considerar a quantidade de dias e registros disponíveis.

Períodos com cobertura muito diferente não deverão ser comparados sem contextualização.

## RB-058: Histórico corrigido

Quando um registro for alterado ou excluído, todos os indicadores dependentes deverão ser recalculados.

---

# 8. Regras de estimativa

## RB-060: Natureza da estimativa

Uma estimativa representa uma possibilidade calculada, não um acontecimento confirmado.

## RB-061: Dados mínimos

O sistema deverá definir critérios mínimos de dados para cada método de estimativa.

Esses critérios deverão ser documentados antes da implementação.

## RB-062: Histórico individual

Quando houver dados individuais suficientes, o sistema deverá considerar o padrão da própria criança.

## RB-063: Referências populacionais

Referências por idade poderão servir como contexto inicial quando ainda não houver histórico individual suficiente.

Não deverão ser apresentadas como horários obrigatórios.

## RB-064: Janela temporal

A estimativa deverá ser apresentada como janela ou intervalo quando a precisão disponível não justificar um horário exato.

## RB-065: Confiança

O nível de confiança deverá refletir critérios reais, como:

- Quantidade de registros.
- Consistência dos horários.
- Regularidade do padrão.
- Atualidade dos dados.
- Presença de registros incompletos.

Não deverão ser utilizadas porcentagens arbitrárias.

## RB-066: Ausência de estimativa

O sistema poderá deixar de apresentar uma estimativa quando os dados forem insuficientes ou contraditórios.

## RB-067: Atualização

A estimativa deverá ser recalculada quando dados relevantes forem adicionados, corrigidos ou excluídos.

## RB-068: Não coercitividade

O aplicativo não deverá indicar que a criança precisa dormir obrigatoriamente no horário estimado.

## RB-069: Mudanças de rotina

Alterações importantes no padrão deverão ser consideradas antes de reutilizar estimativas antigas.

## RB-070: Limites do modelo

Cada algoritmo de estimativa deverá possuir:

- Objetivo.
- Dados de entrada.
- Regras de cálculo.
- Limites conhecidos.
- Critérios de confiança.
- Casos de indisponibilidade.
- Estratégia de validação.

---

# 9. Regras de desenvolvimento do sono

## RB-080: Referências por idade

As informações gerais deverão respeitar as fontes científicas documentadas.

## RB-081: Necessidade de sono

As recomendações de duração total deverão incluir o sono diurno quando a diretriz assim estabelecer.

## RB-082: Médias não são metas

Médias observacionais não deverão ser apresentadas como necessidades obrigatórias.

## RB-083: Variação individual

O aplicativo deverá reconhecer diferenças entre crianças da mesma idade.

## RB-084: Transições

Mudanças na quantidade de sonecas deverão ser apresentadas como possíveis tendências, não como eventos obrigatórios em uma idade específica.

## RB-085: Janelas de vigília

Janelas de vigília poderão ser utilizadas como referências educativas ou parâmetros experimentais.

Não deverão ser apresentadas como regras universais com precisão artificial.

## RB-086: Conteúdo científico

Cada informação educativa deverá possuir fonte identificável e contexto apropriado.

## RB-087: Atualização científica

Alterações em diretrizes relevantes deverão motivar revisão dos conteúdos afetados.

---

# 10. Regras sobre eventos e monitoramento automático

## RB-090: Evento detectado

Um evento detectado automaticamente deverá ser identificado como candidato até que sua natureza esteja suficientemente validada.

## RB-091: Evento não confirmado

O sistema não deverá converter automaticamente todo evento acústico em despertar confirmado.

## RB-092: Sono ativo

Movimentos e vocalizações poderão ocorrer durante o sono.

O aplicativo deverá considerar essa possibilidade.

## RB-093: Revisão do responsável

Eventos automáticos deverão permitir revisão humana quando fizerem parte do produto.

## RB-094: Limitação de áudio

Áudio isolado não deverá ser apresentado como medição confiável de estágios fisiológicos do sono.

## RB-095: Alertas médicos

A Nanalu não deverá emitir alertas que afirmem detectar apneia, sufocamento, SMSI ou outras condições médicas.

## RB-096: Transparência

O usuário deverá saber quando o monitoramento está ativo e quais dados são processados.

## RB-097: Falha de monitoramento

Se o monitoramento for interrompido, o aplicativo deverá informar a interrupção quando relevante.

Não deverá aparentar que permaneceu monitorando continuamente.

---

# 11. Regras de interface e comunicação

## RB-100: Linguagem

A comunicação deverá ser acolhedora, objetiva e respeitosa.

## RB-101: Ausência de culpa

O aplicativo não deverá responsabilizar ou culpabilizar os cuidadores por variações naturais do sono.

## RB-102: Indicadores

Cada indicador deverá possuir um significado compreensível.

## RB-103: Estados vazios

Ausência de dados deverá ser apresentada como ausência de informação, não como erro.

## RB-104: Dados insuficientes

O aplicativo deverá diferenciar dados insuficientes de falhas técnicas.

## RB-105: Estimativas identificadas

Estimativas deverão ser visualmente e textualmente diferenciadas de registros confirmados.

## RB-106: Confirmações

O sistema deverá informar quando uma operação foi efetivamente concluída.

## RB-107: Não exagerar precisão

Valores calculados deverão respeitar a precisão dos dados de origem.

---

# 12. Regras de privacidade e dados

## RB-110: Minimização

Somente informações necessárias deverão ser coletadas.

## RB-111: Dados locais

O funcionamento essencial deverá priorizar armazenamento local.

## RB-112: Permissões

Permissões sensíveis deverão ser solicitadas somente quando necessárias.

## RB-113: Áudio

A gravação, retenção ou transmissão de áudio deverá possuir justificativa técnica e comunicação transparente.

## RB-114: Compartilhamento

Nenhum perfil deverá ser compartilhado automaticamente.

## RB-115: Exclusão

A exclusão deverá respeitar as regras de privacidade e retenção aplicáveis.

## RB-116: Dados de crianças

O tratamento deverá observar a LGPD, o melhor interesse da criança e as exigências legais aplicáveis.

---

# 13. Regras de funcionamento offline

## RB-120: Registro local

Os registros essenciais deverão funcionar sem conexão.

## RB-121: Consulta local

Histórico e estatísticas locais deverão permanecer disponíveis offline.

## RB-122: Ausência de sincronização

Na primeira versão, o sistema não deverá presumir que existe sincronização remota.

## RB-123: Futuro compartilhamento

Caso a sincronização seja implementada, deverá possuir regras próprias para conflitos, duplicações e revogação de acesso.

---

# 14. Regras de correção e exclusão

## RB-130: Edição

Alterações deverão ser validadas antes de persistidas.

## RB-131: Exclusão

A exclusão deverá ser explícita.

## RB-132: Integridade

A edição ou exclusão de um episódio deverá atualizar indicadores dependentes.

## RB-133: Preservação

O sistema deverá evitar perda de dados durante operações interrompidas.

## RB-134: Histórico de alterações

A necessidade de manter trilha de auditoria deverá ser definida na arquitetura, considerando privacidade e minimização de dados.

---

# 15. Exemplos de comportamento esperado

## EX-001: Registro que atravessa meia-noite

Entrada:

- Início: 23h.
- Término: 03h do dia seguinte.

Resultado:

- Duração total: 4 horas.
- Distribuição diária: 1 hora no primeiro dia e 3 horas no segundo.

## EX-002: Episódio em andamento

Entrada:

- Início: 10h.
- Sem término.

Resultado:

- Estado: em andamento.
- Duração concluída: indisponível.
- Tempo transcorrido: poderá ser exibido separadamente, com identificação apropriada.

## EX-003: Sobreposição

Entrada:

- Episódio A: 10h às 11h.
- Episódio B: 10h30 às 11h30.

Resultado:

- Conflito identificado.
- Usuário informado.
- Intervalo sobreposto não contabilizado duas vezes.
- Registros preservados até revisão.

## EX-004: Histórico vazio

Entrada:

- Nenhum episódio registrado.

Resultado:

- Total de sono registrado: sem dados.
- Média: indisponível.
- Estimativa individual: indisponível.
- Orientação para iniciar registros.

O sistema não deverá mostrar zero horas como se tivesse observado um dia inteiro.

## EX-005: Poucos registros

Entrada:

- Um episódio registrado.

Resultado:

- Duração individual disponível.
- Estatísticas limitadas.
- Estimativa individual confiável indisponível até que os critérios mínimos sejam atendidos.

## EX-006: Registro corrigido

Entrada:

- Episódio inicialmente registrado com duração de 30 minutos.
- Responsável corrige o término para completar 45 minutos.

Resultado:

- Duração atualizada.
- Histórico atualizado.
- Estatísticas recalculadas.
- Estimativas dependentes revisadas.

## EX-007: Ruído durante o sono

Entrada:

- Evento acústico detectado durante episódio registrado como sono.

Resultado:

- Evento poderá ser apresentado para revisão.
- Não deverá ser classificado automaticamente como despertar confirmado.

---

# 16. Regras que exigem definição técnica posterior

Os seguintes pontos precisam de decisões de arquitetura ou validação antes de implementação:

1. Representação temporal interna.
2. Estratégia de fuso horário.
3. Política de retenção de registros excluídos.
4. Definição de dias estatísticos.
5. Critérios mínimos de histórico para estimativas.
6. Algoritmo de estimativa individual.
7. Método de classificação diurna e noturna.
8. Armazenamento e proteção dos dados.
9. Estratégia de backup.
10. Tratamento de alterações manuais do relógio.
11. Precisão e validação de eventos automáticos.
12. Regras de sincronização futura.

Essas decisões deverão ser registradas antes de serem tratadas como definitivas.

---

# 17. Rastreabilidade

As regras deverão estar relacionadas aos requisitos funcionais e aos testes.

Exemplos:

| Regra | Requisitos relacionados |
|---|---|
| RB-013 | FR-005 |
| RB-020 a RB-028 | FR-006 a FR-012 |
| RB-040 a RB-045 | FR-014 e FR-015 |
| RB-050 a RB-058 | FR-024 a FR-029 |
| RB-060 a RB-070 | FR-030 a FR-035 |
| RB-080 a RB-087 | FR-036 a FR-040 |
| RB-090 a RB-097 | FR-044 a FR-049 |
| RB-110 a RB-123 | FR-047 e FR-050 a FR-060 |

A rastreabilidade deverá ser ampliada durante a especificação dos testes.

## 18. Critério de conclusão

Uma regra será considerada implementada somente quando:

- Existir comportamento correspondente no código.
- Os casos válidos forem tratados.
- Os casos inválidos forem tratados.
- Os testes correspondentes forem aprovados.
- Os resultados forem coerentes com a documentação.

## 19. Princípio central

A Nanalu deverá distinguir aquilo que sabe, aquilo que calcula e aquilo que apenas estima.

Nenhum dado deverá receber mais certeza do que realmente possui.

**A confiança do aplicativo começa quando ele sabe reconhecer os próprios limites.**