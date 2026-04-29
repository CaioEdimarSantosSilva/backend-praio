package com.praio.backend.repository;

import com.praio.backend.model.CondicoesAmbientais;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CondicoesAmbientaisRepository extends MongoRepository<CondicoesAmbientais, String> {
    List<CondicoesAmbientais> findByPraiaId(String praiaId);
}