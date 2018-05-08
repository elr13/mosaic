package com.bio.serenity.test.piece;

/**
 * Store the 4 corners of the rectangle describing the piece, and calculate its score based on the area
 *
 * @author elr
 */
public class Piece {

   private final int r1;
   private final int c1;
   private final int r2;
   private final int c2;
   private final int score;

   public Piece(int r1, int c1, int r2, int c2) {
      super();
      this.r1 = r1;
      this.c1 = c1;
      this.r2 = r2;
      this.c2 = c2;
      this.score = (1 + r2 - r1) * (1 + c2 - c1);
   }

   @Override
   public String toString() {
      return r1 + " " + c1 + " " + r2 + " " + c2;
   }

   public int getScore() {
      return score;
   }

   public int getR1() {
      return r1;
   }

   public int getC1() {
      return c1;
   }

   public int getR2() {
      return r2;
   }

   public int getC2() {
      return c2;
   }

}
