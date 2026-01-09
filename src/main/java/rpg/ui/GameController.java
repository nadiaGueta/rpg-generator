package rpg.ui;

import rpg.combat.CombatService;
import rpg.combat.FighterState;
import rpg.command.AttackCommand;
import rpg.command.CommandManager;
import rpg.command.DefendCommand;
import rpg.core.Character;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController {

    private final GameFrame view;

    private final CombatService combat = new CombatService();
   // private final CommandManager manager = new CommandManager();

    private final Map<String, FighterState> states = new HashMap<>();
   private final CommandManager manager;

    public GameController(GameFrame view) {
        this.view = view;
        this.manager = new CommandManager(view::log); // 👈 connecte le log
    }


    public void init(List<Character> team1, List<Character> team2) {



        view.setTeam1(team1);
        view.setTeam2(team2);

        for (Character c : team1) states.put(c.getName(), new FighterState(c, 50));
        for (Character c : team2) states.put(c.getName(), new FighterState(c, 50));

        // Team 1 actions
        view.attack1().addActionListener(e -> onAttack(true));
        view.defend1().addActionListener(e -> onDefend(true));
        view.power1().addActionListener(e -> onPower(true));
        view.replay1().addActionListener(e -> onReplay());

        // Team 2 actions
        view.attack2().addActionListener(e -> onAttack(false));
        view.defend2().addActionListener(e -> onDefend(false));
        view.power2().addActionListener(e -> onPower(false));
        view.replay2().addActionListener(e -> onReplay());

        //  ICI
        view.team1List().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) refreshHpLabels();
        });
        view.team2List().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) refreshHpLabels();
        });
        refreshHpLabels();

        view.log("✅ Prêt. Sélectionne un perso dans Team 1 et/ou Team 2, puis clique sur une action.");
    }


    private FighterState state(Character c) {
        return states.get(c.getName());
    }

    // fromTeam1=true : Team1 attaque Team2
    // fromTeam1=false: Team2 attaque Team1
    private void onAttack(boolean fromTeam1) {
        Character attacker = fromTeam1 ? view.getSelectedTeam1() : view.getSelectedTeam2();
        Character defender = fromTeam1 ? view.getSelectedTeam2() : view.getSelectedTeam1();

        if (attacker == null || defender == null) {
            view.log("⚠️ Pour attaquer: sélectionne 1 perso dans chaque team.");
            return;
        }

        FighterState a = state(attacker);
        FighterState d = state(defender);

        manager.execute(new AttackCommand(combat, a, d));
        view.log(attacker.getName() + " attaque " + defender.getName() + " (HP " + defender.getName() + "=" + d.getHp() + ")");

        if (!d.isAlive()) {
            view.log("🏆 KO: " + defender.getName());
        }
        refreshHpLabels();
    }

    private void onDefend(boolean team1) {
        Character c = team1 ? view.getSelectedTeam1() : view.getSelectedTeam2();
        if (c == null) {
            view.log("⚠️ Pour défendre: sélectionne un perso dans la team.");

            return;

        }

        FighterState s = state(c);
        manager.execute(new DefendCommand(combat, s));
        view.log(c.getName() + " se défend (prochain dégât réduit).");
        refreshHpLabels();
    }

    private void onPower(boolean team1) {
        Character c = team1 ? view.getSelectedTeam1() : view.getSelectedTeam2();
        if (c == null) {
            view.log("⚠️ Pour pouvoir: sélectionne un perso dans la team.");
            return;
        }

        // MVP volontaire (tu peux brancher UsePowerCommand après)
        view.log(c.getName() + " utilise un pouvoir (MVP).");
    }

    private void onReplay() {

        manager.replay();

        refreshHpLabels();
    }



    private void refreshHpLabels() {
        Character c1 = view.getSelectedTeam1();
        Character c2 = view.getSelectedTeam2();

        if (c1 != null) {
            FighterState s1 = state(c1);
            view.setHpTeam1("Énergie vitale de  " + c1.getName() + " = " + s1.getHp());
        } else {
            view.setHpTeam1("Énergie vitale: -");
        }

        if (c2 != null) {
            FighterState s2 = state(c2);
            view.setHpTeam2("Énergie vitale de  " + c2.getName() + " = " + s2.getHp());
        } else {
            view.setHpTeam2("Énergie vitale : -");
        }
    }

}
