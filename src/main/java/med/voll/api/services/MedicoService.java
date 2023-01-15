package med.voll.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

	public Page<Medico> findAllByAtivoTrue(Pageable paginacao) {
		return medicoRepository.findAllByAtivoTrue(paginacao);
	}

	public void cadastrarMedico(Medico medico) {
		medicoRepository.save(medico);
	}

	public Medico listarMedicoPorId(Long id) {
		Optional<Medico> optional = medicoRepository.findById(id);
		if (optional.isPresent()) {
			Medico medico = optional.get();
			return medico;
		}
		return null;
	}

	public Medico atualizarMedico(Long id, DadosMedicoAtualizado dados) {
		Optional<Medico> optional = medicoRepository.findById(id);
		if (optional.isPresent()) {
			Medico medico = optional.get();
			medico.atualizarMedico(dados);
			return medico;
		}
		return null;
	}

	public void deletarMedico(Long id) {
		Optional<Medico> optional = medicoRepository.findById(id);
		if (optional.isPresent()) {
			medicoRepository.deleteById(id);
		}
	}

	public void alternarAtivoMedico(Long id) {
		Optional<Medico> optional = medicoRepository.findById(id);
		if (optional.isPresent()) {
			Medico medico = optional.get();
			medico.setAtivo(!medico.getAtivo());
		}
	}
}
