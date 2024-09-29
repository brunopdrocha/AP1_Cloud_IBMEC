package AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import AP1_BrunoPilao.Gerenciamento_Clientes_Enderecos.model.TodoCliente;

@Repository
public interface TodoClienteRepository extends JpaRepository<TodoCliente, String> {

}
