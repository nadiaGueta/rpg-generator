package rpg.decorator;

import rpg.core.Character;

public abstract class CharacterDecorator extends Character {

    protected  Character character ;

    // On appelle le constructeur de Character pour avoir une base,
    // mais les vraies valeurs/pouvoir viennent de "character"
    public CharacterDecorator (Character character){
        super(character.getName(),
        0 , 0 ,0 );

        this.character = character;
    }

    @Override
    public abstract int getTotalStats();

    @Override
    public abstract String getDescription();
}
