package com.app.util;

public class ConsoleUtils {
    public static final int CONSOLE_WIDTH = 120;
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static void printCentered(String text, int width) {
        if (text == null || width <= 0) {
            return;
        }
        
        int padding = (width - text.length()) / 2;
        String format = "%" + (padding + text.length()) + "s";
        System.out.println(format.formatted(text));
    }

    public static void printSeparator(char character, int length) {
        for (int i = 0; i < length; i++) {
            System.out.print(character);
        }
        System.out.println();
    }

    public static void printMenu(String[] options) {
        System.out.println("Menu:");
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d. %s%n", i + 1, options[i]);
        }
    }

    public static void promptUser(String message) {
        System.out.print(message + ": ");
    }
    
    public static void printNewLines(int lines){
        for (int i = 0; i<lines; i++){
            System.out.println();
        }
    }
}

