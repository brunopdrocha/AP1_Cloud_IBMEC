# API de Gerenciamento de Clientes e Endereços

Esta API permite o gerenciamento de clientes e seus endereços, oferecendo funcionalidades de criação, atualização, listagem e remoção.

## Estrutura da API

A API está estruturada em dois principais recursos: **Clientes** e **Endereços**. Todos os endpoints estão sob o prefixo `http://localhost:8080/api`.

## Endpoints

### Clientes

### 1. Listar Todos os Clientes
- **Método**: `GET`
- **Endpoint**: `/api/cliente`
- **Descrição**: Retorna uma lista de todos os clientes.

### 2. Buscar Cliente por CPF
- **Método**: `GET`
- **Endpoint**: `/api/cliente/{cpf}`
- **Descrição**: Busca um cliente específico pelo CPF.
- **Resposta**: 
  - **200 OK**: Retorna o cliente encontrado.
  - **404 NOT FOUND**: Se o cliente não for encontrado.

### 3. Adicionar Novo Cliente
- **Método**: `POST`
- **Endpoint**: `/api/cliente`
- **Descrição**: Adiciona um novo cliente.
- **Body**:
  ```json
  {
    "nome": "Nome do Cliente",
    "email": "email@exemplo.com",
    "cpf": "123.456.789-00",
    "dataNascimento": "1990-01-01",
    "telefone": "21999999999"
  }
  
Resposta:
201 CREATED: Retorna o cliente criado.
400 BAD REQUEST: Se CPF ou e-mail já existirem.

### 4. Atualizar Cliente
Método: PUT
Endpoint: /api/cliente/{cpf}
Descrição: Atualiza os dados de um cliente existente pelo CPF.
Body: (mesmo formato do POST)
Respostas:
200 OK: Retorna o cliente atualizado.
404 NOT FOUND: Se o cliente não for encontrado.

### 5. Remover Cliente
- **Método**: DELETE
- **Endpoint**: /api/cliente/{cpf}
- **Descrição**: Remove um cliente pelo CPF.
  
Respostas:
204 NO CONTENT: Se removido com sucesso.
404 NOT FOUND: Se o cliente não existir.

### Endereços
### 1. Listar Todos os Endereços
- **Método**: GET
- **Endpoint**: /api/endereco
- **Descrição**: Retorna uma lista de todos os endereços.
  
### 2. Buscar Endereço por CEP
- **Método**: GET
- **Endpoint**: /api/endereco/{cep}
- **Descrição**: Busca um endereço específico pelo CEP.

Respostas:
200 OK: Retorna o endereço encontrado.
404 NOT FOUND: Se o endereço não for encontrado.

### 3. Adicionar Novo Endereço
- **Método**: POST
- **Endpoint**: /api/endereco
- **Descrição**: Adiciona um novo endereço.
- **Body**:
  ```json
  {
    "rua": "Rua Exemplo",
    "numero": "123",
    "bairro": "Bairro Exemplo",
    "cidade": "Cidade Exemplo",
    "estado": "SP",
    "cep": "12345-678"
  }

Resposta:
201 CREATED: Retorna o endereço criado.

### 4. Atualizar Endereço
- **Método**: PUT
- **Endpoint**: /api/endereco/{cep}
- **Descrição**: Atualiza um endereço existente pelo CEP.
- **Body**: (mesmo formato do POST)
  
Respostas:
200 OK: Retorna o endereço atualizado.
404 NOT FOUND: Se o endereço não for encontrado.

### 5. Remover Endereço
- **Método**: DELETE
- **Endpoint**: /api/endereco/{cep}
- **Descrição**: Remove um endereço pelo CEP.
 
Respostas:
204 NO CONTENT: Se removido com sucesso.
404 NOT FOUND: Se o endereço não existir
