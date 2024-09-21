package AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;



@Data
public class TodoCliente {

    // Nome - obrigatório, entre 3 e 100 caracteres
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    // Email - obrigatório, formato válido e único
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    // CPF - obrigatório, deve seguir o padrão de CPF e ser único
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato XXX.XXX.XXX-XX")
    private String cpf;

    // Data de nascimento - deve ser uma data válida e garantir maioridade (18 anos)
    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado no Formato YYYY-MM-DD")
    private LocalDate dataNascimento;

    // Telefone - opcional, mas se fornecido, deve seguir o padrão
    @Pattern(regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", message = "Telefone deve estar no formato (XX) XXXXX-XXXX")
    private String telefone;
}
