package rpg.combat;

import rpg.core.Character;

public class FighterState {
    private final Character character;
    private int hp;
    private boolean defending;

    public FighterState(Character character, int hp) {
        this.character = character;
        this.hp = hp;
        this.defending = false;
    }

    public Character getCharacter() { return character; }
    public int getHp() { return hp; }
    public boolean isDefending() { return defending; }

    public void setDefending(boolean defending) { this.defending = defending; }

    public void damage(int amount) {
        hp = Math.max(0, hp - Math.max(0, amount));
    }

    public boolean isAlive() { return hp > 0; }
}
