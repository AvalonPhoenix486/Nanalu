# Estratégia de testes do Nanalu

## 1. Objetivo

Definir uma estratégia de testes para garantir que o Nanalu implemente corretamente suas regras funcionais, temporais, estatísticas, de privacidade e segurança.

Os testes devem acompanhar a implementação. Não basta declarar uma funcionalidade concluída: ela precisa apresentar evidências verificáveis.

## 2. Pirâmide de testes

### 2.1 Testes unitários

Prioridade para regras isoladas:
- Cálculo de duração.
- Validação cronológica.
- Classificação diurna e noturna.
- Tratamento de intervalos sobrepostos.
- Divisão de episódios entre dias.
- Elegibilidade de registros.
- Cálculos estatísticos.
- Regras de estimativas.
- Validação de entradas.

### 2.2 Testes de integração

Verificar a comunicação entre:
- ViewModels e casos de uso.
- Casos de uso e repositórios.
- Repositórios e Room.
- Persistência e atualização da interface.
- Exclusão de perfis e respectivos registros.
- Transações e integridade dos dados.

### 2.3 Testes de interface

Verificar:
- Navegação entre telas.
- Estados vazios e carregados.
- Criação e edição de perfil.
- Início e encerramento de episódios.
- Registros retroativos.
- Correção e exclusão.
- Confirmações de ações destrutivas.
- Acessibilidade e adaptação a diferentes telas.

### 2.4 Testes de sistema

Verificar o comportamento integrado:
- Primeiro uso.
- Uso offline.
- Reinicialização do aplicativo.
- Persistência após encerramento.
- Migrações do banco.
- Exclusão efetiva de dados.
- Comportamento diante de falhas.

## 3. Categorias obrigatórias

### FUNC: funcionalidades

Validar os requisitos funcionais e os fluxos documentados.

### TIME: regras temporais

Validar duração, datas, fusos horários, virada de dia, intervalos e sobreposições.

### DATA: integridade dos dados

Validar persistência, consistência, correções, exclusões e transações.

### EST: estimativas

Validar elegibilidade, suficiência, variabilidade, janelas históricas, reprodutibilidade e abstenção.

### PRIV: privacidade

Validar ausência de transmissão não autorizada, armazenamento local, exclusão e transparência.

### SEC: segurança

Validar permissões, exposição de dados, proteção do armazenamento e regras de backup.

### UX: experiência de uso

Validar clareza, prevenção de erros, acessibilidade e compreensão dos estados do aplicativo.

## 4. Regras temporais críticas

Os testes deverão contemplar:
- Episódios que começam e terminam no mesmo dia.
- Episódios que atravessam a meia-noite.
- Mudanças de fuso horário.
- Mudanças de horário local.
- Episódios com duração inválida.
- Episódios sobrepostos.
- Registros retroativos.
- Intervalos sem confirmação de despertar.
- Limites de períodos de agregação.

Duração deverá ser calculada pela diferença entre instantes, não pela simples subtração dos horários exibidos.

Intervalos deverão respeitar a convenção [início, fim).

Nenhuma lacuna deverá ser interpretada automaticamente como vigília confirmada.

## 5. Testes de exclusão e privacidade

Verificar:
- Exclusão de episódio.
- Exclusão de perfil e registros dependentes.
- Exclusão de todos os dados do aplicativo.
- Ausência de dependências órfãs.
- Tratamento de arquivos temporários e caches sob controle do aplicativo.
- Ausência de envio externo de dados no MVP.
- Exclusão dos dados de backups automáticos conforme a especificação.
- Ausência de restauração automática não autorizada.

Não declarar sobrescrita física garantida em armazenamento flash.

## 6. Testes das estimativas

Utilizar os cenários ET-001 a ET-013 definidos na especificação de cálculo.

Acrescentar testes comparativos para janelas históricas de 3, 7 e 14 dias.

Verificar que:
- Apenas registros elegíveis participam.
- Contextos diferentes não são misturados indevidamente.
- Correções e exclusões atualizam os resultados.
- Dados insuficientes impedem estimativas.
- Resultados são reproduzíveis.
- Estimativas não são apresentadas como garantias.

Testes sintéticos verificam o comportamento do código, mas não comprovam precisão preditiva real.

## 7. Critérios de conclusão

Uma funcionalidade somente poderá ser considerada testada quando:
1. Os cenários relevantes estiverem identificados.
2. Os testes tiverem sido executados.
3. Os resultados estiverem registrados.
4. Falhas críticas estiverem corrigidas.
5. Não houver regressões conhecidas sem justificativa documentada.

## 8. Evidências

Para cada conjunto relevante, registrar:
- Identificador do teste.
- Requisito relacionado.
- Cenário.
- Resultado esperado.
- Resultado obtido.
- Estado: aprovado, reprovado, bloqueado ou não executado.
- Evidência de execução.
- Observações e limitações.

## 9. Diretrizes para o desenvolvimento

- Priorizar testes unitários para regras de domínio.
- Não depender exclusivamente de testes manuais.
- Não usar dados reais de crianças sem fundamento legal e proteção apropriada.
- Não desativar verificações de segurança para facilitar testes.
- Não declarar testes aprovados sem execução.
- Automatizar testes de regressão sempre que viável.
- Manter testes alinhados aos requisitos e às decisões técnicas vigentes.

## 10. Limites

Esta estratégia define a organização dos testes, não comprova que o aplicativo já foi implementado, validado ou aprovado.

A validação científica de estimativas e qualquer avaliação clínica permanecem fora do escopo dos testes comuns de software.