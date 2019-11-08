package com.swingy.view.console;

import com.swingy.utilities.database.Database;
import com.swingy.model.heroes.HeroGenerator;
import com.swingy.model.heroes.Hero;
import java.util.Scanner;
import java.util.List;

public class ConsoleSelectHero {

    private HeroGenerator _generate;
    private List<String> names;
    private Scanner scanner;
    private Hero hero;

    public ConsoleSelectHero(Scanner scanner) {
        this._generate = new HeroGenerator();
        this.hero = null;
        this.scanner = scanner;
    }

    private void oldHeroesManager() throws Exception {
        int          index;
        int          value;

        while (true) {
            index = 0;
            if (names.size() == 0) {
                System.out.println("_______________________________________________________________________________");
                System.out.println("\n You don't have any heroes, you can create one if you want......");
                System.out.println("_______________________________________________________________________________");
                return;
            }

            System.out.println("_______________________________________________________________________________");
            System.out.println("\n 0: Go back");
            System.out.println("_______________________________________________________________________________");
            for (String name : names)
                System.out.println(++index + ") " + name);
            if ( (value = this.getCheckValue()) == 0 )
                break;
            else if ( value <= index) {
                System.out.println(Database.getDatabase().getHero(names.get(value - 1)).getHeroStats());
                System.out.println("_______________________________________________________________________________");
                System.out.println("\n Make choice: \n\n 1: Select hero \n\n 2: Remove hero \n\n 3: Cancel");
                System.out.println("_______________________________________________________________________________");

                int choice = this.getCheckValue();

                if (choice == 1)
                    hero = Database.getDatabase().getHero(names.get(value - 1));
                else if (choice == 2) {
                    try {
                        Database.getDatabase().remove(names.get(value - 1));
                        names.remove(value - 1);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (choice == 1 || choice == 3)
                    break;
            }
        }
    }

    private void heroCreator() {
        
        int value;

        while (true) {
            System.out.println("_______________________________________________________________________________");
            System.out.println("\n 0: Go back");
            System.out.println("\n 1: Knight");
            System.out.println("\n 2: Saiyan");
            System.out.println("\n 3: Soldier");
            System.out.println("_______________________________________________________________________________");
            if ((value = this.getCheckValue()) == 0)
                break;
            if ((value > 0 && value < 4)) {
                switch (value) {
                    case 1:
                        this.tryCreateNewHero("Knight");
                        break;
                    case 2:
                        this.tryCreateNewHero("Saiyan");
                        break;
                    case 3:
                        this.tryCreateNewHero("Soldier");
                        break;
                }
            }
        }
    }

    private void tryCreateNewHero(String type) {
        
        String nameHero = "";
        String error = "";
        Hero newHero = _generate.GenerateHero(type);
        System.out.println("_______________________________________________________________________________");
        System.out.println(newHero.getHeroStats() + "\n\n Create hero ? \n\n 1: Yes \n\n 2: No");
        System.out.println("_______________________________________________________________________________");
        // System.out.println(newHero.getHeroStats() + "\nCreate him ? 1) Yes   2) No");
        int value = this.getCheckValue();

        if (value == 2)
            return;
        else {
            while (error != null) {
                System.out.print("Enter name: ");
                while (nameHero.equals(""))
                    nameHero = scanner.nextLine();
                error = _generate.SetNameCheck(newHero, nameHero);
                for (String name : names)
                    if (name.equals(nameHero))
                        error = "Hero with that name already created";
                if (error != null)
                    System.err.println(error);
                nameHero = "";
            }
            try {
                Database.getDatabase().addNewHero(newHero);
                this.names.add(newHero.getHeroName());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //// Getters //////
    public Hero getHero() throws Exception {
        
        int value;
        Database.getDatabase().connectDb();
        names = Database.getDatabase().getNames();

        while (hero == null) {
            System.out.print("\033[H\033[2J");
            System.out.println("_______________________________________________________________________________");
            System.out.println("\n                                 Welcome to:                                 ");
            System.out.println("_______________________________________________________________________________");
            System.out.println("    ______     __     __     __     __   __     ______     __  __    ");
            System.out.println("   /\\  ___\\   /\\ \\  _ \\ \\   /\\ \\   /\\ \"-.\\ \\   /\\  ___\\   /\\ \\_\\ \\   ");
            System.out.println("   \\ \\___  \\  \\ \\ \\/ \".\\ \\  \\ \\ \\  \\ \\ \\-.  \\  \\ \\ \\__ \\  \\ \\____ \\  ");
            System.out.println("    \\/\\_____\\  \\ \\__/\".~\\_\\  \\ \\_\\  \\ \\_\\\\\"\\_\\  \\ \\_____\\  \\/\\_____\\ ");
            System.out.println("     \\/_____/   \\/_/   \\/_/   \\/_/   \\/_/ \\/_/   \\/_____/   \\/_____/ ");
            System.out.println("_______________________________________________________________________________");
            System.out.println("\n                    Please select from what you want to do                    ");
            System.out.println("_______________________________________________________________________________");
            System.err.println("\n 0: Exit game");
            System.err.println("\n 1: Select previous created hero");
            System.err.println("\n 2: Create new hero");
            System.out.println("_______________________________________________________________________________\n");
            // System.out.println("0) Exit\n1) Select previous created hero\n2) Create new hero");
            value = this.getCheckValue();
            switch (value) {
                case 0:
                    System.exit(0);
                case 1:
                    this.oldHeroesManager();
                    break;
                case 2:
                    this.heroCreator();
                    break;
            }
        }
        return hero;
    }

    private int getCheckValue() {
        
        String str;
        while (true) {
            str = "";
            while (str.equals(""))
                str = scanner.nextLine();
            if (!str.matches("^[0-9]+")) {
                System.out.println("_______________________________________________________________________________");
                System.err.println("\n Invalid input...............");
                System.out.println("_______________________________________________________________________________");
            }
            else
                return Integer.parseInt(str);
        }
    }
    
}