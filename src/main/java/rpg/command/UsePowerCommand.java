package rpg.command;

import rpg.combat.CombatService;
import rpg.combat.FighterState;

public class UsePowerCommand implements Command {
    private final CombatService combat;
    private final FighterState user;

    public UsePowerCommand(CombatService combat, FighterState user) {
        this.combat = combat;
        this.user = user;
    }

    @Override
    public void execute() {
        combat.usePower(user);
    }

    @Override
    public String name() {
        return "POWER(" + user.getCharacter().getName() + ")";
    }

    @Override
    public String description() {
        return user.getCharacter().getName() + " utilise son pouvoir";
    }
}
