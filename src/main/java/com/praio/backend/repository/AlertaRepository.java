package com.praio.backend.repository;

import com.praio.backend.model.Alerta;
import com.praio.backend.model.TipoAlerta;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AlertaRepository extends MongoRepository<Alerta, String> {
    List<Alerta> findByPraiaId(String praiaId);
    List<Alerta> findByTipo(TipoAlerta tipo);
}