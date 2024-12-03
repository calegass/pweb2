package web.primeiroprojetospring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SorteDiariaFelizService implements SorteDiariaService {

	private static final Logger logger = LoggerFactory.getLogger(SorteDiariaFelizService.class);
	
	public SorteDiariaFelizService() {
		logger.debug(">> SorteDiariaFelizService: dentro do construtor padrão");
	}
	
	@Override
	public String getSorteDiaria() {
		return "Hoje é seu dia de sorte!";
	}
}
