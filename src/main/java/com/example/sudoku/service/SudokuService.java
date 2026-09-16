package com.example.sudoku.service;

import org.springframework.stereotype.Service;

@Service
public class SudokuService {

    public String getMessage() {
        return "Sudoku application is running successfully!";
    }

    public boolean validateNumber(int number) {
        return number >= 1 && number <= 9;
    }
}
