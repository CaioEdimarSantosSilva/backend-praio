package com.praio.backend.repository;

import com.praio.backend.model.Avaliacao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AvaliacaoRepository extends MongoRepository<Avaliacao, String> {
    List<Avaliacao> findByPraiaId(String praiaId);
    List<Avaliacao> findByUsuarioId(String usuarioId);
}