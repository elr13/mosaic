package com.bio.serenity.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bio.serenity.test.in.InputChecker;
import com.bio.serenity.test.out.OutputWriter;
import com.bio.serenity.test.piece.Piece;
import com.bio.serenity.test.piece.PieceCell;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {

   /*
    * Test valid input from file
    * 
    * 
    */
   @Test
   public void testValidInputs() {
      assertEquals(InputChecker.validateL(1, 2, 2), new Integer(1));
      assertEquals(InputChecker.validateH(2, 1), new Integer(2));
      assertEquals(InputChecker.validateC(5), new Integer(5));
      assertEquals(InputChecker.validateR(6), new Integer(6));
      assertEquals(PieceCell.setFromChar('W'), PieceCell.W);
      assertEquals(PieceCell.setFromChar('B'), PieceCell.B);

      final Optional<String> gridRow = Optional.of("test");
      InputChecker.validateRowNumber(10, 10);
      InputChecker.validateColumnNumber(gridRow, 4);
   }

   /*
    * Test non-valid input from file
    * 
    * 
    */
   @Test(expected = IllegalArgumentException.class)
   public void testValidateSizeLessThanOne() {
      InputChecker.validateC(0);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testValidateSizeMoreTausend() {
      InputChecker.validateR(10001);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testWrongInputH() {
      InputChecker.validateH(1, 2);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testWrongInputL() {
      InputChecker.validateL(100, 2, 2);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testWrongInputColumnSize() {
      final Optional<String> gridRow = Optional.of("test");
      InputChecker.validateColumnNumber(gridRow, 10);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testWrongInputRowCount() {
      InputChecker.validateRowNumber(1, 10);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testPieceCell() {
      PieceCell.setFromChar('X');
   }

   /*
    * Test valid output from application
    * 
    * 
    */
   @Test
   public void testValidOutput() throws IOException {
      final List<Piece> list = new ArrayList<>();
      list.add(new Piece(0, 0, 1, 1));
      OutputWriter.writeOutputFile(list, 4, 4);
      final File file = new File("src/main/resources/mosaic.out");
      final String s = FileUtils.readFileToString(file);
      assertTrue(file.exists());
      assertEquals(s, "1\n0 0 1 1\n");
      assertTrue(file.delete());
   }

}
