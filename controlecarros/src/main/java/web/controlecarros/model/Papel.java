package web.controlecarros.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "papel")
public class Papel implements Serializable {

	private static final long serialVersionUID = 3377158425416402634L; // gere um outro valor

	@Id
	@SequenceGenerator(name = "gerador", sequenceName = "papel_codigo_seq", allocationSize = 1)
	@GeneratedValue(generator = "gerador", strategy = GenerationType.SEQUENCE)
	private Long codigo;
	private String nome;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Papel other = (Papel) obj;
		if (codigo == null) {
			return other.codigo == null;
		} else return codigo.equals(other.codigo);
	}

	@Override
	public String toString() {
		return "codigo: " + codigo + "\nnome: " + nome;
	}

}