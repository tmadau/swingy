package com.swingy.model.items;

public class Artifact {

    private int _Loot;
    private String _Type;

    public Artifact(int Loot, String Type) {
        this._Loot = Loot;
        this._Type = Type;
    }

    // Getters
    public int getLoot() {
        return _Loot;
    }
    public String getType() {
        return _Type;
    }

}