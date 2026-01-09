package rpg.composite;

import java.util.ArrayList;
import java.util.List;

public class Party implements GroupComponent{
    private String name ;
    private final List<GroupComponent> members = new ArrayList<>();
    public Party(String name) {
        this.name = name;
    }

    public void add(GroupComponent component) {
        members.add(component) ;

    } ;
    public void remove(GroupComponent component){

     members.remove(component);
    }
    @Override
    public int getPower() {
        int total = 0;
        for (GroupComponent m : members) {
            total += m.getPower();
        }
        return total;
    }

    @Override
    public void printDetails(String indent) {

        System.out.println(indent + "+ Party: " + name + " (TotalPower=" + getPower() + ")");
        for (GroupComponent m : members) {
            m.printDetails(indent + "  ");
        }
    }
}
