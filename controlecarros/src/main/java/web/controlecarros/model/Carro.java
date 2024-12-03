package web.controlecarros.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "carro")
public class Carro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "gerador", sequenceName = "carro_codigo_seq", allocationSize = 1)
	@GeneratedValue(generator = "gerador", strategy = GenerationType.SEQUENCE)
	private Long codigo;
	@NotBlank(message = "A marca do carro é obrigatória")
	private String marca;
	@NotBlank(message = "O modelo do carro é obrigatório")
	private String modelo;
	private String cor;
	private Integer ano;
	@NotBlank(message = "A placa do carro é obrigatória")
	private String placa;
	@Enumerated(EnumType.STRING)
	private Status ocupado = Status.INATIVO;
	@Enumerated(EnumType.STRING)
	private Status status = Status.ATIVO;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Status getOcupado() {
		return ocupado;
	}

	public void setOcupado(Status ocupado) {
		this.ocupado = ocupado;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Carro carro = (Carro) o;
		return Objects.equals(codigo, carro.codigo) && Objects.equals(marca, carro.marca) && Objects.equals(modelo, carro.modelo) && Objects.equals(cor, carro.cor) && Objects.equals(ano, carro.ano) && Objects.equals(placa, carro.placa) && status == carro.status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, marca, modelo, cor, ano, placa, status);
	}

	@Override
	public String toString() {
		return "Carro{" +
				"codigo=" + codigo +
				", marca='" + marca + '\'' +
				", modelo='" + modelo + '\'' +
				", cor='" + cor + '\'' +
				", ano=" + ano +
				", placa='" + placa + '\'' +
				", status=" + status +
				'}';
	}
}
