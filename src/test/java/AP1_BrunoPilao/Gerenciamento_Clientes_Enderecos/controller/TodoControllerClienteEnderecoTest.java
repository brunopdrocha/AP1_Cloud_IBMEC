package AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model.TodoCliente;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest(TodoControllerClienteEndereco.class)
public class TodoControllerClienteEnderecoTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    // Uma lista estática para armazenar os clientes
    private List<TodoCliente> clientes;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        clientes = new ArrayList<>(); // Inicializa a lista de clientes
    }

    @Test
    public void testGetTodosClientes() throws Exception {
        mockMvc.perform(get("/api/cliente"))
               .andExpect(status().isOk())
               .andExpect(content().json("[]")); // Espera uma lista vazia
    }

    @Test
    public void testPostCliente_Success() throws Exception {
        String novoClienteJson = "{\"nome\": \"John Doe\", \"cpf\": \"79227690085\", \"email\": \"john.doe@example.com\"}";

        mockMvc.perform(post("/api/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(novoClienteJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPostCliente_CpfJaCadastrado() throws Exception {
        String clienteExistenteJson = "{\"nome\": \"Jane Doe\", \"cpf\": \"79227690085\", \"email\": \"jane.doe@example.com\"}";

        // Adiciona o cliente existente diretamente à lista
        clientes.add(objectMapper.readValue(clienteExistenteJson, TodoCliente.class));

        String novoClienteJson = "{\"nome\": \"John Smith\", \"cpf\": \"79227690085\", \"email\": \"john.smith@example.com\"}";

        mockMvc.perform(post("/api/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(novoClienteJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("CPF já cadastrado"));
    }

    @Test
    public void testPutCliente_Success() throws Exception {
        String clienteJson = "{\"nome\": \"Alice\", \"cpf\": \"79227690085\", \"email\": \"alice@example.com\"}";

        // Adiciona o cliente
        clientes.add(objectMapper.readValue(clienteJson, TodoCliente.class));

        // Atualiza o cliente
        String clienteAtualizadoJson = "{\"nome\": \"Alice Updated\", \"cpf\": \"79227690085\", \"email\": \"alice.updated@example.com\"}";

        mockMvc.perform(put("/api/cliente/atualizar/792.276.900-85")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteAtualizadoJson))
                .andExpect(status().isOk())
                .andExpect(content().json(clienteAtualizadoJson));
    }

    @Test
    public void testPutCliente_ClienteNaoEncontrado() throws Exception {
        String clienteAtualizadoJson = "{\"nome\": \"Non Existent\", \"cpf\": \"792.276.900-85\", \"email\": \"nonexistent@example.com\"}";

        mockMvc.perform(put("/api/cliente/atualizar/792.276.900-85")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteAtualizadoJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cliente com CPF 792.276.900-85 não encontrado"));
    }

    @Test
    public void testDeleteCliente_Success() throws Exception {
        String clienteJson = "{\"nome\": \"Bob\", \"cpf\": \"792.276.900-85\", \"email\": \"bob@example.com\"}";

        // Adiciona o cliente
        clientes.add(objectMapper.readValue(clienteJson, TodoCliente.class));

        // Remove o cliente
        mockMvc.perform(delete("/api/cliente/792.276.900-85"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteCliente_ClienteNaoEncontrado() throws Exception {
        mockMvc.perform(delete("/api/cliente/792.276.900-85"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cliente com CPF 792.276.900-85 não encontrado"));
    }

    @Test
    public void testBuscarClientePorCpf_Success() throws Exception {
        String clienteJson = "{\"nome\": \"Charlie\", \"cpf\": \"792.276.900-85\", \"email\": \"charlie@example.com\"}";

        // Adiciona o cliente
        clientes.add(objectMapper.readValue(clienteJson, TodoCliente.class));

        // Busca o cliente
        mockMvc.perform(get("/api/cliente/buscar/792.276.900-85"))
                .andExpect(status().isOk())
                .andExpect(content().json(clienteJson));
    }

    @Test
    public void testBuscarClientePorCpf_ClienteNaoEncontrado() throws Exception {
        mockMvc.perform(get("/api/cliente/buscar/792.276.900-85"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cliente com CPF 792.276.900-85 não encontrado"));
    }
}
