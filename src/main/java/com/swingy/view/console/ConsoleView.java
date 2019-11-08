package com.swingy.view.console;

import com.swingy.controller.GameEngine;
import com.swingy.view.Viewable;
import com.swingy.model.foe.Foe;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class ConsoleView implements Viewable {

    private static final String _MOVEMENT = "_______________________________________________________________________________\n\n 0: Exit\n_______________________________________________________________________________\n\n     1: North\n\n2: West     3: East\n\n     4: South\n\n\"type gui\" - to swith to gui";
    private static final Scanner _scanner = new Scanner(System.in);
    private GameEngine _gameEngine;
    private ArrayList<char[]> _map;
    private int _numStat = 0;

    public ConsoleView(GameEngine gameEngine) {
        this._gameEngine = gameEngine;
        _map = new ArrayList<>();
    }

    @Override
    public void SelectHero() {
        try {
            _gameEngine.setHero(new ConsoleSelectHero(_scanner).getHero());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawGameObjects() {
        drawMap();
        System.out.println(_MOVEMENT);
        _gameEngine.keyPressed(getValueCheck());
    }

    @Override
    public void viewRepaint() {
        this.drawGameObjects();
    }

    @Override
    public void scrollPositionManager() {}

    @Override
    public void updateData() {}

    @Override
    public boolean simpleDialog(String message) {

        System.out.println(message.concat("\n 1) Yes     2) No"));
        int key = getValueCheck();
        while (key != 38 && key != 37) {
            System.err.println("Unknown value.");
            key = getValueCheck();
        }
        return (key == 38);
    }

    @Override
    public String get_Type() {
        return "console";
    }

    @Override
    public void   addLog(String text) {
        System.out.println(text);
    }

    @Override
    public void   close() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void    drawMap() {
        char buff[] = new char[_gameEngine.getSizeMap()];
        Arrays.fill(buff, '.');

        _map.clear();
        for (int i = 0; i < _gameEngine.getSizeMap(); ++i) {
            _map.add(buff.clone());
        }
        _map.get(_gameEngine.getHero().getPosition().y)[_gameEngine.getHero().getPosition().x] = 'H';
        for (Foe foe : _gameEngine.getEnemies()) {
            _map.get(foe.getPosition().y)[foe.getPosition().x] = 'E';
        }
        _numStat = 0;
        for (char str[] : _map) {
            System.out.println("     ".concat(String.valueOf(str)).concat(this.getStat(_numStat++)));
        }
    }

    private int getValueCheck() {
        String str;

        while (true) {
            str = "";
            while (str.isEmpty()) {
                try {
                    str = _scanner.nextLine();
                }
                catch (Exception e) {
                    System.err.println("Error occured");
                    System.exit(0);
                }
            }
            if (str.equals("gui")) {
                return -2;
            }
            else if (!str.matches("^[0-9]+")) {
                System.err.println("Enter correct value !");
            }
            else {
                break;
            }
        }

        int value = -3;
        switch (Integer.parseInt(str)) {
            case 1:
                value = 38;
                break;
            case 2:
                value = 37;
                break;
            case 3:
                value = 39;
                break;
            case 4:
                value = 40;
                break;
            case -2:
                value = -2;
                break;
            case 0:
                _gameEngine.saveHero();
                System.exit(0);
        }

        
        return value;
    }

    private String  getStat(int numStat) {
        
        if (numStat > 9) {
            return "";
        }
        StringBuilder stat = new StringBuilder("       ");
        switch (numStat) {
            case 0:
                stat.append("Name: ").append(_gameEngine.getHero().getHeroName());
                break;
            case 1:
                stat.append("Type: ").append(_gameEngine.getHero().getHeroClass());
                break;
            case 2:
                stat.append("Level: ").append(_gameEngine.getHero().getLevel());
                break;
            case 3:
                stat.append("Location [").append(_gameEngine.getHero().getPosition().x).append(", ")
                    .append(_gameEngine.getHero().getPosition().y).append("]");
                break;
            case 4:
                stat.append("Exp: ").append(_gameEngine.getHero().getExperience()).append("/")
                    .append(_gameEngine.getHero().getExperienceGained());
                break;
            case 5:
                stat.append("Attack: ").append(_gameEngine.getHero().getAttack());
                break;
            case 6:
                stat.append("Defense: ").append(_gameEngine.getHero().getDefense());
                break;
            case 7:
                stat.append("Hp: ").append(_gameEngine.getHero().getHP()).append("/")
                    .append(_gameEngine.getHero().getMaxHp());                          break;
            case 8:
                if (_gameEngine.getHero().getArtifact() != null && !_gameEngine.getHero().getArtifact().getType().isEmpty()) {
                    stat.append("Artifact-").append(_gameEngine.getHero().getArtifact().getType()).append(": ")
                        .append(_gameEngine.getHero().getArtifact().getLoot());
                }
                break;
        }
        return stat.toString();
    }

}
