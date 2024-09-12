package web.primeiroprojetospring.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TecnicoTenis implements Tecnico {

	private static final Logger logger = LoggerFactory.getLogger(TecnicoTenis.class);

	// private SorteDiariaService sorteDiariaService;

	public TecnicoTenis() {
		logger.debug(">> TecnicoTenis: dentro do construtor padrão");
	}

	// public TecnicoTenis(SorteDiariaService sorteDiadiaService) {
	// logger.debug(">> TecnicoTenis: dentro do construtor de inicialização");
	// this.sorteDiariaService = sorteDiadiaService;
	// }

	// public void setSorteDiariaService(SorteDiariaService sorteDiariaService) {
	// logger.debug(">> TecnicoTenis: dentro do setter do SorteDiariaService");
	// this.sorteDiariaService = sorteDiariaService;
	// }

	
	@Override
	public String getExercicioDiario() {
		return "Pratique seu backhand volley";
	}

	// @Override
	// public String getSorteDiaria() {
	// return sorteDiariaService.getSorteDiaria();
	// }

	@Override
	public String getSorteDiaria() {
		return "Hoje é seu dia de sorte!";
	}

}
