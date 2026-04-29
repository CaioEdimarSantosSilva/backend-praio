package com.praio.backend;

import com.praio.backend.model.*;
import com.praio.backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(
			UsuarioRepository usuarioRepository,
			PraiaRepository praiaRepository,
			CondicoesAmbientaisRepository condicoesRepo,
			AvaliacaoRepository avaliacaoRepo,
			RecomendacaoRepository recomendacaoRepo,
			AlertaRepository alertaRepo,
			LogRepository logRepo
	) {
		return args -> {

			// Busca IDs reais do banco
			String usuarioId = usuarioRepository.findAll().get(0).getId();
			String praiaId = praiaRepository.findAll().get(0).getId();

			System.out.println("📌 Usando usuarioId: " + usuarioId);
			System.out.println("📌 Usando praiaId: " + praiaId);

			// ✅ Condições Ambientais
			CondicoesAmbientais condicoes = new CondicoesAmbientais();
			condicoes.setPraiaId(praiaId);
			condicoes.setData(new Date());
			condicoes.setQualidadeAgua(QualidadeAgua.BOA);
			condicoes.setTemperaturaAgua(25.0);
			condicoes.setClima("ensolarado");
			condicoes.setVento("moderado");
			condicoes.setOndas("baixas");
			condicoes.setLotacao(Lotacao.MEDIA);
			condicoes.setIndiceUV(8);
			condicoes.setAlerta(TipoAlertaAmbiental.NENHUM);
			condicoesRepo.save(condicoes);
			System.out.println("✅ CondicoesAmbientais salva: " + condicoes);

			// ✅ Avaliação
			Avaliacao avaliacao = new Avaliacao();
			avaliacao.setUsuarioId(usuarioId);
			avaliacao.setPraiaId(praiaId);
			avaliacao.setNota(5);
			avaliacao.setComentario("Praia muito limpa e tranquila!");
			avaliacao.setData(new Date());
			avaliacaoRepo.save(avaliacao);
			System.out.println("✅ Avaliacao salva: " + avaliacao);

			// ✅ Recomendação
			PraiaRecomendada praiaRecomendada = new PraiaRecomendada();
			praiaRecomendada.setPraiaId(praiaId);
			praiaRecomendada.setScore(0.95);

			Recomendacao recomendacao = new Recomendacao();
			recomendacao.setUsuarioId(usuarioId);
			recomendacao.setPraiasRecomendadas(List.of(praiaRecomendada));
			recomendacao.setDataGeracao(new Date());
			recomendacaoRepo.save(recomendacao);
			System.out.println("✅ Recomendacao salva: " + recomendacao);

			// ✅ Alerta
			Alerta alerta = new Alerta();
			alerta.setPraiaId(praiaId);
			alerta.setTipo(TipoAlerta.POLUICAO);
			alerta.setDescricao("Água imprópria para banho");
			alerta.setDataInicio(new Date());
			alerta.setDataFim(null); // ainda ativo
			alertaRepo.save(alerta);
			System.out.println("✅ Alerta salvo: " + alerta);

			// ✅ Log
			Log log = new Log();
			log.setUsuarioId(usuarioId);
			log.setAcao(AcaoLog.LOGIN);
			log.setDetalhes(Map.of("ip", "192.168.0.1", "dispositivo", "mobile"));
			log.setData(new Date());
			logRepo.save(log);
			System.out.println("✅ Log salvo: " + log);

			System.out.println("\n🎉 Todos os dados de teste foram inseridos com sucesso!");
		};
	}
}