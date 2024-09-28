package AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.Valid;

import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model.TodoCliente;
import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model.TodoEndereco;

@RestController
@RequestMapping("/api")
public class TodoControllerClienteEndereco {

    // Listas estáticas para armazenar clientes e endereços
    @Autowired
    private static List<TodoCliente> clientes = new ArrayList<>();
    private static List<TodoEndereco> enderecos = new ArrayList<>();

    // --------------------- Métodos para Cliente ---------------------

    // GET - Retorna todos os clientes
    @GetMapping("/cliente")
    public ResponseEntity<List<TodoCliente>> getTodosClientes() {
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    // GET - Busca de cliente por CPF
    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<TodoCliente> getClienteByCpf(@PathVariable("cpf") String cpf) {
        TodoCliente response = null;
        for (TodoCliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                response = cliente;
                break;
            }
        }

        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // POST - Adiciona um novo cliente
    @PostMapping("/cliente")
    public ResponseEntity<TodoCliente> postCliente(@Valid @RequestBody TodoCliente novoCliente) {
    // Loop para verificar se já existe um cliente com o mesmo CPF ou e-mail
    for (TodoCliente clienteExistente : clientes) {
        //CPF
        if (clienteExistente.getCpf().equals(novoCliente.getCpf())) {
            return new ResponseEntity<>(novoCliente, HttpStatus.BAD_REQUEST);
        }
        //Email
        if (clienteExistente.getEmail().equals(novoCliente.getEmail())) {
            return new ResponseEntity<>(novoCliente, HttpStatus.BAD_REQUEST);
        }
    }
        clientes.add(novoCliente);
        return new ResponseEntity<>(novoCliente, HttpStatus.CREATED);
    }

    // PUT - Atualiza um cliente existente por CPF
    @PutMapping("/cliente/{cpf}")
    public ResponseEntity<TodoCliente> updateCliente(@PathVariable("cpf") String cpf, @Valid @RequestBody TodoCliente clienteAtualizado) {
        for (TodoCliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                clientes.remove(cliente);
                clientes.add(clienteAtualizado);
                return new ResponseEntity<>(clienteAtualizado, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // DELETE - Remove um cliente por CPF
    @DeleteMapping("/cliente/{cpf}")
    public ResponseEntity<Void> deleteCliente(@PathVariable("cpf") String cpf) {
        boolean removed = clientes.removeIf(cliente -> cliente.getCpf().equals(cpf));
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // --------------------- Métodos para Endereço ---------------------
 // POST - Adiciona um novo endereço para um cliente específico
 @PostMapping("/cliente/{cpf}/endereco")
 public ResponseEntity<TodoEndereco> postEndereco(@PathVariable("cpf") String cpf, @Valid @RequestBody TodoEndereco novoEndereco) {
     TodoCliente cliente = null;
     
     // Verifica se o cliente existe pelo CPF
     for (TodoCliente c : clientes) {
         if (c.getCpf().equals(cpf)) {
             cliente = c;
             break;
         }
     }

     if (cliente == null) {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Cliente não encontrado
     }

     // Associa o CPF ao endereço
     novoEndereco.setCpfCliente(cpf); 
     enderecos.add(novoEndereco);

     return new ResponseEntity<>(novoEndereco, HttpStatus.CREATED);
 }

 // GET - Retorna todos os endereços de um cliente específico pelo CPF
 @GetMapping("/cliente/{cpf}/enderecos")
 public ResponseEntity<List<TodoEndereco>> getEnderecosPorCliente(@PathVariable("cpf") String cpf) {
     List<TodoEndereco> enderecosDoCliente = new ArrayList<>();

     // Filtra endereços pelo CPF do cliente
     for (TodoEndereco endereco : enderecos) {
         if (endereco.getCpfCliente().equals(cpf)) {
             enderecosDoCliente.add(endereco);
         }
     }

     if (enderecosDoCliente.isEmpty()) {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }

     return new ResponseEntity<>(enderecosDoCliente, HttpStatus.OK);
 }

 // PUT - Atualiza um endereço existente por CEP para um cliente específico
 @PutMapping("/cliente/{cpf}/endereco/{cep}")
 public ResponseEntity<TodoEndereco> updateEndereco(@PathVariable("cpf") String cpf, @PathVariable("cep") String cep, @Valid @RequestBody TodoEndereco enderecoAtualizado) {
     TodoCliente cliente = null;

     // Verifica se o cliente existe pelo CPF
     for (TodoCliente c : clientes) {
         if (c.getCpf().equals(cpf)) {
             cliente = c;
             break;
         }
     }

     if (cliente == null) {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Cliente não encontrado
     }

     for (TodoEndereco endereco : enderecos) {
         if (endereco.getCep().equals(cep) && endereco.getCpfCliente().equals(cpf)) {
             enderecos.remove(endereco);
             enderecoAtualizado.setCpfCliente(cpf);
             enderecos.add(enderecoAtualizado);
             return new ResponseEntity<>(enderecoAtualizado, HttpStatus.OK);
         }
     }

     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
 }

 // DELETE - Remove um endereço por CEP para um cliente específico
 @DeleteMapping("/cliente/{cpf}/endereco/{cep}")
 public ResponseEntity<Void> deleteEndereco(@PathVariable("cpf") String cpf, @PathVariable("cep") String cep) {
     boolean removed = enderecos.removeIf(endereco -> endereco.getCep().equals(cep) && endereco.getCpfCliente().equals(cpf));
     if (removed) {
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }
     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
 }

}
