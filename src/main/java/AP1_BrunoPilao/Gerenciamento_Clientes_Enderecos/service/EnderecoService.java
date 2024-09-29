package AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model.TodoEndereco;
import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.repository.TodoEnderecoRepository;

@Service
public class EnderecoService {

    @Autowired
    private TodoEnderecoRepository clienteRepository; 

    public TodoEndereco saveDetails(TodoEndereco Endereco) {
        return clienteRepository.save(Endereco); 
    }
}
