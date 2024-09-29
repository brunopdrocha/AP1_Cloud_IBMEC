package AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Endereco")
@NoArgsConstructor
@AllArgsConstructor
public class TodoEndereco {

    // CPF do Cliente associado ao endereço (opcional no momento da criação)
    @Id
    @Column(name = "cpf_cliente", nullable = false)
    private String cpfCliente;

    // Rua - obrigatório, entre 3 e 255 caracteres
    @Column(name = "rua", nullable = false)
    @NotBlank(message = "Rua é obrigatória")
    @Size(min = 3, max = 255, message = "Rua deve ter entre 3 e 255 caracteres")
    private String rua;

    // Número - obrigatório, pode ser numérico ou alfanumérico
    @Column(name = "numero", nullable = false)
    @NotBlank(message = "Número é obrigatório")
    @Pattern(regexp = "\\d+[A-Za-z]?", message = "Número deve ser numérico ou alfanumérico (ex.: '123', '45A')")
    private String numero;

    // Bairro - obrigatório, entre 3 e 100 caracteres
    @Column(name = "bairro", nullable = false)
    @NotBlank(message = "Bairro é obrigatório")
    @Size(min = 3, max = 100, message = "Bairro deve ter entre 3 e 100 caracteres")
    private String bairro;

    // Cidade - obrigatório, entre 2 e 100 caracteres
    @Column(name = "cidade", nullable = false)
    @NotBlank(message = "Cidade é obrigatória")
    @Size(min = 2, max = 100, message = "Cidade deve ter entre 2 e 100 caracteres")
    private String cidade;

    // Estado - obrigatório, deve ser um dos estados válidos do Brasil
    @Column(name = "estado", nullable = false)
    @NotBlank(message = "Estado é obrigatório")
    @Pattern(regexp = "AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO",
             message = "Estado deve ser um estado válido do Brasil (ex.: SP, RJ, MG)")
    private String estado;

    // CEP - obrigatório, deve seguir o formato XXXXX-XXX
    @Column(name = "cep", nullable = false)
    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve estar no formato XXXXX-XXX")
    private String cep;

}
