# SCREEN_MAP.md
# Mapa de telas e navegação da Nanalu

**Projeto:** Nanalu
**Versão:** 1.0
**Status:** Planejamento estrutural
**Data:** 27/11/2026
**Plataforma inicial:** Android

---

## 1. Objetivo

Definir a estrutura de telas da Nanalu, sua hierarquia de navegação e as principais informações e ações disponíveis em cada área.

Este documento estabelece a organização funcional da interface.

Não define ainda:

- Paleta de cores.
- Tipografia.
- Ilustrações definitivas.
- Aparência final dos componentes.
- Animações.
- Identidade visual detalhada.

Esses elementos serão especificados posteriormente, considerando a referência visual oficial da mascote Luli.

## 2. Princípios de navegação

A navegação deverá seguir estes princípios:

1. As funções mais utilizadas deverão exigir poucas interações.
2. O registro de sono deverá ser acessível rapidamente.
3. O usuário deverá conseguir identificar facilmente o estado atual do acompanhamento.
4. Informações detalhadas não deverão sobrecarregar o painel principal.
5. A navegação deverá ser previsível.
6. Ações destrutivas deverão exigir confirmação adequada.
7. O botão de voltar do Android deverá respeitar a hierarquia de navegação.
8. Telas sem dados deverão apresentar orientações úteis.
9. A interface deverá funcionar sem internet nas funções essenciais.
10. O usuário não deverá precisar navegar por várias telas para realizar tarefas frequentes.

## 3. Estrutura principal

A aplicação será organizada em cinco áreas principais:

1. Início.
2. Histórico.
3. Estatísticas.
4. Conteúdo.
5. Perfil e configurações.

A navegação principal deverá permanecer acessível nas áreas centrais do aplicativo.

O registro de sono deverá receber destaque próprio no painel inicial, sem precisar ser uma seção independente da navegação principal.

## 4. Hierarquia de telas

```text
Nanalu
│
├── 00. Inicialização
│   ├── Splash
│   └── Verificação de configuração
│
├── 01. Primeiro acesso
│   ├── Apresentação
│   ├── Cadastro da criança
│   └── Confirmação
│
├── 02. Aplicativo principal
│   │
│   ├── Início
│   │   ├── Resumo atual
│   │   ├── Estado do sono
│   │   ├── Ação iniciar/finalizar sono
│   │   ├── Resumo diário
│   │   ├── Último episódio
│   │   └── Próxima estimativa
│   │
│   ├── Histórico
│   │   ├── Lista de episódios
│   │   ├── Filtro por período
│   │   ├── Detalhes do episódio
│   │   ├── Novo registro
│   │   └── Editar registro
│   │
│   ├── Estatísticas
│   │   ├── Visão geral
│   │   ├── Sono diurno
│   │   ├── Sono noturno
│   │   └── Comparação de períodos
│   │
│   ├── Conteúdo
│   │   ├── Categorias
│   │   ├── Lista de artigos
│   │   └── Leitura do artigo
│   │
│   └── Perfil
│       ├── Dados da criança
│       ├── Editar perfil
│       ├── Preferências
│       ├── Notificações
│       ├── Privacidade
│       ├── Sobre a Nanalu
│       └── Exclusão de dados
│
└── 03. Futuras expansões
    ├── Compartilhamento familiar
    ├── Gerenciamento de cuidadores
    ├── Monitoramento acústico
    ├── Relatórios exportáveis
    └── Múltiplos perfis infantis
```

## 5. Identificação das telas

Os identificadores abaixo deverão ser utilizados para facilitar referências durante implementação e testes.

### 5.1. Inicialização

#### SCR-001: Splash

**Prioridade:** P0

Tela apresentada durante a inicialização.

Elementos:

- Identidade Nanalu.
- Mascote Luli, quando a identidade visual estiver definida.
- Transição breve para o conteúdo principal.

Regras:

- Não deverá prolongar artificialmente a inicialização.
- Não deverá bloquear o usuário com animações longas.
- Deverá respeitar as recomendações de inicialização do Android.

#### SCR-002: Verificação de configuração

**Prioridade:** P0

Verifica se existe um perfil infantil cadastrado e se a configuração inicial foi concluída.

Destinos:

- Sem perfil: primeiro acesso.
- Com perfil: painel principal.
- Com configuração incompleta: etapa correspondente.

### 5.2. Primeiro acesso

#### SCR-003: Apresentação

**Prioridade:** P0

Apresenta brevemente a proposta da Nanalu.

Conteúdo:

- O que o aplicativo faz.
- Como funciona o acompanhamento.
- Limitações importantes.
- Explicação de que não é um dispositivo médico.

A apresentação deverá ser curta e poderá ser ignorada quando isso não comprometer informações essenciais.

#### SCR-004: Cadastro da criança

**Prioridade:** P0

Permite cadastrar o primeiro perfil infantil.

Campos:

- Nome ou apelido.
- Data de nascimento.
- Foto opcional.

Ações:

- Salvar.
- Cancelar, quando aplicável.

Validações:

- Nome obrigatório.
- Data válida.
- Data não futura.
- Foto opcional.

#### SCR-005: Confirmação do cadastro

**Prioridade:** P0

Confirma a criação do perfil e direciona ao painel principal.

Não deverá apresentar informações que ainda não foram calculadas ou registradas.

## 6. Área principal

### 6.1. SCR-010: Início

<badge color="success">Tela central do MVP</badge>

**Prioridade:** P0

É a tela mais importante da Nanalu.

Deverá permitir compreender rapidamente a situação do acompanhamento e realizar as ações frequentes.

#### Seções previstas

**A. Identificação da criança**

- Nome ou apelido.
- Idade.
- Acesso ao perfil.

**B. Estado atual**

Exibir um dos estados:

- Sono em andamento.
- Nenhum episódio ativo.
- Sem informações suficientes.

Se existir um episódio ativo, apresentar:

- Horário de início.
- Tempo transcorrido.
- Botão para finalizar.

**C. Ação principal**

Quando não existir episódio ativo:

- Botão iniciar sono.

Quando existir episódio ativo:

- Botão finalizar sono.

O botão deverá possuir destaque funcional e comportamento inequívoco.

**D. Resumo do dia**

- Quantidade de sonecas.
- Total de sono diurno registrado.
- Último episódio.
- Informações incompletas, quando existirem.

**E. Próxima janela de descanso**

Quando houver dados suficientes:

- Horário ou intervalo estimado.
- Explicação resumida.
- Indicação de incerteza.

Quando não houver dados suficientes:

- Explicar que ainda não existe histórico suficiente.

**F. Acesso ao histórico**

Apresentar atalho para consultar episódios recentes.

#### Regras

- Os dados deverão ser atualizados após cada registro.
- O painel não deverá apresentar estatísticas fictícias.
- Estimativas deverão ser claramente identificadas.
- O estado do sono deverá refletir registros, não uma detecção fisiológica presumida.
- A tela deverá priorizar informações úteis em vez de exibir todos os dados disponíveis.

### 6.2. SCR-011: Registro de sono

**Prioridade:** P0

Interface para iniciar ou finalizar um episódio.

Pode ser apresentada como tela dedicada, painel modal ou componente expandido do início. A escolha visual será definida durante o protótipo.

#### Modo iniciar

Exibir:

- Identificação da criança.
- Horário atual.
- Ação iniciar.
- Possibilidade de cancelar.

#### Modo em andamento

Exibir:

- Horário inicial.
- Tempo transcorrido.
- Ação finalizar.

#### Modo finalizar

Após a finalização:

- Horário final.
- Duração calculada.
- Opção de salvar.
- Possibilidade de correção antes de confirmar, quando necessário.

#### Regras

- Impedir episódios simultâneos incompatíveis.
- Preservar o episódio ativo após fechar o aplicativo.
- Não perder dados após interrupção inesperada.
- Informar claramente o resultado da operação.

### 6.3. SCR-012: Registro retroativo

**Prioridade:** P0

Permite registrar episódios ocorridos anteriormente.

Campos:

- Data.
- Horário de início.
- Horário de término.
- Classificação, quando aplicável.
- Observação opcional.

Ações:

- Salvar.
- Cancelar.

Validações:

- Data válida.
- Horários coerentes.
- Duração positiva.
- Aviso de sobreposição.
- Revisão antes de salvar.

### 6.4. SCR-013: Confirmação de registro

**Prioridade:** P0

Apresenta confirmação após salvar um episódio.

Informações:

- Horário.
- Duração.
- Classificação.
- Origem do registro.

Ações:

- Voltar ao início.
- Consultar detalhes.

A confirmação não deverá interromper desnecessariamente a utilização.

## 7. Área de histórico

### 7.1. SCR-020: Histórico

**Prioridade:** P0

Apresenta todos os episódios registrados.

Elementos:

- Data selecionada.
- Lista cronológica.
- Horários.
- Duração.
- Classificação.
- Origem.

Ações:

- Abrir detalhes.
- Editar.
- Excluir.
- Adicionar registro retroativo.
- Navegar entre datas.

Estados:

- Com registros.
- Sem registros.
- Carregando.
- Erro de leitura.

### 7.2. SCR-021: Filtro de histórico

**Prioridade:** P1

Permite filtrar registros por:

- Dia.
- Semana.
- Mês.
- Intervalo personalizado.

O filtro deverá deixar claro qual período está sendo consultado.

### 7.3. SCR-022: Detalhes do episódio

**Prioridade:** P0

Apresenta informações completas de um registro.

Elementos:

- Data.
- Início.
- Término.
- Duração.
- Classificação.
- Origem.
- Observação.

Ações:

- Editar.
- Excluir.
- Voltar.

### 7.4. SCR-023: Editar episódio

**Prioridade:** P0

Permite corrigir os dados de um registro.

Após salvar:

- Recalcular duração.
- Atualizar estatísticas.
- Atualizar estimativas dependentes.
- Preservar integridade do histórico.

### 7.5. SCR-024: Confirmação de exclusão

**Prioridade:** P0

Apresenta confirmação antes de excluir um episódio.

Deverá informar claramente qual registro será removido.

## 8. Área de estatísticas

### 8.1. SCR-030: Visão geral

**Prioridade:** P0

Apresenta estatísticas básicas do sono.

Informações:

- Total diário registrado.
- Quantidade de episódios.
- Duração média disponível.
- Distribuição entre sono diurno e noturno.
- Período analisado.

### 8.2. SCR-031: Sono diurno

**Prioridade:** P1

Apresenta informações sobre sonecas.

Possibilidades:

- Quantidade por dia.
- Duração.
- Horários recorrentes.
- Comparação entre períodos.

### 8.3. SCR-032: Sono noturno

**Prioridade:** P1

Apresenta informações sobre episódios classificados como sono noturno.

Possibilidades:

- Horário de início.
- Horário de término.
- Duração registrada.
- Variações entre noites.

### 8.4. SCR-033: Comparação de períodos

**Prioridade:** P1

Permite comparar períodos diferentes.

Exemplos:

- Semana atual e anterior.
- Mês atual e anterior.
- Evolução de duração.
- Mudanças na quantidade de sonecas.

As comparações deverão explicar diferenças na quantidade de dados disponíveis.

## 9. Área educativa

### 9.1. SCR-040: Biblioteca de conteúdo

**Prioridade:** P1

Apresenta os temas educativos.

Categorias:

- Desenvolvimento do sono.
- Necessidades por idade.
- Sonecas.
- Transições.
- Ciclos.
- Despertares.
- Higiene do sono.
- Sono seguro.

### 9.2. SCR-041: Lista de artigos

**Prioridade:** P1

Apresenta conteúdos de uma categoria.

Elementos:

- Título.
- Resumo.
- Faixa etária relacionada, quando aplicável.
- Tempo estimado de leitura, se disponível.

### 9.3. SCR-042: Leitura do artigo

**Prioridade:** P1

Apresenta o conteúdo completo.

Elementos:

- Título.
- Texto.
- Imagens, quando disponíveis.
- Referências.
- Data ou versão de atualização.

O conteúdo deverá ser legível e possuir hierarquia clara.

## 10. Área de perfil e configurações

### 10.1. SCR-050: Perfil da criança

**Prioridade:** P0

Apresenta:

- Nome.
- Data de nascimento.
- Idade calculada.
- Foto, quando disponível.

Ações:

- Editar.
- Consultar informações adicionais.

### 10.2. SCR-051: Editar perfil

**Prioridade:** P0

Permite alterar as informações cadastradas.

### 10.3. SCR-052: Preferências

**Prioridade:** P0

Apresenta configurações básicas.

Exemplos:

- Formato de horário.
- Preferências de apresentação.
- Opções de acessibilidade.

### 10.4. SCR-053: Notificações

**Prioridade:** P2

Permite configurar notificações futuras.

Deverá explicar as opções disponíveis e permitir desativá-las.

### 10.5. SCR-054: Privacidade

**Prioridade:** P0

Apresenta informações sobre:

- Dados armazenados.
- Permissões concedidas.
- Uso de áudio, caso exista.
- Armazenamento local.
- Compartilhamento, quando disponível.
- Exclusão de dados.
- Política de privacidade.

### 10.6. SCR-055: Sobre a Nanalu

**Prioridade:** P0

Apresenta:

- Nome e versão.
- Propósito.
- Limitações.
- Informações de suporte, quando disponíveis.
- Créditos.
- Referências relevantes.

### 10.7. SCR-056: Exclusão de dados

**Prioridade:** P0

Permite solicitar a exclusão do perfil infantil e dos episódios e dados dependentes associados, conforme o alcance da solicitação.

Deverá:

- Identificar claramente o perfil e os dados que serão excluídos.
- Explicar que a exclusão do perfil remove também seus episódios e dados dependentes armazenados pelo aplicativo.
- Informar que a operação não apaga cópias externas fora do controle do aplicativo.
- Solicitar confirmação explícita.
- Evitar exclusão acidental.
- Informar o resultado somente após a confirmação da operação.

## 11. Futuras telas

As seguintes telas não são necessárias para o MVP.

| ID | Tela | Prioridade futura |
|---|---|---|
| SCR-060 | Compartilhamento familiar | P2 |
| SCR-061 | Gerenciar cuidadores | P2 |
| SCR-062 | Convidar responsável | P2 |
| SCR-063 | Permissões de acesso | P2 |
| SCR-064 | Monitoramento acústico | EXP |
| SCR-065 | Revisão de eventos detectados | EXP |
| SCR-066 | Relatórios exportáveis | P2 |
| SCR-067 | Gerenciamento de múltiplas crianças | P2 |

## 12. Navegação principal proposta

A navegação principal deverá apresentar cinco destinos:

- Início.
- Histórico.
- Estatísticas.
- Conteúdo.
- Perfil.

A estrutura deverá priorizar os destinos mais frequentes.

A navegação não deverá esconder a ação de iniciar ou finalizar um episódio.

O comportamento final será validado em protótipo.

## 13. Estados globais de interface

As telas deverão considerar estados comuns.

### 13.1. Carregamento

Indicar quando os dados estiverem sendo carregados.

### 13.2. Vazio

Explicar por que não existem dados e orientar a próxima ação.

### 13.3. Erro

Informar o problema sem expor detalhes técnicos desnecessários.

### 13.4. Sucesso

Confirmar operações concluídas.

### 13.5. Sem conexão

Informar a ausência de internet somente quando ela afetar alguma função.

### 13.6. Dados insuficientes

Diferenciar falta de dados de erro de funcionamento.

### 13.7. Permissão negada

Explicar a necessidade de uma permissão quando uma função depender dela.

## 14. Regras de navegação

1. A navegação principal deverá permanecer consistente.
2. Telas de detalhes deverão permitir retorno ao contexto anterior.
3. O botão voltar não deverá descartar alterações silenciosamente.
4. Formulários com alterações não salvas deverão alertar o usuário.
5. Modais deverão ser reservados para tarefas pontuais.
6. Ações irreversíveis deverão exigir confirmação.
7. O aplicativo deverá preservar o estado de um episódio ativo.
8. A navegação não deverá depender de conexão com a internet para funções essenciais.

## 15. Prioridade de implementação das telas

### Grupo A: MVP essencial

- SCR-001: Splash.
- SCR-002: Verificação de configuração.
- SCR-003: Apresentação.
- SCR-004: Cadastro da criança.
- SCR-005: Confirmação.
- SCR-010: Início.
- SCR-011: Registro de sono.
- SCR-012: Registro retroativo.
- SCR-013: Confirmação de registro.
- SCR-020: Histórico.
- SCR-022: Detalhes do episódio.
- SCR-023: Editar episódio.
- SCR-024: Confirmação de exclusão.
- SCR-030: Visão geral.
- SCR-050: Perfil da criança.
- SCR-051: Editar perfil.
- SCR-052: Preferências.
- SCR-054: Privacidade.
- SCR-055: Sobre a Nanalu.
- SCR-056: Exclusão de dados.

### Grupo B: Expansão funcional

- SCR-021: Filtro de histórico.
- SCR-031: Sono diurno.
- SCR-032: Sono noturno.
- SCR-033: Comparação de períodos.
- SCR-040: Biblioteca.
- SCR-041: Lista de artigos.
- SCR-042: Leitura de artigo.

### Grupo C: Funcionalidades futuras

- SCR-053: Notificações.
- SCR-060 a SCR-067: Compartilhamento, monitoramento e expansão.

## 16. Critérios de conclusão

O mapa de telas será considerado suficientemente especificado quando:

- Todas as funções do MVP tiverem uma tela ou ponto de acesso definido.
- A navegação entre as áreas estiver documentada.
- Os estados vazios e de erro estiverem previstos.
- As ações principais estiverem identificadas.
- As telas estiverem relacionadas aos requisitos funcionais.
- Não existirem funções essenciais sem localização na interface.

A aparência visual será definida em documentação própria.

## 17. Documentos relacionados

- PROJECT_CHARTER.md
- PRODUCT_VISION_AND_SCOPE.md
- USER_ROLES_AND_PERSONAS.md
- FUNCTIONAL_REQUIREMENTS.md
- USER_FLOWS.md
- PRODUCT_RULES.md
- ../design/

## 18. Princípio central

A estrutura da Nanalu deverá permitir que o responsável encontre rapidamente o que precisa, registre informações sem esforço desnecessário e compreenda o acompanhamento sem precisar interpretar uma interface complexa.

**A navegação deverá acompanhar a rotina da família, não atrapalhá-la.**
