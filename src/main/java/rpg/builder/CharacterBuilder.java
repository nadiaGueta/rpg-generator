package rpg.builder;

import rpg.core.Character;
import rpg.settings.GameSettings;

import java.util.HashSet;
import java.util.Set;

//Le Builder valide les données minimales avant la construction de l’objet
// afin de garantir la cohérence du modèle
public class CharacterBuilder {

    private   String name;
    private  int strength;
    private   int agility;
    private   int intelligence;



    public CharacterBuilder setName( String name) {
         this.name = name ;
         return this ;
    }



    public CharacterBuilder setStrength(int  strength) {

        this.strength = strength ;
        return this ;
    }

    public CharacterBuilder setAgility( int agility) {
        this.agility = agility ;
        return this ;
    }

    public CharacterBuilder setIntelligence( int intelligence) {
        this.intelligence =  intelligence ;

        return this ;
    }
    private static final Set<String> USED_NAMES = new HashSet<>();


    public Character build() {
// US 2.3 nom est obligatoire
        if (name == null || name.isBlank() ) {
            throw new IllegalArgumentException("Le nom est obligatoire");


        }
// US 2.3 nom unique
        String key = name.toLowerCase();
        if (USED_NAMES.contains(key)) {
            throw new IllegalArgumentException("Nom déjà utilisé");
        }


        Character character =    new Character(this);


   // US 2.3 statsValidator
        if(!GameSettings.getInstance().isValid(character)){

            throw new IllegalArgumentException("Stats trop élevées");

        }
        USED_NAMES.add(key);
return character ;
    }


    public String getName() {
        return name;
    }

    public int getStrength() {
        return strength;
    }

    public int getAgility() {
        return agility;
    }

    public int getIntelligence() {
        return intelligence;
    }
}
