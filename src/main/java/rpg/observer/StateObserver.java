package rpg.observer;



import rpg.combat.FighterState;

public interface StateObserver {
    void onStateChanged(FighterState state);
}
