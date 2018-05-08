package com.bio.serenity.test.runner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.bio.serenity.test.in.Inputs;
import com.bio.serenity.test.out.OutputWriter;
import com.bio.serenity.test.piece.Piece;
import com.bio.serenity.test.piece.PieceCell;
import com.bio.serenity.test.piece.PieceDrawer;

/**
 * Implements a Runner, will be executed at application start. All beans are injected in this class
 *
 * @author elr
 *
 */
@Component
public class AppStartupRunner implements ApplicationRunner {

   private static final Logger LOG = LoggerFactory.getLogger(AppStartupRunner.class);

   @Autowired
   private Inputs mosaic;

   @Autowired
   private PieceDrawer pieceBuilder;

   private final HashSet<String> hset = new HashSet<String>();

   private List<Piece> pieceList;

   @Override
   public void run(ApplicationArguments args) throws Exception {

      final long start = System.currentTimeMillis();
      LOG.info("Application started with option names : {}", args.getOptionNames());

      pieceList = new ArrayList<>();

      resolvePiece();

      final int maxScore = mosaic.getCol() * mosaic.getRow();

      OutputWriter.writeOutputFile(pieceList, maxScore, hset.size());

      LOG.info("Program executed in {}ms", System.currentTimeMillis() - start);
   }

   /**
    * for each cell on the board, if the cell was not previously cut, try to cut a piece starting from this point
    */
   public void resolvePiece() {

      for (int row = 0; row < mosaic.getRow(); row++) {

         for (int col = 0; col < mosaic.getCol(); col++) {

            if (mosaic.getCell(row, col) != PieceCell.E) {

               final Optional<Piece> piece = pieceBuilder.drawPiece(row, col);
               piece.ifPresent(x -> savePiece(x));

            }
         }
      }
   }

   private void cachePiece(Piece piece) {
      pieceList.add(piece);
   }

   private void savePiece(Piece piece) {
      LOG.info("New piece {}", piece.toString());
      cachePiece(piece);
      markPieceOnBoard(piece.getR1(), piece.getC1(), piece.getR2(), piece.getC2());
   }

   /**
    * When a new piece is cut, set area to Empty on singleton's grid, add each cell coordinate to a HashSet
    *
    * @param r1
    * @param c1
    * @param r2
    * @param c2
    */
   public void markPieceOnBoard(int r1, int c1, int r2, int c2) {

      for (int row = r1; row <= r2; row++) {

         for (int col = c1; col <= c2; col++) {

            final String coordinates = row + "," + col;

            LOG.debug("{}", coordinates);

            /* each cell of the mosaic can be included in at most one piece */
            if (!hset.add(coordinates)) {
               throw new IllegalStateException("A coordinate should be marked only once.");
            }

            mosaic.setEmptyCell(row, col);

         }

      }
   }
}
