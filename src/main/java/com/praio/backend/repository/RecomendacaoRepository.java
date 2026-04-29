package com.praio.backend.repository;

import com.praio.backend.model.Recomendacao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RecomendacaoRepository extends MongoRepository<Recomendacao, String> {
    Optional<Recomendacao> findByUsuarioId(String usuarioId);
}