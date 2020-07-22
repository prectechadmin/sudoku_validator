package com.company;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.net.URLDecoder;

import static org.junit.Assert.*;


/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();


    @Test
    public void shouldThrowSolutionExceptionOnNoFile() throws Exception
    {
        expectedEx.expect(SudokuSolutionException.class);
        expectedEx.expectMessage("NoSuchFileException");
        SudokuSolution asolution = SudokuSolution.fromTxtFile("file_that_doesnt_exist.csv");

    }

    @Test
    public void shouldThrowSolutionExceptionOnEmptyFile() throws Exception
    {
        expectedEx.expect(SudokuSolutionException.class);
        expectedEx.expectMessage("Invalid sudoku solution. Input file must be made of 9x9 grid of numbers");
        File test_file = new File(
                URLDecoder.decode(
                        this.getClass().getClassLoader().getResource("test_empty_solution.csv").getFile())
        );
        SudokuSolution asolution = SudokuSolution.fromTxtFile(
                test_file.getAbsolutePath()
        );

    }

    @Test
    public void shouldThrowSolutionExceptionOnInvalidData() throws Exception
    {
        expectedEx.expect(SudokuSolutionException.class);
        expectedEx.expectMessage("Invalid sudoku solution. values can only be between 1 and 9");
        File test_file = new File(
                URLDecoder.decode(
                        this.getClass().getClassLoader().getResource("test_bad_data_solution.csv").getFile()
                )
        );
        SudokuSolution asolution = SudokuSolution.fromTxtFile(test_file.getAbsolutePath());

    }

    @Test
    public void shouldRetunTrueOnValidSolution() throws Exception
    {
        File test_file = new File(
                URLDecoder.decode(
                        this.getClass().getClassLoader().getResource("test_valid_solution.csv").getFile())
        );
        SudokuSolution asolution = SudokuSolution.fromTxtFile(test_file.getAbsolutePath());
        assertTrue( new SudokuValidator(asolution).isValidSolution());

    }

    @Test
    public void shouldRetunFalseOnInValidSolution() throws Exception
    {
        File test_file = new File(
                URLDecoder.decode(
                        this.getClass().getClassLoader().getResource("test_incorrect_solution.csv").getFile())
        );
        SudokuSolution asolution = SudokuSolution.fromTxtFile(test_file.getAbsolutePath());
        assertFalse( new SudokuValidator(asolution).isValidSolution());

    }
}
