package com.swingy.model.foe;

import java.awt.Point;

public class Foe {

    private int _Attack;
    private int _Defense;
    private int _HP;
    private int _IMGCount;
    private Point _position;

    // Getters
    public int getAttack() {
        return _Attack;
    }
    public int getDefense() {
        return _Defense;
    }
    public int getHP() {
        return _HP;
    }
    public int getIMGCount() {
        return _IMGCount;
    }
    public Point getPosition() {
        return _position;
    }

    // Setters
    public void setAttack(int Attack) {
        this._Attack = Attack;
    }
    public void setDefense(int Defense) {
        this._Defense = Defense;
    }
    public void setHP(int HP) {
        this._HP = HP;
    }
    public void setIMGCount(int IMGCount) {
        this._IMGCount = IMGCount;
    }
    public void setPosition(Point position) {
        this._position = position;
    }

}
