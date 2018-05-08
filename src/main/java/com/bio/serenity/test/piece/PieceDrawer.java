package com.bio.serenity.test.piece;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bio.serenity.test.in.Inputs;

/**
 * This class attempt drawing a piece from current state of the grid
 *
 * @author elr
 *
 */
@Component
public class PieceDrawer {

   final Logger LOGGER = LoggerFactory.getLogger(PieceDrawer.class);

   private final int minColor;
   private final int minArea;
   private final int maxArea;
   private final int rowSize;
   private final int colSize;

   private final PieceFactory pieceFactory;

   @Autowired
   private Inputs mosaic;

   @Autowired
   public PieceDrawer(Inputs mosaic) {
      super();
      this.minColor = mosaic.getMinColor();
      this.minArea = mosaic.getMinColor() * 2;
      this.maxArea = mosaic.getMaxArea();
      this.rowSize = mosaic.getRow();
      this.colSize = mosaic.getCol();

      pieceFactory = Piece::new;
      LOGGER.info("PieceBuilder was successfully initialized");
   }

   /**
    * Attempt to draw the best new piece possible may fail, for this reason return type is optional
    *
    * @param row
    * @param col
    * @return
    */
   public Optional<Piece> drawPiece(int row, int col) {

      final List<Piece> options = new ArrayList<>();

      int r2 = 0;
      int c2 = 0;

      /*
       * count is number of rows. It will affect the shape of the piece. Start with horizontal shape.
       */
      int count = 1;
      while (this.maxArea > count) {

         /*
          * r2,c2 are the farthest piece coordinate possible according to the the max area and the piece shape
          */
         r2 = row + count - 1;
         c2 = col + Math.abs(this.maxArea / count) - 1;

         final Optional<Piece> piece = draw(row, col, r2, c2);

         /* if the piece matches the max area, this piece should be returned */
         if (piece.filter(x -> x.getScore() == this.maxArea)
               .isPresent()) {

            LOGGER.debug("Optimal piece found");
            return piece;
         }
         /* if not, if option is not empty, it is stored in an option list */
         piece.ifPresent(x -> options.add(x));

         /*
          * The biggest piece possible was attempted for current shape, count increment to try next shape, until the shape becomes vertical
          */
         count++;
      }
      /* if none optimal piece was foud, return the largest one */
      return getBestPieceFromOptions(options);

   }

   /**
    * Descending sort of the list based on area, then the first element is returned
    *
    * @param optionList
    * @return
    */
   private Optional<Piece> getBestPieceFromOptions(List<Piece> optionList) {
      if (optionList.size() == 0) {
         return Optional.empty();
      }
      optionList.sort(Comparator.comparingInt(Piece::getScore)
            .reversed());
      return Optional.of(optionList.get(0));
   }

   /**
    * Try to draw a piece. In case of success return the piece. if not possible try smaller piece
    *
    * @param r1
    * @param c1
    * @param r2
    * @param c2
    * @return
    */
   private Optional<Piece> draw(int r1, int c1, int r2, int c2) {
      int wCount = 0;
      int bCount = 0;

      LOGGER.debug("Trying draw {},{} {},{}", r1, c1, r2, c2);

      if (!validMinArea(r1, c1, r2, c2)) {
         return Optional.empty();
      }
      if (!validOnBoard(r1, c1, r2, c2)) {
         return retrySmaller(r1, c1, r2, c2);
      }
      /*
       * Iterate through piece's cells and count B/W. If Empty is found, exclude it from next smallest piece
       */
      for (int col = c1; col <= c2; col++) {
         for (int row = r1; row <= r2; row++) {
            switch (mosaic.getCell(row, col)) {
               case B:
                  bCount++;
                  break;
               case W:
                  wCount++;
                  break;
               default:
                  return retrySmaller(r1, c1, row, col);
            }
         }
      }

      if (bCount >= this.minColor && wCount >= this.minColor) {
         return Optional.of(pieceFactory.getPiece(r1, c1, r2, c2));
      }

      return Optional.empty();
   }

   /**
    * Decrement the longest side of the rectangle
    *
    * @param r1
    * @param c1
    * @param r2
    * @param c2
    * @return
    */
   private Optional<Piece> retrySmaller(int r1, int c1, int r2, int c2) {

      if (c2 - c1 > r2 - r1) {
         return draw(r1, c1, r2, c2 - 1);
      }
      return draw(r1, c1, r2 - 1, c2);
   }

   /**
    * ensure piece is big enough
    *
    * @param r1
    * @param c1
    * @param r2
    * @param c2
    * @return
    */
   private boolean validMinArea(int r1, int c1, int r2, int c2) {
      if ((1 + c2 - c1) * (1 + r2 - r1) >= this.minArea) {
         return true;
      }
      return false;

   }

   /**
    *
    * ensure piece is on board
    *
    * @param r1
    * @param c1
    * @param r2
    * @param c2
    * @return
    */
   private boolean validOnBoard(int r1, int c1, int r2, int c2) {
      if (c2 < this.colSize && r2 < this.rowSize) {
         return true;
      }
      return false;

   }

}
