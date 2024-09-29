package AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model.TodoCliente;
import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.repository.TodoClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private TodoClienteRepository clienteRepository; 

    public TodoCliente saveDetails(TodoCliente cliente) {
        return clienteRepository.save(cliente); 
    }
}
