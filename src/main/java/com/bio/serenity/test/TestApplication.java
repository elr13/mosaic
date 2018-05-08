package com.bio.serenity.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TestApplication {

   static final Logger LOGGER = LoggerFactory.getLogger(TestApplication.class);

   /**
    * Program exits if an exception raises
    * 
    * @param args
    */
   public static void main(String[] args) {
      LOGGER.info("starting application");

      ConfigurableApplicationContext context = null;
      try {
         context = SpringApplication.run(TestApplication.class, args);

      } catch (final Exception e) {
         LOGGER.error(e.getMessage(), e);
         LOGGER.error("Application stops");
         System.exit(0);
      }

   }

}
