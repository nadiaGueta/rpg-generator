package rpg.ui;

import rpg.combat.CombatService;
import rpg.combat.FighterState;
import rpg.command.AttackCommand;
import rpg.command.CommandManager;
import rpg.command.DefendCommand;
import rpg.command.UsePowerCommand;
import rpg.core.Character;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController {

    private List<Character> team1Chars;
    private List<Character> team2Chars;
    private boolean gameOver = false;

    private boolean initialized = false;

    private final GameFrame view;
    private final CombatService combat = new CombatService();
    private final Map<String, FighterState> states = new HashMap<>();
    private final CommandManager manager;

    public GameController(GameFrame view) {
        this.view = view;
        this.manager = new CommandManager(view::log);
    }

    public void init(List<Character> team1, List<Character> team2) {
        if (initialized) return;
        initialized = true;

        this.team1Chars = team1;
        this.team2Chars = team2;
        this.gameOver = false;

        // US 4.2 (Observer) : journal auto (CombatService -> View)
        combat.addObserver(view::log);

        // Affichage des teams
        view.setTeam1(team1);
        view.setTeam2(team2);

        // Création des états (HP)
        for (Character c : team1) states.put(c.getName(), new FighterState(c, 50));
        for (Character c : team2) states.put(c.getName(), new FighterState(c, 50));

        //  US 3.2 (Observer) : synchro auto HP (PAS de checkWinner ici)
        for (FighterState s : states.values()) {
            s.addObserver(changed -> refreshHpLabels());
        }

        // Sélection par défaut
        if (!team1.isEmpty()) view.team1List().setSelectedIndex(0);
        if (!team2.isEmpty()) view.team2List().setSelectedIndex(0);

        // Actions Team 1
        view.attack1().addActionListener(e -> onAttack(true));
        view.defend1().addActionListener(e -> onDefend(true));
        view.power1().addActionListener(e -> onPower(true));
        view.replay1().addActionListener(e -> onReplay());

        // Actions Team 2
        view.attack2().addActionListener(e -> onAttack(false));
        view.defend2().addActionListener(e -> onDefend(false));
        view.power2().addActionListener(e -> onPower(false));
        view.replay2().addActionListener(e -> onReplay());

        // Changement de sélection => maj labels
        view.team1List().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) refreshHpLabels();
        });
        view.team2List().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) refreshHpLabels();
        });

        refreshHpLabels();
        view.log("Prêt. Clique sur une action.");
    }

    private FighterState state(Character c) {
        return states.get(c.getName());
    }

    private void onAttack(boolean fromTeam1) {
        if (gameOver) return;

        Character attacker = fromTeam1 ? view.getSelectedTeam1() : view.getSelectedTeam2();
        Character defender = fromTeam1 ? view.getSelectedTeam2() : view.getSelectedTeam1();

        if (attacker == null || defender == null) {
            view.log(" Sélection manquante.");
            return;
        }

        FighterState a = state(attacker);
        FighterState d = state(defender);

        manager.execute(new AttackCommand(combat, a, d));

        //  On check la victoire UNIQUEMENT si quelqu’un vient de tomber KO
        if (!d.isAlive()) {
            checkWinner();
        }
    }

    private void onDefend(boolean team1) {
        if (gameOver) return;

        Character c = team1 ? view.getSelectedTeam1() : view.getSelectedTeam2();
        if (c == null) {
            view.log(" Sélection manquante.");
            return;
        }

        FighterState s = state(c);
        manager.execute(new DefendCommand(combat, s));
    }

    private void onPower(boolean team1) {
        if (gameOver) return;

        Character c = team1 ? view.getSelectedTeam1() : view.getSelectedTeam2();
        if (c == null) {
            view.log(" Sélection manquante.");
            return;
        }

        FighterState s = state(c);
        manager.execute(new UsePowerCommand(combat, s)); // passe par CombatService (gère KO)
    }

    private void onReplay() {
        if (gameOver) return;
        manager.replay();
    }

    private void refreshHpLabels() {
        Character c1 = view.getSelectedTeam1();
        Character c2 = view.getSelectedTeam2();

        if (c1 != null) {
            FighterState s1 = state(c1);
            view.setHpTeam1("Énergie vitale de " + c1.getName() + " = " + s1.getHp());
        } else {
            view.setHpTeam1("Énergie vitale: -");
        }

        if (c2 != null) {
            FighterState s2 = state(c2);
            view.setHpTeam2("Énergie vitale de " + c2.getName() + " = " + s2.getHp());
        } else {
            view.setHpTeam2("Énergie vitale : -");
        }
    }

    // version robuste : utilise state(c) et pas states.get(...)
    private boolean teamAlive(List<Character> team) {
        for (Character c : team) {
            FighterState s = state(c);
            if (s != null && s.isAlive()) return true;
        }
        return false;
    }

    private void checkWinner() {
        if (gameOver) return;

        boolean t1 = teamAlive(team1Chars);
        boolean t2 = teamAlive(team2Chars);

        if (t1 && !t2) {
            view.log(" VICTOIRE : Team 1 !");
            gameOver = true;
        } else if (!t1 && t2) {
            view.log(" VICTOIRE : Team 2 !");
            gameOver = true;
        } else if (!t1 && !t2) {
            view.log(" ÉGALITÉ : les deux équipes sont KO !");
            gameOver = true;
        }
    }
}
