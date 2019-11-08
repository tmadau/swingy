package com.swingy.view.gui;

import com.swingy.utilities.util.Utilities;
import com.swingy.model.heroes.Hero;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.HashMap;
import java.awt.Graphics;
import java.util.Map;

@SuppressWarnings("serial")
public class GUIStats extends JPanel {
    
    private final String _ASSETS;
    private Hero _hero;
    private Map<String, JLabel> _stats;

    public GUIStats(Hero hero) {
        _ASSETS = Utilities.findPathToResources().concat("icons/");
        this._hero = hero;
        _stats = new HashMap<>();
        setLayout(null);
        setSize(325, 500);
        setLocation(50, 50);
    }

    @Override
    public void paint(Graphics draw) {
        super.paint(draw);
        draw.drawRect(1, 1, 323, 498);
    }

    public void updateData() {
        if (_stats.isEmpty()) {
            prepareInfo();
        }

        _stats.get("name").setText("Name: ".concat(_hero.getHeroName() == null ? "" : _hero.getHeroName()));
        _stats.get("type").setText("Type: ".concat(_hero.getHeroClass()));

        if (_hero.getPosition() != null) {
            _stats.get("location").setText("Location: [".concat(String.valueOf(_hero.getPosition().x)).concat(", ")
                    .concat(String.valueOf(_hero.getPosition().y)).concat("]"));
        }

        _stats.get("level").setText("Level: " + _hero.getLevel());
        _stats.get("exp").setText("Exp: " + _hero.getExperience() + "/" + _hero.getExperienceGained());
        _stats.get("attack").setText(String.valueOf(_hero.getAttack()));
        _stats.get("defense").setText(" " + _hero.getDefense());
        _stats.get("hp").setText(" " + _hero.getHP() + "/" + _hero.getMaxHp());

        if (_hero.getArtifact() != null && !_hero.getArtifact().getType().equals("")) {
            _stats.get("artifact").setIcon(new ImageIcon(
                    _ASSETS.concat(_hero.getArtifact().getType().equals("attack") ? "artifactA.png" : "artifactD.png"))
            );
            _stats.get("artifact").setText(" " + _hero.getArtifact().getLoot());
        }
        else {
            _stats.get("artifact").setText("");
            _stats.get("artifact").setIcon(null);
        }
    }

    private void prepareInfo() {
        JLabel name = new JLabel("Name: ");
        name.setLocation(20, 5);
        name.setSize(225, 75);
        name.setFont(Utilities.bigFont);
        if (_hero.getHeroName() != null) {
            name.setText("Name: " + _hero.getHeroName());
        }

        JLabel type = new JLabel("Type: " + _hero.getHeroClass());
        type.setLocation(20, 55);
        type.setSize(150, 50);
        type.setFont(Utilities.bigFont);

        JLabel level = new JLabel("Level: " + _hero.getLevel());
        level.setLocation(20, 105);
        level.setSize(150, 50);
        level.setFont(Utilities.bigFont);

        JLabel location = new JLabel("Location: [ ]");
        location.setLocation(20, 155);
        location.setSize(200, 50);
        location.setFont(Utilities.bigFont);
        if (_hero.getPosition() != null) {
            location.setText("Location: [" + _hero.getPosition().x + ", " + _hero.getPosition().y + "]");
        }

        JLabel exp = new JLabel("Exp: " + _hero.getExperience() + "/" + _hero.getExperienceGained() );
        exp.setLocation(20, 205);
        exp.setSize(320, 50);
        exp.setFont(Utilities.bigFont);

        JLabel attack = new JLabel( String.valueOf(_hero.getAttack()), new ImageIcon(_ASSETS.concat("sword.png")), JLabel.LEFT);
        attack.setLocation(20, 260);
        attack.setSize(200, 50);
        attack.setFont(Utilities.bigFont);

        JLabel defense = new JLabel( " " + _hero.getDefense(), new ImageIcon(_ASSETS.concat("shield.png")), JLabel.LEFT);
        defense.setLocation(15, 315);
        defense.setSize(200, 50);
        defense.setFont(Utilities.bigFont);

        JLabel hp = new JLabel( " " + _hero.getHP() + "/" + this._hero.getMaxHp(), new ImageIcon(_ASSETS.concat("hp.png")), JLabel.LEFT);
        hp.setLocation(15, 370);
        hp.setSize(320, 50);
        hp.setFont(Utilities.bigFont);
        hp.setVerticalTextPosition(JLabel.NORTH);

        JLabel artifact = new JLabel("");
        if (_hero.getArtifact() != null)
            artifact.setIcon( new ImageIcon(_ASSETS.concat(_hero.getArtifact().getType().equals("attack") ? "artifactA.png" : "artifactD.png")));
        artifact.setLocation(15, 430);
        artifact.setSize(200, 50);
        artifact.setFont(Utilities.bigFont);

        this._stats.put("name", name);
        this._stats.put("type", type);
        this._stats.put("level", level);
        this._stats.put("location", location);
        this._stats.put("attack", attack);
        this._stats.put("defense", defense);
        this._stats.put("hp", hp);
        this._stats.put("exp", exp);
        this._stats.put("artifact", artifact);
        this._stats.put("artifact", artifact);

        this.add(name);
        this.add(type);
        this.add(level);
        this.add(location);
        this.add(exp);
        this.add(attack);
        this.add(defense);
        this.add(hp);
        this.add(artifact);
    }

    // Getters
    public Hero getHero() {
        return _hero;
    }
    // Setters
    public void setHero(Hero hero) {
        this._hero = hero;
    }
}
