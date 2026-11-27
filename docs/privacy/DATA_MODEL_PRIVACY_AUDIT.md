# Auditoria de privacidade do modelo de dados

## 1. Objetivo
Comparar o DATA_MODEL.md com o DATA_INVENTORY.md e identificar divergências entre os campos previstos e as necessidades de privacidade.

## 2. ChildProfile.name
Situação: obrigatório no modelo, mas opcional no inventário.
Decisão proposta: tornar opcional e permitir apelido.
Justificativa: o funcionamento não deve depender da identificação nominal completa da criança.
Prioridade: alta.

## 3. ChildProfile.birthDate
Situação: data completa obrigatória.
Decisão proposta: avaliar armazenamento de referência etária menos precisa, conforme as necessidades reais de cálculo.
Justificativa: minimizar dados pessoais e evitar armazenar precisão desnecessária.
Prioridade: alta.

## 4. SleepEpisode.notes
Situação: texto livre opcional não previsto claramente no inventário.
Decisão proposta: retirar do MVP ou definir finalidade, limites e aviso para não inserir informações médicas ou dados de terceiros.
Justificativa: campos livres podem receber informações pessoais e sensíveis imprevistas.
Prioridade: alta.

## 5. SleepEvent.metadata
Situação: estrutura flexível com risco de armazenar dados não previstos.
Decisão proposta: restringir a propriedades previamente definidas e documentadas.
Justificativa: impedir coleta acidental e preservar previsibilidade do armazenamento.
Prioridade: alta.

## 6. SleepEpisode.DELETED
Situação: estado reservado para exclusão lógica.
Decisão proposta: definir diferença entre remoção lógica temporária e exclusão efetiva dos dados pessoais.
Justificativa: exclusão lógica não deve ser apresentada como eliminação definitiva.
Prioridade: alta.

## 7. SleepEvent
Situação: entidade preparada principalmente para eventos automáticos futuros.
Decisão proposta: não implementar no MVP sem necessidade funcional comprovada.
Justificativa: evitar complexidade e armazenamento sem finalidade atual.
Prioridade: média.

## 8. SleepEvent.confidence
Situação: decimal sem semântica formal definida.
Decisão proposta: definir significado, escala e origem antes de qualquer implementação.
Justificativa: impedir interpretação equivocada como probabilidade clínica.
Prioridade: média.

## 9. Conteúdo educativo
Situação: EducationalContent e ContentReference representam conteúdo editorial.
Decisão proposta: manter conteúdo estático local no MVP, sem histórico individual de leitura.
Justificativa: não existe necessidade atual de perfilamento de consumo de conteúdo.
Prioridade: média.

## 10. Timestamps e fuso horário
Situação: DateTime não especifica integralmente a representação temporal.
Decisão proposta: preservar instantes inequívocos, definir armazenamento e apresentação de fuso horário conforme a especificação de cálculos.
Justificativa: evitar erros de duração e de agregação diária.
Prioridade: alta.

## 11. Conclusão
O modelo apresenta uma base adequada, mas ainda não deve ser considerado definitivamente aprovado sob a perspectiva de privacidade.
As decisões propostas deverão ser avaliadas no DATA_MODEL.md, DATA_INVENTORY.md e requisitos funcionais antes da implementação.

## 12. Estado
- Auditoria inicial concluída.
- Divergências identificadas.
- Correções ainda não aplicadas.
- Revisão cruzada final pendente.

## 13. Decisões propostas para o MVP

1. ChildProfile.name: opcional. O aplicativo deverá funcionar sem nome completo.
2. ChildProfile.birthDate: mantido para calcular a idade atual e aplicar referências etárias. Armazenamento exclusivamente local no MVP.
3. SleepEpisode.notes: não será implementado no MVP.
4. SleepEvent: entidade reservada para evolução futura, sem implementação no MVP.
5. SleepEvent.confidence e metadata: reservados ao processamento futuro, com semântica e propriedades formalmente definidas antes da implementação.
6. Exclusão: o MVP deverá realizar remoção efetiva dos registros selecionados e de seus dados dependentes. O estado DELETED não será usado como substituto da exclusão.
7. Conteúdo educativo: conteúdo editorial local, sem histórico individual de leitura no MVP.
8. Datas: instantes deverão ser armazenados de forma inequívoca, preservando a duração real e permitindo apresentação no fuso horário local.

Estas decisões são propostas de especificação e deverão ser refletidas nos documentos originais antes da implementação.
