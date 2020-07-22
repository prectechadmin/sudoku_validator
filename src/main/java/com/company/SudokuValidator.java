package com.company;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class SudokuValidator {

    @NonNull
    @Getter
    private SudokuSolution sudoku2Validate;

    public boolean isValidSolution() {

        try {
            // check each solution rows, columns and 3x3 blocks for duplicates
            List<List<Integer[]>> list_under_test = Arrays.asList(
                    sudoku2Validate.getSolutionRows()
                    , sudoku2Validate.getSolutionColumns()
                    , sudoku2Validate.getSolutionBlocks()
            );
            list_under_test.forEach(alist -> {

                alist.forEach( rec -> {
                    List<Integer> result = Arrays.asList(rec).stream().filter(
                            i -> Collections.frequency(Arrays.asList(rec), i) > 1
                    ).collect(Collectors.toList());
                    if (result.size() > 0) {
                        throw new RuntimeException("Invalid Solution. Please try again");
                    }
                });
            });
        } catch (RuntimeException ex) {
            return false;
        }

        return true;
    }

}


