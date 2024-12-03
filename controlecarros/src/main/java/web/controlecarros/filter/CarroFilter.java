package web.controlecarros.filter;

public class CarroFilter {

    private Long codigo;
    private String marca;
    private String modelo;
    private String cor;
    private String placa;
    private Integer anoDe;
    private Integer anoAte;

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

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getAnoDe() {
        return anoDe;
    }

    public void setAnoDe(Integer anoDe) {
        this.anoDe = anoDe;
    }

    public Integer getAnoAte() {
        return anoAte;
    }

    public void setAnoAte(Integer anoAte) {
        this.anoAte = anoAte;
    }

    @Override
    public String toString() {
        return "CarroFilter{" +
                "codigo=" + codigo +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", cor='" + cor + '\'' +
                ", placa='" + placa + '\'' +
                ", anoDe=" + anoDe +
                ", anoAte=" + anoAte +
                '}';
    }
}
