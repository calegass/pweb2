package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TesteLogging {
    private static final Logger logger = LoggerFactory.getLogger(TesteLogging.class);

    public static void main(String[] args) {
        logger.trace("No início do programa principal");
        logger.debug("No início do programa principal");
        logger.info("No início do programa principal");
        logger.warn("No início do programa principal");
        logger.error("No início do programa principal");
        System.out.println("Funciona!");
        logger.trace("No final do programa principal");
        logger.debug("No final do programa principal");
        logger.info("No final do programa principal");
        logger.warn("No final do programa principal");
        logger.error("No final do programa principal");
    }

}
