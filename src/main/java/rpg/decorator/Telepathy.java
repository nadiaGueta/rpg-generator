package rpg.decorator;

import rpg.core.Character;

public class Telepathy extends CharacterDecorator{


    public Telepathy(Character character) {
        super(character);
    }

    @Override
    public int getTotalStats() {
        return character.getTotalStats()+ 20 ;
    }

    @Override
    public String getDescription() {
        return character.getDescription() + " + Ability(Telepathy)";
    }
}
