package rpg.composite;

import java.util.ArrayList;
import java.util.List;

public class Army implements GroupComponent {

    private final String name;
    private final List<GroupComponent> groups = new ArrayList<>();

    public Army(String name) {
        this.name = name;
    }

    public void add(GroupComponent component) {
        groups.add(component);
    }

    public void remove(GroupComponent component) {
        groups.remove(component);
    }

    @Override
    public int getPower() {
        int total = 0;
        for (GroupComponent g : groups) {
            total += g.getPower();
        }
        return total;
    }

    @Override
    public void printDetails(String indent) {
        System.out.println(indent + "# Army: " + name);

        int armyTotal = 0;

        for (GroupComponent g : groups) {
            int teamPower = g.getPower();
            armyTotal += teamPower;

            System.out.println(indent + "  - Team power = " + teamPower);
            g.printDetails(indent + "    ");
        }

        System.out.println(indent + "=> Army Total = " + armyTotal);
    }

}
