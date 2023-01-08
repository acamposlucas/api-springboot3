package med.voll.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.DadosMedicoAtualizado;
import med.voll.api.medico.Medico;
import med.voll.api.repositories.MedicoRepository;

@Service
public class MedicoService {

	@Autowired
	private MedicoRepository medicoRepository;

	public Page<Medico> listarMedicos(Pageable paginacao) {
		return medicoRepository.findAll(paginacao);
	}

	public void cadastrarMedico(DadosCadastroMedico dados) {
		Medico medico = new Medico(dados);
		medicoRepository.save(medico);
	}

	public ResponseEntity<Medico> listarMedicoPorId(Long id) {
		Optional<Medico> optional = medicoRepository.findById(id);
		if (optional.isPresent()) {
			Medico medico = optional.get();
			return ResponseEntity.ok().body(medico);
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<Void> atualizarMedico(Long id, DadosMedicoAtualizado dados) {
		Optional<Medico> optional = medicoRepository.findById(id);
		if (optional.isPresent()) {
			Medico medico = optional.get();
			medico.atualizarMedico(dados);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	public ResponseEntity<Void> deletarMedico(Long id) {
		Optional<Medico> optional = medicoRepository.findById(id);
		if (optional.isPresent()) {
			medicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
