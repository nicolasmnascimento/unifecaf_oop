Sistema de Gestão de Veículos

API REST orientada a objetos para controle de estoque de uma concessionária, desenvolvida como projeto acadêmico da disciplina de Programação Orientada a Objetos.

Show Image Show Image Show Image Show Image

Sobre o projeto

O sistema permite cadastrar marcas, modelos e veículos, consultar o estoque com filtros combinados e manter as informações atualizadas. A comunicação é feita por uma API REST em formato JSON, e há uma interface web simples para testar as principais operações.

Autor(a): preencher Disciplina: Programação Orientada a Objetos Instituição: preencher

Funcionalidades
CRUD de marcas.
CRUD de modelos, sempre vinculados a uma marca.
CRUD de veículos, vinculados a um modelo (a marca é obtida pelo modelo).
Filtros combináveis de veículos por marca, modelo, preço mínimo e máximo, ano, status e tipo.
Alteração específica do status do veículo (DISPONIVEL, VENDIDO, DESCONTINUADO).
Validação dos dados recebidos e respostas de erro padronizadas.
Interface web simples em index.html.
Tecnologias
Tecnologia	Uso
Java 21	Linguagem do projeto
Spring Boot 3.5.6	Framework da aplicação e servidor web embutido
Spring Web	Endpoints REST
Spring Data JPA / Hibernate	Mapeamento objeto-relacional e acesso ao banco
Bean Validation	Validação dos dados (@NotNull, @NotBlank, @DecimalMin...)
MySQL 8	Banco de dados definitivo
H2	Banco em memória para testes rápidos (perfil padrão)
Maven	Gerenciamento de dependências e execução
JUnit (Spring Boot Test)	Testes automatizados
Arquitetura

O projeto segue uma arquitetura em camadas, com cada pasta responsável por uma parte do sistema:

Cliente  ->  Controller  ->  Service  ->  Repository  ->  Banco de dados
Camada	Responsabilidade
controller	Recebe as requisições HTTP e devolve JSON
service	Regras de negócio e validações
repository	Acesso ao banco de dados (Spring Data JPA)
model	Entidades do domínio: Marca, Modelo, Veiculo, StatusVeiculo
exception	Exceções próprias e tratamento padronizado de erros
Modelo de dados
Marca (1) ------ (N) Modelo (1) ------ (N) Veiculo

Uma marca possui vários modelos, e um modelo possui vários veículos.

Estrutura de pastas
gestao-veiculos/
├── pom.xml
├── README.md
├── database/
│   └── schema.sql                  # modelo físico do MySQL
├── docs/
│   └── primeiros-testes.http       # requisições prontas para testar a API
└── src/
    ├── main/
    │   ├── java/com/concessionaria/veiculos/
    │   │   ├── GestaoVeiculosApplication.java
    │   │   ├── controller/
    │   │   ├── service/
    │   │   ├── repository/
    │   │   ├── model/
    │   │   └── exception/
    │   └── resources/
    │       ├── application.properties          # perfil padrão (H2)
    │       ├── application-mysql.properties    # perfil MySQL
    │       └── static/index.html               # interface web simples
    └── test/
Pré-requisitos
JDK 21
Maven 3.9+
MySQL 8 (apenas para o perfil MySQL)

Confira a instalação no terminal:

powershell
java -version
javac -version
mvn -version
Como executar

Abra a pasta que contém o arquivo pom.xml no VS Code (ou entre nela pelo terminal).

Execução rápida com H2 (sem instalar banco)
powershell
mvn spring-boot:run

Quando aparecer Started GestaoVeiculosApplication, a aplicação está no ar:

Interface web: http://localhost:8080
API: http://localhost:8080/api/marcas
Console do H2: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:gestaoveiculos
Usuário: sa
Senha: (vazia)

No perfil H2 os dados ficam em memória e são apagados quando a aplicação é encerrada.

Para parar a aplicação, use Ctrl + C no terminal.

Execução com MySQL
Instale e inicie o MySQL.
Execute o script database/schema.sql no seu cliente MySQL.
Defina as variáveis de ambiente no PowerShell:
powershell
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "SUA_SENHA"

Se o endereço do banco for diferente do padrão (localhost:3306/gestao_veiculos), defina também DB_URL.

Inicie a aplicação com o perfil mysql:
powershell
mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=mysql"

Alternativa, usando o JAR empacotado:

powershell
mvn clean package
java -jar target/gestao-veiculos-0.0.1-SNAPSHOT.jar --spring.profiles.active=mysql
Testes automatizados
powershell
mvn test
Endpoints da API
Marcas
Método	Rota	Descrição
GET	/api/marcas	Lista as marcas
GET	/api/marcas/{id}	Busca uma marca
POST	/api/marcas	Cria uma marca
PUT	/api/marcas/{id}	Atualiza uma marca
DELETE	/api/marcas/{id}	Remove uma marca
json
{ "nome": "Toyota", "paisOrigem": "Japão" }
Modelos
Método	Rota	Descrição
GET	/api/modelos	Lista os modelos
GET	/api/modelos?marcaId=1	Lista os modelos de uma marca
GET	/api/modelos/{id}	Busca um modelo
POST	/api/modelos	Cria um modelo
PUT	/api/modelos/{id}	Atualiza um modelo
DELETE	/api/modelos/{id}	Remove um modelo
json
{ "nome": "Corolla", "tipo": "Sedan", "marca": { "id": 1 } }
Veículos
Método	Rota	Descrição
GET	/api/veiculos	Lista os veículos (aceita filtros)
GET	/api/veiculos/{id}	Busca um veículo
POST	/api/veiculos	Cria um veículo
PUT	/api/veiculos/{id}	Atualiza um veículo
PATCH	/api/veiculos/{id}/status?status=VENDIDO	Altera somente o status
DELETE	/api/veiculos/{id}	Remove um veículo
json
{
  "modelo": { "id": 1 },
  "anoFabricacao": 2023,
  "cor": "Prata",
  "preco": 118900.00,
  "quilometragem": 15000,
  "status": "DISPONIVEL"
}

Filtros disponíveis (todos opcionais e combináveis):

Parâmetro	Exemplo
marcaId	marcaId=1
modeloId	modeloId=1
precoMin / precoMax	precoMin=50000&precoMax=120000
ano	ano=2023
status	status=DISPONIVEL
tipo	tipo=sedan

Exemplo completo:

GET /api/veiculos?marcaId=1&precoMax=150000&status=DISPONIVEL

O arquivo docs/primeiros-testes.http contém requisições prontas. No VS Code, instale a extensão REST Client para executá-las com um clique.

Respostas de erro

Os erros seguem um formato único:

json
{
  "timestamp": "2026-10-19T21:50:00Z",
  "status": 404,
  "erro": "Not Found",
  "mensagem": "Veículo não encontrado: 99"
}
Código	Quando acontece
400	Dados inválidos ou ausentes
404	Registro não encontrado
409	Duplicidade ou vínculo existente (por exemplo, remover uma marca que possui modelos)
Observações de uso
Cadastre primeiro a marca, depois o modelo e por último o veículo.
A remoção física falha se houver registros vinculados (modelos de uma marca ou veículos de um modelo), preservando a integridade do banco.
Para registrar uma venda mantendo o histórico, use o endpoint de alteração de status.
A versão atual não possui autenticação nem controle de permissões.
