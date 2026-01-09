package rpg.decorator;

import rpg.core.Character;

public class FireResistance extends CharacterDecorator {
    public FireResistance(Character character) {
        super(character);
    }

    @Override
    public int getTotalStats() {
        return character.getTotalStats() + 15;
    }

    @Override
    public String getDescription() {
        return character.getDescription() + " + Ability(Fire Resistance)";
    }
}
