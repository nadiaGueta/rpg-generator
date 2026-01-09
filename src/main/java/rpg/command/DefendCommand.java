package rpg.command;

import rpg.combat.CombatService;
import rpg.combat.FighterState;

public class DefendCommand implements Command {
    private final CombatService combat;
    private final FighterState defender;

    public DefendCommand(CombatService combat, FighterState defender) {
        this.combat = combat;
        this.defender = defender;
    }
    @Override
    public String description() {
        return defender.getCharacter().getName() + " se défend";
    }

    @Override
    public void execute() {
        combat.defend(defender);
    }
    @Override
    public String name() {
        return "DEFEND(" + defender.getCharacter().getName() + ")";
    }
}
