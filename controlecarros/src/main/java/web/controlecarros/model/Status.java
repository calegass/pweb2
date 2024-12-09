package web.controlecarros.model;

public enum Status {

    DISPONIVEL("disponível"),
    ALUGADO("alugado");

    private String descricao;

    private Status(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
