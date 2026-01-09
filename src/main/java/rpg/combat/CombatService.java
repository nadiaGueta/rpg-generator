package rpg.combat;

import rpg.observer.CombatObserver;

import java.util.ArrayList;
import java.util.List;

public class CombatService {

    private final List<CombatObserver> observers = new ArrayList<>();

    public void addObserver(CombatObserver o) {
        observers.add(o);
    }

    private void notifyObservers(String msg) {
        for (CombatObserver o : observers) {
            o.onEvent(msg);
        }
    }

    public void attack(FighterState attacker, FighterState defender) {
        int power = attacker.getCharacter().getTotalStats();
        int damage = Math.max(1, power / 5);
        if (!attacker.isAlive()) {
            notifyObservers(attacker.getCharacter().getName() + " est KO et ne peut plus attaquer.");
            return;
        }
        if (!defender.isAlive()) {
            notifyObservers(defender.getCharacter().getName() + " est déjà KO.");
            return;
        }
        if (defender.isDefending()) {
            damage = Math.max(1, damage / 2);
            defender.setDefending(false);
        }

        defender.damage(damage);

        String msg = attacker.getCharacter().getName() + " attaque " +
                defender.getCharacter().getName() + " : -" + damage +
                " HP (HP " + defender.getCharacter().getName() + "=" + defender.getHp() + ")";

        notifyObservers(msg);

        if (!defender.isAlive()) {
            notifyObservers("🏆 KO: " + defender.getCharacter().getName());
        }
    }

    public void defend(FighterState defender) {
        if (!defender.isAlive()) {
            notifyObservers(defender.getCharacter().getName() + " est KO et ne peut plus se défendre.");
            return;
        }
        defender.setDefending(true);

        String msg = defender.getCharacter().getName()
                + " se défend (prochain dégât réduit)";
        notifyObservers(msg);
    }

    public void usePower(FighterState user) {
        if (!user.isAlive()) {
            notifyObservers(user.getCharacter().getName() + " est KO et ne peut pas utiliser de pouvoir.");
            return;
        }

        String msg = user.getCharacter().getName() + " utilise un pouvoir (MVP).";
        notifyObservers(msg);
    }



}
