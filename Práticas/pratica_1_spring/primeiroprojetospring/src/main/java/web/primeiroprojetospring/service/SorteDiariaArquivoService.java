package web.primeiroprojetospring.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SorteDiariaArquivoService implements SorteDiariaService {

    private static final Logger logger = LoggerFactory.getLogger(SorteDiariaArquivoService.class);
    
   	private String nomeArquivo = "sortes-diarias-data.txt";
   	private List<String> sortesDiarias;
   
   	private Random random = new Random();
   
   	public SorteDiariaArquivoService() {
   		logger.debug(">> SorteDiariaArquivoService: dentro do construtor padrão");
   	}
   
   	public void carregarArquivoSortesDiarias() {
   		try {
   			logger.debug(">> SorteDiariaArquivoService: dentro do método carregarArquivoSortesDiarias");
   
   			ClassLoader classLoader = getClass().getClassLoader();
   			URL recurso = classLoader.getResource(nomeArquivo);
   			URI arquivo = recurso.toURI();
   			Path caminhoArquivo = Paths.get(arquivo);
   			sortesDiarias = Files.readAllLines(caminhoArquivo);
   
   			logger.debug("Arquivo existe: " + Files.exists(caminhoArquivo));
		logger.debug("Lendo fortunas do arquivo: " + caminhoArquivo.getFileName());
   
   		} catch (IOException e) {
   			logger.error("Erro ao ler o arquivo das fortunas " + e);
   
   		} catch (URISyntaxException e) {
   			logger.error("Erro ao abrir o arquivo com as fortunas " + e);
   		}
   	}
   
   	@Override
   	public String getSorteDiaria() {
   		return sortesDiarias.get(random.nextInt(sortesDiarias.size()));
   	}

}
