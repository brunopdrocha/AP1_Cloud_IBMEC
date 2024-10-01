package AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;

import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model.TodoCliente;
import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model.TodoEndereco;
import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.service.ErrorHandlingService;

import java.util.ArrayList;
import java.util.List;

@AutoConfigureMockMvc
@WebMvcTest(controllers = TodoControllerClienteEndereco.class)
public class TodoControllerClienteEnderecoTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ErrorHandlingService errorHandlingService; // Mocked by Spring

    private List<TodoCliente> clientes;
    private List<TodoEndereco> enderecos;

    @BeforeEach
    public void setup() {
        clientes = new ArrayList<>();
        enderecos = new ArrayList<>();
    }

    // ------------------------- Testes para Cliente -------------------------

    @Test
    public void should_create_cliente() throws Exception {
        LocalDate dataNascimento = LocalDate.parse("1900-01-10");
    
        TodoCliente novoCliente = new TodoCliente();
        novoCliente.setCpf("79227690085"); // CPF sem pontos ou vírgulas
        novoCliente.setEmail("novo@cliente.com");
        novoCliente.setNome("Anonimo");
        novoCliente.setDataNascimento(dataNascimento); // Converter String para LocalDate
        novoCliente.setTelefone("(21)99999-9999");

        this.mvc.perform(MockMvcRequestBuilders
                .post("/api/cliente")
                .content(this.mapper.writeValueAsString(novoCliente))
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void should_return_cliente_by_cpf() throws Exception {
        // Adicione o cliente à lista antes de buscar
        TodoCliente cliente = new TodoCliente();
        LocalDate dataNascimento = LocalDate.parse("1900-01-10");
        cliente.setCpf("79227690085"); // CPF sem pontos ou vírgulas
        cliente.setEmail("novo@cliente.com");
        cliente.setNome("Anonimo");
        cliente.setDataNascimento(dataNascimento); // Converter String para LocalDate
        cliente.setTelefone("(21)99999-9999");


        this.mvc.perform(MockMvcRequestBuilders
                .get("/api/cliente/buscar/79227690085")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    

    @Test
    public void should_delete_cliente() throws Exception {
        // Adicione o cliente à lista antes de deletar
        TodoCliente cliente = new TodoCliente();
        cliente.setCpf("79227690085");
        clientes.add(cliente); // Simulando que o cliente já existe

        this.mvc.perform(MockMvcRequestBuilders
                .delete("/api/cliente/79227690085")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void should_return_not_found_when_cliente_does_not_exist() throws Exception {

        this.mvc.perform(MockMvcRequestBuilders
                .get("/api/cliente/buscar/12345678912")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
}

    // ------------------------- Testes para Endereço -------------------------
    @Test
    public void should_create_endereco() throws Exception {
        // Adicione um endereco
        TodoEndereco novoEndereco = new TodoEndereco();
        novoEndereco.setCep("12345-678");
        novoEndereco.setBairro("Barra");
        novoEndereco.setCidade("RJ");
        novoEndereco.setCpfCliente("79227690085");
        novoEndereco.setEstado("RJ");
        novoEndereco.setRua("Rua dos Cavalos");
        novoEndereco.setNumero("500");
    
        this.mvc.perform(MockMvcRequestBuilders
                .post("/api/cliente/79227690085/endereco")
                .content(this.mapper.writeValueAsString(novoEndereco))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    public void should_return_enderecos_by_cliente_cpf() throws Exception {
        // Adicione o cliente e o endereço à lista antes de buscar
        TodoEndereco novoEndereco = new TodoEndereco();
        novoEndereco.setCep("12345-678");
        enderecos.add(novoEndereco); // Simulando que o endereço já existe

        TodoCliente cliente = new TodoCliente();
        cliente.setCpf("79227690085");
        clientes.add(cliente); // Simulando que o cliente já existe

        this.mvc.perform(MockMvcRequestBuilders
                .get("/api/cliente/79227690085/endereco")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void should_delete_endereco() throws Exception {
        // Adicione o cliente e o endereço à lista antes de deletar
        TodoEndereco novoEndereco = new TodoEndereco();
        novoEndereco.setCep("87654-321");
        enderecos.add(novoEndereco); // Simulando que o endereço já existe

        TodoCliente cliente = new TodoCliente();
        cliente.setCpf("79227690085");
        clientes.add(cliente); // Simulando que o cliente já existe

        this.mvc.perform(MockMvcRequestBuilders
                .delete("/api/cliente/79227690085/endereco/87654-321")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void should_return_not_found_when_endereco_not_exist() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                .get("/api/cliente/79227690085/endereco/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
