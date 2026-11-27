# Especificação de segurança da Nanalu

## 1. Objetivo
Definir requisitos técnicos para proteger o aplicativo, os registros e as informações relacionadas às crianças e seus responsáveis.

## 2. Princípios
- Segurança desde a concepção.
- Privacidade por padrão.
- Coleta e retenção mínimas.
- Acesso restrito ao necessário.
- Proteção proporcional à sensibilidade dos dados.
- Transparência sobre operações e permissões.
- Segurança não pode depender exclusivamente da boa conduta do usuário.

## 3. Modelo de ameaças
Considerar, no mínimo:
- Acesso indevido por outros aplicativos.
- Acesso físico não autorizado ao aparelho.
- Aparelho perdido ou roubado.
- Vazamento por logs, arquivos temporários ou notificações.
- Corrupção ou perda do banco de dados.
- Dependências vulneráveis ou desatualizadas.
- Exportação ou compartilhamento acidental.
- Uso indevido de permissões.

## 4. Armazenamento local
- Dados privados devem permanecer no armazenamento interno privado do aplicativo.
- Não utilizar armazenamento externo público para registros pessoais.
- Não disponibilizar arquivos internos diretamente a outros aplicativos.
- Arquivos temporários devem ter finalidade e ciclo de vida definidos.
- A estratégia de criptografia do banco deverá ser avaliada e documentada antes da implementação.

## 5. Criptografia e chaves
- Avaliar criptografia dos dados armazenados, considerando o modelo de ameaças.
- Utilizar Android Keystore para proteger material criptográfico quando aplicável.
- Não armazenar chaves secretas diretamente no código-fonte.
- Não inventar algoritmos criptográficos próprios.
- Documentar geração, armazenamento, rotação e perda de chaves.
- Definir comportamento seguro quando uma chave não puder mais ser recuperada.

## 6. Autenticação e acesso
- O MVP não exigirá criação de conta ou autenticação em servidor.
- A proteção do aplicativo não deve ser confundida com autenticação do responsável.
- Avaliar bloqueio local opcional em etapa posterior.
- Não implementar biometria como única forma de recuperação de acesso.
- Não criar mecanismos de acesso oculto aos dados.

## 7. Permissões Android
- Solicitar apenas permissões indispensáveis a uma função escolhida pelo usuário.
- Explicar a finalidade antes de solicitar uma permissão sensível.
- Não solicitar microfone no primeiro acesso do MVP.
- Não solicitar localização, contatos ou câmera sem necessidade funcional comprovada.
- O aplicativo deve continuar funcionando nas funções essenciais quando permissões opcionais forem negadas.

## 8. Logs e diagnóstico
- Não registrar nomes, datas de nascimento completas, áudio ou conteúdo pessoal em logs de produção.
- Não registrar conteúdo integral de registros de sono em mensagens de erro.
- Remover dados sensíveis de relatórios de falhas.
- Diagnósticos devem evitar identificadores que permitam rastrear uma criança.
- Logs temporários devem ter retenção limitada.

## 9. Backup e restauração
- No MVP, os dados infantis deverão ficar excluídos dos backups automáticos do Android.
- A exclusão deverá abranger backup em nuvem e transferência entre dispositivos.
- Não deverá existir restauração automática desses dados em outro aparelho.
- A ausência de backup deverá ser comunicada claramente ao responsável, incluindo o risco de perda.
- Uma futura exportação ou restauração deverá ser explícita, protegida e validada quanto à integridade e compatibilidade.

## 10. Integridade e recuperação
- Operações críticas devem preservar consistência do banco de dados.
- Utilizar transações para alterações relacionadas.
- Tratar falhas sem descartar registros silenciosamente.
- Testar encerramento inesperado durante gravações.
- Definir recuperação para corrupção e migrações malsucedidas.

## 11. Dependências e atualizações
- Utilizar dependências conhecidas e mantidas.
- Evitar bibliotecas sem necessidade comprovada.
- Revisar vulnerabilidades conhecidas antes de versões de lançamento.
- Manter ferramentas e dependências atualizadas.
- Não incluir segredos, chaves privadas ou credenciais no repositório.

## 12. Compartilhamento de dados
- O MVP não terá sincronização automática ou compartilhamento remoto.
- Compartilhamentos futuros deverão ser iniciados pelo responsável.
- Exibir claramente quais dados serão compartilhados e com quem.
- Permissões de compartilhamento deverão ser revogáveis.
- Não permitir acesso silencioso por terceiros.

## 13. Monitoramento acústico futuro
- Não integra os requisitos de segurança do MVP.
- Captura de áudio exige autorização explícita e indicação clara de atividade.
- Processamento local deve ser priorizado.
- Áudio bruto não deve ser retido sem justificativa e consentimento específicos.
- Não enviar áudio a serviços externos por padrão.
- Interrupções e falhas devem encerrar a captura de maneira segura.

## 14. Exclusão de dados
- Disponibilizar procedimento compreensível para excluir perfis e registros.
- Após a exclusão, limpar caches, arquivos temporários e outros resíduos sob controle do aplicativo, conforme a estratégia técnica.
- Documentar o efeito da exclusão sobre backups e exportações.
- Não prometer remoção de cópias externas que o aplicativo não controla.

## 15. Testes de segurança
- Verificar isolamento do armazenamento interno.
- Verificar ausência de dados pessoais nos logs.
- Testar permissões negadas e revogadas.
- Testar a exclusão dos dados infantis dos backups automáticos e da transferência entre dispositivos.
- Verificar que não ocorre restauração automática desses dados.
- Testar restauração somente se uma função explícita for implementada futuramente.
- Testar integridade do banco e migrações.
- Verificar dependências vulneráveis.
- Testar exclusão e limpeza de arquivos temporários.

## 16. Critérios de aprovação
- Nenhuma permissão desnecessária.
- Nenhum dado pessoal exposto em logs de produção.
- Armazenamento privado e controles de acesso documentados.
- Estratégia de criptografia definida antes do lançamento.
- Exclusão testada.
- Exclusão dos backups automáticos e ausência de restauração automática verificadas.
- Restauração futura testada somente se implementada.
- Nenhuma falha crítica ou alta conhecida sem tratamento documentado.

## 17. Referências oficiais
- Android Developers: Security best practices.
- Android Developers: Android Keystore System.
- Lei nº 13.709/2018: Lei Geral de Proteção de Dados Pessoais.
- ANPD: Enunciado CD/ANPD nº 1, de 22 de maio de 2023.
- Lei nº 15.211/2025: Estatuto Digital da Criança e do Adolescente.

## 18. Pendências
- Definir estratégia de criptografia do banco.
- Definir estratégia futura de exportação e restauração, caso seja implementada.
- Definir proteção opcional de acesso local.
- Revisar requisitos legais antes da distribuição.
- Realizar avaliação de segurança antes do lançamento.

## 19. Política detalhada de armazenamento

- Utilizar exclusivamente armazenamento interno privado para dados pessoais.
- Não armazenar registros infantis em diretórios públicos ou compartilhados.
- Considerar a criptografia do sistema Android como uma camada de proteção, não como garantia absoluta.
- Não presumir proteção contra aparelho desbloqueado, comprometido ou com acesso administrativo indevido.

## 20. Estratégia de criptografia

- Avaliar criptografia adicional do banco Room antes da implementação definitiva.
- Comparar soluções mantidas e reconhecidas, considerando segurança, compatibilidade, manutenção e desempenho.
- Não desenvolver algoritmos criptográficos próprios.
- Não considerar Android Keystore uma criptografia automática do banco de dados.
- Utilizar Keystore para proteger chaves quando houver criptografia adicional.
- Verificar disponibilidade de proteção por hardware sem exigir que todos os aparelhos ofereçam o mesmo recurso.
- Documentar comportamento em caso de perda, invalidação ou indisponibilidade da chave.
- A escolha da biblioteca de criptografia permanece pendente de avaliação técnica.

## 21. Política de backup do MVP

- Excluir os dados infantis dos backups automáticos do Android, incluindo backup em nuvem e transferência entre dispositivos no MVP.
- Não permitir transferência automática do banco para outro dispositivo antes de existir estratégia segura de restauração.
- Excluir arquivos de áudio e arquivos temporários de qualquer backup.
- Configurar regras explícitas de backup para Android 12 ou superior.
- Configurar também as regras compatíveis com Android 11 ou inferior.
- Não depender dos padrões automáticos do sistema.
- Validar separadamente que os dados infantis permanecem excluídos do backup em nuvem e da transferência entre dispositivos.
- Informar ao responsável que os registros podem ser perdidos com desinstalação ou perda do aparelho.
- Implementar e testar o comportamento definido para reinstalação e troca de aparelho: não restaurar automaticamente dados infantis no MVP.
- Uma futura função de exportação deverá ser iniciada pelo responsável e protegida.

## 22. Proteção e ciclo de vida das chaves

- Nunca incluir chaves privadas ou segredos no código-fonte.
- Não registrar chaves, tokens ou material criptográfico em logs.
- Não exportar material de chave protegido pelo Keystore.
- Definir o vínculo entre chaves e dados antes de implementar criptografia.
- Qualquer futura restauração deverá depender de uma função explícita, com estratégia própria de proteção e validação.
- Não prometer recuperação de dados criptografados quando a chave original estiver perdida.

## 23. Exclusão e resíduos de dados

- No MVP, remover efetivamente os registros do banco conforme o modelo de dados, utilizando as operações transacionais previstas.
- Utilizar transações para manter a integridade do banco.
- Considerar arquivos WAL, journals, caches e arquivos temporários durante a limpeza.
- Limpar exportações temporárias após o uso.
- Não afirmar que sobrescrita física segura é garantida em memória flash.
- Se futuramente houver criptografia por perfil, avaliar destruição controlada de chaves como mecanismo adicional de eliminação.
- Não afirmar que excluir do aplicativo elimina cópias externas já exportadas ou armazenadas em backups anteriores.

## 24. Consequências para o usuário

- A ausência de backup automático reduz exposição externa, mas aumenta o risco de perda por falha ou troca do aparelho.
- A interface deve explicar essa limitação antes do lançamento.
- A futura restauração deverá validar integridade, versão e compatibilidade dos dados.
- Nenhuma função de backup poderá ser apresentada como concluída sem teste real de restauração.
