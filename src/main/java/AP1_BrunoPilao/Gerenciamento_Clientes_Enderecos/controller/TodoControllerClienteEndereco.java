package AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.Valid;

import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model.TodoCliente;
import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model.TodoEndereco;
import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.service.ErrorHandlingService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TodoControllerClienteEndereco {
    
    @Autowired
    // Classe para tratamento de Erro
    private ErrorHandlingService errorHandlingService;

    // Listas estáticas para armazenar clientes e endereços
    private static List<TodoCliente> clientes = new ArrayList<>();
    private static List<TodoEndereco> enderecos = new ArrayList<>();

    // --------------------- Métodos para Cliente --------------------- 

    // GET - Retorna todos os clientes
    @GetMapping("/cliente")
    public ResponseEntity<List<TodoCliente>> getTodosClientes() {
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    // GET - Busca de cliente por CPF
    @GetMapping("/cliente/buscar/{cpf}")
    public ResponseEntity<?> buscarClientePorCpf(@PathVariable("cpf") String cpf) {
        for (TodoCliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return new ResponseEntity<>(cliente, HttpStatus.OK);
            }
        }
        return errorHandlingService.createErrorResponse("Cliente com CPF " + cpf + " não encontrado", HttpStatus.NOT_FOUND);
    }

    // POST - Adiciona um novo cliente
    @PostMapping("/cliente")
    public ResponseEntity<?> postCliente(@Valid @RequestBody TodoCliente novoCliente) {
        for (TodoCliente clienteExistente : clientes) {
            if (clienteExistente.getCpf().equals(novoCliente.getCpf())) {
                return errorHandlingService.createErrorResponse("CPF já cadastrado", HttpStatus.BAD_REQUEST);
            }
            if (clienteExistente.getEmail().equals(novoCliente.getEmail())) {
                return errorHandlingService.createErrorResponse("Email já cadastrado", HttpStatus.BAD_REQUEST);
            }
        }
        clientes.add(novoCliente);
        return new ResponseEntity<>(novoCliente, HttpStatus.CREATED);
    }

    // PUT - Atualiza um cliente existente por CPF
    @PutMapping("/cliente/atualizar/{cpf}")
    public ResponseEntity<?> atualizarClientePorCpf(@PathVariable("cpf") String cpf, @Valid @RequestBody TodoCliente clienteAtualizado) {
        for (TodoCliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                clientes.remove(cliente);
                clientes.add(clienteAtualizado);
                return new ResponseEntity<>(clienteAtualizado, HttpStatus.OK);
            }
        }
        return errorHandlingService.createErrorResponse("Cliente com CPF " + cpf + " não encontrado", HttpStatus.NOT_FOUND);
    }

    // DELETE - Remove um cliente por CPF
    @DeleteMapping("/cliente/{cpf}")
    public ResponseEntity<?> deleteCliente(@PathVariable("cpf") String cpf) {
        boolean removed = clientes.removeIf(cliente -> cliente.getCpf().equals(cpf));
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return errorHandlingService.createErrorResponse("Cliente com CPF " + cpf + " não encontrado", HttpStatus.NOT_FOUND);
    }

    // --------------------- Métodos para Endereço --------------------- 

    // POST - Adiciona um novo endereço para um cliente específico
    @PostMapping("/cliente/{cpf}/endereco")
    public ResponseEntity<?> postEndereco(@PathVariable("cpf") String cpf, @Valid @RequestBody TodoEndereco novoEndereco) {
        for (TodoCliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                novoEndereco.setCpfCliente(cpf);
                enderecos.add(novoEndereco);
                return new ResponseEntity<>(novoEndereco, HttpStatus.CREATED);
            }
        }
        return errorHandlingService.createErrorResponse("Cliente com CPF " + cpf + " não encontrado", HttpStatus.NOT_FOUND);
    }

    // GET - Retorna todos os endereços de um cliente específico pelo CPF
    @GetMapping("/cliente/{cpf}/endereco")
    public ResponseEntity<?> getEnderecosPorCliente(@PathVariable("cpf") String cpf) {
        List<TodoEndereco> enderecosDoCliente = new ArrayList<>();
        for (TodoEndereco endereco : enderecos) {
            if (endereco.getCpfCliente().equals(cpf)) {
                enderecosDoCliente.add(endereco);
            }
        }
        if (enderecosDoCliente.isEmpty()) {
            return errorHandlingService.createErrorResponse("Nenhum endereço encontrado para o cliente com CPF " + cpf, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(enderecosDoCliente, HttpStatus.OK);
    }

    // PUT - Atualiza um endereço existente por CEP para um cliente específico
    @PutMapping("/cliente/{cpf}/endereco/{cep}")
    public ResponseEntity<?> updateEndereco(@PathVariable("cpf") String cpf, @PathVariable("cep") String cep, @Valid @RequestBody TodoEndereco enderecoAtualizado) {
        for (TodoCliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                for (TodoEndereco endereco : enderecos) {
                    if (endereco.getCep().equals(cep) && endereco.getCpfCliente().equals(cpf)) {
                        enderecos.remove(endereco);
                        enderecoAtualizado.setCpfCliente(cpf);
                        enderecos.add(enderecoAtualizado);
                        return new ResponseEntity<>(enderecoAtualizado, HttpStatus.OK);
                    }
                }
            }
        }
        return errorHandlingService.createErrorResponse("Endereço com CEP " + cep + " não encontrado para o cliente com CPF " + cpf, HttpStatus.NOT_FOUND);
    }

    // DELETE - Remove um endereço por CEP para um cliente específico
    @DeleteMapping("/cliente/{cpf}/endereco/{cep}")
    public ResponseEntity<?> deleteEndereco(@PathVariable("cpf") String cpf, @PathVariable("cep") String cep) {
        boolean removed = enderecos.removeIf(endereco -> endereco.getCep().equals(cep) && endereco.getCpfCliente().equals(cpf));
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return errorHandlingService.createErrorResponse("Endereço com CEP " + cep + " não encontrado para o cliente com CPF " + cpf, HttpStatus.NOT_FOUND);
    }
}
