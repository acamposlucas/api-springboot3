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
	public Page<DadosListagemMedico> listarMedicos(
			@PageableDefault(size = 10, page = 0, sort = { "nome" }) Pageable paginacao) {
		return medicoService.listarMedicos(paginacao).map(DadosListagemMedico::new);
	}

	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	public void cadastrarMedico(@RequestBody @Valid DadosCadastroMedico dados) {
		medicoService.cadastrarMedico(dados);
	}

	@GetMapping(value = "/{id}")
	@Transactional
	@ReadOnlyProperty
	public ResponseEntity<Medico> listarMedicoPorId(@PathVariable Long id) {
		return medicoService.listarMedicoPorId(id);
	}

	@PutMapping(value = "/{id}")
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<Void> atualizarMedico(@PathVariable Long id,
			@RequestBody @Valid DadosMedicoAtualizado dados) {
		return medicoService.atualizarMedico(id, dados);
	}

	@DeleteMapping(value = "/{id}")
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<Void> deletarMedico(@PathVariable Long id) {
		return medicoService.deletarMedico(id);
	}

	@PutMapping(value = "/{id}/ativo")
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<Void> alternarAtivo(@PathVariable Long id) {
		return medicoService.alternarAtivoMedico(id);
	}
}
