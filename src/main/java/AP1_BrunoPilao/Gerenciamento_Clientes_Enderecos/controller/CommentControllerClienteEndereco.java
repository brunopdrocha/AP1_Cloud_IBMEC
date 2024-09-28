package AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model.TodoCliente;
import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model.TodoEndereco;

@RestController
@RequestMapping("/cliente/{cpf}/endereco")
public class CommentControllerClienteEndereco {

    // Lista estática para armazenar os endereços (Comentário equivalente a "Comment")
    private static List<TodoEndereco> enderecos = new ArrayList<>();
    private static List<TodoCliente> clientes = new ArrayList<>();

    // GET - Retorna todos os endereços de um cliente específico
    @GetMapping
    public ResponseEntity<List<TodoEndereco>> getAll(@PathVariable("cpf") String cpf) {
        Optional<TodoCliente> clienteOpt = clientes.stream()
                                                   .filter(cliente -> cliente.getCpf().equals(cpf))
                                                   .findFirst();

        if (clienteOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<TodoEndereco> enderecosDoCliente = new ArrayList<>();
        for (TodoEndereco endereco : enderecos) {
            if (endereco.getCpfCliente().equals(cpf)) {
                enderecosDoCliente.add(endereco);
            }
        }

        return new ResponseEntity<>(enderecosDoCliente, HttpStatus.OK);
    }

    // GET - Busca um endereço específico de um cliente por CEP
    @GetMapping("{cep}")
    public ResponseEntity<TodoEndereco> getById(@PathVariable("cpf") String cpf, @PathVariable("cep") String cep) {
        Optional<TodoEndereco> enderecoOpt = enderecos.stream()
                                                      .filter(endereco -> endereco.getCep().equals(cep) && endereco.getCpfCliente().equals(cpf))
                                                      .findFirst();

        return enderecoOpt.map(endereco -> new ResponseEntity<>(endereco, HttpStatus.OK))
                          .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST - Adiciona um novo endereço para um cliente específico
    @PostMapping
    public ResponseEntity<TodoEndereco> create(@PathVariable("cpf") String cpf, @Valid @RequestBody TodoEndereco novoEndereco) {
        Optional<TodoCliente> clienteOpt = clientes.stream()
                                                   .filter(cliente -> cliente.getCpf().equals(cpf))
                                                   .findFirst();

        if (clienteOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        novoEndereco.setCpfCliente(cpf);
        enderecos.add(novoEndereco);

        return new ResponseEntity<>(novoEndereco, HttpStatus.CREATED);
    }

    // DELETE - Remove um endereço específico de um cliente por CEP
    @DeleteMapping("{cep}")
    public ResponseEntity<Void> delete(@PathVariable("cpf") String cpf, @PathVariable("cep") String cep) {
        Optional<TodoEndereco> enderecoOpt = enderecos.stream()
                                                      .filter(endereco -> endereco.getCep().equals(cep) && endereco.getCpfCliente().equals(cpf))
                                                      .findFirst();

        if (enderecoOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        enderecos.remove(enderecoOpt.get());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
