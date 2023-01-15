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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.DadosMedicoAtualizado;
import med.voll.api.medico.Medico;
import med.voll.api.services.MedicoService;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

	@Autowired
	private MedicoService medicoService;

	@GetMapping
	@Transactional
	@ReadOnlyProperty
	public ResponseEntity<Page<DadosListagemMedico>> listarMedicos(
			@PageableDefault(size = 10, page = 0, sort = { "nome" }) Pageable paginacao) {
		var page = medicoService.listarMedicos(paginacao).map(DadosListagemMedico::new);
		return ResponseEntity.ok(page);
	}

	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<DadosListagemMedico> cadastrarMedico(@RequestBody @Valid DadosCadastroMedico dados,
			UriComponentsBuilder uriBuilder) {
		var medico = new Medico(dados);
		medicoService.cadastrarMedico(medico);
		var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosListagemMedico(medico));
	}

	@GetMapping(value = "/{id}")
	@Transactional
	@ReadOnlyProperty
	public ResponseEntity<Medico> listarMedicoPorId(@PathVariable Long id) {
		var medico = medicoService.listarMedicoPorId(id);
		return ResponseEntity.ok(medico);
	}

	@GetMapping(value = "/ativos")
	@Transactional
	@ReadOnlyProperty
	public ResponseEntity<Page<Medico>> listarMedicosAtivos(
			@PageableDefault(size = 10, page = 0, sort = { "nome" }) Pageable paginacao) {
		var page = medicoService.findAllByAtivoTrue(paginacao);
		return ResponseEntity.ok(page);
	}

	@PutMapping(value = "/{id}")
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<DadosListagemMedico> atualizarMedico(@PathVariable Long id,
			@RequestBody @Valid DadosMedicoAtualizado dados) {
		var medico = medicoService.atualizarMedico(id, dados);
		var medicoAtualizado = new DadosListagemMedico(medico);
		return ResponseEntity.ok(medicoAtualizado);
	}

	@DeleteMapping(value = "/{id}")
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<Void> deletarMedico(@PathVariable Long id) {
		medicoService.deletarMedico(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}/ativo")
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<Void> alternarAtivo(@PathVariable Long id) {
		medicoService.alternarAtivoMedico(id);
		return ResponseEntity.noContent().build();
	}
}
