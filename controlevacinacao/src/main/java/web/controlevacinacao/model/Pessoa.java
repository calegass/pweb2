package web.controlevacinacao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "gerador2", sequenceName = "pessoa_codigo_seq", allocationSize = 1)
    @GeneratedValue(generator = "gerador2", strategy = GenerationType.SEQUENCE)
    private Long codigo;
    @NotBlank(message = "O nome da pessoa é obrigatório")
    private String nome;
    @NotBlank(message = "O CPF da pessoa é obrigatório")
    private String cpf;
    @NotNull(message = "A data de nascimento da pessoa é obrigatória")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    @NotBlank(message = "A profissão da pessoa é obrigatória")
    private String profissao;
    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "codigo: " + codigo + "\\nnome: " + nome + "\\ncpf: " + cpf + "\\ndataNascimento: " + dataNascimento
                + "\\nprofissao: " + profissao + "\\nstatus: " + status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pessoa other = (Pessoa) obj;
        return Objects.equals(codigo, other.codigo);
    }

}
