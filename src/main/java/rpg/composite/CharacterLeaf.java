package rpg.composite;

import rpg.core.Character;

public class CharacterLeaf implements GroupComponent{

    protected Character character ;
public CharacterLeaf (Character character){
    this.character = character ;

}

    @Override
    public int getPower() {
        return character.getTotalStats();
    }

    @Override
    public void printDetails(String indent) {
        System.out.println(indent + "- " + character.getDescription() + " (Power=" + character.getTotalStats() + ")");

    }
}
