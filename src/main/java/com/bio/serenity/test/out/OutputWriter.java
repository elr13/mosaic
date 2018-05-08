package com.bio.serenity.test.out;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bio.serenity.test.piece.Piece;

/**
 * @author elr
 *
 *         This class manages the output file creation based on the list a piece list
 *
 */
public class OutputWriter {

   final static Logger LOGGER = LoggerFactory.getLogger(OutputWriter.class);
   private static final String FILE_NAME = "src/main/resources/mosaic.out";

   /**
    * Generate the mosaic.out file, log the scoring percentage
    *
    * @param pieceList
    * @param maxScore
    * @throws IOException
    */
   public static void writeOutputFile(List<Piece> pieceList, int maxScore, int currentScore) throws IOException {

      BufferedWriter bw = null;
      FileWriter fw = null;

      try {

         LOGGER.info("Writing output file");

         fw = new FileWriter(FILE_NAME);
         bw = new BufferedWriter(fw);

         final String size = String.valueOf(pieceList.size());
         bw.write(size);

         bw.newLine();
         for (final Piece p : pieceList) {
            bw.write(p.toString());
            bw.newLine();
         }

         LOGGER.info("Output file created at path src/main/resources/mosaic.out");
         LOGGER.info("Total score is :{}. Maximum score is {}. Scoring percentage is {}%", currentScore, maxScore, Math.round((double) currentScore / maxScore * 10000d) / 100d);

      } catch (final IOException e) {
         LOGGER.error(e.getMessage(), e);
         throw e;
      } finally {
         try {
            if (bw != null) {
               bw.close();
            }
            if (fw != null) {
               fw.close();
            }
         } catch (final IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
         }
      }
   }

}
