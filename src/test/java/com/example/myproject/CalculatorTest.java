package com.example.myproject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    public void testAdd() {
        Calculator calc = new Calculator();
        assertEquals(5, calc.add(2, 3));
    }

    @Test
    public void testSubtract() {
        Calculator calc = new Calculator();
        assertEquals(1, calc.subtract(3, 2));
    }

    @Test
    public void testMultiply() {
        Calculator calc = new Calculator();
        assertEquals(6, calc.multiply(2, 3));
    }

    @Test
    public void testDivide() {
        Calculator calc = new Calculator();
        // assertEquals(2.0, calc.divide(4, 2));
        // assertEquals(2.0, calc.divide(0, 0));
        assertEquals(2.0, calc.divide(1, 1));

    }

    @Test
    public void testDivideByZero() {
        Calculator calc = new Calculator();
        assertThrows(IllegalArgumentException.class, () -> calc.divide(4, 0));
    }
}