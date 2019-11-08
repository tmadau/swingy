package com.swingy.controller;

import com.swingy.utilities.database.Database;
import com.swingy.view.console.ConsoleView;
import com.swingy.model.foe.FoeGenerator;
import com.swingy.model.items.Artifact;
import com.swingy.view.gui.GUIView;
import com.swingy.model.heroes.Hero;
import com.swingy.model.foe.Foe;
import com.swingy.view.Viewable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;

public class GameEngine {
    
    private Viewable _view;
    private Hero _hero;
    private int _MapSize = 0;
    private ArrayList<Foe> _foes;
    private Random _random;

    public GameEngine() {
        _foes = new ArrayList<>();
        _random = new Random();
    }

    public void startGame(String argument) throws Exception {
        if (!argument.equals("gui") && !argument.equals("console"))
            throw new IOException("wrong argument run with: [gui or console]");
        this.changeView(argument);

        if (this._hero == null) {
            this._view.SelectHero();
            this._MapSize = (this._hero.getLevel() - 1) * 5 + 10 - (this._hero.getLevel() % 2);
            this.initNewGame();
        }
        this._view.drawGameObjects();
    }

    public void keyPressed(int key) {
        this.handleKey(key);
        this.handleCollisions();
        this._view.updateData();
        this._view.viewRepaint();
        if (this._MapSize > 9)
            this._view.scrollPositionManager();
    }

    public void saveHero() {
        if (_view.simpleDialog("Save your hero ?"))
            Database.getDatabase().updateHero(this._hero);
    }

    private void handleKey(int key) {
        switch (key) {
            case 37:
                if (this._hero.getPosition().x - 1 >= 0)
                    this._hero.move(-1, 0);
                else
                    key = -1;
                break;
            case 38:
                if (this._hero.getPosition().y - 1 >= 0)
                    this._hero.move(0, -1);
                else
                    key = -1;
                break;
            case 39:
                if (this._hero.getPosition().x + 1 < this._MapSize)
                    this._hero.move(1, 0);
                else
                    key = -1;
                break;
            case 40:
                if (this._hero.getPosition().y + 1 < this._MapSize)
                    this._hero.move(0, 1);
                else
                    key = -1;
                break;
            case -2:
                try {
                    String type = _view.get_Type().equals("gui") ? "console" : "gui";
                    _view.close();
                    this.startGame(type);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        if (key == -1) {
            if (_view.simpleDialog("End of map, start a new game?"))
                this.initNewGame();
            else {
                this.saveHero();
                System.exit(0);
            }
        }
    }

    private void handleCollisions() {
        for (int i = 0; i < this._foes.size(); i++)
            if ( this._foes.get(i).getPosition().equals( this._hero.getPosition() ) )
                this.manageBattle(this._foes.get(i));
    }

    private void initNewGame() {
        this._hero.getPosition().setLocation( _MapSize >> 1, _MapSize >> 1);
        this._hero.setHP( _hero.getMaxHp() );
        this.dropEnemies();
    }

    private void manageBattle(Foe foe)
    {
        Point enemyPos = foe.getPosition();

        this._view.addLog("You met an opponent:\n    hp: " + foe.getHP() + "\n    attack: "
            + foe.getAttack() + "\n    defense: " + foe.getDefense());

        if ( _view.simpleDialog("Do you want a battle ?") )
            this.battleAlgorithm(foe);
        else {
            if ( _random.nextInt(2) % 2 == 0 ) {
                this._hero.setPosition(new Point(this._hero.getOldPosition()));
                this._view.addLog("You are lucky to escape");
            }
            else {
                this._view.addLog("Run away failed");
                this.battleAlgorithm(foe);
            }
        }
        if ( !enemyPos.equals(_hero.getPosition()) )
            return ;
        if (foe.getHP() <= 0) {
            this._hero.setExperience(_hero.getExperience() + ((foe.getAttack() + foe.getDefense()) << 3 ));
            this._view.addLog("Opponent killed ! Raised " + (foe.getAttack() + foe.getDefense() << 3) + " experience !" );
            this._foes.remove(foe);
            this.manageMyHero(foe);
        }
        else
            _hero.setPosition(new Point(_hero.getOldPosition()));
    }

    private void battleAlgorithm(Foe foe) {
        if (_random.nextInt(7) == 6) {
            foe.setHP(0);
            this._view.addLog("Critical hit !!!");
        }
        else {
            this._hero.setHP( _hero.getHP() - (foe.getAttack() << 2) + _hero.getDefensePower());
            if (this.checkDeath())
                return;
            foe.setHP(foe.getHP() - _hero.getAttackPower() + foe.getDefense() );
            int raisedDamage = (foe.getAttack() << 2) - _hero.getDefensePower();
            this._view.addLog("You caused " + (_hero.getAttackPower() - foe.getDefense())
                + " damage to the opponent !\n" + ( raisedDamage < 0 ? " Blocked up all" : " Raised " + raisedDamage ) + " damage." );
        }
    }

    private void manageMyHero(Foe foe) {
        if (_hero.getExperience() >= _hero.getExperienceGained()) {
            _view.addLog("Level up ! Skills increased !");
            _hero.setMaxHp(_hero.getMaxHp() + (4 << _hero.getLevel()));
            _hero.setHP(_hero.getMaxHp());
            _hero.setAttack(_hero.getAttack() + (_hero.getLevel() << 2));
            _hero.setDefense(_hero.getDefense() + (_hero.getLevel() << 1));
            _hero.setLevel(_hero.getLevel() + 1);
            _MapSize = (_hero.getLevel() - 1) * 5 + 10 - (_hero.getLevel() % 2);
            this.dropEnemies();
        }
        this.manageBonuses(foe);
    }

    private boolean   checkDeath() {
        if (_hero.getHP() <= 0) {
            this._view.updateData();
            if (_view.simpleDialog("You died, respawn at center of map ?"))
                this.initNewGame();
            else {
                this.saveHero();
                System.exit(0);
            }
            return true;
        }
        return false;
    }

    private void manageBonuses(Foe foe) {
        if (_random.nextInt(3) == 2) {
            if (_random.nextInt(2) == 0) {
                int up = _random.nextInt(30) + 5;
                _hero.setHP(_hero.getHP() + up);
                _view.addLog("Found health elixir + " + up + " hp !");
            }
            else
                this.manageArtifacts(foe);
        }
    }

    private void dropEnemies() {
        this._foes.clear();
        FoeGenerator foeGenerator = new FoeGenerator();
        for (int i = _random.nextInt(_MapSize) + _MapSize; i > 0; i--)
            _foes.add(foeGenerator.GenerateFoe(_MapSize, _foes, _hero));
    }

    private void  manageArtifacts(Foe foe) {
        String artifact = _random.nextInt(2) == 0 ? "attack" : "defense";
        int value = ((artifact.equals("attack") ? foe.getAttack() : foe.getDefense() ) >> 1) + 1;
        if ( _view.simpleDialog("Found " + artifact + " artifact (" + value + ") pick it up ?") ) {
            _hero.setArtifact(new Artifact(value, artifact));
            _view.addLog("New artifact equipped");
        }
    }

    private void changeView(String ViewName) {
        this._view = ViewName.equals("gui") ? new GUIView(this) : new ConsoleView(this);
    }

    public Hero getHero() {
        return _hero;
    }

    public void setHero(Hero _hero) {
        this._hero = _hero;
    }

    public int getSizeMap() {
        return _MapSize;
    }

    public ArrayList<Foe> getEnemies() {
        return _foes;
    }

}