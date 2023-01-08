package med.voll.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import med.voll.api.paciente.DadosCadastroPaciente;
import med.voll.api.paciente.Paciente;
import med.voll.api.repositories.PacienteRepository;

@Service
public class PacienteService {

	@Autowired
	PacienteRepository pacienteRepository;
	
	public ResponseEntity<Void> cadastrarPaciente(DadosCadastroPaciente dados) {
		Paciente paciente = new Paciente(dados);
		pacienteRepository.save(paciente);
		return ResponseEntity.noContent().build();
	}
	
	public Page<Paciente> listarPacientes(Pageable paginacao) {
		return pacienteRepository.findAll(paginacao);
	}
}
