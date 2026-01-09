package rpg.decorator;

import rpg.core.Character;

public abstract class CharacterDecorator extends Character {

    protected Character character;

    public CharacterDecorator(Character character) {
        super(character.getName(), 0, 0, 0);
        this.character = character;
    }

    // ✅ Délégation des données de base
    @Override
    public String getName() {
        return character.getName();
    }

    @Override
    public int getStrength() {
        return character.getStrength();
    }

    @Override
    public int getAgility() {
        return character.getAgility();
    }

    @Override
    public int getIntelligence() {
        return character.getIntelligence();
    }

    // (optionnel mais utile)
    @Override
    public String toString() {
        return character.toString(); // ou return getDescription();
    }

    @Override
    public abstract int getTotalStats();

    @Override
    public abstract String getDescription();
}
