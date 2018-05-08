package com.bio.serenity.test.in;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.bio.serenity.test.piece.PieceCell;

/**
 * This class is storing information from input file as a singleton.
 *
 * @author elr
 *
 */
@Component
public class Inputs {

   final Logger LOGGER = LoggerFactory.getLogger(Inputs.class);

   @Value("classpath:mosaic.in")
   private Resource res;

   private int row;
   private int col;
   private int minColor;
   private int maxArea;

   private PieceCell[][] grid;

   public Inputs() {
      super();
   }

   /**
    * Initialize bean from resource values
    *
    * @throws IOException
    * @throws IllegalArgumentException
    * @throws Exception
    */
   @PostConstruct
   public void init() throws IOException, IllegalArgumentException, Exception {

      final InputStream is = res.getInputStream();

      final BufferedReader br = new BufferedReader(new InputStreamReader(is));

      final Optional<String> line = Optional.ofNullable(br.readLine());

      final int[] constraints = parseConstraints(line);

      this.row = InputChecker.validateR(constraints[0]);
      this.col = InputChecker.validateC(constraints[1]);
      this.minColor = InputChecker.validateL(constraints[2], this.col, this.row);
      this.maxArea = InputChecker.validateH(constraints[3], minColor);
      initializeGrid(br);

      br.close();

      LOGGER.info("Mosaic grid was successfully initialized.");
   }

   /**
    * Get R C L H
    *
    * @param line
    * @return
    * @throws IllegalArgumentException
    */
   private int[] parseConstraints(Optional<String> line) throws IllegalArgumentException {

      line.filter(s -> !s.isEmpty())
            .map(String::trim)
            .orElseThrow(() -> new IllegalArgumentException("Wrong first line input from file! Application expects 4 natural numbers separated by single space."));

      final String[] strings = line.get()
            .split(" ");

      if (strings.length != 4) {
         throw new IllegalArgumentException("Wrong first line input from file! Application expects 4 natural numbers separated by single space.");
      }

      int[] array;
      try {

         array = Arrays.asList(strings)
               .stream()
               .mapToInt(Integer::parseInt)
               .toArray();

      } catch (final NumberFormatException e) {
         throw new IllegalArgumentException("Wrong first line input from file! Application expects numbers separated by single space.");
      }

      return array;

   }

   private void initializeGrid(BufferedReader br) throws IOException, IllegalArgumentException {

      this.grid = new PieceCell[this.row][this.col];

      Optional<String> line = Optional.ofNullable(br.readLine());
      int row = 0;

      while (line.isPresent()) {

         // each row is validated
         InputChecker.validateColumnNumber(line, this.col);

         for (int i = 0; i < line.get()
               .length(); i++) {

            grid[row][i] = PieceCell.setFromChar(line.get()
                  .charAt(i));
         }

         line = Optional.ofNullable(br.readLine());
         row++;
      }

      // row count must be equal
      InputChecker.validateRowNumber(row, this.row);
   }

   public PieceCell[][] getGrid() {
      return grid;
   }

   public int getRow() {
      return row;
   }

   public int getCol() {
      return col;
   }

   public int getMinColor() {
      return minColor;
   }

   public int getMaxArea() {
      return maxArea;
   }

   public void setEmptyCell(int row, int col) {
      grid[row][col] = PieceCell.E;
   }

   public PieceCell getCell(int row, int col) throws IllegalArgumentException {
      if (row < this.row && col < this.col && row >= 0 && col >= 0) {
         return grid[row][col];
      }
      LOGGER.debug("{},{},{},{}", row < this.row, col < this.col, row >= 0, col >= 0);
      throw new IllegalArgumentException("Wrong cell coordinate:" + row + "," + col);
   }

}
