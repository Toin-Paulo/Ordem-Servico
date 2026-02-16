package br.com.assistencia.os.domain.repository;

import br.com.assistencia.os.domain.model.Comentario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepository extends CrudRepository<Comentario, Long> {
}
