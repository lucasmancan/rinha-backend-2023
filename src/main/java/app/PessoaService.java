package app;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PessoaService {

    Pessoa salvar(Pessoa pessoa);

    Optional<Pessoa> buscarPorId(UUID id);

    List<Pessoa> buscarPorTermo(String termo);

    Integer contarPessoas();

}
