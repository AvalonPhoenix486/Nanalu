# Build, versões e decisões técnicas da estrutura inicial

Complementa `TECHNICAL_DECISIONS.md`. Estado: decisões abaixo tomadas na estrutura inicial; revisar se surgir incompatibilidade.

## Decisões pendentes resolvidas aqui
| Tema | Decisão | Justificativa |
|---|---|---|
| Android mínimo | API 26 (Android 8.0) | `java.time` nativo (sem desugaring) e ícone adaptativo sem fallback. compileSdk/targetSdk 35. |
| Navegação (DEC-006) | Navigation Compose, rotas por `TopLevelDestination` | Biblioteca oficial, suficiente para as 5 áreas de `SCREEN_MAP.md`. |
| Injeção de dependências (DEC-005) | Manual (`AppContainer`) | Poucas dependências no MVP; sem framework adicional. |
| DataStore | Não incluído ainda | Nenhuma preferência persistida nesta etapa; entra com a tela de preferências. |

## Versões (centralizadas em `gradle/libs.versions.toml`)
AGP 8.7.3 · Gradle 8.10.2 · Kotlin 2.0.21 · KSP 2.0.21-1.0.28 · Compose BOM 2024.12.01 · Room 2.6.1 · Lifecycle 2.8.7 · Navigation 2.8.5 · Coroutines 1.9.0.
Escolhidas por compatibilidade conhecida entre si; não foram verificadas contra versões mais recentes. Atualizar com a primeira compilação verde do CI.

## Backup e privacidade
`allowBackup=false` mais regras explícitas de exclusão total (`xml/data_extraction_rules.xml`, Android 12+, e `xml/backup_rules.xml`, Android 11-). Nenhuma permissão declarada.
Ainda falta o teste de verificação de backup/transferência (`SECURITY_SPEC.md` §15).

## Comandos
- Testes unitários: `gradle :app:testDebugUnitTest`
- APK debug: `gradle :app:assembleDebug` (saída em `app/build/outputs/apk/debug/`)
- Teste instrumentado do Room (requer emulador/dispositivo): `gradle :app:connectedDebugAndroidTest`

O wrapper do Gradle ainda não foi gerado. Quando houver acesso a uma máquina com Gradle: `gradle wrapper --gradle-version 8.10.2` e commitar os arquivos gerados.

## Pendência de especificação
`FUNCTIONAL_REQUIREMENTS.md` (FR-011) lista origens "manual em tempo real" e "manual retroativo"; `DATA_MODEL.md` define apenas `MANUAL`. O esquema v1 usa `MANUAL`; decidir antes da Fase 3 (registro de sono).
