package com.swingy.utilities.database;

import com.swingy.model.heroes.HeroGenerator;
import com.swingy.model.heroes.Hero;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class Database {
    
    private static Database database = null;
    private static Statement statement;
    private static ResultSet info;
    private final String driverName;
    private final String connectionString;
    private Connection connect;

    private Database() {
        this.driverName  = "org.sqlite.JDBC";
        this.connectionString = "jdbc:sqlite:".concat(System.getProperty("user.dir")).concat("/heroes.db");
        this.connect = null;
    }

    public static Database getDatabase() {
        if (database == null)
            database = new Database();
        return database;
    }

    public void connectDb() {
        if (connect == null) {
            try {
                Class.forName(driverName);
                connect = DriverManager.getConnection(connectionString);
                statement = connect.createStatement();
                statement.execute("CREATE  TABLE if not EXISTS 'heroes' ('name' text, 'class' text, 'level' INT, 'experience' INT," +
                        "'attack' INT, 'defense' INT, 'HP' INT, 'maxHP' INT, 'artifactName' text, 'artifactValue' INT);");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        try {
            info = statement.executeQuery("SELECT * FROM heroes");
            while (info.next())
                names.add(info.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

    public void addNewHero(Hero newHero) {
        String artifactType = newHero.getArtifact() == null ? "" : newHero.getArtifact().getType();
        int artifactLoot = artifactType == "" ? 0 : newHero.getArtifact().getLoot();

        String  requestAdd = "VALUES ('" + newHero.getHeroName() + "', '" + newHero.getHeroClass() + "', " + newHero.getLevel() + "," +
                newHero.getExperience() + "," + newHero.getAttack() + "," + newHero.getDefense() + "," + newHero.getHP() + ","
                + newHero.getMaxHp() + ",'" + artifactType + "'," + artifactLoot + ");";

        try {
            statement.execute("INSERT INTO 'heroes' ('name', 'class', 'level', 'experience', 'attack', 'defense', 'HP', 'maxHP', 'artifactName', 'artifactValue')" + requestAdd );
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(String name) {
        try {
            statement.execute("DELETE FROM heroes WHERE name = '" + name + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Hero getHero(String name) {
        try {
            info = statement.executeQuery("SELECT * FROM heroes where name = '" + name + "';");
            if (info.next()) {
                return new HeroGenerator().HeroStats(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateHero(Hero hero) {
        String request = "UPDATE heroes SET level = " + hero.getLevel() + ", experience = " + hero.getExperience() +
                ", attack = " + hero.getAttack() + ", defense = " + hero.getDefense() + ", HP = " + hero.getMaxHp() +
                ", maxHP = " + hero.getMaxHp() + ", artifactName = '" + ( hero.getArtifact() == null ? "" : hero.getArtifact().getType() ) +
                "' , artifactValue = " + hero.getArtifact().getLoot() + " WHERE name = '" + hero.getHeroName() + "';";
        try {
            statement.execute(request);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
