package rpg.decorator;

import rpg.core.Character;

public class Invisibility extends CharacterDecorator{
    public Invisibility(Character character) {
        super(character);
    }

    @Override
    public int getTotalStats() {
        return character.getTotalStats() + 10 ;
    }

    @Override
    public String getDescription() {
        return character.getDescription()+"+ Ability(Invisibility)";
    }
}
