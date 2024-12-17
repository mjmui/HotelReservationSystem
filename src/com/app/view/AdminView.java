package com.app.view;

import com.app.model.Customer;
import com.app.model.Hotel;
import com.app.model.Reservation;
import com.app.model.Room;
import com.app.util.ConsoleUtils;
import static com.app.util.ConsoleUtils.CYAN;
import static com.app.util.ConsoleUtils.GREEN;
import static com.app.util.ConsoleUtils.RED;
import static com.app.util.ConsoleUtils.WHITE;
import static com.app.util.ConsoleUtils.YELLOW;

public class AdminView {

    public void displayAdminOptions() {
        
        ConsoleUtils.printNewLines(20);
        System.out.println("");
        System.out.println(CYAN + "\t\t\t\t╔═══ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ═══╗");
        System.out.println(CYAN + "\t\t\t\t    👨🏻‍💻 Admin Menu:");
        System.out.println(WHITE + "\t\t\t\t    👨🏻‍💻 1. Login");
        System.out.println(WHITE + "\t\t\t\t    👨🏻‍💻 2. Back");
        System.out.println(WHITE + "\t\t\t\t    👨🏻‍💻 3. Exit"); 
        System.out.println(CYAN + "\t\t\t\t╚═══ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ═══╝");
        ConsoleUtils.printNewLines(15);
        System.out.print(YELLOW + "\t\t\t\t➤    Enter your choice: ");
        
    }
    
    public void displayMainOptions(){
        
        ConsoleUtils.printNewLines(15);
        System.out.println(CYAN + "\t\t\t\t╔═══ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ═══╗");
        System.out.println(CYAN + "\t\t\t\t    🏠 Main Menu:");
        System.out.println(WHITE + "\t\t\t\t    🏠 1. Manage customers");
        System.out.println(WHITE + "\t\t\t\t    🏠 2. Manage rooms");
        System.out.println(WHITE + "\t\t\t\t    🏠 3. Manage hotels");
        System.out.println(WHITE + "\t\t\t\t    🏠 4. Manage reservations");
        System.out.println(WHITE + "\t\t\t\t    🏠 5. Back");
        System.out.println(WHITE + "\t\t\t\t    🏠 6. Exit");
        System.out.println(CYAN + "\t\t\t\t╚═══ -ˋˏ *.·:·.⟐.·:·.* ˎˊ- ═══╝");
        ConsoleUtils.printNewLines(15);
        System.out.print(YELLOW +"\t\t\t\t➤   Enter your choice: ");
    } 
    

    public void displayInvalidOptionMessage() {
        System.out.println(RED + "\t\t\t\t「 ⛔ Invalid option. Please try again. ⛔ 」");
    }


    public void displayLoginSuccessMessage() {
        System.out.println(GREEN + "\t\t\t\t「 ✦ Login successful! Welcome, Admin. ✦ 」");
    }

    public void displayLoginFailureMessage() {
        System.out.println(RED + "\t\t\t\t「 ⛔ Login failed! Please check your email and password. ⛔ 」");
    }

    public void displayExitMessage() {
        ConsoleUtils.printNewLines(10);
        System.out.println(GREEN + "\t\t\t\tExiting the application. Thank you!");
//        System.out.println(GREEN + "\t\t\t _   _                   _  ");
//        System.out.println(GREEN + "\t\t\t| | | |                 | |");
//        System.out.println(GREEN + "\t\t\t| |_| |__    __ _ _ __  | |  __ _   _   ___   _   _ ");
//        System.out.println(GREEN + "\t\t\t| __| '_ \\ /_ ` | '_ \\| | / /| | | | / _ \\| | | |");
//        System.out.println(GREEN + "\t\t\t| |_| | | |( _ | | | |  |   <  | |_| |  (_)  | |_| |");
//        System.out.println(GREEN + "\t\t\t\\__|_| |_|\\__,_|_| |_ |_|\\_\\ _   | \\__, \\___/");
//        System.out.println(GREEN + "\t\t\t                                __ / | ");
//        System.out.println(GREEN + "\t\t\t                               | ___/ ");
//          System.out.println(GREEN +"\t\t\t┌──────────────────────────────────────────────────────────────────┐\n" +
//GREEN +"\t\t\t│  _   _                 _                           __            │\n" +
//GREEN +"\t\t\t│ | | | |               | |                         / _|           │\n" +
//GREEN +"\t\t\t│ | |_| |__   _ _ _ _ | | _  _   _  __  _   _  | |_ _  _ __  │\n" +
//GREEN +"\t\t\t│ | _| ' \\ / ` | ' \\| |/ / | | | |/ _ \\| | | | |  _/ _ \\| '__| │\n" +
//GREEN +"\t\t\t│ | |_| | | | (| | | | |   <  | |_| | () | |_| | | || (_) | |    │\n" +
//GREEN +"\t\t\t│  \\__|_| |_|\\__,_|_| |_|_|\\_\\  \\__, |\\___/ \\__,_| |_| \\___/|_|    │\n" +
//GREEN +"\t\t\t│     | |            (_)         __/ |                             │\n" +
//GREEN +"\t\t\t│  __| |\n" +
//GREEN +"\t\t\t_ _ _   _ _ _ _   _|___/                              │\n" +
//GREEN +"\t\t\t│ / __| __/ _ | | | | | '_ \\ / _ |                               │\n" +
//GREEN +"\t\t\t│ \\__ \\ || (_| | |_| | | | | | (_| |                               │\n" +
//GREEN +"\t\t\t│ |___/\\__\\__,_|\\__, |_|_| |_|\\__, |                               │\n" +
//GREEN +"\t\t\t│                __/ |         __/ |                               │\n" +
//GREEN +"\t\t\t│               |___/         |___/                                │\n" +
//GREEN +"\t\t\t└──────────────────────────────────────────────────────────────────┘");
        System.out.println(GREEN +"\t\t\t _   _                 _                      ");
        System.out.println(GREEN +"\t\t\t| |_| |__   _ _ _ _ | | _   _  _  _   _ ");
        System.out.println(GREEN +"\t\t\t| _| ' \\ / ` | ' \\| |/ / | | |/ _ \\| | | |");
        System.out.println(GREEN +"\t\t\t| |_| | | | (| | | | |   <| |_| | () | |_| |");
        System.out.println(GREEN +"\t\t\t \\__|_| |_|\\__,_|_| |_|_|\\_\\\\__, |\\___/ \\__,_|");
        System.out.println(GREEN +"\t\t\t                             |___/             ");
        ConsoleUtils.printNewLines(10);
    }


    public void displayErrorMessage(String message) {
        System.out.println("「 ⛔ Error: " + message + " ⛔ 」");
    }
    

}
