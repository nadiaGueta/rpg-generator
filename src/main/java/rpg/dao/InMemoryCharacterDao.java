package rpg.dao;

import rpg.core.Character;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCharacterDao implements CharacterDao{
    private final List<Character> characters = new ArrayList<>();

    @Override
    public void save(Character character) {
        characters.add(character);
    }

    @Override
    public Character findByName(String name) {
        for (Character c : characters) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Character> findAll() {
        return characters ;
    }
}
