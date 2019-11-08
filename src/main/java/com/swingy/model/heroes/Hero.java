package com.swingy.model.heroes;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.swingy.model.items.Artifact;
import java.awt.Point;

public class Hero {

    @Pattern(regexp = "^[0-9A-Za-z]+", message = "Allowed input only characters and numbers")
    @Size(min = 4, max = 12, message = "Name must between 4 - 12")
    private String _HeroName;
    private String _HeroClass;
    private int _Level;
    private int _Experience;
    private int _Attack;
    private int _Defense;
    private int _HP;
    private int _MaxHP;
    private Point _position;
    private Point _oldPosition;
    private Artifact _artifact;

    public Hero() {
        _position = new Point(0, 0);
        _oldPosition = new Point(0, 0);
        _artifact = null;
    }

    public String getHeroStats() {
        String HeroStats = "\n Type: ".concat(_HeroClass)
                .concat("\n\n Level: ").concat(String.valueOf(_Level))
                .concat("\n\n Experience: ").concat(String.valueOf(_Experience))
                .concat("\n\n Attack: ").concat(String.valueOf(_Attack))
                .concat("\n\n Defense: ").concat(String.valueOf(_Defense))
                .concat("\n\n HP: ").concat(String.valueOf(_HP));
        return HeroStats;
    }

    public void move(int x, int y) {
        _oldPosition.setLocation(_position.x, _position.y);
        _position.setLocation(_position.x + x, _position.y + y);
    }

    // Getters
    public String getHeroName() {
        return _HeroName;
    }
    public String getHeroClass() {
        return _HeroClass;
    }
    public int getLevel() {
        return _Level;
    }
    public int getExperienceGained() {
        return (int)(_Level * 1000 + Math.pow(_Level - 1, 2) * 450);
    }
    public int getExperience() {
        return _Experience;
    }
    public int getAttackPower() {
        return (_artifact != null && _artifact.getType().equals("attack")) ? ((_Attack + _artifact.getLoot()) << 2) : _Attack << 2;
    }
    public int getAttack() {
        return _Attack;
    }
    public int getDefensePower() {
        return (_artifact != null && _artifact.getType().equals("defense")) ? _Defense + _artifact.getLoot() : _Defense;
    }
    public int getDefense() {
        return _Defense;
    }
    public int getHP() {
        return _HP;
    }
    public int getMaxHp() {
        return _MaxHP;
    }
    public Point getPosition() {
        return _position;
    }
    public Point getOldPosition() {
        return _oldPosition;
    }
    public Artifact getArtifact() {
        return _artifact;
    }

    // Setters
    public void setHeroName(String HeroName) {
        this._HeroName = HeroName;
    }
    public void setHeroclass(String HeroClass) {
        this._HeroClass = HeroClass;
    }
    public void setLevel(int Level) {
        this._Level = Level;
    }
    public void setExperience(int Experience) {
        this._Experience = Experience;
    }
    public void setAttack(int Attack) {
        this._Attack = Attack;
    }
    public void setDefense(int Defense) {
        this._Defense = Defense;
    }
    public void setHP(int HP) {
        if (HP < 0)
            this._HP = 0;
        else if (HP > _MaxHP)
            this._HP = _MaxHP;
        else
            this._HP = HP;
    }
    public void setMaxHp(int MaxHP) {
        this._MaxHP = MaxHP;
    }
    public void setPosition(Point position) {
        this._position = position;
    }
    public void setArtifact(Artifact artifact) {
        this._artifact = artifact;
    }

}
