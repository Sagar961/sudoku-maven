package com.example.sudoku.controller;

import com.example.sudoku.service.SudokuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SudokuController {

    private final SudokuService sudokuService;

    public SudokuController(SudokuService sudokuService) {
        this.sudokuService = sudokuService;
    }

    @GetMapping("/")
    public String home() {
        return sudokuService.getMessage();
    }

    @GetMapping("/validate")
    public String validate(@RequestParam int number) {

        if (sudokuService.validateNumber(number)) {
            return "Valid Sudoku number: " + number;
        }

        return "Invalid Sudoku number. Use 1-9.";
    }
}
