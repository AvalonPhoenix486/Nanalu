# Especificação dos algoritmos de sono

## 1. Objetivo
Definir regras matemáticas, temporais e estatísticas para calcular e apresentar informações de sono na Nanalu.

## 2. Princípios fundamentais
- Todo cálculo deve ser reproduzível a partir dos registros disponíveis.
- Duração observada, duração calculada e duração estimada devem ser identificadas separadamente.
- Dados ausentes não podem ser tratados como zero.
- Intervalos sem registros não representam necessariamente períodos acordados.
- Episódios sobrepostos não podem gerar contagem duplicada.
- Estimativas devem comunicar incerteza e qualidade dos dados.
- Nenhum resultado deve ser apresentado como diagnóstico ou avaliação médica.

## 3. Vocabulário
- Episódio: intervalo registrado com início e, quando concluído, fim.
- Episódio ativo: registro cujo início existe, mas ainda não possui término confirmado.
- Episódio concluído: registro com início e fim válidos.
- Duração: diferença temporal entre início e fim de um episódio concluído.
- Sono observado: período registrado pelo responsável como sono.
- Sono estimado: resultado produzido por um algoritmo, sujeito a incerteza.
- Intervalo sem registro: tempo entre episódios, sem classificação automática como vigília.

## 4. Escopo dos cálculos
- Duração de um episódio.
- Distribuição do sono entre dias do calendário.
- Total diário e por períodos selecionados.
- Sonecas e sono noturno.
- Intervalos entre episódios.
- Médias e tendências.
- Estimativas de horários e janelas de sono.
- Qualidade e suficiência dos dados.

## 5. Convenções temporais
- Instantes devem ser armazenados de forma inequívoca, preservando o instante real do evento.
- A apresentação utiliza o fuso horário local configurado no dispositivo.
- Durações são calculadas pela diferença entre instantes, não pela subtração de horários exibidos.
- Episódios que atravessam a meia-noite devem ser divididos para agregações diárias, sem alterar o episódio original.
- Alterações de fuso horário não podem modificar a duração real já registrada.

## 6. Regras de integridade
- O término deve ser posterior ao início.
- Episódios inválidos ou excluídos não entram nos cálculos.
- Episódios ativos não entram em totais de duração concluída.
- Sobreposições devem ser tratadas antes de somar durações.
- Correções retroativas devem atualizar os resultados derivados.

## 7. Limitações
Os algoritmos trabalham com registros fornecidos pelo responsável e não conseguem confirmar fisiologicamente que a criança dormiu durante todo o intervalo.

## 8. Relação com outros documentos
- docs/product/PRODUCT_RULES.md
- docs/product/FUNCTIONAL_REQUIREMENTS.md
- docs/architecture/DATA_MODEL.md
- docs/science/SLEEP_NEEDS_BY_AGE.md
- docs/science/NAPS_AND_TRANSITIONS.md

## 9. Duração de episódios

Para um episódio concluído:

Duração = instante_final - instante_inicial

- O resultado é uma duração transcorrida, expressa internamente em segundos.
- A apresentação pode converter o resultado para horas e minutos.
- Segundos excedentes não devem ser arredondados antes das agregações.
- Episódios ativos não possuem duração final confirmada.
- Um término anterior ou igual ao início torna o episódio inválido.

## 10. Divisão por dia do calendário

Para calcular o sono de cada dia, utilizar intervalos semiabertos: [início, fim).

Cada episódio deve ser dividido nos limites dos dias locais atravessados.
- O instante original de início e fim não é alterado.
- Cada fragmento pertence ao dia local correspondente.
- A conversão deve considerar o fuso horário e eventuais mudanças de horário oficial.
- A duração de cada fragmento é calculada pela diferença entre seus instantes reais.

Exemplo:
- Início: 22:30.
- Fim: 06:30 do dia seguinte.
- Total do episódio: 8 horas.
- Primeiro dia: 1 hora e 30 minutos.
- Segundo dia: 6 horas e 30 minutos.

## 11. Total diário sem duplicidade

O total diário deve considerar a união dos intervalos de sono concluídos e válidos, após sua divisão pelos dias locais.

Procedimento:
1. Selecionar os episódios concluídos e válidos.
2. Converter seus limites para instantes inequívocos.
3. Dividir os episódios nos limites dos dias locais.
4. Agrupar os fragmentos por dia.
5. Ordenar os intervalos pelo instante inicial.
6. Unir intervalos sobrepostos ou adjacentes.
7. Somar a duração dos intervalos resultantes.

Fórmula conceitual:

Total diário = duração da união dos intervalos válidos pertencentes ao dia.

Intervalos sobrepostos são contados uma única vez no total agregado.
Isso não altera a duração individual exibida em cada registro.

## 12. Exemplo de sobreposição

- Episódio A: 09:00 até 10:30.
- Episódio B: 10:00 até 11:00.
- Soma individual: 2 horas e 30 minutos.
- Total agregado sem duplicidade: 2 horas.

Uma sobreposição deve ser sinalizada para revisão, quando aplicável. A união matemática impede duplicidade estatística, mas não confirma que os registros representam eventos independentes.

## 13. Precisão e apresentação

- Armazenar e calcular durações em segundos ou unidades inteiras equivalentes.
- Evitar cálculos baseados em horários formatados para exibição.
- Converter para horas e minutos somente na camada de apresentação.
- Não arredondar fragmentos individualmente antes de somá-los.
- Distinguir duração de um episódio do total agregado do dia.

## 14. Classificação diurna e noturna

A classificação organiza os registros para análise. Ela não determina o estágio fisiológico do sono.

- DAYTIME: episódio classificado como sono diurno.
- NIGHTTIME: episódio classificado como sono noturno.
- UNCLASSIFIED: episódio sem classificação suficientemente confiável.

A classificação deve considerar uma janela noturna configurável pelo responsável.
- A janela pode atravessar a meia-noite.
- A configuração deve possuir valores iniciais documentados e editáveis.
- O sistema não deve presumir que todos os horários noturnos representam sono principal.
- O horário de início, término e a distribuição do episódio devem ser considerados.

## 15. Classificação de episódios que atravessam a janela

Um episódio pode atravessar a janela noturna ou sair dela.
- A classificação individual deve seguir uma regra determinística documentada.
- A classificação não deve alterar a duração do episódio.
- A classificação diária e a classificação individual são conceitos diferentes.
- Fragmentos diários podem ser utilizados para estatísticas por período, sem reclassificar o registro original.
- Quando não houver dados ou configuração suficientes, utilizar UNCLASSIFIED.

## 16. Sonecas e sono noturno

Sonecas são episódios classificados como sono diurno.
Períodos de sono noturno são episódios classificados como NIGHTTIME.

Regras:
- Contar cada episódio concluído uma única vez nas estatísticas de episódios.
- Apresentar quantidade, duração total e duração média separadamente.
- Não inferir que vários episódios noturnos representam necessariamente uma única noite contínua.
- Não juntar episódios separados apenas porque o intervalo entre eles parece pequeno.
- Não interpretar intervalos sem registros como vigília confirmada.
- Episódios sem classificação confiável permanecem disponíveis nas estatísticas gerais.

## 17. Intervalos entre episódios

Para dois episódios cronologicamente ordenados e não sobrepostos:

Intervalo = início do episódio seguinte - fim do episódio anterior.

- O intervalo representa tempo sem registro de sono.
- Não representa necessariamente tempo acordado.
- Não deve ser contabilizado como sono.
- Se houver sobreposição, não calcular intervalo negativo como período de vigília.
- Episódios ativos não devem produzir intervalos definitivos.

## 18. Estatísticas de sonecas e sono noturno

Para um período selecionado:
- Quantidade de episódios concluídos por classificação.
- Duração total dos episódios.
- Duração média por episódio.
- Maior e menor duração, quando houver amostra suficiente.
- Distribuição dos horários de início e término.

Os resultados devem informar o número de episódios utilizados.
Registros UNCLASSIFIED não devem desaparecer das estatísticas gerais.

## 19. Configuração e transparência

- A janela noturna deve ser armazenada nas configurações do aplicativo.
- Mudanças na janela podem alterar classificações derivadas, mas não os horários originais.
- A interface deve explicar que a classificação é organizacional.
- O responsável deve poder compreender e corrigir uma classificação inadequada.
- Não apresentar classificações como conclusões clínicas.

## 20. Médias e agregações estatísticas

As estatísticas devem identificar claramente o conjunto de dados utilizado.

### Média de duração por episódio

Média = soma das durações dos episódios válidos / quantidade de episódios considerados.

- Episódios ativos, inválidos e excluídos não entram no cálculo.
- A classificação deve ser informada quando houver separação entre períodos diurnos e noturnos.
- A quantidade de episódios utilizados deve acompanhar o resultado.

### Média diária

Média diária = soma dos totais diários / quantidade de dias elegíveis.

- Dias sem informação não devem ser interpretados automaticamente como dias com zero horas de sono.
- Dias parcialmente registrados devem ser identificados como incompletos.
- A interface deve informar quantos dias entraram no cálculo.
- Não apresentar média diária como rotina habitual quando a cobertura dos registros for insuficiente.

## 21. Janelas de análise

A Nanalu poderá oferecer períodos móveis de 7, 14 e 30 dias.

- Cada janela deve utilizar datas locais claramente definidas.
- O início e o fim do período devem ser reproduzíveis.
- Comparações devem utilizar períodos de duração equivalente.
- Alterações retroativas nos registros devem atualizar os resultados.
- A interface deve distinguir período selecionado de período efetivamente coberto por dados.

## 22. Tendências

Tendências descrevem mudanças observadas nos registros, não mudanças fisiológicas comprovadas.

Indicadores possíveis:
- Variação da duração média diária.
- Variação da quantidade de episódios diurnos.
- Mudança nos horários habituais de início.
- Mudança na distribuição do sono entre dia e noite.

Regras:
- Não declarar tendência com base em um único episódio.
- Informar o tamanho da amostra e o intervalo analisado.
- Comparações com cobertura desigual devem ser sinalizadas.
- Oscilações pequenas não devem ser transformadas em alertas.
- Não inferir causas para mudanças observadas.

## 23. Cobertura e qualidade dos dados

A qualidade estatística depende da quantidade, continuidade e consistência dos registros.

Indicadores:
- Quantidade de episódios válidos.
- Quantidade de dias com registros.
- Quantidade de dias parcialmente cobertos.
- Presença de sobreposições ou inconsistências.
- Distribuição temporal dos registros.

Não utilizar um único percentual de confiança sem uma metodologia validada.
Não confundir quantidade de registros com certeza de que todos os períodos de sono foram registrados.

## 24. Ausência de dados

- Ausência de episódio não comprova ausência de sono.
- Dia sem registro não equivale a zero horas de sono.
- Intervalo sem registro não equivale a vigília.
- Dados incompletos devem permanecer identificados como incompletos.
- A interface deve oferecer uma explicação simples quando não houver amostra suficiente.

## 25. Estimativas de sono

As estimativas são ferramentas informativas baseadas no histórico registrado. Não representam previsões clínicas ou garantias de comportamento.

### Fonte dos dados
- Priorizar o histórico individual da criança.
- Considerar idade e contexto apenas quando houver justificativa científica documentada.
- Não substituir o histórico individual por médias populacionais.
- Excluir registros inválidos, excluídos e inconsistentes.
- Identificar períodos com alterações importantes na rotina.

## 26. Metodologia estatística

- A mediana deve ser considerada para reduzir a influência de valores extremos.
- A média pode ser apresentada como indicador complementar.
- Dispersão e variabilidade devem ser consideradas antes de produzir uma janela estimada.
- Amostras pequenas ou concentradas em poucos dias não devem sustentar previsões confiáveis.
- O método e os dados utilizados devem ser reproduzíveis.

## 27. Janelas estimadas

- Preferir intervalos de horário a previsões pontuais.
- Informar que a janela representa um padrão observado, não um horário garantido.
- Não afirmar que a criança estará cansada ou pronta para dormir.
- Não gerar janela quando a variabilidade ou a insuficiência dos dados impedir uma estimativa responsável.
- Não transformar recomendações gerais por idade em previsões individuais.

## 28. Níveis de confiança

A confiança representa a qualidade estatística e a cobertura dos dados utilizados, não a probabilidade de a criança dormir em determinado horário.

Dimensões consideradas:
- Quantidade de observações válidas.
- Distribuição das observações ao longo de dias diferentes.
- Regularidade dos horários registrados.
- Variabilidade dos intervalos observados.
- Presença de lacunas, inconsistências e alterações recentes.

Não atribuir percentuais de confiança sem validação empírica.
Os critérios e limites numéricos deverão ser definidos após testes com dados representativos.

## 29. Situações sem estimativa

Não apresentar estimativa quando:
- Não houver histórico suficiente.
- Os registros forem inconsistentes.
- A variabilidade impedir uma janela útil.
- Houver mudança recente de rotina que torne o histórico pouco representativo.
- O algoritmo não conseguir explicar a origem do resultado.

Nessas situações, explicar que ainda não existem dados suficientes, sem apresentar isso como falha do responsável.

## 30. Transparência das estimativas

Toda estimativa apresentada deverá informar:
- O que está sendo estimado.
- Qual período histórico foi considerado.
- Quantas observações válidas foram utilizadas.
- Qual método foi aplicado.
- Quais limitações afetam o resultado.

Estimativas não devem ser apresentadas como diagnóstico, recomendação médica ou garantia de sono.

## 31. Casos de teste matemáticos

Os casos abaixo definem resultados esperados para validar a implementação.

### CT-001: episódio simples
- Início: 13:00.
- Fim: 14:30.
- Resultado esperado: 90 minutos.

### CT-002: episódio atravessando a meia-noite
- Início: dia 1, 22:30.
- Fim: dia 2, 06:30.
- Duração total: 480 minutos.
- Dia 1: 90 minutos.
- Dia 2: 390 minutos.

### CT-003: episódios sem sobreposição
- Episódio A: 09:00 até 10:00.
- Episódio B: 11:00 até 12:30.
- Total: 150 minutos.
- Intervalo sem registro: 60 minutos.
- O intervalo não pode ser classificado automaticamente como vigília.

### CT-004: sobreposição parcial
- Episódio A: 09:00 até 10:30.
- Episódio B: 10:00 até 11:00.
- Soma individual: 150 minutos.
- Total agregado: 120 minutos.
- Sobreposição: 30 minutos.

### CT-005: episódios adjacentes
- Episódio A: 09:00 até 10:00.
- Episódio B: 10:00 até 11:00.
- Total agregado: 120 minutos.
- Não existe intervalo positivo entre os episódios.

### CT-006: episódio ativo
- Início: 13:00.
- Sem horário de término.
- Resultado: duração concluída indisponível.
- Não incluir em totais de sono concluído.

### CT-007: término inválido
- Início: 14:00.
- Fim: 13:00.
- Resultado: episódio inválido.
- Não incluir em estatísticas.

### CT-008: ausência de registros
- Dia sem episódios registrados.
- Resultado: dado ausente.
- Não apresentar zero horas como se o sono tivesse sido confirmado como inexistente.

### CT-009: episódio dividido em três dias
- Início: dia 1, 23:00.
- Fim: dia 3, 01:00.
- Duração total: 26 horas.
- Dia 1: 60 minutos.
- Dia 2: 1.440 minutos.
- Dia 3: 60 minutos.

### CT-010: união de três intervalos
- Episódio A: 08:00 até 09:00.
- Episódio B: 08:30 até 10:00.
- Episódio C: 09:30 até 11:00.
- Soma individual: 240 minutos.
- Total agregado: 180 minutos.

## 32. Testes temporais adicionais

### CT-011: mudança de fuso horário
- Um episódio deve preservar sua duração real quando o fuso de apresentação mudar.
- A divisão por dias locais pode mudar conforme o fuso.
- O instante original não pode ser reinterpretado como outro instante.

### CT-012: mudança de horário oficial
- A duração deve ser calculada entre instantes reais.
- Um dia local pode ter duração diferente de 24 horas em regiões com mudança oficial de horário.
- Não presumir que todo dia contém exatamente 86.400 segundos.

### CT-013: limites de intervalo
- Os intervalos utilizam a convenção [início, fim).
- O instante final não pertence ao intervalo.
- Episódios adjacentes não devem compartilhar tempo contado.

### CT-014: correção retroativa
- Alterar início ou fim deve recalcular os resultados derivados.
- Estatísticas antigas não podem permanecer desatualizadas.

### CT-015: exclusão de episódio
- Episódio excluído não participa de totais ou médias.
- A exclusão não pode deixar agregações anteriores armazenadas como se ainda fossem válidas.

## 33. Critérios de aprovação dos testes

- Todos os resultados devem ser reproduzíveis.
- Não pode haver duplicidade na união de intervalos.
- A duração individual deve permanecer distinta do total agregado.
- Nenhuma ausência de registro pode ser convertida automaticamente em vigília.
- Os cálculos temporais devem funcionar em diferentes fusos horários.
- Alterações e exclusões devem atualizar os resultados derivados.
- Cada teste deve ser implementado posteriormente como teste automatizado.

## 34. Consistência entre estatísticas

- O total geral diário deve ser calculado pela união de todos os intervalos válidos.
- Estatísticas diurnas e noturnas devem ser calculadas separadamente.
- Se intervalos de categorias diferentes se sobrepuserem, a soma das categorias poderá superar o total geral.
- Essa diferença deve ser identificada e explicada, nunca ocultada.
- Episódios UNCLASSIFIED permanecem incluídos no total geral.
- Nenhum episódio pode desaparecer das estatísticas apenas por não possuir classificação.

## 35. Cobertura real dos dias

- Um dia com registros não deve ser considerado automaticamente completo.
- A ausência de registros não comprova ausência de sono.
- A quantidade de dias registrados representa cobertura observada, não cobertura integral.
- Médias devem informar a quantidade de dias considerados.
- Não utilizar a expressão dia completo sem um critério verificável de completude.

## 36. Classificação ainda dependente de definição

A existência de uma janela noturna configurável não define, por si só, como classificar episódios que atravessam essa janela.

A regra matemática definitiva de classificação deverá especificar:
- Como medir a duração do episódio dentro e fora da janela noturna.
- Como tratar episódios que atravessam várias janelas.
- Como resolver empates.
- Como tratar episódios sem classificação manual.
- Como recalcular classificações após alterações na configuração.

Até essa definição ser aprovada, não implementar uma regra implícita baseada apenas no horário de início.

## 37. Limites de validade

- Episódios excessivamente longos devem ser sinalizados para revisão, sem serem corrigidos silenciosamente.
- Limites máximos de duração e critérios de alerta precisam ser definidos antes da implementação.
- Sobreposições devem ser preservadas para auditoria, mesmo quando a agregação utilizar união de intervalos.
- A correção estatística não deve alterar ou apagar o histórico original do responsável.

## 38. Pendências para implementação

- Definir limites de duração considerados suspeitos.
- Definir a regra determinística de classificação diurna e noturna.
- Definir critérios mínimos de amostra para médias e estimativas.
- Definir método estatístico e largura das janelas estimadas.
- Validar os algoritmos com testes automatizados e dados sintéticos.
- Verificar comportamento em fusos horários e transições de horário oficial.
## 39. Pendências da metodologia de estimativas

A metodologia de estimativas ainda não está pronta para implementação. Os princípios definidos nas seções anteriores são obrigatórios, mas não substituem critérios operacionais e validação.

### Decisões já estabelecidas
- Priorizar o histórico individual da criança.
- Considerar mediana, dispersão e variabilidade.
- Não gerar estimativas sem dados suficientes.
- Apresentar janelas, não horários garantidos.
- Explicar a origem, o período e as limitações do resultado.
- Não utilizar percentuais de confiança sem validação empírica.

### Decisões pendentes
- A duração do histórico deverá respeitar os limites da evidência científica documentada abaixo.

### Limites da evidência sobre duração do histórico

Estudos sobre diários de sono não devem ser interpretados automaticamente como validação de uma janela para previsão individual da próxima soneca.

- Thomas e Burr (2009; PMID: 19587572) encontraram estabilidade de determinadas medidas de sono com três dias de diário em uma amostra de mães e bebês de 4 a 10 semanas. O estudo avaliou a confiabilidade da descrição dos padrões, não a previsão do próximo cochilo.
- Protocolos de pesquisa com cinco a sete dias de coleta demonstram práticas de mensuração, mas não estabelecem, por si só, um número mínimo de dias ou observações para previsão individual.
- A janela histórica inicial de sete dias poderá ser avaliada como hipótese de engenharia, mas não deverá ser apresentada como limite cientificamente validado.
- A suficiência deverá considerar simultaneamente intervalos elegíveis, dias distintos, distribuição temporal, contexto e variabilidade.
- A definição dos limites definitivos dependerá de validação específica para a finalidade de estimar a próxima soneca.

Referências:
- Thomas KA, Burr RL. Accurate assessment of mother & infant sleep: how many diary days are required? 2009. https://pubmed.ncbi.nlm.nih.gov/19587572/
- Associations between parent-reported and objectively measured sleep duration and timing in infants at age 6 months. https://pubmed.ncbi.nlm.nih.gov/33098646/

- Definir exatamente quais eventos e intervalos serão estimados.
- Definir quais registros podem participar de cada cálculo.
- Definir a janela histórica de análise.
- Definir critérios mínimos de quantidade e distribuição das observações.
- Definir o método de cálculo da janela estimada.
- Definir como identificar variabilidade excessiva e mudanças de rotina.
- Definir os critérios de qualidade dos dados e sua comunicação.
- Avaliar o comportamento do método com dados representativos.

### Testes específicos pendentes
- Amostra insuficiente.
- Registros distribuídos em poucos dias.
- Horários regulares e horários muito variáveis.
- Lacunas e registros incompletos.
- Mudança recente de rotina.
- Valores extremos e registros inconsistentes.
- Inclusão, correção e exclusão de registros.
- Reprodutibilidade dos resultados.

### Restrição de implementação
Nenhum limite numérico, percentual, janela histórica ou método estatístico poderá ser inventado durante a implementação.

As decisões deverão ser documentadas, justificadas e acompanhadas de casos de teste antes da implementação da estimativa no MVP.

Enquanto a metodologia não estiver definida, a aplicação deverá preservar os dados e apresentar a indisponibilidade da estimativa de maneira transparente, sem produzir resultados improvisados.

### Escopo funcional da estimativa no MVP

A primeira versão da estimativa terá escopo limitado à próxima soneca da criança.

#### O que será estimado
- Uma janela provável para o início da próxima soneca.
- A janela será apresentada como intervalo de horários, nunca como horário garantido.
- A estimativa será atualizada quando novos registros relevantes forem adicionados ou corrigidos.

#### O que não será estimado no MVP
- Duração da próxima soneca.
- Horário exato em que a criança adormecerá.
- Horário de despertar.
- Quantidade futura de despertares noturnos.
- Duração ou qualidade futura do sono noturno.
- Comportamentos ou condições clínicas da criança.

#### Dados considerados
- Horário de término do último episódio de sono válido.
- Intervalos individuais entre o despertar e o início de uma soneca posterior.
- Histórico recente de episódios válidos e concluídos.
- Quantidade de sonecas já registradas no dia.
- Idade, somente quando houver justificativa científica documentada.

O histórico individual deverá ter prioridade. Referências populacionais não poderão substituir automaticamente os padrões observados na própria criança.

#### Contextos que exigem tratamento separado
A metodologia deverá avaliar separadamente:
- Intervalo entre o despertar do sono noturno e a primeira soneca.
- Intervalos entre uma soneca e a soneca seguinte.

Esses contextos não deverão ser combinados automaticamente como se representassem o mesmo padrão.

#### Limites de apresentação
- Identificar claramente o resultado como estimativa da próxima soneca.
- Informar quando não houver dados suficientes.
- Não apresentar a estimativa como recomendação médica ou indicação de que a criança precisa dormir.
- Não utilizar monitoramento acústico ou eventos automáticos experimentais como fundamento da estimativa no MVP.

A metodologia estatística, os requisitos mínimos de dados e os critérios de validade permanecem pendentes de definição e validação.

### Elegibilidade dos registros para estimativas

#### Registros elegíveis
Um intervalo poderá ser considerado quando:
- O episódio de sono anterior estiver concluído e válido.
- O episódio seguinte estiver concluído e válido.
- Os horários originais de término e início estiverem disponíveis.
- A sequência temporal dos episódios for consistente.
- Ambos os episódios tiverem sido registrados manualmente no MVP.
- O contexto dos episódios permitir identificar qual tipo de intervalo está sendo observado.

#### Registros excluídos
Não utilizar:
- Episódios ativos ou sem horário de término.
- Episódios inválidos ou excluídos.
- Registros com horários inconsistentes.
- Intervalos cuja origem ou destino não possa ser identificado.
- Eventos acústicos ou detecções automáticas experimentais.
- Períodos sem registro tratados indevidamente como períodos de vigília confirmada.

#### Separação dos contextos
- Intervalo entre o término do sono noturno e o início da primeira soneca do dia deve formar uma categoria própria.
- Intervalo entre o término de uma soneca e o início da soneca seguinte deve formar outra categoria.
- Não combinar as duas categorias automaticamente.
- Não considerar o término de um episódio noturno como despertar confirmado se o registro não permitir essa interpretação.

#### Integridade e atualização
- Correções nos horários devem invalidar e recalcular os intervalos derivados afetados.
- Exclusões devem retirar os intervalos dependentes das análises.
- Um mesmo intervalo não poderá ser contabilizado mais de uma vez.
- Ausência de registro não poderá ser interpretada como observação de vigília.

#### Critérios ainda pendentes
- Definir limites temporais para identificar intervalos atípicos.
- Definir como tratar episódios registrados retroativamente.
- Definir como lidar com dias sem cobertura suficiente.
- Definir como identificar mudanças relevantes na rotina.
- Validar se os critérios de classificação diurna e noturna permitem identificar esses contextos com segurança.

Nenhum limite temporal de elegibilidade deverá ser presumido antes de sua definição e justificativa.

### Princípios para construção da janela estimada

#### Referência central
- A mediana dos intervalos individuais válidos deverá ser avaliada como referência central.
- A média poderá ser utilizada como indicador complementar, mas não deverá substituir automaticamente a mediana.
- A escolha do método deverá considerar a distribuição e a quantidade de observações disponíveis.

#### Representação da variabilidade
- A janela deverá representar a dispersão observada nos intervalos individuais.
- A largura da janela deverá refletir a variabilidade dos dados, sem transmitir precisão artificial.
- Valores extremos deverão ser identificados e tratados por método documentado, sem exclusão arbitrária.
- A janela não poderá ser reduzida artificialmente apenas para produzir um resultado visualmente mais preciso.

#### Conversão para horário
- O intervalo estimado deverá ser relacionado ao horário de término do último episódio válido.
- A conversão deverá respeitar o contexto correspondente: primeira soneca ou soneca subsequente.
- O cálculo deverá preservar a distinção entre duração observada e horário estimado.
- O resultado deverá ser reproduzível a partir dos registros e da versão do método utilizado.

#### Limitações
- A mediana isoladamente não demonstra que a amostra seja representativa.
- A dispersão observada não garante que o próximo intervalo seguirá o mesmo padrão.
- Mudanças de rotina podem tornar o histórico anterior pouco representativo.
- A estimativa deverá ser omitida quando a variabilidade impedir uma janela útil.

#### Decisões pendentes
- A duração do histórico deverá respeitar os limites da evidência científica documentada abaixo.

### Limites da evidência sobre duração do histórico

Estudos sobre diários de sono não devem ser interpretados automaticamente como validação de uma janela para previsão individual da próxima soneca.

- Thomas e Burr (2009; PMID: 19587572) encontraram estabilidade de determinadas medidas de sono com três dias de diário em uma amostra de mães e bebês de 4 a 10 semanas. O estudo avaliou a confiabilidade da descrição dos padrões, não a previsão do próximo cochilo.
- Protocolos de pesquisa com cinco a sete dias de coleta demonstram práticas de mensuração, mas não estabelecem, por si só, um número mínimo de dias ou observações para previsão individual.
- A janela histórica inicial de sete dias poderá ser avaliada como hipótese de engenharia, mas não deverá ser apresentada como limite cientificamente validado.
- A suficiência deverá considerar simultaneamente intervalos elegíveis, dias distintos, distribuição temporal, contexto e variabilidade.
- A definição dos limites definitivos dependerá de validação específica para a finalidade de estimar a próxima soneca.

Referências:
- Thomas KA, Burr RL. Accurate assessment of mother & infant sleep: how many diary days are required? 2009. https://pubmed.ncbi.nlm.nih.gov/19587572/
- Associations between parent-reported and objectively measured sleep duration and timing in infants at age 6 months. https://pubmed.ncbi.nlm.nih.gov/33098646/

- Definir a medida de dispersão mais adequada.
- Definir como construir os limites inferior e superior da janela.
- Definir como tratar valores extremos.
- Definir se e quando diferentes períodos históricos deverão receber pesos distintos.
- Validar o método com dados representativos antes de sua adoção definitiva.

### Avaliação da suficiência e qualidade dos dados

A disponibilidade de uma estimativa deverá depender da qualidade e da representatividade dos registros, não apenas da existência de observações.

#### Estados de disponibilidade

A avaliação deverá distinguir:

1. Dados insuficientes:
   - Não há observações elegíveis suficientes.
   - Os registros estão concentrados em poucos dias.
   - Ainda não existe cobertura adequada do contexto analisado.

2. Estimativa indisponível:
   - Existem observações, mas há inconsistências relevantes.
   - A variabilidade impede a construção de uma janela útil.
   - Alterações recentes na rotina tornam o histórico pouco representativo.
   - O método não consegue justificar o resultado.

3. Estimativa disponível:
   - Os critérios mínimos de quantidade e distribuição foram atendidos.
   - Os registros são válidos e pertencem ao mesmo contexto de análise.
   - A variabilidade permite construir uma janela interpretável.
   - O resultado pode ser reproduzido e explicado.

#### Dimensões obrigatórias de avaliação

- Quantidade de intervalos elegíveis.
- Quantidade de dias diferentes representados.
- Distribuição temporal das observações.
- Regularidade e variabilidade dos intervalos.
- Presença de lacunas e registros incompletos.
- Alterações recentes na rotina.
- Coerência entre os episódios que formam cada intervalo.

#### Comunicação ao responsável

- Explicar por que a estimativa está indisponível.
- Não tratar ausência de dados como erro do responsável.
- Não apresentar uma estimativa indisponível como se fosse uma previsão de baixa confiança.
- Não utilizar percentuais ou classificações de confiança sem metodologia validada.
- Informar quais dimensões de qualidade foram consideradas, em linguagem compreensível.

#### Critérios numéricos pendentes

Ainda deverão ser definidos e justificados:
- Quantidade mínima de observações.
- Quantidade mínima de dias distintos.
- Período mínimo e máximo de histórico.
- Limites de variabilidade aceitável.
- Critérios objetivos para identificar mudanças de rotina.
- Regras para transição entre os estados de disponibilidade.

Esses limites deverão ser avaliados com dados representativos e casos de teste. Até essa validação, não poderão ser inventados durante a implementação.

### Cenários de teste das estimativas

Os cenários abaixo complementam os testes matemáticos e temporais anteriores. Os resultados numéricos definitivos dependem da metodologia estatística ainda pendente.

#### ET-001: histórico vazio
- Não existem intervalos registrados.
- Resultado esperado: estimativa indisponível por ausência de dados.

#### ET-002: histórico insuficiente
- Existem observações, mas os critérios mínimos ainda não foram atendidos.
- Resultado esperado: informar insuficiência de dados, sem gerar janela.

#### ET-003: observações concentradas
- Existem vários registros, mas concentrados em poucos dias.
- Resultado esperado: avaliar a cobertura temporal e impedir que a quantidade isolada seja considerada suficiente.

#### ET-004: intervalos regulares
- Existem observações válidas e distribuídas ao longo de dias diferentes.
- Os intervalos apresentam baixa variabilidade.
- Resultado esperado: permitir estimativa somente se todos os critérios de suficiência forem atendidos.

#### ET-005: intervalos muito variáveis
- Existem observações válidas, mas com grande dispersão.
- Resultado esperado: não apresentar janela inadequada ou artificialmente precisa.

#### ET-006: contextos diferentes
- Existem intervalos entre despertar noturno e primeira soneca, e intervalos entre sonecas.
- Resultado esperado: manter os contextos separados.

#### ET-007: mudança recente de rotina
- O histórico anterior apresenta padrão diferente dos registros recentes.
- Resultado esperado: avaliar a representatividade do histórico e evitar uma estimativa baseada em padrão desatualizado.

#### ET-008: registro incompleto
- Um dos episódios necessários para formar o intervalo está incompleto.
- Resultado esperado: excluir o intervalo da análise.

#### ET-009: correção retroativa
- O responsável corrige o horário de um episódio utilizado na estimativa.
- Resultado esperado: recalcular os intervalos e invalidar resultados derivados desatualizados.

#### ET-010: exclusão de registro
- O responsável exclui um episódio utilizado na estimativa.
- Resultado esperado: retirar os intervalos dependentes e reavaliar a disponibilidade da estimativa.

#### ET-011: valores extremos
- Um ou mais intervalos apresentam valores muito diferentes dos demais.
- Resultado esperado: aplicar somente o tratamento estatístico documentado, sem exclusão arbitrária.

#### ET-012: reprodução do resultado
- Os mesmos registros, configurações e versão do método são utilizados novamente.
- Resultado esperado: produzir o mesmo resultado, respeitando as regras de precisão e apresentação.

#### ET-013: ausência de estimativa justificável
- Os dados não permitem explicar de maneira reproduzível a origem da janela.
- Resultado esperado: não apresentar estimativa.

#### Critérios de aprovação
- Cada cenário deverá possuir dados de entrada reproduzíveis.
- Os resultados esperados deverão ser definidos quando a metodologia for ratificada.
- Nenhum teste poderá depender de horários ou limites arbitrários.
- A implementação deverá cobrir todos os cenários aplicáveis.
