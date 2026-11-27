# NON_FUNCTIONAL_REQUIREMENTS.md
# Requisitos não funcionais da Nanalu

**Projeto:** Nanalu
**Versão:** 1.0
**Status:** Especificação inicial
**Data:** 27/11/2026

---

## 1. Objetivo

Este documento define os requisitos de qualidade, confiabilidade, desempenho, segurança, compatibilidade e manutenção da Nanalu.

Enquanto os requisitos funcionais determinam o que o aplicativo deve fazer, os requisitos não funcionais determinam as condições sob as quais essas funcionalidades deverão operar.

Os requisitos deste documento deverão orientar a escolha tecnológica, a arquitetura, a implementação e os testes.

## 2. Princípios

A Nanalu deverá priorizar:

1. Confiabilidade dos registros.
2. Privacidade dos dados infantis.
3. Funcionamento offline.
4. Baixo consumo de recursos.
5. Interface simples e responsiva.
6. Compatibilidade com dispositivos Android reais.
7. Recuperação segura após interrupções.
8. Manutenção sustentável.
9. Acessibilidade.
10. Transparência sobre limitações.

---

# 3. Desempenho

## NFR-001: Inicialização

O aplicativo deverá apresentar sua interface inicial rapidamente.

Meta inicial:
- Primeira abertura após instalação: até 3 segundos em dispositivo de referência.
- Aberturas subsequentes: até 2 segundos em dispositivo de referência.

Essas metas deverão ser medidas em condições documentadas e ajustadas conforme os resultados dos testes.

## NFR-002: Navegação

A navegação entre telas principais deverá ocorrer sem atrasos perceptíveis.

Meta inicial: resposta visual em até 300 ms para interações comuns, desconsiderando operações externas ou processamento excepcional.

## NFR-003: Operações locais

Operações comuns de cadastro, edição e consulta deverão ser executadas localmente sempre que possível.

## NFR-004: Histórico

A consulta ao histórico deverá continuar responsiva com pelo menos 5.000 episódios armazenados em um perfil.

## NFR-005: Estatísticas

O cálculo de estatísticas deverá evitar bloqueios da interface.

Processamentos demorados deverão ocorrer fora da thread principal.

## NFR-006: Uso de memória

O aplicativo deverá evitar consumo excessivo de memória e liberar recursos que não estejam em uso.

A meta quantitativa será estabelecida durante os testes em dispositivos de referência.

## NFR-007: Operações simultâneas

O sistema deverá impedir inconsistências quando operações de leitura, gravação e atualização ocorrerem em sequência rápida.

---

# 4. Consumo de energia

## NFR-010: Eficiência energética

O aplicativo deverá minimizar o consumo de bateria durante seu uso normal.

## NFR-011: Processamento em segundo plano

Processamentos em segundo plano deverão ser utilizados somente quando necessários.

## NFR-012: Monitoramento acústico

Qualquer funcionalidade futura de monitoramento acústico deverá possuir avaliação específica de consumo energético.

O recurso não deverá permanecer ativo sem indicação clara ao responsável.

## NFR-013: Ausência de atividade

Quando não houver funcionalidade que justifique processamento contínuo, o aplicativo deverá permanecer em estado de baixo consumo.

## NFR-014: Limitações do Android

A arquitetura deverá respeitar as políticas de execução em segundo plano, permissões e restrições de bateria do Android.

Não deverá prometer monitoramento contínuo garantido quando o sistema operacional ou o fabricante puder interrompê-lo.

---

# 5. Compatibilidade

## NFR-020: Plataforma principal

A primeira versão será desenvolvida para Android.

## NFR-021: Versão mínima

A versão mínima do Android será definida após avaliação tecnológica.

A decisão deverá considerar:
- Base de dispositivos compatíveis.
- Segurança.
- APIs necessárias.
- Suporte das bibliotecas.
- Recursos de áudio em segundo plano, caso sejam implementados.

## NFR-022: Diferentes fabricantes

O aplicativo deverá ser testado em dispositivos de fabricantes diferentes.

Diferenças de gerenciamento de bateria, permissões, notificações e processos em segundo plano deverão ser consideradas.

## NFR-023: Diferentes resoluções

A interface deverá adaptar-se a diferentes tamanhos de tela e densidades de pixels.

## NFR-024: Orientação

As telas deverão funcionar adequadamente nas orientações oficialmente suportadas.

A orientação preferencial será definida na especificação de interface.

## NFR-025: Atualizações

Atualizações do Android não deverão provocar perda de registros ou corrupção dos dados locais.

---

# 6. Confiabilidade e integridade

## NFR-030: Persistência

Um registro confirmado deverá permanecer armazenado após o fechamento do aplicativo.

## NFR-031: Integridade

Operações de gravação deverão preservar a consistência dos dados.

## NFR-032: Atomicidade

Operações que envolvam múltiplas alterações relacionadas deverão utilizar mecanismos que evitem estados parcialmente gravados.

## NFR-033: Recuperação

Após encerramento inesperado, o aplicativo deverá recuperar os dados persistidos.

## NFR-034: Interrupções

O sistema deverá tratar adequadamente:
- Encerramento pelo Android.
- Reinicialização do dispositivo.
- Falta de bateria.
- Falha durante gravação.
- Atualização do aplicativo.
- Interrupção durante edição.

## NFR-035: Registros ativos

Um episódio em andamento deverá possuir estado persistente suficiente para ser recuperado após uma interrupção.

## NFR-036: Ausência de duplicação

A recuperação de operações interrompidas não deverá criar registros duplicados.

## NFR-037: Tratamento de erros

Erros deverão ser tratados de maneira controlada.

O aplicativo não deverá ocultar falhas importantes nem apresentar operações como concluídas quando não foram persistidas.

## NFR-038: Consistência de cálculos

Os mesmos dados e parâmetros deverão produzir os mesmos resultados, respeitando a precisão definida.

---

# 7. Funcionamento offline

## NFR-040: Operação independente de internet

As funcionalidades essenciais deverão funcionar sem conexão.

## NFR-041: Armazenamento local

O perfil infantil, registros, histórico e estatísticas essenciais deverão permanecer disponíveis localmente.

## NFR-042: Ausência de dependência externa

A consulta aos registros não deverá depender de serviços remotos.

## NFR-043: Falha de conexão

A ausência de internet não deverá impedir operações locais.

## NFR-044: Dependências externas

Funcionalidades que futuramente dependerem de internet deverão informar claramente essa necessidade.

---

# 8. Segurança

## NFR-050: Proteção dos dados

Os dados pessoais e infantis deverão ser protegidos contra acesso não autorizado.

## NFR-051: Armazenamento

A arquitetura deverá avaliar mecanismos adequados de proteção do armazenamento local.

## NFR-052: Comunicação

Qualquer comunicação futura com servidores deverá utilizar canais seguros.

## NFR-053: Segredos

Chaves, senhas, tokens e credenciais não deverão ser armazenados diretamente no código-fonte.

## NFR-054: Permissões

O aplicativo deverá solicitar somente as permissões necessárias para funcionalidades efetivamente utilizadas.

## NFR-055: Princípio do menor privilégio

Componentes e serviços deverão receber apenas os acessos necessários.

## NFR-056: Dependências

Bibliotecas externas deverão ser avaliadas quanto à manutenção, origem, permissões e riscos conhecidos.

## NFR-057: Registro técnico

Logs não deverão armazenar dados pessoais ou conteúdo sensível desnecessário.

## NFR-058: Atualizações seguras

O processo de atualização deverá preservar a integridade dos dados e evitar instalação de componentes não confiáveis.

---

# 9. Privacidade

## NFR-060: Minimização de dados

A coleta deverá ser limitada ao necessário para as funcionalidades.

## NFR-061: Privacidade por padrão

As configurações iniciais deverão favorecer a privacidade.

## NFR-062: Transparência

O responsável deverá compreender quais informações são armazenadas e por quê.

## NFR-063: Áudio

Caso exista processamento acústico, a arquitetura deverá definir:
- Se o áudio será gravado.
- Se haverá processamento temporário.
- Se haverá armazenamento.
- Se haverá transmissão.
- Por quanto tempo os dados serão mantidos.
- Como o usuário poderá interromper o recurso.

Essas decisões não poderão ser presumidas durante a implementação.

## NFR-064: Exclusão de dados

O usuário deverá possuir meios claros para excluir seus dados, respeitando as obrigações legais aplicáveis.

## NFR-065: Serviços de terceiros

Integrações futuras deverão passar por avaliação de privacidade antes de serem adotadas.

---

# 10. Usabilidade e acessibilidade

## NFR-070: Simplicidade

As tarefas mais frequentes deverão exigir poucas interações.

## NFR-071: Clareza

Rótulos, ícones e mensagens deverão possuir significados compreensíveis.

## NFR-072: Hierarquia visual

Informações importantes deverão ser facilmente identificáveis.

## NFR-073: Feedback

A interface deverá informar o resultado de operações relevantes.

## NFR-074: Erros compreensíveis

Mensagens de erro deverão explicar o problema e, quando possível, como resolvê-lo.

## NFR-075: Toque

Controles deverão possuir dimensões adequadas para interação por toque.

## NFR-076: Contraste

Textos e controles deverão respeitar critérios de contraste adequados.

## NFR-077: Escala de texto

A interface deverá respeitar as configurações de tamanho de fonte do sistema sempre que possível.

## NFR-078: Leitores de tela

Componentes interativos deverão possuir descrições acessíveis.

## NFR-079: Cores

Informações importantes não deverão depender exclusivamente da distinção entre cores.

## NFR-080: Linguagem

A comunicação deverá utilizar português brasileiro claro e natural.

---

# 11. Manutenibilidade

## NFR-090: Organização

O código deverá ser dividido em módulos com responsabilidades bem definidas.

## NFR-091: Separação de responsabilidades

Interface, regras de negócio, persistência e processamento deverão permanecer separados.

## NFR-092: Legibilidade

O código deverá utilizar nomes claros, estruturas consistentes e comentários quando necessários.

## NFR-093: Testabilidade

As regras de negócio deverão ser implementadas de modo que possam ser testadas sem depender obrigatoriamente da interface.

## NFR-094: Dependências

Dependências deverão ser justificadas e mantidas em versões controladas.

## NFR-095: Documentação

Decisões arquiteturais relevantes deverão ser documentadas.

## NFR-096: Evolução

A estrutura deverá permitir adicionar funcionalidades sem exigir reescrita desnecessária das partes estáveis.

## NFR-097: Compatibilidade

Alterações no modelo de dados deverão considerar registros já existentes e migrações seguras.

---

# 12. Observabilidade e diagnóstico técnico

## NFR-100: Logs técnicos

O aplicativo poderá manter logs técnicos para diagnóstico de falhas.

## NFR-101: Minimização

Logs deverão evitar informações pessoais e conteúdo sensível.

## NFR-102: Erros

Falhas importantes deverão ser identificáveis durante testes e desenvolvimento.

## NFR-103: Ambiente de produção

A versão de produção não deverá expor informações internas desnecessárias.

## NFR-104: Diagnóstico local

Sempre que possível, falhas deverão ser diagnosticáveis sem exigir transmissão automática de dados pessoais.

---

# 13. Distribuição e instalação

## NFR-110: Instalação

O pacote deverá ser instalável em dispositivos Android compatíveis.

## NFR-111: Atualização

Atualizações deverão preservar os dados locais.

## NFR-112: Identificação

O aplicativo deverá possuir identificador de pacote estável.

## NFR-113: Assinatura

Versões distribuídas deverão possuir assinatura digital válida.

## NFR-114: Build reproduzível

O processo de compilação deverá ser documentado e controlado.

## NFR-115: Versões

O projeto deverá manter identificação clara de versões e histórico de alterações.

---

# 14. Testabilidade e qualidade

## NFR-120: Testes automatizados

Regras de negócio e cálculos deverão possuir testes automatizados.

## NFR-121: Testes de interface

Fluxos principais deverão ser testados em ambiente Android.

## NFR-122: Testes em dispositivos reais

A validação deverá incluir dispositivos físicos, não apenas emuladores.

## NFR-123: Testes de interrupção

Deverão ser testados encerramento forçado, reinicialização e interrupção durante gravação.

## NFR-124: Testes offline

As funcionalidades essenciais deverão ser verificadas sem internet.

## NFR-125: Testes de desempenho

Inicialização, navegação, memória, armazenamento e cálculos deverão ser medidos.

## NFR-126: Testes de segurança

As permissões, o armazenamento e as dependências deverão passar por revisão.

## NFR-127: Regressão

Correções e novas funcionalidades não deverão quebrar comportamentos previamente validados.

---

# 15. Metas de qualidade do MVP

As metas abaixo são objetivos iniciais, não resultados já comprovados.

| Indicador | Meta inicial |
|---|---|
| Inicialização subsequente | Até 2 segundos |
| Resposta de interações comuns | Até 300 ms |
| Histórico | 5.000 episódios por perfil |
| Funcionamento essencial offline | Obrigatório |
| Recuperação de dados persistidos | Obrigatória |
| Duplicação após recuperação | Não permitida |
| Testes das regras de cálculo | Cobertura dos casos críticos |
| Perda de registros confirmados | Não permitida em condições normais de operação |
| Permissões desnecessárias | Nenhuma |
| Falhas críticas conhecidas | Nenhuma na versão de lançamento |

Os resultados deverão ser medidos em dispositivos de referência e documentados.

A cobertura de testes não deverá ser utilizada isoladamente como prova de qualidade.

---

# 16. Dispositivos de referência

Antes da implementação final, deverá ser estabelecida uma matriz de dispositivos para testes.

A matriz deverá incluir:

- Android de entrada.
- Android intermediário.
- Android mais recente.
- Pelo menos dois fabricantes.
- Diferentes tamanhos de tela.
- Dispositivos com restrições agressivas de bateria.

A versão mínima do Android e os dispositivos de referência serão definidos durante a avaliação tecnológica.

---

# 17. Riscos técnicos conhecidos

| Risco | Tratamento esperado |
|---|---|
| Encerramento em segundo plano | Persistência e recuperação |
| Restrições de bateria | Respeitar políticas do Android |
| Pouca memória | Otimização e testes |
| Mudança de fuso | Estratégia temporal consistente |
| Corrupção de dados | Transações e validação |
| Bibliotecas vulneráveis | Auditoria de dependências |
| Falha de armazenamento | Tratamento de erros |
| Atualização do Android | Testes de compatibilidade |
| Interrupção do monitoramento acústico futuro | Estado explícito, transparência e recuperação segura, caso a função seja implementada |
| Perda de dispositivo | Informar que o MVP não possui backup automático e que os registros locais podem ser perdidos; eventual exportação e restauração futura deverá ser explícita e protegida |

---

# 18. Decisões técnicas ainda pendentes

As seguintes definições permanecem necessárias para orientar a implementação:

1. Versões exatas das dependências, ferramentas e ambiente de compilação.
2. Versão mínima do Android e matriz de dispositivos de referência.
3. Biblioteca e estratégia definitiva de navegação.
4. Estratégia de injeção de dependências.
5. Estratégia detalhada de migrações e evolução do banco Room.
6. Estratégia de criptografia do banco e proteção adicional dos dados locais.
7. Ferramentas e estratégia de testes automatizados.
8. Distribuição, assinatura e proteção do pacote de instalação.
9. Exportação e restauração protegidas, somente se esses recursos forem implementados futuramente.
10. Viabilidade técnica do monitoramento acústico, que permanece fora do MVP.

As decisões tecnológicas já ratificadas não devem ser tratadas como pendentes nesta seção.

---

# 19. Critérios de aceitação

Um requisito não funcional será considerado atendido quando:

- Existir implementação verificável.
- Houver método de teste definido.
- O teste tiver sido executado.
- O resultado estiver documentado.
- Não houver falha crítica conhecida relacionada ao requisito.

Metas ainda não medidas deverão permanecer identificadas como pendentes.

---

# 20. Documentos relacionados

- `PROJECT_CHARTER.md`
- `docs/product/PRODUCT_VISION_AND_SCOPE.md`
- `docs/product/FUNCTIONAL_REQUIREMENTS.md`
- `docs/product/PRODUCT_RULES.md`
- `docs/product/USER_FLOWS.md`
- `docs/science/SCIENTIFIC_REFERENCES.md`

Documentos futuros:

- `docs/architecture/DATA_MODEL.md`
- `docs/architecture/SYSTEM_ARCHITECTURE.md`
- `docs/architecture/TECHNICAL_DECISIONS.md`
- `docs/architecture/SECURITY_ARCHITECTURE.md`
- `docs/privacy/PRIVACY_POLICY_SPECIFICATION.md`
- `docs/testing/TEST_STRATEGY.md`

---

## Princípio final

A Nanalu deverá ser confiável não apenas quando tudo funciona perfeitamente, mas também quando o dispositivo falha, a conexão desaparece, o sistema interrompe processos ou os dados são insuficientes.

Qualidade não é somente velocidade ou aparência. É preservar a confiança do responsável em cada interação.