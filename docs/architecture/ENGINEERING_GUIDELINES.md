# Diretrizes de Engenharia do Nanalu

## 1. Responsabilidade

O desenvolvimento deverá seguir práticas de engenharia de software, priorizando correção, simplicidade, segurança, manutenção e verificabilidade.

O agente de desenvolvimento deverá agir com autonomia técnica dentro das decisões aprovadas, sem alterar silenciosamente o escopo ou as regras do produto.

## 2. Hierarquia de documentação

Em caso de divergência, seguir esta ordem:

1. Decisões técnicas formalmente aprovadas.
2. Especificações de segurança e privacidade.
3. Requisitos funcionais e regras de produto.
4. Especificações de domínio e algoritmos.
5. Arquitetura e diretrizes de engenharia.
6. Plano de implementação.
7. Documentação auxiliar.

Conflitos relevantes deverão ser identificados e resolvidos antes de implementar comportamentos incompatíveis.

## 3. Arquitetura

Respeitar a separação entre:
- Presentation.
- Application.
- Domain.
- Data.
- Platform Services.

Regras de negócio não deverão depender diretamente de componentes visuais, banco de dados ou APIs Android.

A camada de domínio deverá permanecer testável de maneira isolada sempre que possível.

Evitar abstrações desnecessárias, dependências circulares e duplicação de regras.

## 4. Kotlin e Android

- Utilizar Kotlin como linguagem principal.
- Utilizar Jetpack Compose para interface.
- Utilizar Room sobre SQLite para persistência.
- Utilizar Coroutines e Flow para operações assíncronas e fluxos de dados.
- Utilizar ViewModel e StateFlow para estado de apresentação.
- Utilizar DataStore para configurações apropriadas.
- Utilizar WorkManager somente para tarefas adiáveis que exijam execução persistente.

Não introduzir bibliotecas sem necessidade justificada.

Versões e dependências deverão ser centralizadas e documentadas.

## 5. Qualidade do código

- Utilizar nomes claros e consistentes.
- Preferir funções pequenas e responsabilidades bem definidas.
- Evitar duplicação.
- Tratar erros explicitamente.
- Evitar estados impossíveis ou inconsistentes.
- Não esconder falhas com valores padrão arbitrários.
- Não utilizar comentários para compensar código confuso.
- Documentar decisões não óbvias.
- Evitar complexidade prematura.

## 6. Persistência e integridade

- Utilizar operações transacionais quando a integridade exigir.
- Preservar as restrições do modelo de dados.
- Implementar migrações explícitas do Room.
- Testar persistência, atualização e exclusão.
- Evitar dados derivados persistidos sem necessidade.
- Não introduzir sincronização ou armazenamento remoto no MVP.
- Não alterar esquemas existentes sem avaliar compatibilidade e migração.

## 7. Segurança e privacidade

- Armazenar dados no espaço privado do aplicativo.
- Não solicitar permissões desnecessárias.
- Não introduzir telemetria, publicidade ou serviços externos no MVP.
- Não registrar dados pessoais em logs.
- Respeitar as regras documentadas de backup e transferência.
- Implementar exclusão efetiva conforme a especificação.
- Não prometer sobrescrita física em memória flash.
- Não introduzir processamento de áudio no MVP.
- Não compartilhar dados sem especificação e autorização adequadas.

## 8. Regras científicas e de domínio

- Diferenciar observações, cálculos e estimativas.
- Não tratar ausência de registro como ausência de sono.
- Não tratar lacunas como vigília confirmada.
- Não contar intervalos sobrepostos duas vezes.
- Respeitar a confirmação de despertar definida no modelo.
- Não apresentar estimativas como garantias.
- Não transformar correlações em explicações causais.
- Não implementar diagnóstico ou prevenção de condições médicas.
- Não inventar limites científicos ou estatísticos.

## 9. Interface e experiência

- Priorizar clareza e facilidade de uso.
- Apresentar estados vazios, carregamento e erro.
- Fornecer confirmação para ações destrutivas.
- Evitar notificações coercitivas.
- Manter acessibilidade e legibilidade.
- Preservar consistência visual.
- Não permitir que elementos decorativos prejudiquem tarefas essenciais.

## 10. Testes

- Criar testes unitários para regras de domínio.
- Criar testes de integração para persistência e fluxos.
- Criar testes de interface para jornadas críticas.
- Criar testes de regressão para correções.
- Testar limites, datas, horários, sobreposições e exclusões.
- Executar os testes pertinentes após mudanças.
- Não declarar testes aprovados sem execução real.
- Registrar falhas e limitações conhecidas.

## 11. Processo de implementação

Para cada tarefa:

1. Identificar requisitos e documentos relacionados.
2. Inspecionar o código existente.
3. Definir a menor alteração coerente.
4. Implementar a funcionalidade.
5. Criar ou atualizar testes.
6. Executar verificações pertinentes.
7. Corrigir problemas encontrados.
8. Registrar resultado e pendências relevantes.

Evitar reescrever módulos inteiros quando uma alteração localizada resolver o problema.

## 12. Gestão de decisões

Decisões aprovadas não deverão ser modificadas por conveniência.

Quando surgir uma decisão não especificada:
- Avaliar alternativas.
- Escolher a opção mais simples e segura quando não houver risco relevante.
- Registrar justificativa, impacto e limitações.
- Marcar como pendente quando depender de evidência, validação científica ou análise jurídica.
- Não transformar hipótese em requisito definitivo.

## 13. Controle de alterações

Antes de modificar arquivos:
- Identificar o estado atual.
- Preservar trabalho existente.
- Evitar sobrescritas destrutivas.
- Respeitar alterações anteriores.
- Não remover código sem compreender suas dependências.

Ao finalizar:
- Informar arquivos alterados.
- Informar funcionalidades implementadas.
- Informar testes realmente executados.
- Informar resultados e falhas.
- Identificar pendências e riscos.

## 14. Critério de conclusão

Uma tarefa somente estará concluída quando:
- A implementação corresponder ao requisito.
- Os testes pertinentes tiverem sido executados.
- Não houver falhas críticas conhecidas.
- A documentação necessária estiver atualizada.
- O resultado puder ser verificado.

Um relatório, uma proposta ou um plano não substitui código funcional.

## 15. Princípio central

O Nanalu deverá ser desenvolvido com rigor proporcional ao impacto de suas funcionalidades.

A prioridade é entregar um aplicativo simples, confiável, compreensível e seguro, sem prometer capacidades que ainda não foram demonstradas.