package rpg.dao;

import java.util.List;
import rpg.core.Character ;

public interface CharacterDao {
    void save(Character character);

    Character findByName(String name);

    List<Character> findAll();


}
