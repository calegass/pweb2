package web.controlevacinacao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "lote")
public class Lote implements Serializable {

//    CREATE TABLE public.lote
//            (
//                    codigo            bigserial NOT NULL,
//                    validade          date,
//                    nro_doses_do_lote integer,
//                    nro_doses_atual   integer,
//                    codigo_vacina     bigint,
//                    status            text DEFAULT 'ATIVO',
//                    PRIMARY KEY (codigo),
//    FOREIGN KEY (codigo_vacina) REFERENCES vacina (codigo)
//            );

    private static final long serialVersionUID = -3935828642122652510L;

    @Id
    @SequenceGenerator(name = "gerador7", sequenceName = "lote_codigo_seq", allocationSize = 1)
    @GeneratedValue(generator = "gerador7", strategy = GenerationType.SEQUENCE)
    private Long codigo;
    @NotNull(message = "A validade do lote é obrigatória")
    @Column(name = "validade")
    private LocalDate validade;
    @NotNull(message = "O número de doses do lote é obrigatório")
    private Integer nro_doses_do_lote;
    private Integer nro_doses_atual;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_vacina")
    private Vacina vacina;
    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public Integer getNro_doses_atual() {
        return nro_doses_atual;
    }

    public void setNro_doses_atual(Integer nro_doses_atual) {
        this.nro_doses_atual = nro_doses_atual;
    }

    public Integer getNro_doses_do_lote() {
        return nro_doses_do_lote;
    }

    public void setNro_doses_do_lote(Integer nro_doses_do_lote) {
        this.nro_doses_do_lote = nro_doses_do_lote;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Lote{" +
                "codigo=" + codigo +
                ", validade=" + validade +
                ", nro_doses_do_lote=" + nro_doses_do_lote +
                ", nro_doses_atual=" + nro_doses_atual +
                ", vacina=" + vacina +
                ", status=" + status +
                '}';
    }
}
