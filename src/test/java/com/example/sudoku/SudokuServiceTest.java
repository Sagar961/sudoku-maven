package com.example.sudoku;

import com.example.sudoku.service.SudokuService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuServiceTest {

    private final SudokuService sudokuService = new SudokuService();

    @Test
    void testMessage() {

        String result = sudokuService.getMessage();

        assertEquals(
            "Sudoku application is running successfully!",
            result
        );
    }

    @Test
    void testValidNumber() {

        assertTrue(
            sudokuService.validateNumber(5)
        );
    }

    @Test
    void testInvalidNumber() {

        assertFalse(
            sudokuService.validateNumber(10)
        );
    }
}
