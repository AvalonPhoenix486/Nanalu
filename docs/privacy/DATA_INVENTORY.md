# Inventário de dados da Nanalu

## 1. Objetivo
Mapear os dados tratados pela Nanalu, suas finalidades, necessidade, armazenamento, retenção e riscos.

## 2. Princípios
- Não coletar dados sem finalidade definida.
- Não armazenar informações apenas por conveniência futura.
- Preferir dados derivados quando dispensarem dados originais.
- Diferenciar dados fornecidos, calculados e estimados.
- Revisar este inventário quando uma funcionalidade mudar.

## 3. Dados do perfil infantil
- Identificador interno: necessário para relacionar registros ao perfil.
- Nome ou apelido: opcional, apenas para identificação visual local.
- Data de nascimento: mantida para calcular continuamente a idade e selecionar referências etárias adequadas.
- A data de nascimento será armazenada exclusivamente no dispositivo no MVP.
- Foto: fora do escopo do MVP.
- Dados médicos: fora do escopo do MVP.

## 4. Dados dos responsáveis
- O MVP não exige conta ou cadastro remoto.
- Não coletar documento, endereço, telefone ou contatos.
- Identificação de responsável deverá ser reavaliada caso haja compartilhamento futuro.

## 5. Registros de sono
- Identificador do episódio.
- Identificador do perfil infantil relacionado.
- Data e horário de início.
- Data e horário de término, quando concluído.
- Origem do registro.
- Classificação diurna, noturna ou não classificada.
- Estado do episódio.
- Datas de criação e atualização.
- Não armazenar duração como dado independente quando puder ser calculada.

## 6. Estatísticas e dados derivados
- Totais de sono por dia: calculados a partir dos episódios.
- Médias e medianas: calculadas a partir dos registros disponíveis.
- Intervalos entre episódios: derivados dos horários registrados.
- Tendências: calculadas a partir de períodos definidos.
- Estimativas: resultados aproximados, com indicação de limitações.
- Não armazenar resultados derivados permanentemente sem necessidade comprovada.
- Não apresentar inferências como observações diretas.

## 7. Configurações
- Preferências de apresentação.
- Configuração de janela diurna e noturna.
- Preferências de notificações, caso existam.
- Preferências de acessibilidade.
- Configurações necessárias ao funcionamento.
- Não utilizar configurações para criar perfis comerciais.

## 8. Dados técnicos
- Versão do aplicativo.
- Versão do esquema do banco de dados.
- Informações técnicas mínimas para compatibilidade.
- Erros técnicos sem conteúdo pessoal.
- Não coletar identificadores persistentes do aparelho sem justificativa.
- Não utilizar identificadores de publicidade.
- Não enviar telemetria para serviços externos por padrão.

## 9. Dados de áudio futuros
- Não coletados no MVP.
- Se a função for aprovada, avaliar processamento local de características acústicas.
- Evitar retenção de áudio bruto.
- Eventos acústicos não devem ser tratados como registros confirmados de sono.
- Definir finalidade, base legal, retenção e proteção antes da implementação.
- Considerar dados incidentais de terceiros captados no ambiente.

## 10. Dados excluídos do escopo do MVP
- Localização geográfica.
- Lista de contatos.
- Fotografias e vídeos.
- Gravações de áudio.
- Documentos pessoais.
- Histórico médico.
- Dados biométricos.
- Identificadores publicitários.
- Dados de navegação enviados a terceiros.

## 11. Armazenamento e ciclo de vida
- Dados do MVP permanecem no armazenamento privado local.
- Não há sincronização remota prevista.
- Dados essenciais permanecem enquanto o perfil e os registros forem mantidos pelo responsável.
- A exclusão do perfil deverá remover efetivamente o perfil e todos os episódios de sono associados.
- Resultados derivados armazenados, configurações vinculadas e arquivos temporários do perfil também deverão ser removidos.
- Logs e diagnósticos não deverão conservar dados pessoais identificáveis do perfil.
- A exclusão deverá abranger os dados sob controle do aplicativo, sem prometer sobrescrita física de blocos do armazenamento flash.
- Exportações ou cópias externas futuras deverão possuir procedimento próprio de exclusão e informação ao responsável.
- Dados técnicos temporários devem ter retenção limitada.
- Caches e arquivos temporários devem ser limpos quando deixarem de ser necessários.
- Backup automático dos registros infantis ficará desativado no MVP.
- Exportação futura deverá ser explícita e protegida.

## 12. Matriz de finalidade e necessidade
| Categoria | Finalidade | Necessidade no MVP |
|---|---|---|
| Identificador infantil | Relacionar registros | Necessário |
| Nome ou apelido | Identificação visual | Opcional |
| Data de nascimento | Calcular idade e selecionar referências etárias | Necessário, armazenado localmente |
| Horários de sono | Registrar episódios | Necessário |
| Estado do episódio | Controlar registros | Necessário |
| Classificação | Organizar períodos | Necessário |
| Estatísticas | Apresentar padrões | Derivadas |
| Configurações | Personalizar funcionamento | Necessárias conforme função |
| Dados técnicos | Diagnóstico e compatibilidade | Mínimos |
| Áudio | Análise acústica futura | Não necessário no MVP |

## 13. Riscos de privacidade
- Exposição de horários e rotinas pode revelar hábitos familiares.
- Um aparelho compartilhado pode permitir acesso indevido aos registros.
- Exportações podem circular fora do controle do aplicativo.
- Backups externos podem conservar cópias não controladas pela Nanalu.
- Dados de áudio podem captar terceiros incidentalmente.
- Estatísticas podem ser interpretadas incorretamente como avaliação médica.
- Informações excessivas de perfil aumentam o impacto de um eventual vazamento.

## 14. Medidas de redução de risco
- Armazenamento interno privado.
- Ausência de conta remota no MVP.
- Ausência de sincronização automática.
- Minimização dos campos de perfil.
- Controle de acesso ao aparelho e avaliação de bloqueio local futuro.
- Exportação explícita e protegida quando implementada.
- Mensagens claras sobre limites e natureza das estatísticas.
- Exclusão de dados acessível ao responsável.

## 15. Dados que exigem decisão técnica


- Definir quais configurações precisam persistir.
- Definir quais erros técnicos podem ser registrados sem dados pessoais.
- Definir formato e proteção de eventual exportação.
- Definir retenção de dados temporários.

## 16. Revisão do inventário
- Revisar antes da primeira versão distribuída.
- Revisar antes de adicionar áudio ou processamento em segundo plano.
- Revisar antes de introduzir contas, sincronização ou compartilhamento.
- Revisar quando novas dependências receberem dados.
- Revisar quando uma finalidade ou categoria de dado mudar.

## 17. Critérios de aprovação
- Toda categoria possui finalidade identificada.
- Nenhum dado sem necessidade justificada é coletado.
- Dados sensíveis ou potencialmente sensíveis possuem avaliação específica.
- Armazenamento e retenção estão definidos.
- Exclusão e direitos dos titulares estão contemplados.
- Dados futuros estão separados dos requisitos do MVP.
- Inventário consistente com modelo de dados, arquitetura e requisitos funcionais.

## 18. Decisões consolidadas para o MVP

### Perfil infantil
- Identificador interno obrigatório.
- Nome opcional, sem exigência de nome completo.
- Data de nascimento mantida para cálculo contínuo da idade e referências etárias.
- Data de nascimento armazenada exclusivamente no dispositivo no MVP.

### Registros de sono
- Registros manuais são a única origem implementada no MVP.
- Anotações livres não serão oferecidas no MVP.
- Exclusão deverá remover efetivamente o registro e seus dados dependentes.
- Marcar um registro como excluído não será considerado eliminação efetiva.

### Eventos e monitoramento
- SleepEvent e dados de confiança ou metadados ficam reservados para versões futuras.
- Não haverá coleta de áudio, movimento ou eventos automáticos no MVP.

### Conteúdo educativo
- Conteúdo editorial será disponibilizado localmente.
- Não será mantido histórico individual de leitura ou perfil de consumo.

### Dados temporais
- Datas e horários deverão preservar instantes inequívocos.
- A apresentação poderá utilizar o fuso horário local.
- A duração será derivada dos instantes de início e término.

### Consequência para a implementação
Qualquer campo ou coleta não justificado por uma finalidade do MVP deverá permanecer fora da implementação. Funcionalidades futuras exigirão nova avaliação de necessidade, finalidade, segurança e privacidade.
