package com.company;


public class App 
{
    final static int VALID_CODE = 100;
    final static int INVALID_CODE = 200;
    final static int ERROR_CODE = 300;

    public static void print_help() {
        System.out.println("-------------------------------------------------------");
        System.out.println("Welcome to Sudoku Validator. Usage instructions are:");
        System.out.println("validate.bat_rename [solution_file]");

    }
    public static void main( String[] args )
    {
        try {
            if (args.length > 0) {
                // load solution from file
                SudokuSolution asolution = SudokuSolution.fromTxtFile(args[0]);
                if (new SudokuValidator(asolution).isValidSolution()) {
                    System.out.println(String.format("VALID %d %s"
                            , VALID_CODE, "Congratulation. You solved the Sudoku puzzle.")
                    );

                } else {
                    System.out.println(String.format("INVALID %d %s"
                            , INVALID_CODE, "Sorry, Looks like this is not a valid solution. Better luck next time")
                    );
                }
            } else {
                App.print_help();
            }
        } catch (SudokuSolutionException ex) {
            System.out.println(String.format("ERROR: %d %s",ERROR_CODE,ex.getMessage()));
        }
    }
}
