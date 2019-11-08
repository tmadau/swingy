package com.swingy;

import com.swingy.controller.GameEngine;

public class Swingy {
    public static void main(String args[]) throws InterruptedException {
        if (args.length != 1) {
            System.out.println("_______________________________________________________________________________");
            System.err.println("\n Please run with argument: java -jar swingy.jar console or gui");
            System.out.println("_______________________________________________________________________________\n");
        }
        else {
            try {
                GameEngine gameEngine = new GameEngine();
                gameEngine.startGame(args[0]);
            }
            catch (Exception e) {
                System.out.println("_______________________________________________________________________________");
                System.err.println("\n Error: " + e.getMessage());
                System.out.println("_______________________________________________________________________________\n");
            }
        }
    }
}
