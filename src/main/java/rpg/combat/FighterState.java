package rpg.combat;

import rpg.core.Character;
import rpg.observer.StateObserver;

import java.util.ArrayList;
import java.util.List;

public class FighterState {
    private final Character character;
    private int hp;
    private boolean defending;

    private final List<StateObserver> observers = new ArrayList<>();

    public FighterState(Character character, int hp) {
        this.character = character;
        this.hp = hp;
        this.defending = false;
    }

    // --- Observer ---
    public void addObserver(StateObserver o) {
        observers.add(o);
    }

    private void notifyObservers() {
        for (StateObserver o : observers) {
            o.onStateChanged(this);
        }
    }

    // --- getters ---
    public Character getCharacter() { return character; }
    public int getHp() { return hp; }
    public boolean isDefending() { return defending; }

    public void setDefending(boolean defending) {
        this.defending = defending;
        notifyObservers(); // optionnel mais propre
    }

    public void damage(int amount) {
        hp = Math.max(0, hp - Math.max(0, amount));
        notifyObservers(); // ✅ ICI : synchro auto
    }

    public boolean isAlive() { return hp > 0; }
}
