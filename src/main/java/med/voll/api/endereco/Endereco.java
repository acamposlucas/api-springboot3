package med.voll.api.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

	private String bairro;
	private String cep;
	private String cidade;
	private String complemento;
	private String logradouro;
	private String numero;
	private String uf;

	public Endereco(DadosEndereco dados) {
		this.bairro = dados.bairro();
		this.cep = dados.cep();
		this.cidade = dados.cidade();
		this.complemento = dados.complemento();
		this.logradouro = dados.logradouro();
		this.numero = dados.numero();
		this.uf = dados.uf();
	}
}
