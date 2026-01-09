package rpg.combat;

public class CombatService {

    public String attack(FighterState attacker, FighterState defender) {
        int power = attacker.getCharacter().getTotalStats();
        int damage = Math.max(1, power / 5); // simple

        if (defender.isDefending()) {
            damage = Math.max(1, damage / 2);
            defender.setDefending(false); // défense ne protège qu’un coup
        }

        defender.damage(damage);

        return attacker.getCharacter().getName() + " attaque " +
                defender.getCharacter().getName() + " : -" + damage +
                " HP (HP " + defender.getCharacter().getName() + "=" + defender.getHp() + ")";
    }

    public String defend(FighterState defender) {
        defender.setDefending(true);
        return defender.getCharacter().getName() + " se défend (prochain dégât réduit)";
    }

    public String usePower(FighterState user) {
        // Version minimale : juste un message.
        // Option mieux : boost temporaire => mais on garde simple pour MVP.
        return user.getCharacter().getName() + " utilise un pouvoir";
    }

    public String winner(FighterState a, FighterState b) {
        if (a.isAlive() && !b.isAlive()) return a.getCharacter().getName();
        if (!a.isAlive() && b.isAlive()) return b.getCharacter().getName();
        return "Aucun (égalité)";
    }
}
