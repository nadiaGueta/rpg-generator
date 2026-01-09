package rpg.command;

public interface Command {
    void execute();
    String name();
    String description();

}
