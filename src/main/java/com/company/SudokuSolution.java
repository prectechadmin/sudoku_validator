package com.company;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SudokuSolution {

    private static final int SUDOKU_GRID_ROWS = 9;
    private static final int SUDOKU_GRID_COLS = 9;

    @NonNull
    @Getter
    private List<Integer[]> solutionRows;
    @NonNull
    @Getter
    private List<Integer[]> solutionColumns;
    @NonNull
    @Getter
    private List<Integer[]> solutionBlocks;

    public static SudokuSolution fromTxtFile(@NonNull String text_filename) throws SudokuSolutionException {
        List<Integer[]> tmp_blks = new ArrayList<>();
        List<Integer[]> tmp_rows = new ArrayList<>();
        List<Integer[]> tmp_cols = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(text_filename))) {
            stream.forEach(aline -> {
                if (Arrays.asList(aline.split(",")).size() != SUDOKU_GRID_COLS) {
                    throw new RuntimeException("Invalid sudoku solution. Input file must be made of 9x9 grid of numbers");
                }
                tmp_rows.add(
                        Arrays.stream(aline.split(","))
                                .map(a -> {
                                    if (a.matches("[0-9]{1,1}")) return a;
                                    else throw new RuntimeException("Invalid sudoku solution. values can only be between 1 and 9");
                                })
                                .map(Integer::parseInt)
                                .collect(Collectors.toList()).stream().toArray(Integer[]::new)
                );
            });

            if (tmp_rows.size() != SUDOKU_GRID_ROWS) {
                throw new SudokuSolutionException("Invalid sudoku solution. Input file must be made of 9x9 grid of numbers");
            }

        } catch (SudokuSolutionException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new SudokuSolutionException(ex.getClass().getSimpleName() + " : "+ ex.getMessage());
        }

        // now pivot the rows List for column list
        for (int i=0; i < 9; i++) {
            List<Integer> tmp = new ArrayList<Integer>();
            final int indx = i;
            tmp_rows.forEach( arow -> {
                tmp.add(arow[indx]);
            });
            tmp_cols.add(tmp.stream().toArray(Integer[]::new));
        }

        // split row list into list of 3 rows
        Arrays.asList(0,3,6).forEach(arow -> {
            Arrays.asList(0,3,6).forEach(acol -> {
                tmp_blks.add(getSudokuBlock(tmp_rows.stream().toArray(Integer[][]::new),arow,acol));
            });
        });

        return new SudokuSolution(tmp_rows,tmp_cols,tmp_blks);
    }

    private static Integer[] getSudokuBlock(Integer[][] agrid, int start_row, int start_col) {
        List<Integer> tmp_blks = new ArrayList<Integer>();
        for (int i=start_row; i-start_row < 3; i++) {
            for (int j=start_col; j-start_col < 3; j++) {
                tmp_blks.add(agrid[i][j]);
            }
        }
        return tmp_blks.stream().toArray(Integer[]::new);
    }
}
