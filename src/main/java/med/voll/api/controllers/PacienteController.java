package med.voll.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.paciente.DadosCadastroPaciente;
import med.voll.api.paciente.DadosListagemPaciente;
import med.voll.api.paciente.Paciente;
import med.voll.api.services.PacienteService;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

	@Autowired
	PacienteService pacienteService;

	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<Void> cadastrarPaciente(@RequestBody @Valid DadosCadastroPaciente dados) {
		return pacienteService.cadastrarPaciente(dados);
	}

	@GetMapping
	@Transactional
	@ReadOnlyProperty
	public Page<DadosListagemPaciente> listarPacientes(
			@PageableDefault(size = 10, page = 0, sort = { "nome" }) Pageable paginacao) {
		return pacienteService.listarPacientes(paginacao).map(DadosListagemPaciente::new);
	}

	@GetMapping(value = "/{id}")
	@Transactional
	@ReadOnlyProperty
	public ResponseEntity<Paciente> listarPacientePorId(@PathVariable Long id) {
		return pacienteService.listarPacientePorId(id);
	}

	@DeleteMapping(value = "/{id}")
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<Void> deletarPacientePorId(@PathVariable Long id) {
		pacienteService.deletarPacientePorId(id);
		return ResponseEntity.noContent().build();
	}
}
