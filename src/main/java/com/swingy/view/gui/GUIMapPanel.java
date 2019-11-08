package com.swingy.view.gui;

import com.swingy.utilities.util.Utilities;
import com.swingy.controller.GameEngine;
import com.swingy.model.foe.Foe;
import java.util.ArrayList;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;

@SuppressWarnings("serial")
public class GUIMapPanel extends JPanel {
    
    private GameEngine _gameEngine;
    private int _sizeSquare;

    public GUIMapPanel(GameEngine gameEngine, int sizeSquare) {
        this._gameEngine = gameEngine;
        this._sizeSquare = sizeSquare;
        setLayout(null);
        setDoubleBuffered(true);
    }

    @Override
    public void  paint(Graphics draw) {
        super.paint(draw);
        Graphics2D draw2d = (Graphics2D)draw;
        this.drawMap(draw2d);
        this.drawHero(draw2d);
        this.drawEnemies(draw2d);
    }

    private void drawMap(Graphics2D draw2d) {
        setPreferredSize(new Dimension(_sizeSquare * _gameEngine.getSizeMap(), _sizeSquare * _gameEngine.getSizeMap()));

        for (int i = 0; i < _gameEngine.getSizeMap(); ++i) {
            for (int j = 0; j < _gameEngine.getSizeMap(); ++j) {
                draw2d.drawRect(_sizeSquare * j, _sizeSquare * i, _sizeSquare, _sizeSquare);
            }
        }
    }

    private void drawHero(Graphics2D draw2d) {
        Image img = getToolkit().getImage(Utilities.findPathToResources().concat("characters/")
                .concat(_gameEngine.getHero().getHeroClass()).concat(".png"));
        prepareImage(img, this);
        draw2d.setColor(new Color(101, 255, 0, 75));
        draw2d.fillRect(_gameEngine.getHero().getPosition().x * _sizeSquare, _gameEngine.getHero().getPosition().y * _sizeSquare, _sizeSquare, _sizeSquare);
        draw2d.drawImage(img, _gameEngine.getHero().getPosition().x * _sizeSquare + (_sizeSquare >> 2), _gameEngine.getHero().getPosition().y * _sizeSquare, this);
    }

    private void drawEnemies(Graphics2D draw2d) {
        ArrayList<Foe> foes = _gameEngine.getEnemies();
        String pathToResources = Utilities.findPathToResources();

        for (Foe foe : foes) {
            Image image = getToolkit().getImage(pathToResources.concat("characters/enemy")
                    .concat(String.valueOf(foe.getIMGCount())).concat(".png"));
            prepareImage(image, this);
            draw2d.drawImage(image, foe.getPosition().x * _sizeSquare + (_sizeSquare >> 2), foe.getPosition().y * _sizeSquare, this);
        }
    }

}
