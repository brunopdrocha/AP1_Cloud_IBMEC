package AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TodoEndereco {

    // Rua - obrigatório, entre 3 e 255 caracteres
    @NotBlank(message = "Rua é obrigatória")
    @Size(min = 3, max = 255, message = "Rua deve ter entre 3 e 255 caracteres")
    private String rua;

    // Número - obrigatório, pode ser numérico ou alfanumérico
    @NotBlank(message = "Número é obrigatório")
    @Pattern(regexp = "\\d+[A-Za-z]?", message = "Número deve ser numérico ou alfanumérico (ex.: '123', '45A')")
    private String numero;

    // Bairro - obrigatório, entre 3 e 100 caracteres
    @NotBlank(message = "Bairro é obrigatório")
    @Size(min = 3, max = 100, message = "Bairro deve ter entre 3 e 100 caracteres")
    private String bairro;

    // Cidade - obrigatório, entre 2 e 100 caracteres
    @NotBlank(message = "Cidade é obrigatória")
    @Size(min = 2, max = 100, message = "Cidade deve ter entre 2 e 100 caracteres")
    private String cidade;

    // Estado - obrigatório, deve ser um dos estados válidos do Brasil
    @NotBlank(message = "Estado é obrigatório")
    @Pattern(regexp = "AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO",
             message = "Estado deve ser um estado válido do Brasil (ex.: SP, RJ, MG)")
    private String estado;

    // CEP - obrigatório, deve seguir o formato XXXXX-XXX
    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve estar no formato XXXXX-XXX")
    private String cep;

    // CPF do Cliente associado ao endereço (opcional no momento da criação)
    private String cpfCliente;
}
