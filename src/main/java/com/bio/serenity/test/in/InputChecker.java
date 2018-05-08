package com.bio.serenity.test.in;

import java.util.Optional;

/**
 * @author elr
 *
 *         This class validates all inputs from file
 *
 */
public class InputChecker {

   /**
    * * Validate the grid has correct number of columns according to input C
    *
    * @param gridRow
    * @param colSize
    * @throws IllegalArgumentException
    */
   public static void validateColumnNumber(Optional<String> gridRow, int colSize) throws IllegalArgumentException {
      gridRow.filter(value -> value.length() == colSize)
            .orElseThrow(() -> new IllegalArgumentException("Grid has wrong column size"));
   }

   /**
    * * Validate the grid has correct number of row according to input R
    *
    * @param currentRow
    * @param maxRow
    * @throws IllegalArgumentException
    */
   public static void validateRowNumber(int currentRow, int maxRow) throws IllegalArgumentException {
      if (currentRow != maxRow) {
         throw new IllegalArgumentException("Wrong number of row:" + currentRow + " should be:" + maxRow);
      }
   }

   /**
    * Check if the minimum piece area is not bigger than the grid
    *
    * @param x
    * @param col
    * @param row
    * @return
    * @throws IllegalArgumentException
    */
   public static Integer validateL(int x, int col, int row) throws IllegalArgumentException {
      if (x > col * row / 2) {
         throw new IllegalArgumentException("Wrong L value: " + x);
      }
      return validateSize(x, "L");
   }

   /**
    * check max area is not smaller than the min area
    *
    * @param x
    * @param minColor
    * @return
    * @throws IllegalArgumentException
    */
   public static Integer validateH(int x, int minColor) throws IllegalArgumentException {
      if (x < minColor * 2) {
         throw new IllegalArgumentException("Wrong H value: " + x);
      }
      return validateSize(x, "H");
   }

   /**
    * Check number input are in range
    *
    * @param x
    * @param letter
    * @return
    * @throws IllegalArgumentException
    */
   private static Integer validateSize(int x, String letter) throws IllegalArgumentException {
      if (x < 1 || x > 1000) {
         throw new IllegalArgumentException("Wrong " + letter + " value: " + x);
      }
      return x;
   }

   public static Integer validateR(int x) throws IllegalArgumentException {
      return validateSize(x, "R");
   }

   public static Integer validateC(int x) throws IllegalArgumentException {
      return validateSize(x, "C");
   }

}
