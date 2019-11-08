package com.swingy.view.gui;

import com.swingy.utilities.database.Database;
import com.swingy.model.heroes.HeroGenerator;
import com.swingy.utilities.util.Utilities;
import com.swingy.model.heroes.Hero;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GUISelectHero {
    
    private GUIPanel _panel;
    private JTextField _inputName;
    private JComboBox<String> _heroTypes;
    private JComboBox<String> _SavedHeroes;
    private GUIStats _stats;
    private Hero _selectedHero;
    private HeroGenerator _generate;
    private Map<String, JLabel> _labels;
    private Map<String, JButton> _buttons;

    public GUISelectHero(GUIPanel panel) {
        this._panel = panel;
        String HeroNames[] = {"Knight", "Saiyan", "Soldier"};
        _inputName = new JTextField("", 5);
        _heroTypes = new JComboBox<>(HeroNames); // check
        _SavedHeroes = new JComboBox<>(); // check
        _generate = new HeroGenerator();
        _labels = new HashMap<>();
        _labels.put("Name", new JLabel("Name:"));
        _labels.put("Old", new JLabel("Previous saved heroes:"));
        _buttons = new HashMap<>();
        _buttons.put("create", new JButton("Create new hero"));
        _buttons.put("select", new JButton("Select"));
        _buttons.put("remove", new JButton("Remove"));
    }

    public Hero SelectHero() {
        Database.getDatabase().connectDb();

        prepareObjects();
        addObjOnFrame();

        _panel.revalidate();
        _panel.repaint();

        while (true) {
            if (_panel.getComponents().length == 0)
                break;
        }
        _panel.revalidate();
        _panel.repaint();

        return _selectedHero;
    }

    private void prepareObjects() {
        prepareLabels();
        prepareNameField();
        prepareBoxes();
        prepareButtons();
    }

    private void prepareLabels() {
        _labels.get("Name").setLocation(950, 50);
        _labels.get("Name").setSize(150, 100);
        _labels.get("Name").setFont(Utilities.font2);
        _labels.get("Old").setLocation(85, 100);
        _labels.get("Old").setSize(200, 100);
        _labels.get("Old").setFont(Utilities.font2);
    }

    private void prepareNameField() {
        _inputName.setLocation(900, 125);
        _inputName.setSize(150, 35);
        _inputName.setFont(Utilities.font3);
    }

    private void prepareBoxes() {
        _heroTypes.setLocation(898, 175);
        _heroTypes.setSize(160, 50);
        _heroTypes.addItemListener((ItemEvent e) -> {
                this._stats.setHero(_generate.GenerateHero(_heroTypes.getSelectedItem().toString()));
                this._stats.updateData();
            }
        );

        List<String> names = Database.getDatabase().getNames();
        for (String name : names) {
            _SavedHeroes.addItem(name);
        }
        _SavedHeroes.setLocation(100, 175);
        _SavedHeroes.setSize(160, 50);
        _SavedHeroes.addItemListener((ItemEvent e) -> {
            try {
                Hero newHero = Database.getDatabase().getHero( (String)_SavedHeroes.getSelectedItem() );
                if (newHero == null) {
                    newHero = _generate.GenerateHero(_heroTypes.getSelectedItem().toString());
                }

                _stats.setHero(newHero);
                _stats.updateData();
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        _stats = new GUIStats(!names.isEmpty() ? Database.getDatabase().getHero(_SavedHeroes.getSelectedItem().toString()) :
                _generate.GenerateHero(_heroTypes.getSelectedItem().toString()));
        _stats.setLocation(400, 400);
    }

    private void    prepareButtons() {
        _buttons.get("create").setLocation(875, 250);
        _buttons.get("create").setSize(210, 25);
        _buttons.get("create").addActionListener((ActionEvent e) -> tryCreateNewHero());
        _buttons.get("create").setFont(Utilities.font3);

        _buttons.get("select").setLocation(75, 250);
        _buttons.get("select").setSize(100, 25);
        _buttons.get("select").addActionListener((ActionEvent e) -> {
            if (_SavedHeroes.getItemCount() == 0) {
                JOptionPane.showMessageDialog(_panel, "You haven't any hero, create him");
            }
            else {
                selectHero();
                _panel.removeAll();
            }
        });
        _buttons.get("select").setFont(Utilities.font3);
        _buttons.get("remove").setLocation(185, 250);
        _buttons.get("remove").setSize(100, 25);
        _buttons.get("remove").addActionListener((ActionEvent e) -> tryRemoveHero());
        _buttons.get("remove").setFont(Utilities.font3);
    }

    private void    addObjOnFrame() {
        _panel.add(_labels.get("Name"));
        _panel.add(_labels.get("Old"));
        _panel.add(_inputName);
        _panel.add(_heroTypes);
        _panel.add(_SavedHeroes);
        _panel.add(_stats);
        _panel.add(_buttons.get("create"));
        _panel.add(_buttons.get("select"));
        _panel.add(_buttons.get("remove"));

        _labels.get("Name").repaint();
        _labels.get("Old").repaint();
        _inputName.repaint();
        _heroTypes.repaint();
        _SavedHeroes.repaint();
        _stats.repaint();
        _stats.updateData();
        _buttons.get("create").repaint();
        _buttons.get("select").repaint();
        _buttons.get("remove").repaint();
    }

    private void tryCreateNewHero() {
        Hero newHero = _generate.GenerateHero(_heroTypes.getSelectedItem().toString());
        String error = _generate.SetNameCheck(newHero, _inputName.getText());

        for (int i = 0; i < _SavedHeroes.getItemCount(); ++i) {
            if (_SavedHeroes.getItemAt(i).equals(_inputName.getText())) {
                error = "Hero with that name already created";
            }
        }

        if (!error.isEmpty()) {
            JOptionPane.showMessageDialog(_panel, error);
        }
        else {
            Database.getDatabase().addNewHero(newHero);
            _SavedHeroes.addItem(_inputName.getText());
            _inputName.setText("");
        }
    }

    private void tryRemoveHero() {
        Object heroToRemoving = _SavedHeroes.getSelectedItem();
        Database.getDatabase().remove(heroToRemoving.toString());
        _SavedHeroes.removeItem(heroToRemoving);
    }

    private void selectHero() {
        this._selectedHero = Database.getDatabase().getHero(_SavedHeroes.getSelectedItem().toString());
    }

}
