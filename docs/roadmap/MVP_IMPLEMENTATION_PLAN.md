# Plano de implementação do MVP

## 1. Objetivo

Estabelecer a sequência oficial de implementação do MVP do Nanalu, transformando os requisitos, regras, decisões arquiteturais e especificações existentes em entregas verificáveis.

O desenvolvimento deverá ser incremental. Cada etapa precisa produzir código funcional, testes e evidências.

## 2. Fase 0: preparação técnica

- Criar e configurar o repositório.
- Configurar o projeto Android nativo.
- Configurar Kotlin e Jetpack Compose.
- Configurar Gradle e dependências.
- Configurar Room, Coroutines e Flow.
- Configurar ViewModel e StateFlow.
- Configurar DataStore.
- Configurar testes automatizados.
- Definir versões e Android mínimo com base na avaliação de compatibilidade.
- Documentar comandos de compilação e execução.

Critério de conclusão: projeto compilando, testes básicos executando e estrutura arquitetural estabelecida.

## 3. Fase 1: fundação e persistência

- Implementar entidades do MVP.
- Implementar banco Room.
- Implementar DAOs e repositórios.
- Implementar conversões e validações.
- Implementar migrações e testes de persistência.
- Configurar armazenamento privado.
- Aplicar as regras de backup e transferência definidas na especificação de segurança.

Critério de conclusão: dados persistem corretamente e as operações básicas de armazenamento estão testadas.

## 4. Fase 2: perfil da criança

- Criar perfil.
- Permitir nome opcional.
- Exigir data de nascimento.
- Consultar e editar perfil.
- Excluir perfil e registros dependentes.
- Implementar confirmação de exclusão.

Critério de conclusão: ciclo completo de perfil funcional, persistente e testado.

## 5. Fase 3: registro manual do sono

- Iniciar episódio.
- Encerrar episódio.
- Registrar episódio retroativo.
- Registrar confirmação opcional de despertar no término.
- Validar cronologia.
- Detectar sobreposições.
- Corrigir episódios.
- Excluir episódios.
- Preservar estados e regras de integridade.

Critério de conclusão: registros manuais funcionais, inclusive após reinicialização do aplicativo.

## 6. Fase 4: regras temporais e classificação

- Calcular duração por diferença entre instantes.
- Tratar episódios que atravessam dias.
- Aplicar intervalos semiabertos.
- Evitar dupla contagem.
- Implementar classificação diurna e noturna conforme decisão documentada.
- Preservar distinção entre dados observados e derivados.

Critério de conclusão: regras temporais cobertas pelos testes pertinentes.

Observação: decisões ainda pendentes deverão ser resolvidas antes da implementação da regra específica, sem inventar comportamento silenciosamente.

## 7. Fase 5: histórico e painel

- Construir painel principal.
- Exibir episódio em andamento.
- Exibir resumo do dia.
- Exibir histórico.
- Permitir acesso à edição e exclusão.
- Exibir estados vazios, carregamento e erro.
- Implementar estatísticas básicas.

Critério de conclusão: usuário consegue registrar e consultar o sono sem depender de conexão com a internet.

## 8. Fase 6: estimativa inicial da próxima soneca

- Implementar elegibilidade dos registros.
- Separar primeira soneca e sonecas seguintes.
- Implementar avaliação de suficiência dos dados.
- Implementar método estatístico documentado.
- Permitir comparação experimental das janelas históricas.
- Exibir janela estimada e limitações.
- Abster-se quando não houver dados suficientes.
- Executar cenários ET-001 a ET-013.

Critério de conclusão: estimativa reproduzível, testada e claramente identificada como experimental, sem promessa de precisão.

Parâmetros ainda pendentes deverão ser definidos e registrados antes de serem tratados como definitivos.

## 9. Fase 7: conteúdo educativo

- Implementar conteúdo educativo inicial.
- Organizar conteúdo por faixa etária quando aplicável.
- Apresentar referências científicas.
- Incluir orientações de sono seguro.
- Exibir avisos de limites e caráter não médico do aplicativo.

Critério de conclusão: conteúdo acessível, compreensível e rastreável às fontes documentadas.

## 10. Fase 8: configurações e acabamento

- Implementar configurações essenciais.
- Implementar exclusão de todos os dados.
- Revisar acessibilidade.
- Revisar adaptação a diferentes dimensões de tela.
- Revisar estados de erro e recuperação.
- Revisar identidade visual e presença da mascote Luli.
- Otimizar desempenho sem comprometer integridade ou privacidade.

Critério de conclusão: MVP coerente, utilizável e visualmente consistente.

## 11. Fase 9: validação e preparação para distribuição

- Executar suíte automatizada.
- Executar testes em dispositivos ou emuladores compatíveis.
- Verificar comportamento offline.
- Verificar backup e transferência.
- Verificar exclusão e privacidade.
- Revisar permissões.
- Revisar textos e avisos.
- Verificar compilação de release.
- Documentar limitações conhecidas.
- Preparar checklist de distribuição.

Critério de conclusão: versão candidata com evidências de testes e pendências conhecidas documentadas.

## 12. Funcionalidades fora do MVP

Não implementar nesta sequência:
- Monitoramento acústico.
- Processamento de áudio.
- Sincronização em nuvem.
- Compartilhamento entre cuidadores.
- Múltiplos perfis.
- Exportação avançada.
- Integrações externas.
- Diagnósticos ou alertas clínicos.

Essas funcionalidades exigem especificações, avaliação de segurança, privacidade e viabilidade próprias.

## 13. Regras de execução

- Seguir os documentos vigentes do projeto.
- Consultar requisitos e regras antes de implementar funcionalidades.
- Não inventar decisões ausentes.
- Registrar decisões técnicas novas.
- Preferir alterações pequenas e verificáveis.
- Executar testes após mudanças relevantes.
- Corrigir regressões antes de avançar.
- Não substituir implementação por relatórios.
- Não declarar uma etapa concluída sem código e evidências.
- Manter o projeto compilável sempre que possível.
- Não implementar funcionalidades futuras antecipadamente.

## 14. Controle de progresso

Cada fase deverá registrar:
- Estado: não iniciada, em andamento, bloqueada ou concluída.
- Arquivos implementados.
- Requisitos atendidos.
- Testes executados.
- Evidências.
- Pendências.
- Decisões tomadas.

## 15. Resultado esperado

Ao final, o Nanalu deverá oferecer uma experiência Android offline para cadastro de uma criança, registro manual do sono, histórico, estatísticas básicas, estimativa experimental da próxima soneca e conteúdo educativo, respeitando as restrições científicas, técnicas, de segurança e privacidade estabelecidas.