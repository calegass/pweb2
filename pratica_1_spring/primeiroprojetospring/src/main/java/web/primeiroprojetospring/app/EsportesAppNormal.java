package web.primeiroprojetospring.app;

import web.primeiroprojetospring.model.TecnicoNatacao;
import web.primeiroprojetospring.model.TecnicoPingPong;

public class EsportesAppNormal {
    public static void main(String[] args) {
        TecnicoPingPong tecnicoPingPong = new TecnicoPingPong();
        TecnicoNatacao tecnicoNatacao = new TecnicoNatacao();

        System.out.println(tecnicoPingPong.getExercicioDiario());
        System.out.println(tecnicoPingPong.getSorteDiaria());
        System.out.println(tecnicoNatacao.getExercicioDiario());
        System.out.println(tecnicoNatacao.getSorteDiaria());
    }
}
