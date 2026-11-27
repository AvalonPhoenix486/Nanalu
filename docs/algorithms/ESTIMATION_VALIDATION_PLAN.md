# Plano de validação das estimativas

## 1. Objetivo

Definir como o Nanalu avaliará a qualidade das estimativas de horário da próxima soneca, sem apresentar hipóteses experimentais como evidência científica.

## 2. Escopo

A validação inicial contempla exclusivamente a estimativa da janela de início da próxima soneca.

Não contempla previsão de duração do sono, horário exato de adormecimento, despertares futuros ou avaliação clínica.

## 3. Janelas históricas experimentais

Serão comparadas três possibilidades:

- 3 dias: janela curta.
- 7 dias: referência experimental inicial.
- 14 dias: janela ampliada.

Essas janelas são parâmetros de experimentação, não limites científicos estabelecidos.

A comparação deverá observar:
- Quantidade de intervalos elegíveis.
- Quantidade de dias distintos representados.
- Distribuição temporal dos registros.
- Variabilidade dos intervalos.
- Presença de dados incompletos.
- Mudanças recentes de rotina.
- Diferenças entre primeira soneca e sonecas seguintes.

## 4. Elegibilidade dos dados

Somente intervalos derivados de episódios válidos, concluídos e cronologicamente coerentes poderão participar.

Para utilizar o término de um episódio como início de um intervalo de vigília, será obrigatório que `wakeConfirmedAtEnd` seja verdadeiro.

Registros sem confirmação não serão considerados prova de horário de despertar.

Episódios noturnos e intervalos entre sonecas deverão permanecer separados.

## 5. Estratégia de validação

### Etapa A: testes sintéticos

Criar conjuntos controlados para testar:
- Rotinas regulares.
- Rotinas variáveis.
- Registros concentrados em poucos dias.
- Registros incompletos.
- Valores extremos.
- Mudanças de rotina.
- Histórico insuficiente.
- Correções e exclusões.
- Reprodutibilidade dos resultados.

### Etapa B: comparação experimental

Comparar os resultados produzidos pelas janelas de 3, 7 e 14 dias.

Avaliar estabilidade, sensibilidade a valores extremos e comportamento diante de alterações na rotina.

### Etapa C: validação com dados reais

Antes de apresentar desempenho preditivo como validado, será necessária avaliação com dados reais representativos, obtidos com base legal, transparência e proteção de privacidade adequadas.

Os resultados deverão ser comparados com horários de despertar e início de soneca efetivamente observados.

Testes sintéticos, isoladamente, não comprovam precisão no mundo real.

## 6. Critérios de segurança

O sistema deverá se abster de estimar quando os dados forem insuficientes, inconsistentes ou inadequados.

Não deverá inventar horários para preencher lacunas.

Não deverá apresentar porcentagens de confiança sem validação empírica.

Toda estimativa deverá ser identificada como estimativa, acompanhada de seu período de referência e de limitações relevantes.

## 7. Decisões reservadas para validação

Permanecem em aberto:
- Quantidade mínima de intervalos elegíveis.
- Quantidade mínima de dias distintos.
- Critério de dispersão aceitável.
- Tratamento de valores extremos.
- Detecção de mudanças de rotina.
- Método definitivo para construção dos limites da janela estimada.

Esses parâmetros deverão ser definidos após comparação experimental e avaliação adequada, não por escolha arbitrária.

## 8. Orientação para implementação

A arquitetura deverá permitir alterar a janela histórica e os critérios de elegibilidade sem reescrever o restante do sistema.

Os parâmetros experimentais deverão ficar centralizados e documentados.

A implementação deverá preservar os estados de dados insuficientes, estimativa indisponível e estimativa disponível.

A funcionalidade não poderá ser apresentada como previsão garantida ou orientação médica.