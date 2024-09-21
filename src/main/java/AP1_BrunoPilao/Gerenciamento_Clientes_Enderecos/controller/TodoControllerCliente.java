package AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model.TodoCliente;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/cliente")

public class TodoControllerCliente {
    private static List<TodoCliente> Clientes = new ArrayList<>();
    
    @GetMapping
    public ResponseEntity<List<TodoCliente>> getTodoCliente() {
        return new ResponseEntity(Clientes,HttpStatus.OK);
    }
    
    @GetMapping("{nome}")
        public ResponseEntity<TodoCliente> getTodoByName(@PathVariable("nome")String nome) {

        TodoCliente response = null;
        for(TodoCliente todo : Clientes){
            if (todo.getNome().equals(nome)) {
                response = todo;
                break;
            }
        if (response == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }    
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity <TodoCliente> postTodoCliente(@RequestBody TodoCliente input){
        Clientes.add(input);
        return new ResponseEntity<>(input,HttpStatus.CREATED);
    }

    
}