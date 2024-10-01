package AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model.TodoCliente;
import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model.TodoEndereco;
import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.service.ErrorHandlingService;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

@AutoConfigureMockMvc
@WebMvcTest(controllers = TodoControllerClienteEndereco.class)
public class TodoControllerClienteEnderecoTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private List<TodoCliente> clientes = new ArrayList<>();
    private List<TodoEndereco> enderecos = new ArrayList<>();

    private ErrorHandlingService errorHandlingService = mock(ErrorHandlingService.class);

    @BeforeEach
    public void setup() {
        // Adicionando um cliente à lista
        TodoCliente cliente = new TodoCliente();
        cliente.setCpf("12345678901");
        cliente.setEmail("cliente@example.com");
        clientes.add(cliente);
        
        // Configurando o mock do serviço de tratamento de erros
        when(errorHandlingService.createErrorResponse(anyString(), any())).thenReturn(null);
    }

    // ------------------------- Testes para Cliente -------------------------
    
    @Test
    public void should_create_cliente() throws Exception {
        TodoCliente novoCliente = new TodoCliente();
        novoCliente.setCpf("792.276.900-85");
        novoCliente.setEmail("novo@cliente.com");

        this.mvc.perform(MockMvcRequestBuilders
                .post("/api/cliente")
                .content(this.mapper.writeValueAsString(novoCliente))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf", is("792.276.900-85")));
    }

    @Test
    public void should_return_cliente_by_cpf() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                .get("/api/cliente/buscar/792.276.900-85")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf", is("792.276.900-85")));
    }

    @Test
    public void should_update_cliente() throws Exception {
        TodoCliente clienteAtualizado = new TodoCliente();
        clienteAtualizado.setCpf("792.276.900-85");
        clienteAtualizado.setEmail("atualizado@cliente.com");

        this.mvc.perform(MockMvcRequestBuilders
                .put("/api/cliente/atualizar/792.276.900-85")
                .content(this.mapper.writeValueAsString(clienteAtualizado))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is("atualizado@cliente.com")));
    }

    @Test
    public void should_delete_cliente() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                .delete("/api/cliente/792.276.900-85")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void should_return_not_found_when_cliente_does_not_exist() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                .get("/api/cliente/buscar/792.276.900-85")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // ------------------------- Testes para Endereço -------------------------
    
    @Test
    public void should_create_endereco() throws Exception {
        TodoEndereco novoEndereco = new TodoEndereco();
        novoEndereco.setCep("12345-678");

        this.mvc.perform(MockMvcRequestBuilders
                .post("/api/cliente/792.276.900-85/endereco")
                .content(this.mapper.writeValueAsString(novoEndereco))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cep", is("12345-678")));
    }

    @Test
    public void should_return_enderecos_by_cliente_cpf() throws Exception {
        TodoEndereco novoEndereco = new TodoEndereco();
        novoEndereco.setCep("12345-678");
        this.enderecos.add(novoEndereco); // Adicionando endereço manualmente para o teste

        this.mvc.perform(MockMvcRequestBuilders
                .get("/api/cliente/792.276.900-85/endereco")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cep", is("12345-678")));
    }

    @Test
    public void should_update_endereco() throws Exception {
        TodoEndereco novoEndereco = new TodoEndereco();
        novoEndereco.setCep("12345-678");
        this.enderecos.add(novoEndereco); // Adicionando endereço manualmente para o teste

        TodoEndereco enderecoAtualizado = new TodoEndereco();
        enderecoAtualizado.setCep("87654-321");

        this.mvc.perform(MockMvcRequestBuilders
                .put("/api/cliente/792.276.900-85/endereco/12345-678")
                .content(this.mapper.writeValueAsString(enderecoAtualizado))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cep", is("87654-321")));
    }

    @Test
    public void should_delete_endereco() throws Exception {
        TodoEndereco novoEndereco = new TodoEndereco();
        novoEndereco.setCep("87654-321");
        this.enderecos.add(novoEndereco); // Adicionando endereço manualmente para o teste

        this.mvc.perform(MockMvcRequestBuilders
                .delete("/api/cliente/792.276.900-85/endereco/87654-321")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void should_return_not_found_when_endereco_not_exist() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                .get("/api/cliente/792.276.900-85/endereco/00000-000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
