package web.primeiroprojetospring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SorteDiariaTristeService implements SorteDiariaService {

   	private static final Logger logger = LoggerFactory.getLogger(SorteDiariaTristeService.class);
   	
   	public SorteDiariaTristeService() {
   		logger.debug(">> SorteDiariaTristeService: dentro do construtor padrão");
   	}
   	
   	@Override
   	public String getSorteDiaria() {
   		return "Hoje é um dia triste!";
   	}

}
