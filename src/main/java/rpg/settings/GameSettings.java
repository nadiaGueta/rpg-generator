package rpg.settings;

import rpg.core.Character;
//Le Singleton permet de centraliser les règles globales du jeu
// et de garantir une configuration unique partagée par toute l’application
public class GameSettings {

    private int maxStatPoints = 30;

    //instance singleton

    private static GameSettings instance = new GameSettings();

    //Globale
    public static GameSettings getInstance(){

        return instance ;
    }

    public boolean isValid(Character c){

        return c.getTotalStats() <= maxStatPoints;
    };


    //getter et setter
    public int getMaxStatPoints() {
        return maxStatPoints;
    }

    public void setMaxStatPoints(int maxStatPoints) {
        this.maxStatPoints = maxStatPoints;
    }





}
