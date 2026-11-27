# Nanalu - Índice Mestre da Documentação

## 1. Finalidade

Este documento é o ponto de entrada para toda a documentação do Nanalu.

Antes de implementar qualquer funcionalidade, o agente de desenvolvimento deverá consultar os documentos relacionados ao trabalho.

Não presumir que um requisito está ausente sem consultar este índice e seus documentos.

## 2. Identidade e visão do projeto

- `PROJECT_CHARTER.md`: visão, propósito, público, princípios e limites fundamentais.
- `docs/product/PRODUCT_VISION_AND_SCOPE.md`: visão do produto, MVP e funcionalidades futuras.
- `docs/design/`: identidade visual, experiência e especificações de design.
- Mascote: Luli, uma lua cuja aparência acompanha o período do dia.

## 3. Ciência e conteúdo

Diretório: `docs/science/`

- `CHILD_SLEEP_DEVELOPMENT.md`: desenvolvimento do sono infantil.
- `SLEEP_NEEDS_BY_AGE.md`: necessidades de sono por faixa etária.
- `NAPS_AND_TRANSITIONS.md`: sonecas e transições.
- `SLEEP_CYCLES_AND_AWAKENINGS.md`: ciclos e despertares.
- `SLEEP_HYGIENE.md`: higiene do sono.
- `SCIENTIFIC_REFERENCES.md`: catálogo de referências científicas e institucionais.

Princípio: conteúdo educativo e estimativas devem respeitar as evidências disponíveis e explicitar limitações.

## 4. Produto e requisitos

Diretório: `docs/product/`

- `PRODUCT_VISION_AND_SCOPE.md`: escopo e visão.
- `USER_ROLES_AND_PERSONAS.md`: usuários e necessidades.
- `FUNCTIONAL_REQUIREMENTS.md`: requisitos funcionais FR.
- `SCREEN_MAP.md`: mapa de telas SCR.
- `USER_FLOWS.md`: fluxos FL.
- `PRODUCT_RULES.md`: regras gerais RG e regras de cálculo RC.

Os identificadores dos requisitos deverão ser preservados durante a implementação.

## 5. Arquitetura e tecnologia

Diretório: `docs/architecture/`

- `NON_FUNCTIONAL_REQUIREMENTS.md`: requisitos não funcionais.
- `DATA_MODEL.md`: entidades, campos e integridade.
- `SYSTEM_ARCHITECTURE.md`: organização arquitetural e escopo técnico.
- `TECHNICAL_DECISIONS.md`: decisões técnicas aprovadas e pendentes.
- `TECHNOLOGY_EVALUATION.md`: avaliação das tecnologias.
- `ENGINEERING_GUIDELINES.md`: padrões de engenharia e processo de implementação.

Decisões técnicas aprovadas devem ser respeitadas. Pendências não devem ser silenciosamente transformadas em decisões definitivas.

## 6. Algoritmos

Diretório: `docs/algorithms/`

- `SLEEP_CALCULATION_SPEC.md`: regras de cálculo, elegibilidade, estatísticas e estimativas.
- `ESTIMATION_VALIDATION_PLAN.md`: plano de comparação e validação experimental.

As estimativas iniciais são limitadas à janela de início da próxima soneca. Não representam garantia, diagnóstico ou previsão clínica.

## 7. Segurança

Diretório: `docs/safety/`

- `SECURITY_SPEC.md`: armazenamento, backup, exclusão, proteção e segurança.

As regras de armazenamento local e exclusão efetiva são obrigatórias para o MVP.

## 8. Privacidade e proteção de dados

Diretório: `docs/privacy/`

- `PRIVACY_AND_LGPD.md`: princípios de privacidade, transparência e LGPD.
- `DATA_INVENTORY.md`: inventário de dados tratados.

O MVP é offline, sem conta, sincronização, publicidade, telemetria externa ou processamento de áudio.

## 9. Testes

Diretório: `docs/testing/`

- `TEST_STRATEGY.md`: categorias, organização e critérios de conclusão dos testes.

Os testes de estimativas também devem seguir os cenários ET definidos na especificação de cálculo.

## 10. Roadmap

Diretório: `docs/roadmap/`

- `MVP_IMPLEMENTATION_PLAN.md`: sequência oficial de implementação do MVP.

A ordem do roadmap orienta o desenvolvimento, mas dependências técnicas e correções necessárias podem justificar ajustes documentados.

## 11. Hierarquia em caso de divergência

Consultar primeiro:
1. Decisões técnicas aprovadas.
2. Segurança e privacidade.
3. Requisitos funcionais e regras do produto.
4. Especificações de domínio e algoritmos.
5. Arquitetura e diretrizes de engenharia.
6. Roadmap e documentação auxiliar.

Se dois documentos apresentarem regras incompatíveis, identificar a divergência e resolvê-la antes de implementar o comportamento afetado.

## 12. Regras para o agente de desenvolvimento

- Ler este índice antes de iniciar o trabalho.
- Consultar os documentos específicos da funcionalidade.
- Preservar identificadores de requisitos e decisões.
- Não inventar conteúdo científico, jurídico ou técnico.
- Não implementar funcionalidades fora do MVP sem solicitação explícita.
- Não substituir implementação por relatórios.
- Registrar decisões novas e justificativas.
- Atualizar a documentação quando o comportamento aprovado mudar.
- Executar e registrar testes reais.
- Não declarar como concluído aquilo que não foi implementado e verificado.

## 13. Estado do projeto

A documentação estabelece a base de produto, ciência, arquitetura, segurança, privacidade, algoritmos, testes e implementação.

A existência de uma especificação não significa que sua funcionalidade já esteja implementada.

O desenvolvimento do aplicativo ainda deverá seguir as fases do plano de implementação do MVP.