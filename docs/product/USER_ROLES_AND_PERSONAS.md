# USER_ROLES_AND_PERSONAS.md
# Perfis de usuários, personas e responsabilidades

**Projeto:** Nanalu
**Versão:** 1.0
**Status:** Documento de planejamento
**Data:** 27/11/2026

---

## 1. Objetivo

Definir quem utilizará a Nanalu, quais necessidades cada perfil possui e quais responsabilidades e permissões deverão existir.

O aplicativo deverá considerar diferentes configurações familiares e formas de cuidado.

Não deverá presumir que a criança possui apenas um cuidador ou que todos os responsáveis participam da rotina da mesma maneira.

## 2. Usuário principal

O usuário principal é a pessoa responsável por organizar o acompanhamento do sono da criança.

Pode ser:

- Pai.
- Mãe.
- Responsável legal.
- Outro responsável autorizado.

Esse perfil deverá conseguir:

- Criar e administrar o perfil da criança.
- Registrar e consultar episódios de sono.
- Corrigir informações.
- Consultar estatísticas e histórico.
- Configurar preferências.
- Gerenciar permissões de outros cuidadores, quando o compartilhamento estiver disponível.
- Solicitar exportação ou exclusão dos dados, conforme as funcionalidades implementadas.

O aplicativo não deverá exigir que o usuário principal seja necessariamente a mãe ou o pai biológico.

## 3. Perfis de utilização

### 3.1. Responsável principal

**Descrição:** pessoa que administra o acompanhamento da criança.

**Necessidades:**

- Registrar informações rapidamente.
- Visualizar a rotina atual.
- Consultar o histórico.
- Identificar tendências.
- Entender estimativas.
- Controlar os dados compartilhados.

**Permissões previstas:**

- Criar e editar o perfil infantil.
- Registrar, editar e excluir episódios.
- Consultar todas as informações autorizadas.
- Configurar o aplicativo.
- Administrar outros usuários autorizados.

### 3.2. Responsável secundário

**Descrição:** pessoa que participa dos cuidados e precisa acompanhar os mesmos registros.

Exemplos: outro genitor, responsável legal ou familiar autorizado.

**Necessidades:**

- Consultar o estado atual do acompanhamento.
- Registrar episódios de sono.
- Informar despertares observados.
- Consultar o histórico.
- Evitar registros duplicados.
- Manter informações consistentes entre os responsáveis.

**Permissões previstas:**

- Consultar dados compartilhados.
- Criar registros.
- Editar ou excluir registros conforme a política de permissões.
- Consultar estatísticas autorizadas.

O responsável principal deverá controlar o acesso, sem que isso elimine os direitos legais de outros responsáveis.

### 3.3. Cuidador autorizado

**Descrição:** pessoa que cuida da criança durante determinados períodos, mas não necessariamente administra o aplicativo.

Exemplos:

- Avós.
- Babás.
- Cuidadores.
- Familiares.

**Necessidades:**

- Informar quando a criança dormiu.
- Informar quando acordou.
- Consultar instruções relevantes.
- Registrar observações simples.
- Evitar acesso a informações desnecessárias.

**Permissões previstas:**

O acesso deverá ser limitado ao que for necessário.

Possibilidades:

- Registrar episódios.
- Consultar informações básicas da rotina.
- Adicionar observações.
- Visualizar orientações compartilhadas.

O cuidador não deverá receber automaticamente permissão para excluir o perfil infantil, administrar outros usuários ou alterar configurações de privacidade.

### 3.4. Usuário sem perfil compartilhado

**Descrição:** pessoa que utiliza a Nanalu individualmente.

Esse deverá ser um cenário plenamente funcional desde a primeira versão.

**Necessidades:**

- Utilizar todas as funções essenciais sem convidar outras pessoas.
- Manter os dados no próprio dispositivo.
- Registrar e consultar informações sem depender de conexão.
- Não precisar criar uma conta desnecessariamente.

A experiência individual será a referência inicial para o MVP.

## 4. Personas de referência

As personas abaixo são personagens fictícios utilizados para orientar decisões de produto. Não representam pesquisas estatísticas nem perfis universais de famílias.

### Persona A: Responsável com rotina intensa

**Contexto:**

Cuida de uma criança pequena e possui diversas tarefas durante o dia.

**Dificuldades:**

- Esquece horários.
- Não consegue fazer anotações longas.
- Precisa registrar informações rapidamente.
- Tem pouco tempo para navegar pelo aplicativo.

**O que espera da Nanalu:**

- Botões de acesso rápido.
- Registro simples.
- Histórico organizado.
- Informações resumidas.
- Poucas etapas para tarefas frequentes.

**Implicação para o produto:**

As ações mais utilizadas deverão estar disponíveis diretamente no painel principal.

### Persona B: Responsáveis que dividem os cuidados

**Contexto:**

Duas ou mais pessoas participam da rotina da criança.

**Dificuldades:**

- Informações ficam espalhadas entre conversas.
- Um responsável pode não saber quando ocorreu a última soneca.
- Podem existir registros duplicados.
- A rotina pode mudar conforme a pessoa responsável pelo cuidado.

**O que esperam da Nanalu:**

- Histórico compartilhado.
- Atualizações consistentes.
- Identificação da origem dos registros.
- Controle de permissões.
- Facilidade para registrar informações.

**Implicação para o produto:**

A arquitetura deverá permitir compartilhamento futuro sem tornar essa função obrigatória no MVP.

### Persona C: Cuidador com acesso limitado

**Contexto:**

Uma pessoa cuida da criança em determinados horários, mas não administra sua rotina completa.

**Dificuldades:**

- Não sabe quais informações precisam ser registradas.
- Pode não ter acesso ao histórico completo.
- Precisa transmitir informações ao responsável.

**O que espera da Nanalu:**

- Interface simples.
- Registro rápido.
- Orientações claras.
- Permissões adequadas.
- Facilidade para comunicar acontecimentos.

**Implicação para o produto:**

O sistema de permissões deverá distinguir quem administra o acompanhamento de quem apenas participa dele.

### Persona D: Responsável interessado em compreender padrões

**Contexto:**

Utiliza registros para entender como o sono da criança muda ao longo das semanas e meses.

**Dificuldades:**

- Não percebe tendências observando apenas um dia.
- Tem dificuldade para comparar períodos.
- Não sabe interpretar médias e variações.
- Pode confundir recomendações gerais com necessidades individuais.

**O que espera da Nanalu:**

- Gráficos compreensíveis.
- Comparações por período.
- Explicações contextualizadas.
- Estimativas transparentes.
- Informações educativas confiáveis.

**Implicação para o produto:**

As estatísticas deverão destacar padrões individuais e explicar suas limitações.

## 5. A criança como titular dos dados

A criança é o sujeito ao qual os dados se referem, mas não é o usuário operacional do aplicativo.

A Nanalu deverá considerar a criança como titular dos dados pessoais registrados.

Isso significa que:

- Os dados deverão ser tratados com proteção adequada.
- A coleta deverá ser limitada ao necessário.
- Os responsáveis deverão receber informações claras sobre o tratamento.
- O acesso deverá ser controlado.
- A exclusão e a correção deverão ser consideradas desde a arquitetura.
- O melhor interesse da criança deverá orientar as decisões relacionadas aos seus dados.

A implementação deverá observar a LGPD e as regras aplicáveis ao tratamento de dados pessoais de crianças.

## 6. Matriz inicial de permissões

A tabela apresenta uma proposta conceitual para uma futura versão com múltiplos usuários.

| Ação | Principal | Secundário | Cuidador |
|---|---|---|---|
| Consultar histórico | Sim | Autorizado | Limitado |
| Criar registro | Sim | Sim | Autorizado |
| Editar registro | Sim | Conforme permissão | Conforme permissão |
| Excluir registro | Sim | Conforme permissão | Não por padrão |
| Editar perfil infantil | Sim | Conforme permissão | Não |
| Gerenciar usuários | Sim | Não por padrão | Não |
| Alterar privacidade | Sim | Não por padrão | Não |
| Excluir perfil infantil | Sim, mediante confirmação | Conforme direitos legais | Não |

As permissões definitivas dependerão da arquitetura, das regras de compartilhamento e das exigências legais.

A tabela não deverá ser interpretada como uma definição de direitos familiares ou de guarda.

## 7. Identificação da origem dos registros

Quando houver múltiplos usuários, cada registro deverá possuir informações suficientes para identificar sua origem.

Campos conceituais:

- Identificador do registro.
- Identificador da criança.
- Identificador do usuário que criou o registro, quando aplicável.
- Data e horário do evento.
- Data e horário de criação.
- Origem do registro.
- Última modificação.
- Identificação de quem realizou a modificação.

Possíveis origens:

- Registro manual.
- Registro retroativo.
- Evento detectado automaticamente.
- Registro importado.
- Registro sincronizado.

A origem deverá permanecer identificável mesmo quando um registro for editado.

## 8. Conflitos e registros duplicados

Em uma futura versão compartilhada, duas pessoas poderão registrar o mesmo episódio ou modificar informações simultaneamente.

A arquitetura deverá prever:

- Identificadores únicos.
- Detecção de possíveis duplicações.
- Controle de alterações.
- Regras de sincronização.
- Tratamento de conflitos.
- Histórico de modificações quando necessário.

O aplicativo não deverá apagar automaticamente informações conflitantes sem uma regra segura e documentada.

## 9. Privacidade entre usuários

O compartilhamento deverá ser explícito.

Princípios:

- Nenhum cuidador deverá receber acesso automaticamente.
- O responsável deverá compreender quais dados serão compartilhados.
- O acesso deverá poder ser revogado.
- Permissões deverão seguir o princípio do menor privilégio.
- Dados desnecessários não deverão ser expostos.
- A revogação deverá impedir novos acessos conforme as capacidades técnicas do sistema.

A revogação de acesso não garante, por si só, a remoção de informações que já tenham sido visualizadas ou armazenadas por outro usuário. Essa limitação deverá ser explicada.

## 10. Acessibilidade e diversidade de usuários

A experiência deverá considerar:

- Diferentes níveis de familiaridade com tecnologia.
- Diferentes tamanhos de tela.
- Leitura confortável.
- Contraste adequado.
- Textos objetivos.
- Controles fáceis de identificar.
- Áreas de toque confortáveis.
- Compatibilidade com recursos de acessibilidade do Android.
- Utilização em ambientes com pouca iluminação.

A interface não deverá depender exclusivamente de cores para comunicar estados.

## 11. Cenários prioritários de utilização

### Cenário 1: Registro individual

1. O responsável abre a Nanalu.
2. Visualiza o painel da criança.
3. Inicia o registro de sono.
4. Finaliza o registro quando a criança acorda.
5. O aplicativo calcula a duração.
6. O episódio aparece no histórico.

### Cenário 2: Consulta rápida

1. O responsável abre o aplicativo.
2. Visualiza o último episódio registrado.
3. Consulta a duração.
4. Verifica o histórico recente.
5. Consulta a estimativa seguinte, caso existam dados suficientes.

### Cenário 3: Registro por outro cuidador

1. O cuidador autorizado acessa o perfil compartilhado.
2. Registra o episódio.
3. O aplicativo identifica a origem.
4. O responsável principal consegue consultar a informação.

Esse cenário dependerá da implementação futura de compartilhamento.

### Cenário 4: Correção de informação

1. O responsável identifica um horário incorreto.
2. Abre o registro correspondente.
3. Corrige o horário.
4. O aplicativo recalcula a duração e as estatísticas relacionadas.
5. A alteração é preservada corretamente.

## 12. Decisões para o MVP

A primeira versão deverá:

- Funcionar com um único usuário.
- Permitir acompanhar uma criança.
- Não exigir compartilhamento.
- Não exigir criação de conta sem necessidade técnica.
- Manter registros locais.
- Permitir corrigir informações.
- Diferenciar registros manuais e automáticos, caso estes últimos sejam implementados.

A arquitetura deverá evitar decisões que tornem impossível adicionar múltiplos usuários e crianças posteriormente.

Entretanto, não deverá implementar infraestrutura complexa de compartilhamento antes de existir necessidade comprovada.

## 13. Questões para especificações futuras

Os seguintes pontos precisarão ser definidos em documentos próprios:

- Como será o convite de outros responsáveis.
- Como será feita a autenticação.
- Como serão revogados acessos.
- Como funcionará a sincronização.
- Como serão resolvidos conflitos.
- Como será realizado o compartilhamento entre dispositivos.
- Quais informações cada perfil poderá consultar.
- Como serão tratados dados quando existirem diferentes responsáveis legais.
- Como será feita a exclusão de dados em dispositivos sincronizados.

## 14. Documentos relacionados

- PROJECT_CHARTER.md
- PRODUCT_VISION_AND_SCOPE.md
- ../privacy/
- ../architecture/
- FUNCTIONAL_REQUIREMENTS.md
- USER_FLOWS.md

## 15. Princípio central

A Nanalu deverá acompanhar a realidade da família, não impor um modelo único de cuidado.

Cada pessoa autorizada deverá conseguir contribuir com informações de maneira simples e segura, enquanto os dados da criança permanecem protegidos.

**Uma criança, diferentes cuidadores, informações organizadas e um acompanhamento coerente.**