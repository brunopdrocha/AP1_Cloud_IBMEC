package AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model.TodoEndereco;

@Repository
public interface TodoEnderecoRepository extends JpaRepository<TodoEndereco, String> {

}
