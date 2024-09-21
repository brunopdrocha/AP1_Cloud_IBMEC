package AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model;

import lombok.Data;

@Data
public class TodoCliente {

    private String nome;
    private String email;
    private int cpf;
    private int dataNascimento;
    private int telefone;
    
}