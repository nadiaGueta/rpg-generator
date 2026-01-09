package rpg.command;

import rpg.combat.CombatService;
import rpg.combat.FighterState;

public class AttackCommand implements Command {
    private final CombatService combat;
    private final FighterState attacker;
    private final FighterState defender;

    public AttackCommand(CombatService combat, FighterState attacker, FighterState defender) {
        this.combat = combat;
        this.attacker = attacker;
        this.defender = defender;
    }

    @Override
    public void execute() {
        System.out.println(combat.attack(attacker, defender));
    }

    @Override
    public String name() {
        return "ATTACK(" + attacker.getCharacter().getName() + " -> " + defender.getCharacter().getName() + ")";
    }


    @Override
    public String description() {
        return attacker.getCharacter().getName() + " attaque " + defender.getCharacter().getName();
    }
}
