package rpg.command;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CommandManager {
    private final List<Command> history = new ArrayList<>();
    private final Consumer<String> log;

    public CommandManager(Consumer<String> log) {
        this.log = log;
    }

    public void execute(Command c) {
        c.execute();
        history.add(c);
    }

    // ✅ Replay = rejouer le récit, pas re-exécuter les actions
    public void replay() {
        log.accept("=== REPLAY ===");
        for (Command c : history) {
            log.accept(c.description()); // ou c.name() si tu préfères
        }
        log.accept("(Replay terminé)");

        history.clear(); // si tu veux garder ton comportement actuel
    }

    public void clear() {
        history.clear();
    }
}
