package com.bio.serenity.test.piece;

/**
 * Enumeration having 3 possibles values: Black, white, or empty. A Cell is set to empty when it is marked into a piece.
 *
 * @author elr
 *
 */
public enum PieceCell {

   B("B"),
   W("W"),
   E("E");

   private String state;

   PieceCell(String state) {
      this.state = state;
   }

   /**
    * returns cell enum value based on char contained in input file
    *
    * @param cell
    * @return
    * @throws IllegalArgumentException
    */
   public static PieceCell setFromChar(char cell) throws IllegalArgumentException {
      switch (cell) {
         case 'W':
            return PieceCell.W;
         case 'B':
            return PieceCell.B;
         default:
            // if an input file cell is not black or white, throw an exception
            throw new IllegalArgumentException("Wrong cell input:" + cell + ". Cell must be either B or W");
      }
   }

   @Override
   public String toString() {
      return state;
   }

}
