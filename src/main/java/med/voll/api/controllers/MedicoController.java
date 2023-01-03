package med.voll.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.services.MedicoService;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
	
	@Autowired
	private MedicoService medicoService;

	@GetMapping
	@Transactional
	@ReadOnlyProperty
	public Page<DadosListagemMedico> listarMedicos(@PageableDefault(size = 10, page = 0, sort = {"nome"}) Pageable paginacao) {
		return medicoService.listarMedicos(paginacao).map(DadosListagemMedico::new);
	}
	
	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	public void cadastrarMedico(@RequestBody @Valid DadosCadastroMedico dados) {
		medicoService.cadastrarMedico(dados);
	}
}
