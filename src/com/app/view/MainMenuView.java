package com.app.view;

import com.app.util.ConsoleUtils;

public class MainMenuView extends ConsoleUtils {

    public void displayWelcomeMessage() {
        ConsoleUtils.printSeparator('═', CONSOLE_WIDTH);
        ConsoleUtils.printCentered(WHITE +"\t*****************************************************************************************", CONSOLE_WIDTH);
        ConsoleUtils.printCentered(CYAN + "\t🏨 WELCOME TO OUR ... 🏨", CONSOLE_WIDTH);
        ConsoleUtils.printCentered(WHITE + "\t*****************************************************************************************", CONSOLE_WIDTH);
        ConsoleUtils.printSeparator('═', CONSOLE_WIDTH);
        System.out.println();

        System.out.println(CYAN + "\t\t\t\t _   _       _       _                                 ");
        System.out.println(CYAN + "\t\t\t\t| | | | ___ | |_ ___| |                                 ");
        System.out.println(CYAN + "\t\t\t\t| |_| |/ _ \\| __/ _ \\ |                                 ");
        System.out.println(CYAN + "\t\t\t\t|  _  | (_) | ||  __/ |                                 ");
        System.out.println(CYAN + "\t\t\t\t|_|_|_|\\___/ \\__\\___|_|              _   _              ");
        System.out.println(CYAN + "\t\t\t\t|  _ \\ ___  ___  ___ _ ____   ____ _| |_(_) ___  _ __  ");
        System.out.println(CYAN + "\t\t\t\t| |_) / _ \\/ __|/ _ \\ '__\\ \\ / / _` | __| |/ _ \\| '_ \\ ");
        System.out.println(CYAN + "\t\t\t\t|  _ <  __/\\__ \\  __/ |   \\ V / (_| | |_| | (_) | | | |");
        System.out.println(CYAN + "\t\t\t\t|_|\\_\\___||___/\\___|_|    \\_/  \\__,_|\\__|_|\\___/|_| |_|");
        System.out.println(CYAN + "\t\t\t\t/ ___| _   _ ___| |_ ___ _ __ ___                       ");
        System.out.println(CYAN + "\t\t\t\t \\___ \\| | | / __| __/ _ \\ '_ ` _ \\                      ");
        System.out.println(CYAN + "\t\t\t\t ___) | |_| \\__ \\ ||  __/ | | | | |                     ");
        System.out.println(CYAN + "\t\t\t\t|____/ \\__, |___/\\__\\___|_| |_| |_|                     ");
        System.out.println(CYAN + "\t\t\t\t       |___/                                            ");

        System.out.println();

        System.out.println();
        System.out.println(CYAN + "\t\t\t  _____         ____     __________________________      _________");
        System.out.println(CYAN + "\t\t\t |o o o|_______|    |___|               | | # # #  |____|o o o o o|");
        System.out.println(CYAN + "\t\t\t |o o o|**  ** |:  :|. .| []  []  []  []|o| # # #  |. . |o o o o o|");
        System.out.println(CYAN + "\t\t\t |o o o|**  ** |:  :|. .| []  []  []  []|o| # # #  |. . |o o o o o|");
        System.out.println(CYAN + "\t\t\t |o o o|**  ** |:  :|. .| []  []  []  []|o| # # #  |. . |o o o o o|");
        System.out.println(CYAN + "\t\t\t |_[]__|__[]___|_||_|___|_______________|_|___/\\___|____|____[]___|");

        System.out.println();
        ConsoleUtils.printSeparator('═', CONSOLE_WIDTH);
        System.out.println();
    }

    public void displayMainOptions() {

        System.out.println(CYAN + "\t\t\t\tPlease select an option from the menu below:" + RESET);
        System.out.println(WHITE + "\t\t\t\t1. Admin");
        System.out.println(WHITE + "\t\t\t\t2. Customer");
        System.out.println(WHITE + "\t\t\t\t3. Exit");
        System.out.println();
        ConsoleUtils.printSeparator('═', CONSOLE_WIDTH);
        ConsoleUtils.printNewLines(2);
        System.out.print(YELLOW + "\t\t\t➤  Enter your choice: ");
    }

    public void displayExitMessage() {
        System.out.println(GREEN + "\t\t\t Thank you for staying");
    }

    public void displayInvalidOptionMessage() {
        System.out.println(RED + "\t\t\t Wrong input!");
    }
}
