package com.praio.backend.repository;

import com.praio.backend.model.AcaoLog;
import com.praio.backend.model.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LogRepository extends MongoRepository<Log, String> {
    List<Log> findByUsuarioId(String usuarioId);
    List<Log> findByAcao(AcaoLog acao);
}