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

    public void replay() {
        log.accept("=== REPLAY ===");
        for (Command c : history) {
            c.execute();
            log.accept(c.description());
        }
        log.accept("(Replay terminé)");
    }

    public void clear() {
        history.clear();
    }
}
