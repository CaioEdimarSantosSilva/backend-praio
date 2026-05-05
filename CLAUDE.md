# CLAUDE.md — Backend PRAIÔ

## Visão geral

Backend do app **PRAIÔ** — plataforma de monitoramento de qualidade de praias brasileiras.
Spring Boot 4 + Java 21 + MongoDB Atlas. Expõe API REST consumida pelo frontend React em `http://localhost:5173`.

## Como rodar

```bash
./mvnw spring-boot:run        # inicia em http://localhost:8080
./mvnw clean package          # gera o JAR em /target
./mvnw test                   # roda os testes
```

Requer o arquivo `.env` na raiz do projeto com a string de conexão ao MongoDB (ver seção Configuração).

## Stack

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 21 | Linguagem |
| Spring Boot | 4.0.5 | Framework principal |
| spring-boot-starter-webmvc | — | REST/MVC |
| spring-boot-starter-data-mongodb | — | Repositórios MongoDB |
| Lombok | — | Geração de getters/setters/construtores |
| spring-dotenv (paulschwarz) | 3.0.0 | Carrega variáveis do `.env` |
| spring-boot-devtools | — | Hot-reload em desenvolvimento |

## Configuração

**`src/main/resources/application.properties`**
```properties
spring.application.name=praio-backend
server.port=8080
```

**`.env`** (raiz do projeto — não commitar)
```
MONGODB_URI=mongodb+srv://<user>:<pass>@cluster0.xxx.mongodb.net/praio?appName=Cluster0
```

A classe `MongoConfig.java` lê essa variável via `spring-dotenv` e configura o `MongoClient` e o `MongoTemplate`.

## Estrutura de pacotes

```
com.praio.backend/
├── BackendApplication.java       ← entry point + CommandLineRunner de dados de teste
├── MongoConfig.java              ← configuração do MongoClient via .env
├── config/
│   └── CorsConfig.java           ← CORS liberado para localhost:5173 e localhost:3000
├── model/                        ← entidades mapeadas para coleções MongoDB
│   ├── Usuario.java
│   ├── Praia.java
│   ├── CondicoesAmbientais.java
│   ├── Avaliacao.java
│   ├── Recomendacao.java
│   ├── PraiaRecomendada.java
│   ├── Alerta.java
│   ├── Log.java
│   ├── LocalizacaoPraia.java
│   ├── EstruturaPraia.java
│   ├── PreferenciasUsuario.java
│   ├── HistoricoBuscaUsuario.java
│   └── enums: TipoUsuario, QualidadeAgua, Lotacao, TipoAlerta, TipoAlertaAmbiental, AcaoLog
├── repository/                   ← interfaces MongoRepository (Spring Data)
│   ├── UsuarioRepository.java
│   ├── PraiaRepository.java
│   ├── CondicoesAmbientaisRepository.java
│   ├── AvaliacaoRepository.java
│   ├── RecomendacaoRepository.java
│   ├── AlertaRepository.java
│   └── LogRepository.java
├── dto/                          ← objetos de transferência de dados (request/response)
│   ├── LoginRequest.java
│   ├── CadastroRequest.java
│   └── AuthResponse.java
├── service/
│   └── UsuarioService.java       ← lógica de login e cadastro
└── controller/
    └── UsuarioController.java    ← endpoints REST de autenticação
```

## Coleções MongoDB (banco: `praio`)

| Coleção | Modelo | Descrição |
|---|---|---|
| `usuarios` | `Usuario` | Usuários cadastrados |
| `praias` | `Praia` | Dados das praias |
| `condicoes_ambientais` | `CondicoesAmbientais` | Qualidade da água, clima, ondas, UV, lotação |
| `avaliacoes` | `Avaliacao` | Notas (1–5) e comentários de usuários sobre praias |
| `recomendacoes` | `Recomendacao` | Praias recomendadas por usuário com score |
| `alertas` | `Alerta` | Alertas ativos (poluição, ressaca, etc.) |
| `logs` | `Log` | Registro de ações dos usuários |

### Modelo `Usuario`

```java
String id
String nome
String email        // @Indexed(unique = true)
String senha        // plain text por enquanto — sem hash
String fotoPerfil
Date dataCriacao
PreferenciasUsuario preferencias
List<HistoricoBuscaUsuario> historicoBuscas
List<String> favoritos   // IDs de praias
TipoUsuario tipoUsuario  // enum: ADMIN | COMUM
```

### Modelo `Praia`

```java
String id
String nome
String descricao
LocalizacaoPraia localizacao   // estado + cidade
List<String> imagens
EstruturaPraia estrutura
Double mediaAvaliacoes
Integer totalAvaliacoes
List<String> tags
```

## API REST implementada

Base path: `/api`

### Autenticação — `UsuarioController`

#### `POST /api/auth/cadastro`

Cria um novo usuário.

**Request body:**
```json
{ "nome": "João", "email": "joao@email.com", "senha": "minhasenha" }
```

**Respostas:**
- `201` — sucesso: `{ "success": true, "message": "Cadastro realizado com sucesso", "id": "...", "nome": "João", "email": "joao@email.com" }`
- `409` — email já cadastrado: `{ "success": false, "message": "Este email já está cadastrado" }`

#### `POST /api/auth/login`

Autentica um usuário existente.

**Request body:**
```json
{ "email": "joao@email.com", "senha": "minhasenha" }
```

**Respostas:**
- `200` — sucesso: `{ "success": true, "message": "Login realizado com sucesso", "id": "...", "nome": "João", "email": "joao@email.com" }`
- `401` — credenciais inválidas: `{ "success": false, "message": "Email ou senha inválidos" }`

## CORS

`CorsConfig.java` libera `/api/**` para:
- `http://localhost:5173` (Vite dev server)
- `http://localhost:3000` (Create React App, se usado)

Métodos permitidos: `GET, POST, PUT, DELETE, OPTIONS`. Headers: `*`. Credentials: `true`.

## CommandLineRunner (dados de teste)

O `BackendApplication` tem um `CommandLineRunner` que insere dados de teste na inicialização. Ele busca o primeiro `usuarioId` e `praiaId` existentes no banco e cria registros de teste para: `CondicoesAmbientais`, `Avaliacao`, `Recomendacao`, `Alerta` e `Log`.

Se as coleções `usuarios` ou `praias` estiverem vazias, o runner pula silenciosamente (sem erro). Caso ocorra qualquer exceção, ela é capturada e logada sem derrubar a aplicação.

## Repositórios disponíveis (Spring Data MongoDB)

Todos estendem `MongoRepository<T, String>` e têm os métodos padrão (save, findAll, findById, deleteById, etc.). Métodos customizados:

| Repositório | Método extra |
|---|---|
| `UsuarioRepository` | `Optional<Usuario> findByEmail(String email)` |
| `PraiaRepository` | `findByLocalizacaoEstado`, `findByLocalizacaoCidade`, `findByTagsContaining` |

## Pendências e próximos passos

- **Hash de senhas** — senhas estão em plain text. Adicionar BCrypt (`spring-security-crypto`) antes de ir para produção
- **JWT** — implementar autenticação stateless com token para proteger rotas futuras
- **Controllers restantes** — criar endpoints para Praias, CondicoesAmbientais, Avaliacoes, Alertas, Recomendacoes
- **Validação de entrada** — adicionar `@Valid` + Bean Validation nos DTOs
- **Tratamento global de erros** — criar `@ControllerAdvice` para padronizar respostas de erro
- **Paginação** — usar `Pageable` nos endpoints de listagem
