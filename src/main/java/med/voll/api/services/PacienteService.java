package med.voll.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import med.voll.api.paciente.Paciente;
import med.voll.api.repositories.PacienteRepository;

@Service
public class PacienteService {

	@Autowired
	PacienteRepository pacienteRepository;

	public void cadastrarPaciente(Paciente paciente) {
		pacienteRepository.save(paciente);
	}

	public Page<Paciente> listarPacientes(Pageable paginacao) {
		return pacienteRepository.findAll(paginacao);
	}

	public Paciente listarPacientePorId(Long id) {
		Optional<Paciente> optional = pacienteRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	public void deletarPacientePorId(Long id) {
		pacienteRepository.deleteById(id);
	}
}
