package med.voll.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.Medico;
import med.voll.api.repositories.MedicoRepository;

@Service
public class MedicoService {

	@Autowired
	private MedicoRepository medicoRepository;
	
	public List<Medico> listarMedicos() {
		return medicoRepository.findAll();
	}
	
	public void cadastrarMedico(DadosCadastroMedico dados) {
		Medico medico = new Medico(dados);
		medicoRepository.save(medico);
	}
}
