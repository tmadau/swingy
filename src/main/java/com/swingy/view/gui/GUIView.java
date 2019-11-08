package com.swingy.view.gui;

import com.swingy.utilities.util.Utilities;
import com.swingy.view.gui.GUISelectHero;
import com.swingy.controller.GameEngine;
import com.swingy.view.gui.GUIGameLog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import com.swingy.view.Viewable;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.awt.Point;

@SuppressWarnings("serial")
public class GUIView extends JFrame implements Viewable {

    private Utilities _utilities;
    private GameEngine _gameEngine;
    private KeyAdapter _keySupporter;
    private GUIPanel _panel;
    private GUIMapPanel _map;
    private JScrollPane _scrollMap;
    private JScrollPane _scrollGameLog;
    private int _squareSize;
    private GUIStats _stats;
    private GUIGameLog _gameLog;
    private String _type;

    public GUIView(GameEngine gameEngine) {
        super("Swingy");
        
        if (_utilities == null) {
            _utilities = new Utilities();
        }

        this.setBounds(500, 250, 1200, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (_gameEngine.getHero() != null) {
                    _gameEngine.saveHero();
                }
            }
        });

        this._gameEngine = gameEngine;
        this._keySupporter = new KeySupporter();
        this.setVisible(true);
        this.setFocusable(true);
        this._panel = new GUIPanel();
        this._squareSize = 70;
        setContentPane(_panel);
        this._type = "gui";
        this.addKeyListener(this._keySupporter);
    }

    @Override
    public void SelectHero() throws Exception {
        this._gameEngine.setHero(new GUISelectHero(_panel).SelectHero());
    }

    @Override
    public void drawGameObjects() {
        this.initScrolls();
        _stats = new GUIStats(_gameEngine.getHero());
        _stats.updateData();
        _panel.add(_scrollGameLog);
        _panel.add(_stats);
        _panel.add(_scrollMap);
        _panel.revalidate();
        _panel.repaint();
    }

    @Override
    public void viewRepaint() {
        _panel.repaint();
    }

    @Override
    public void scrollPositionManager() {
        Point newPosition = new Point(_gameEngine.getHero().getPosition().x * _squareSize - 275,
                _gameEngine.getHero().getPosition().y * _squareSize - 275);
        newPosition.y = newPosition.y <= 0 ? _scrollMap.getViewport().getViewPosition().y : newPosition.y;
        newPosition.x = newPosition.x <= 0 ? _scrollMap.getViewport().getViewPosition().x : newPosition.x;
        this._scrollMap.getViewport().setViewPosition(newPosition);
    }

    @Override
    public void updateData() {
        this._stats.updateData();
    }

    @Override
    public void   addLog(String text) {
        _gameLog.append(" " + text + "\n");
        this._scrollGameLog.getViewport().setViewPosition(new Point(_scrollGameLog.getViewport().getViewPosition().x,
                _scrollGameLog.getViewport().getViewPosition().y + 30));
    }

    @Override
    public boolean simpleDialog(String message) {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, message, "You have a choice", dialogButton);
        return dialogResult == 0;
    }

    @Override
    public String get_Type() {
        return this._type;
    }

    @Override
    public void close() {
        setVisible(false);
        dispose();
    }

    private void initScrolls() {
        this._map = new GUIMapPanel(this._gameEngine, this._squareSize);
        this._scrollMap = new JScrollPane(this._map);
        this._scrollMap.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this._scrollMap.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this._scrollMap.setBounds(500, 50, 650, 650);
        this._scrollMap.getViewport().setViewPosition( new Point(_gameEngine.getHero().getPosition().x * 70 - 275, _gameEngine.getHero().getPosition().x * 70 - 275) );
        this._scrollMap.repaint();
        this._gameLog = new GUIGameLog();
        this._scrollGameLog = new JScrollPane(this._gameLog);
        this._scrollGameLog.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this._scrollGameLog.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this._scrollGameLog.setBounds(50, 565, 325, 400);
        this._scrollGameLog.repaint();
    }

    private class KeySupporter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (_gameEngine.getHero() != null) {
                if ((e.getKeyCode() > 36 && e.getKeyCode() < 41))
                    _gameEngine.keyPressed(e.getKeyCode());
                else if (e.getKeyCode() == 49)
                    _gameEngine.keyPressed(-2);
            }
        }
    }

}
