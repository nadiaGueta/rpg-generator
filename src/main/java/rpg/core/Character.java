package rpg.core;


import rpg.builder.CharacterBuilder;

public class Character {
    private String name;
    private int strength;
    private int agility;
    private int intelligence;

    public Character(CharacterBuilder builder ) {
        this.name = builder.getName();
        this.strength = builder.getStrength();
        this.agility = builder.getAgility();
        this.intelligence = builder.getIntelligence();
    }

    //  utilisé par les décorateurs
    public Character(String name, int strength, int agility, int intelligence) {
        this.name = name;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
    }

    public String getName() {
        return name;
    }

    public int getTotalStats() {
        return strength + agility + intelligence;
    }


    public String getDescription() {
        return "Character{" +
                "name='" + name + '\'' +
                ", strength=" + strength +
                ", agility=" + agility +
                ", intelligence=" + intelligence +
                '}';
    }

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", strength=" + strength +
                ", agility=" + agility +
                ", intelligence=" + intelligence +
                '}';
    }


    public int getStrength() {
        return strength;
    }

    public int getAgility() {
        return agility;
    }

    public int getIntelligence() {
        return intelligence;
    }
}
