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
import org.springframework.web.util.UriComponentsBuilder;

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
	public ResponseEntity<DadosListagemPaciente> cadastrarPaciente(@RequestBody @Valid DadosCadastroPaciente dados,
			UriComponentsBuilder uriBuilder) {
		var paciente = new Paciente(dados);
		pacienteService.cadastrarPaciente(paciente);
		var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(paciente.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosListagemPaciente(paciente));
	}

	@GetMapping
	@Transactional
	@ReadOnlyProperty
	public ResponseEntity<Page<DadosListagemPaciente>> listarPacientes(
			@PageableDefault(size = 10, page = 0, sort = { "nome" }) Pageable paginacao) {
		var page = pacienteService.listarPacientes(paginacao).map(DadosListagemPaciente::new);
		return ResponseEntity.ok(page);
	}

	@GetMapping(value = "/{id}")
	@Transactional
	@ReadOnlyProperty
	public ResponseEntity<DadosListagemPaciente> listarPacientePorId(@PathVariable Long id) {
		var paciente = pacienteService.listarPacientePorId(id);
		return ResponseEntity.ok(new DadosListagemPaciente(paciente));
	}

	@DeleteMapping(value = "/{id}")
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<Void> deletarPacientePorId(@PathVariable Long id) {
		pacienteService.deletarPacientePorId(id);
		return ResponseEntity.noContent().build();
	}
}
