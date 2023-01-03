package med.voll.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
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
	public List<DadosListagemMedico> listarMedicos() {
		return medicoService.listarMedicos().stream().map(DadosListagemMedico::new).toList();
	}
	
	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	public void cadastrarMedico(@RequestBody @Valid DadosCadastroMedico dados) {
		medicoService.cadastrarMedico(dados);
	}
}
