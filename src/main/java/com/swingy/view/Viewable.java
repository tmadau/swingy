package com.swingy.view;

import java.lang.Exception;

public interface Viewable {

    void SelectHero() throws Exception;
    void drawGameObjects();
    void viewRepaint();
    void scrollPositionManager();
    void updateData();
    void addLog(String text);
    boolean simpleDialog(String message);
    String get_Type();
    void close();

}
