# Avaliação de tecnologias

## Objetivo
Comparar as tecnologias candidatas para a primeira versão Android da Nanalu.

## Contexto do produto
- Aplicativo inicialmente exclusivo para Android.
- Funcionamento essencial totalmente offline.
- Compatibilidade com aparelhos de entrada e intermediários.
- Dados estruturados e persistentes no dispositivo.
- Privacidade como requisito central.
- Possibilidade futura de recursos acústicos, sem torná-los requisito do MVP.

## Candidatas
- Kotlin com Android Jetpack Compose.
- Flutter com Dart.
- React Native com TypeScript.

## Critérios
- Integração com recursos nativos do Android.
- Desempenho e consumo de memória.
- Confiabilidade e manutenção.
- Persistência local e funcionamento offline.
- Segurança e privacidade.
- Testabilidade e qualidade das ferramentas.
- Complexidade real de desenvolvimento.

## Resultado preliminar
Kotlin nativo apresenta a melhor adequação inicial ao escopo Android da Nanalu.

Essa é uma recomendação preliminar, ainda sujeita à avaliação técnica documentada e à validação prática.

## Fontes oficiais consultadas
- Android Developers: recomendações de arquitetura.
- Android Developers: Jetpack Compose.
- Android Developers: Room e SQLite.
- Android Developers: DataStore.
- Android Developers: WorkManager e serviços em primeiro plano.
- Flutter: visão geral da arquitetura e persistência.
- React Native: nova arquitetura e módulos nativos.
- Android Developers: Android Keystore.

## Limitações da avaliação
Não foram realizados benchmarks próprios entre as tecnologias.
Desempenho final deverá ser medido em aparelhos reais de diferentes capacidades.
Recursos de microfone em segundo plano exigem análise específica das versões Android suportadas.

## Próxima etapa
Concluir a matriz ponderada, registrar justificativas e atualizar as decisões técnicas.

## Matriz ponderada

As notas variam de 1 (menor adequação) a 5 (maior adequação). São avaliações técnicas qualitativas baseadas no escopo e na documentação oficial, não medições de desempenho.

| Critério | Peso | Kotlin nativo | Flutter | React Native |
|---|---:|---:|---:|---:|
| Compatibilidade Android | 20% | 5 | 4 | 4 |
| Confiabilidade | 20% | 5 | 4 | 3 |
| Desempenho | 15% | 4 | 4 | 3 |
| Segurança e privacidade | 15% | 5 | 4 | 3 |
| Manutenção | 10% | 4 | 4 | 3 |
| Ecossistema e documentação | 10% | 5 | 4 | 4 |
| Adequação ao produto | 10% | 5 | 4 | 3 |

### Resultado ponderado

| Alternativa | Pontuação |
|---|---:|
| Kotlin + Jetpack Compose + Room | 4,75 / 5 |
| Flutter + Dart + SQLite/Drift | 4,00 / 5 |
| React Native + TypeScript + SQLite | 3,30 / 5 |

### Interpretação

Kotlin nativo obtém a maior pontuação porque a primeira versão é Android, depende de integração direta com o sistema e prioriza funcionamento local, previsibilidade e controle.

Flutter permanece tecnicamente viável e oferece uma rota mais direta para uma futura versão multiplataforma. Entretanto, recursos específicos do Android podem exigir integração por canais de plataforma e código Kotlin.

React Native também permite módulos nativos, mas adiciona uma camada JavaScript/TypeScript e exige atenção à compatibilidade das bibliotecas com a Nova Arquitetura.

As notas não demonstram que uma tecnologia sempre será mais rápida ou segura. Esses atributos dependem da implementação e deverão ser verificados com testes.

## Justificativas técnicas

### Kotlin com Jetpack Compose
- Integração direta com APIs e componentes oficiais do Android.
- Compose oferece interface declarativa integrada ao ecossistema Kotlin.
- Room fornece persistência estruturada sobre SQLite, com consultas verificáveis em compilação.
- DataStore atende preferências e configurações simples, sem substituir o banco de dados.
- WorkManager atende tarefas adiáveis e persistentes, mas não deve ser tratado como mecanismo de monitoramento contínuo em tempo real.
- Facilita implementar e testar recursos específicos do Android sem uma camada multiplataforma intermediária.

### Flutter com Dart
- Oferece interface consistente e desenvolvimento multiplataforma.
- Possui ecossistema maduro e opções de persistência SQLite, incluindo Drift.
- Recursos específicos do Android podem exigir Platform Channels ou plugins nativos.
- A camada de integração nativa aumenta a superfície de manutenção quando o aplicativo depende intensamente do sistema.
- Seria uma opção mais atraente se Android e iOS fossem prioridades equivalentes desde o início.

### React Native com TypeScript
- Permite desenvolvimento multiplataforma com TypeScript e componentes nativos.
- A Nova Arquitetura melhora a comunicação entre JavaScript e código nativo.
- Bibliotecas e módulos nativos precisam ser avaliados quanto à compatibilidade e manutenção.
- Recursos específicos do Android podem exigir módulos Kotlin ou Java.
- É viável, mas não apresenta vantagem suficiente para justificar a camada adicional neste escopo inicial.

## Implicações para o monitoramento acústico

O monitoramento acústico não integra o MVP e não deve ser pressuposto como recurso garantido.

### Restrições da plataforma Android
- A captura de áudio exige a permissão RECORD_AUDIO concedida pelo usuário.
- Serviços em primeiro plano que utilizam microfone devem declarar o tipo microphone.
- Android 14 (API 34) ou superior exige declaração do tipo de serviço e da permissão específica FOREGROUND_SERVICE_MICROPHONE.
- A permissão de microfone está sujeita às restrições de acesso durante o uso.
- Em condições gerais, o serviço precisa ser iniciado enquanto o aplicativo possui uma atividade visível.
- O sistema pode impedir inicializações em segundo plano que não atendam às exceções previstas.
- A implementação deve respeitar as versões mínimas e máximas de Android efetivamente suportadas.

### Diretrizes arquiteturais
- Isolar captura, processamento e interpretação acústica em um subsistema independente.
- Não acoplar o funcionamento do registro manual ao microfone.
- Não solicitar permissão de áudio durante a primeira execução do MVP.
- Não gravar ou transmitir áudio sem consentimento explícito e informação clara.
- Priorizar processamento local e minimizar retenção de áudio bruto.
- Prever interrupção, pausa, encerramento e recuperação de sessões.
- Exibir claramente quando a captura estiver ativa.
- Não interpretar ruídos como confirmações de sono, despertar ou eventos médicos.
- Não apresentar o recurso como detector de apneia, prevenção de SMSI ou sistema de segurança infantil.

### Consequência para a escolha tecnológica
Kotlin oferece acesso direto às APIs Android necessárias para uma eventual implementação. Isso reduz a dependência de plugins ou pontes de integração, mas não elimina as restrições impostas pelo sistema operacional.

Flutter e React Native também podem acessar essas APIs por integrações nativas. Portanto, a diferença está principalmente na complexidade de integração e manutenção, não na possibilidade de ignorar as regras do Android.

### Decisão de escopo
O MVP será desenvolvido sem captura de áudio. A viabilidade do monitoramento acústico será investigada separadamente, com protótipo, testes em aparelhos reais, avaliação de consumo energético, privacidade e compatibilidade.

## Fontes oficiais específicas sobre áudio
- Android Developers: Foreground service types are required, documentação para Android 14.
- Android Developers: Restrictions on starting a foreground service from the background.
- Android Developers: Foreground service types, incluindo microphone.

Referências:
- https://developer.android.com/about/versions/14/changes/fgs-types-required
- https://developer.android.com/develop/background-work/services/fgs/restrictions-bg-start
- https://developer.android.com/develop/background-work/services/fgs/service-types
