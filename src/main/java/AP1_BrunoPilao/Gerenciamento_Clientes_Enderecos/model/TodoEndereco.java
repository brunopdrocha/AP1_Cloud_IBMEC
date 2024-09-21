
package AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model;

import lombok.Data;

@Data
public class TodoEndereco {

    private String rua;
    private int numero;
    private String bairro;
    private String cidade;
    private String estado;
    private int cep;
    
}