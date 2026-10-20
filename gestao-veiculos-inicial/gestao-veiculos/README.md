# Sistema de Gestão de Veículos

API REST acadêmica para cadastro e gestão de estoque de veículos usando Java 21, Spring Boot, Maven, Spring Data JPA e MySQL.

## Funcionalidades iniciais

- CRUD de marcas.
- CRUD de modelos associados a marcas.
- CRUD de veículos associados a modelos.
- Filtros de veículos por marca, modelo, preço mínimo/máximo, ano, status e tipo.
- Alteração específica de status.
- Validação de dados e respostas de erro padronizadas.
- Interface web simples para testar as operações principais.

## Requisitos

- JDK 21.
- Maven (ou Maven Wrapper, se adicionado ao projeto).
- MySQL 8 para execução com persistência definitiva.

## Abrir no VS Code

Abra a pasta raiz `gestao-veiculos` no VS Code.

Confira no terminal:

```powershell
java -version
javac -version
```

O Java precisa ser reconhecido antes de executar o projeto.

## Executar rapidamente com H2

O perfil padrão utiliza um banco H2 em memória, para que seja possível validar a API antes de configurar o MySQL. Os dados desse perfil são apagados quando a aplicação é encerrada.

Na raiz do projeto:

```powershell
mvn spring-boot:run
```

Se Maven não estiver instalado globalmente, será necessário gerar/usar o Maven Wrapper ou instalar o Maven.

Abra `http://localhost:8080` para usar a interface simples ou `http://localhost:8080/h2-console` para abrir o console H2. JDBC URL do H2: `jdbc:h2:mem:gestaoveiculos`; usuário `sa`; senha vazia.

## Executar com MySQL

1. Instale e inicie o MySQL.
2. Execute o script `database/schema.sql` em seu cliente MySQL.
3. Configure a senha do banco no terminal PowerShell:

```powershell
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "SUA_SENHA"
```

Se o endereço for diferente do padrão, defina também `DB_URL`.

4. Inicie a aplicação com o perfil MySQL:

```powershell
mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=mysql"
```

Alternativa ao comando Maven acima, usando o JAR já empacotado:

```powershell
mvn clean package
java -jar target/gestao-veiculos-0.0.1-SNAPSHOT.jar --spring.profiles.active=mysql
```

O perfil MySQL usa `application-mysql.properties`. O Hibernate está configurado com `ddl-auto=update` para facilitar o desenvolvimento; o script SQL documenta o modelo físico esperado.

## Endpoints

### Marcas

- `GET /api/marcas`
- `GET /api/marcas/{id}`
- `POST /api/marcas`
- `PUT /api/marcas/{id}`
- `DELETE /api/marcas/{id}`

Exemplo de criação:

```json
{
  "nome": "Toyota",
  "paisOrigem": "Japão"
}
```

### Modelos

- `GET /api/modelos`
- `GET /api/modelos?marcaId=1`
- `GET /api/modelos/{id}`
- `POST /api/modelos`
- `PUT /api/modelos/{id}`
- `DELETE /api/modelos/{id}`

Exemplo de criação:

```json
{
  "nome": "Corolla",
  "tipo": "Sedan",
  "marca": { "id": 1 }
}
```

### Veículos

- `GET /api/veiculos`
- `GET /api/veiculos/{id}`
- `POST /api/veiculos`
- `PUT /api/veiculos/{id}`
- `PATCH /api/veiculos/{id}/status?status=VENDIDO`
- `DELETE /api/veiculos/{id}`

Filtros combináveis:

`GET /api/veiculos?marcaId=1&modeloId=1&precoMin=50000&precoMax=120000&ano=2022&status=DISPONIVEL&tipo=sedan`

Exemplo de criação:

```json
{
  "modelo": { "id": 1 },
  "anoFabricacao": 2023,
  "cor": "Prata",
  "preco": 118900.00,
  "quilometragem": 15000,
  "status": "DISPONIVEL"
}
```

Status permitidos: `DISPONIVEL`, `VENDIDO`, `DESCONTINUADO`.

## Observações

- Para cadastrar um veículo, cadastre primeiro uma marca e depois um modelo.
- A exclusão física pode falhar se existirem modelos vinculados à marca ou veículos vinculados ao modelo. Isso preserva a integridade referencial.
- Para registrar uma venda sem apagar o histórico, use o endpoint de atualização de status.
- A interface é uma demonstração inicial; a API REST é o componente principal do trabalho.
- Antes da entrega, execute os testes, capture evidências reais e publique o código em um repositório GitHub público.
