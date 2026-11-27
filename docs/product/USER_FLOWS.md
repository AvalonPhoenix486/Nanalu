# USER_FLOWS.md
# Fluxos de utilização da Nanalu

**Projeto:** Nanalu
**Versão:** 1.0
**Status:** Especificação de fluxos
**Data:** 27/11/2026

---

## 1. Objetivo

Documentar os caminhos percorridos pelo usuário durante as principais operações da Nanalu.

Este documento complementa o mapa de telas e os requisitos funcionais, especificando:

- Ponto de entrada.
- Etapas.
- Decisões.
- Validações.
- Resultados esperados.
- Alternativas em caso de erro.
- Comportamentos excepcionais.

Os fluxos deverão orientar a implementação e os testes de interface.

## 2. Convenções

Cada fluxo possui um identificador.

- FL-001: Primeiro acesso.
- FL-002: Cadastro da criança.
- FL-003: Iniciar sono.
- FL-004: Finalizar sono.
- FL-005: Registro retroativo.
- FL-006: Consultar histórico.
- FL-007: Consultar detalhes.
- FL-008: Editar episódio.
- FL-009: Excluir episódio.
- FL-010: Consultar estatísticas.
- FL-011: Consultar estimativa.
- FL-012: Consultar conteúdo educativo.
- FL-013: Editar perfil.
- FL-014: Excluir dados.
- FL-015: Recuperação de episódio ativo.
- FL-016: Tratar registros conflitantes.
- FL-017: Funcionamento offline.

Os fluxos futuros estarão identificados separadamente.

---

# 3. Fluxo FL-001: Primeiro acesso

**Prioridade:** P0

### Objetivo

Conduzir o usuário desde a abertura inicial até o painel principal.

### Pré-condições

- Aplicativo instalado.
- Nenhum perfil infantil cadastrado.

### Fluxo principal

1. Usuário abre a Nanalu.
2. Aplicativo apresenta a tela de inicialização.
3. Aplicativo verifica a configuração local.
4. Identifica que não existe perfil infantil.
5. Apresenta a introdução.
6. Usuário conhece a proposta e os limites do aplicativo.
7. Usuário prossegue para o cadastro.
8. Conclui o cadastro da criança.
9. Aplicativo confirma a criação.
10. Usuário chega ao painel principal.

### Fluxo alternativo A: apresentação ignorada

1. Usuário escolhe pular a apresentação.
2. Aplicativo direciona ao cadastro.
3. O restante do fluxo permanece igual.

### Fluxo alternativo B: cadastro interrompido

1. Usuário inicia o cadastro.
2. Sai da tela antes de concluir.
3. Aplicativo não cria um perfil incompleto como se estivesse concluído.
4. Na próxima abertura, permite retomar o cadastro.

### Resultado esperado

Primeiro perfil infantil criado e painel principal disponível.

### Critérios de teste

- Instalação nova.
- Ausência de perfil.
- Apresentação concluída.
- Apresentação ignorada.
- Cadastro interrompido.
- Reabertura após interrupção.

---

# 4. Fluxo FL-002: Cadastrar criança

**Prioridade:** P0

### Objetivo

Criar o perfil utilizado no acompanhamento.

### Fluxo principal

1. Usuário acessa o cadastro.
2. Informa nome ou apelido.
3. Informa data de nascimento.
4. Opcionalmente seleciona uma foto.
5. Revisa os dados.
6. Confirma o cadastro.
7. Aplicativo valida as informações.
8. Aplicativo salva o perfil.
9. Aplicativo calcula a idade.
10. Aplicativo apresenta confirmação.

### Fluxo alternativo A: nome inválido

1. Usuário deixa o nome vazio.
2. Tenta salvar.
3. Aplicativo informa que o campo é obrigatório.
4. Usuário corrige.

### Fluxo alternativo B: data inválida

1. Usuário informa uma data inválida ou futura.
2. Tenta salvar.
3. Aplicativo informa o problema.
4. Usuário corrige.

### Fluxo alternativo C: foto indisponível

1. Usuário tenta selecionar uma foto.
2. Não concede permissão ou cancela a seleção.
3. Aplicativo permite continuar sem foto.

### Resultado esperado

Perfil salvo corretamente.

### Critérios de teste

- Cadastro válido.
- Nome vazio.
- Data inválida.
- Data futura.
- Foto ausente.
- Seleção de foto cancelada.
- Persistência após reinicialização.

---

# 5. Fluxo FL-003: Iniciar episódio de sono

**Prioridade:** P0

### Objetivo

Registrar o início de um período de sono.

### Pré-condições

- Perfil infantil existente.
- Nenhum episódio ativo incompatível.

### Fluxo principal

1. Usuário abre o painel principal.
2. Visualiza o estado atual.
3. Pressiona "Iniciar sono".
4. Aplicativo obtém o horário atual.
5. Aplicativo cria um episódio em andamento.
6. Aplicativo salva o registro.
7. Painel passa a apresentar o estado "Dormindo".
8. Horário de início e tempo transcorrido ficam visíveis.

### Fluxo alternativo A: episódio já ativo

1. Usuário tenta iniciar outro episódio.
2. Aplicativo identifica um registro em andamento.
3. Impede a criação duplicada.
4. Apresenta o episódio existente.
5. Oferece acesso para finalizá-lo.

### Fluxo alternativo B: falha ao salvar

1. Usuário solicita início.
2. O armazenamento falha.
3. Aplicativo informa que o registro não foi concluído.
4. O estado da interface não deverá afirmar que existe um episódio salvo.
5. Usuário poderá tentar novamente.

### Fluxo alternativo C: toque repetido

1. Usuário toca várias vezes no botão.
2. Aplicativo evita criar registros duplicados.
3. Apenas uma operação válida é concluída.

### Resultado esperado

Um único episódio ativo, corretamente persistido.

### Critérios de teste

- Início normal.
- Toques repetidos.
- Episódio já ativo.
- Falha de armazenamento.
- Fechamento do aplicativo após iniciar.
- Reinicialização do dispositivo.

---

# 6. Fluxo FL-004: Finalizar episódio de sono

**Prioridade:** P0

### Objetivo

Encerrar um episódio em andamento.

### Pré-condições

Existe um episódio ativo.

### Fluxo principal

1. Usuário abre o painel.
2. Visualiza o episódio em andamento.
3. Pressiona "Finalizar sono".
4. Aplicativo registra o horário final.
5. Valida a ordem temporal.
6. Calcula a duração.
7. Persiste o horário final.
8. Atualiza o estado do episódio para concluído.
9. Atualiza histórico e estatísticas.
10. Apresenta confirmação.

### Fluxo alternativo A: nenhum episódio ativo

1. Usuário tenta finalizar.
2. Aplicativo verifica que não existe episódio em andamento.
3. Informa que não há registro ativo.
4. Mantém o estado consistente.

### Fluxo alternativo B: horário inconsistente

1. Aplicativo identifica horário final anterior ao início.
2. Não conclui o registro com duração inválida.
3. Informa o problema.
4. Permite correção ou recuperação apropriada.

### Fluxo alternativo C: falha ao salvar

1. Usuário solicita finalização.
2. O armazenamento falha.
3. Aplicativo informa que a finalização não foi confirmada.
4. O registro não deverá desaparecer silenciosamente.
5. Usuário poderá tentar novamente.

### Resultado esperado

Episódio concluído, duração calculada e histórico atualizado.

### Critérios de teste

- Finalização normal.
- Finalização sem episódio ativo.
- Falha de persistência.
- Atualização do painel.
- Atualização das estatísticas.
- Persistência após reinicialização.

---

# 7. Fluxo FL-005: Registrar episódio retroativo

**Prioridade:** P0

### Objetivo

Permitir que o usuário registre um período de sono que já aconteceu.

### Fluxo principal

1. Usuário abre o histórico.
2. Seleciona adicionar registro.
3. Informa a data.
4. Informa o horário inicial.
5. Informa o horário final.
6. Seleciona a classificação, quando aplicável.
7. Opcionalmente adiciona observação.
8. Confirma os dados.
9. Aplicativo valida o intervalo.
10. Verifica possíveis sobreposições.
11. Salva o registro.
12. Atualiza histórico e estatísticas.
13. Apresenta confirmação.

### Fluxo alternativo A: horário inválido

1. Usuário informa horário final anterior ao inicial, sem indicar mudança de data.
2. Aplicativo identifica inconsistência.
3. Solicita correção.

### Fluxo alternativo B: sobreposição

1. Aplicativo identifica outro episódio no mesmo intervalo.
2. Apresenta aviso.
3. Informa quais registros estão envolvidos.
4. Permite revisar os dados.
5. Não altera registros anteriores automaticamente.

### Fluxo alternativo C: episódio futuro

1. Usuário informa um período futuro.
2. Aplicativo identifica que o episódio ainda não aconteceu.
3. Impede salvar como episódio concluído.

### Resultado esperado

Registro retroativo válido e corretamente integrado ao histórico.

### Critérios de teste

- Registro no mesmo dia.
- Registro atravessando meia-noite.
- Registro em dia anterior.
- Sobreposição.
- Horário inválido.
- Data futura.
- Persistência.

---

# 8. Fluxo FL-006: Consultar histórico

**Prioridade:** P0

### Objetivo

Permitir consultar os episódios registrados.

### Fluxo principal

1. Usuário seleciona Histórico.
2. Aplicativo carrega os registros.
3. Apresenta episódios em ordem cronológica.
4. Usuário seleciona uma data.
5. Aplicativo apresenta os episódios daquele período.
6. Usuário pode abrir um episódio para consultar detalhes.

### Fluxo alternativo A: nenhum registro

1. Usuário abre o histórico.
2. Aplicativo identifica ausência de registros.
3. Apresenta estado vazio.
4. Oferece acesso ao registro de sono.

### Fluxo alternativo B: registros incompletos

1. Aplicativo encontra episódio sem término.
2. Identifica o estado como incompleto ou em andamento.
3. Não apresenta duração concluída inexistente.

### Fluxo alternativo C: falha de leitura

1. Aplicativo não consegue carregar os registros.
2. Apresenta mensagem compreensível.
3. Permite tentar novamente.
4. Não apresenta ausência de dados como se fosse histórico vazio.

### Resultado esperado

Histórico organizado e consistente.

---

# 9. Fluxo FL-007: Consultar detalhes de episódio

**Prioridade:** P0

### Fluxo principal

1. Usuário abre o histórico.
2. Seleciona um episódio.
3. Aplicativo apresenta detalhes.
4. Usuário consulta horários, duração e classificação.
5. Pode escolher editar ou excluir.

### Fluxo alternativo: registro não encontrado

1. Usuário tenta abrir um registro inexistente ou removido.
2. Aplicativo informa que ele não está disponível.
3. Retorna ao histórico atualizado.

### Resultado esperado

Detalhes corretos do episódio selecionado.

---

# 10. Fluxo FL-008: Editar episódio

**Prioridade:** P0

### Fluxo principal

1. Usuário abre os detalhes.
2. Seleciona editar.
3. Aplicativo apresenta os dados atuais.
4. Usuário altera os campos.
5. Confirma a edição.
6. Aplicativo valida os dados.
7. Verifica conflitos.
8. Salva as alterações.
9. Recalcula duração e indicadores.
10. Apresenta os dados atualizados.

### Fluxo alternativo A: cancelar edição

1. Usuário modifica informações.
2. Seleciona cancelar ou voltar.
3. Aplicativo identifica alterações não salvas.
4. Solicita confirmação para descartar.
5. Mantém o registro original se confirmado.

### Fluxo alternativo B: conflito temporal

1. Usuário altera os horários.
2. Aplicativo identifica sobreposição.
3. Apresenta aviso.
4. Usuário corrige ou revisa o registro.

### Fluxo alternativo C: registro removido

1. Usuário tenta editar um episódio que deixou de existir.
2. Aplicativo informa que o registro não está disponível.
3. Atualiza a tela.

### Resultado esperado

Registro corrigido sem inconsistências.

---

# 11. Fluxo FL-009: Excluir episódio

**Prioridade:** P0

### Fluxo principal

1. Usuário abre os detalhes.
2. Seleciona excluir.
3. Aplicativo apresenta confirmação.
4. Identifica o episódio que será removido.
5. Usuário confirma.
6. Aplicativo remove efetivamente o episódio e seus dados dependentes do banco, utilizando uma transação.
7. Atualiza histórico e estatísticas.
8. Retorna ao histórico.

### Fluxo alternativo A: cancelamento

1. Usuário seleciona excluir.
2. Visualiza confirmação.
3. Cancela.
4. Registro permanece intacto.

### Fluxo alternativo B: falha na exclusão

1. Usuário confirma.
2. O armazenamento falha.
3. Aplicativo informa que a exclusão não foi concluída.
4. A transação deverá ser revertida quando aplicável, preservando os dados anteriores. O histórico e as estatísticas deverão continuar coerentes com o armazenamento.

### Resultado esperado

Episódio e dados dependentes removidos efetivamente do banco. Histórico e estatísticas atualizados após a confirmação da operação.

---

# 12. Fluxo FL-010: Consultar estatísticas

**Prioridade:** P0

### Fluxo principal

1. Usuário seleciona Estatísticas.
2. Aplicativo carrega os registros válidos.
3. Calcula os indicadores disponíveis.
4. Apresenta o período analisado.
5. Usuário consulta os resultados.

### Fluxo alternativo A: dados insuficientes

1. Aplicativo encontra poucos registros.
2. Identifica indicadores que não podem ser calculados com confiabilidade.
3. Apresenta aviso.
4. Mostra somente os indicadores sustentados pelos dados.

### Fluxo alternativo B: histórico vazio

1. Usuário abre estatísticas sem registros.
2. Aplicativo apresenta orientação para iniciar o acompanhamento.

### Fluxo alternativo C: dados inconsistentes

1. Aplicativo encontra registros inválidos ou sobrepostos.
2. Não os contabiliza incorretamente.
3. Informa a existência de dados que precisam de revisão, quando apropriado.

### Resultado esperado

Estatísticas corretas, contextualizadas e transparentes.

---

# 13. Fluxo FL-011: Consultar estimativa da próxima soneca

**Prioridade:** P1

### Objetivo

Apresentar uma estimativa contextualizada do próximo período provável de descanso.

### Fluxo principal

1. Usuário abre o painel.
2. Aplicativo verifica a idade.
3. Verifica os registros disponíveis.
4. Avalia a quantidade e consistência dos dados.
5. Executa o método de estimativa documentado.
6. Verifica se os dados são suficientes.
7. Apresenta uma janela provável.
8. Informa que se trata de estimativa.

### Fluxo alternativo A: dados insuficientes

1. Aplicativo não encontra histórico suficiente.
2. Não apresenta horário aparentemente preciso.
3. Explica que precisa de mais registros.

### Fluxo alternativo B: padrão inconsistente

1. Aplicativo identifica grande variação nos horários.
2. Reduz a confiança ou deixa de apresentar estimativa.
3. Explica a limitação.

### Fluxo alternativo C: idade fora da faixa configurada

1. Aplicativo verifica que a idade não está contemplada pelo modelo.
2. Não aplica regras inadequadas.
3. Informa a indisponibilidade da estimativa.

### Resultado esperado

Estimativa compreensível, sem promessa de precisão.

---

# 14. Fluxo FL-012: Consultar conteúdo educativo

**Prioridade:** P1

### Fluxo principal

1. Usuário seleciona Conteúdo.
2. Aplicativo apresenta categorias.
3. Usuário seleciona uma categoria.
4. Aplicativo apresenta artigos.
5. Usuário abre um artigo.
6. Aplicativo apresenta o conteúdo.
7. Usuário consulta as referências disponíveis.

### Fluxo alternativo A: conteúdo indisponível

1. Aplicativo não encontra artigos na categoria.
2. Apresenta mensagem adequada.
3. Evita tela vazia sem explicação.

### Fluxo alternativo B: ausência de conexão

Se o conteúdo estiver armazenado localmente, deverá permanecer acessível.

Se depender de atualização remota, o aplicativo deverá informar a indisponibilidade sem comprometer o restante das funções.

### Resultado esperado

Conteúdo acessível e corretamente identificado.

---

# 15. Fluxo FL-013: Editar perfil infantil

**Prioridade:** P0

### Fluxo principal

1. Usuário abre Perfil.
2. Seleciona os dados da criança.
3. Escolhe editar.
4. Altera as informações permitidas.
5. Confirma.
6. Aplicativo valida os dados.
7. Salva as alterações.
8. Atualiza a idade e informações relacionadas.

### Fluxo alternativo: cancelamento

Alterações não confirmadas deverão ser descartadas somente após a confirmação apropriada.

### Resultado esperado

Perfil atualizado sem perda do histórico de sono.

---

# 16. Fluxo FL-014: Solicitar exclusão de dados

**Prioridade:** P0

### Fluxo principal

1. Usuário acessa Privacidade.
2. Seleciona exclusão de dados.
3. Aplicativo explica o alcance da exclusão.
4. Apresenta consequências.
5. Usuário confirma explicitamente.
6. Aplicativo executa o processo.
7. Apresenta confirmação do resultado.

### Regras

- A exclusão não deverá ocorrer por toque acidental.
- A exclusão deverá abranger os perfis, episódios e dados dependentes incluídos na solicitação.
- A operação deverá respeitar obrigações legais aplicáveis.
- A interface não deverá afirmar que dados foram apagados antes de confirmar o resultado.
- Dados temporários e caches sob controle do aplicativo deverão ser tratados conforme a estratégia técnica de exclusão.
- A operação não deverá afirmar que removeu cópias externas ou backups que não estejam sob controle do aplicativo.
- A interface deverá informar que o MVP não realiza backup automático e que dados locais podem ser perdidos com a desinstalação ou perda do aparelho.

### Fluxo alternativo: cancelamento

O usuário cancela a operação e os dados permanecem preservados.

---

# 17. Fluxo FL-015: Recuperar episódio ativo

**Prioridade:** P0

### Objetivo

Preservar a continuidade de um episódio iniciado antes do encerramento do aplicativo.

### Fluxo principal

1. Usuário inicia um episódio.
2. Aplicativo salva o início.
3. Usuário fecha o aplicativo ou o sistema encerra o processo.
4. Usuário abre novamente.
5. Aplicativo consulta o armazenamento.
6. Identifica episódio ativo.
7. Recupera os dados.
8. Calcula o tempo transcorrido usando uma referência temporal confiável.
9. Apresenta o episódio como em andamento.

### Fluxo alternativo A: reinicialização do dispositivo

O episódio deverá continuar recuperável após reinicialização, desde que seu início tenha sido persistido corretamente.

### Fluxo alternativo B: inconsistência temporal

Se o dispositivo apresentar uma alteração de horário que comprometa o cálculo, o aplicativo deverá evitar apresentar uma duração incorreta como confirmada e solicitar revisão quando necessário.

### Resultado esperado

Continuidade do registro sem depender de o aplicativo permanecer aberto.

---

# 18. Fluxo FL-016: Tratar registros conflitantes

**Prioridade:** P0

### Objetivo

Evitar que registros sobrepostos comprometam estatísticas e estimativas.

### Fluxo principal

1. Usuário cria ou edita um episódio.
2. Aplicativo compara o intervalo com os registros existentes.
3. Identifica possível sobreposição.
4. Apresenta os episódios envolvidos.
5. Usuário revisa os horários.
6. O sistema salva somente após a validação necessária.

### Regras

- Não excluir registros automaticamente.
- Não somar períodos sobrepostos duas vezes.
- Não ocultar conflitos.
- Preservar os dados originais até uma alteração confirmada.

### Resultado esperado

Histórico consistente e sem contabilização duplicada.

---

# 19. Fluxo FL-017: Utilizar a Nanalu offline

**Prioridade:** P0

### Objetivo

Garantir o funcionamento das funções essenciais sem internet.

### Fluxo principal

1. Usuário abre o aplicativo sem conexão.
2. Aplicativo carrega os dados locais.
3. Usuário registra um episódio.
4. Aplicativo salva o registro localmente.
5. Usuário consulta o histórico.
6. Estatísticas locais são calculadas.
7. Usuário continua utilizando as funções disponíveis.

### Fluxo alternativo: operação dependente de internet

1. Usuário solicita recurso que dependa de conexão.
2. Aplicativo identifica indisponibilidade.
3. Explica que o recurso não está disponível naquele momento.
4. Preserva as funções locais.

### Resultado esperado

Registro, consulta e cálculos essenciais funcionando offline.

---

# 20. Fluxos futuros

Os seguintes fluxos serão especificados quando as respectivas funcionalidades forem aprovadas para implementação.

### FL-FUT-001: Compartilhar perfil infantil

- Responsável principal inicia convite.
- Seleciona nível de acesso.
- Convida outro usuário.
- Usuário convidado aceita.
- Aplicativo estabelece acesso autorizado.

### FL-FUT-002: Revogar acesso

- Responsável abre gerenciamento de usuários.
- Seleciona pessoa autorizada.
- Solicita revogação.
- Confirma.
- Aplicativo atualiza permissões.

### FL-FUT-003: Monitoramento acústico

- Responsável consulta explicações.
- Concede permissão necessária.
- Ativa monitoramento.
- Aplicativo apresenta estado de funcionamento.
- Eventos são processados conforme a arquitetura definida.
- Responsável pode interromper o recurso.

Esse fluxo dependerá de pesquisa técnica, validação e revisão de privacidade.

### FL-FUT-004: Revisar evento detectado

- Aplicativo apresenta evento candidato.
- Responsável consulta o horário e a classificação.
- Confirma, corrige ou descarta.
- Sistema registra a decisão.

---

# 21. Regras globais dos fluxos

Todos os fluxos deverão respeitar:

1. Persistência antes de confirmação de sucesso.
2. Validação antes de operações críticas.
3. Feedback compreensível.
4. Tratamento de interrupções.
5. Preservação dos dados existentes.
6. Ausência de duplicações indevidas.
7. Compatibilidade com funcionamento offline quando aplicável.
8. Acessibilidade.
9. Privacidade.
10. Transparência sobre incertezas.

## 22. Recuperação de erros

Sempre que possível, uma falha deverá permitir:

- Tentar novamente.
- Corrigir os dados.
- Voltar sem perder informações.
- Consultar o estado atual.
- Entender o que aconteceu.

A interface não deverá utilizar mensagens genéricas quando puder explicar o problema de maneira útil.

## 23. Critérios gerais de conclusão

Um fluxo estará implementado quando:

- Todas as etapas principais funcionarem.
- Alternativas relevantes estiverem tratadas.
- Estados inválidos forem reconhecidos.
- Os dados forem preservados corretamente.
- O resultado for apresentado de maneira coerente.
- Os testes correspondentes forem aprovados.

## 24. Documentos relacionados

- PRODUCT_VISION_AND_SCOPE.md
- USER_ROLES_AND_PERSONAS.md
- FUNCTIONAL_REQUIREMENTS.md
- SCREEN_MAP.md
- PRODUCT_RULES.md
- ../testing/

## 25. Princípio central

Cada fluxo deverá ser compreensível, previsível e resistente a interrupções.

O usuário não deverá precisar entender a lógica interna da aplicação para realizar uma tarefa cotidiana.

**A tecnologia deverá trabalhar silenciosamente para que o cuidado continue sendo o centro da experiência.**