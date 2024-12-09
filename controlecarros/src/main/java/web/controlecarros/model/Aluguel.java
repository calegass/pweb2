package web.controlecarros.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rental")
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "carro_id", nullable = false)
    private Carro carro;

    @Column(name = "nome_funcionario", nullable = false)
    private String nomeFuncionario;

    @Column(name = "data_inicial", nullable = false)
    private LocalDateTime dataInicial;

    @Column(name = "data_final")
    private LocalDateTime dataFinal;

    public Aluguel() {
    }

    public Aluguel(Carro car, String nomeFuncionario) {
        this.carro = car;
        this.nomeFuncionario = nomeFuncionario;
        this.dataInicial = LocalDateTime.now();
    }

    public boolean isActive() {
        return dataFinal == null;
    }

    public Carro getCarro() {
        return carro;
    }

    public LocalDateTime getFimAluguel() {
        return dataFinal;
    }

    public void setRentalEnd(LocalDateTime rentalEnd) {
        this.dataFinal = rentalEnd;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getDataInicialPtBr() {
        return dataInicial.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}