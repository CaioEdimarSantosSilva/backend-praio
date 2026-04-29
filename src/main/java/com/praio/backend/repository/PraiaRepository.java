package com.praio.backend.repository;

import com.praio.backend.model.Praia;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PraiaRepository extends MongoRepository<Praia, String> {
    List<Praia> findByLocalizacaoEstado(String estado);
    List<Praia> findByLocalizacaoCidade(String cidade);
    List<Praia> findByTagsContaining(String tag);
}