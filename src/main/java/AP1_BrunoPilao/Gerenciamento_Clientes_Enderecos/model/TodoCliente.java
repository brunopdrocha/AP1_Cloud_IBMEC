package AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import org.hibernate.validator.constraints.br.CPF;

//------------------Bibliotecas-----------------------------//
@Entity
@Data
@Table(name = "Cliente")
@NoArgsConstructor
@AllArgsConstructor
public class TodoCliente {

    // CPF - obrigatório, deve seguir o padrão de CPF e ser único
    @Id
    @Column(name = "cpf", unique = true) // Garantir que o CPF seja único
    @NotBlank(message = "CPF é obrigatório")
    @CPF(message = "CPF deve estar no formato XXX.XXX.XXX-XX")
    private String cpf;

    // Nome - obrigatório, entre 3 e 100 caracteres
    @Column(name = "nome")
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    // Email - obrigatório, formato válido e único
    @Column(name = "email", unique = true) // Garantir que o e-mail seja único
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    // Data de nascimento - deve ser uma data válida e garantir maioridade (18 anos)
    @Column(name = "data_nascimento")
    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate dataNascimento;

    // Telefone - opcional, mas se fornecido, deve seguir o padrão
    @Pattern(regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", message = "Telefone deve estar no formato (XX) XXXXX-XXXX")
    @Column(name = "telefone")
    private String telefone;
}
