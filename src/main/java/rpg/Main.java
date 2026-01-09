package rpg;
import rpg.builder.CharacterBuilder;
import rpg.combat.CombatService;
import rpg.combat.FighterState;
import rpg.command.CommandManager;
import rpg.command.DefendCommand;
import rpg.command.AttackCommand;
import rpg.composite.Army;
import rpg.composite.CharacterLeaf;
import rpg.composite.GroupComponent;
import rpg.composite.Party;
import rpg.core.Character ;
import rpg.dao.CharacterDao;
import rpg.dao.InMemoryCharacterDao;
import rpg.decorator.Telepathy;
import rpg.settings.GameSettings;
import rpg.ui.GameController;
import rpg.ui.GameFrame;

import javax.swing.*;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main() {





//us1.1 Création d’un personnage avec le Builder
        Character mario  = new CharacterBuilder()
                .setName("mario")
                .setStrength(5)
                .setAgility(8)
                .setIntelligence(7)
                .build();
        Character luigi  = new CharacterBuilder()
                .setName("rio")
                .setStrength(7)
                .setAgility(8)
                .setIntelligence(7)
                .build();
        System.out.println(mario.getDescription());
        System.out.println(luigi.getDescription());
        System.out.println("Total stats for " +mario.getName() +" = "+ mario.getTotalStats());
        System.out.println("Total stats for " +luigi.getName()+" = "+luigi.getTotalStats());


//US 2.1 singleton
    GameSettings settings = GameSettings.getInstance();
    settings.setMaxStatPoints(30);
    System.out.println("Personnage valide ? " + settings.isValid(mario));
    System.out.println("Personnage valide ? " + settings.isValid(luigi));


//US 1.2  Decorator

        luigi = new Telepathy(luigi) ;
        System.out.println(luigi.getDescription());
        System.out.println("his new power = " +luigi.getTotalStats());
 //us 1.3 Le sauvegarder / le récupérer (DAO)

CharacterDao dao = new InMemoryCharacterDao() ;

        // sauvegarde
        dao.save(mario);
        dao.save(luigi);
        // lecture
        System.out.println("Tous les personnages :");
        for (Character c : dao.findAll()) {
            System.out.println(c);
        }
        System.out.println("Recherche Mario : " + dao.findByName("Mario"));

//us 1.4 L’ajouter dans une équipe (Composite)

        // feuilles
        GroupComponent marioLeaf = new CharacterLeaf(mario);
        GroupComponent luigiLeaf = new CharacterLeaf(luigi);

        // party
        Party team1 = new Party("Team 1");
        team1.add(marioLeaf);
        team1.add(luigiLeaf);
       // Party team2 = new Party("Team 2");
       // team2.add(luigiLeaf);

        // army
        Army army = new Army("Red Army");
        army.add(team1);
       // army.add(team2);

        // affichage
        army.printDetails("");
        System.out.println("Army Power = " + army.getPower());

// ===== JOUR 2 : TEST COMMAND + COMBAT =====
        System.out.println("\n===== JOUR 2 : COMBAT =====");

        FighterState fMario = new rpg.combat.FighterState(mario, 50);
        FighterState fLuigi = new rpg.combat.FighterState(luigi, 50);

       CombatService combat = new rpg.combat.CombatService();
       CommandManager manager = new rpg.command.CommandManager(System.out::println);

        manager.execute(new AttackCommand(combat, fMario, fLuigi));
        manager.execute(new DefendCommand(combat, fLuigi));
        manager.execute(new AttackCommand(combat, fMario, fLuigi));
        manager.execute(new AttackCommand(combat, fMario, fLuigi));

        System.out.println("HP Mario=" + fMario.getHp());
        System.out.println("HP Luigi=" + fLuigi.getHp());

        manager.replay();
      //  while (fMario.isAlive() && fLuigi.isAlive()) {
          //  manager.execute(new AttackCommand(combat, fMario, fLuigi));
       // }
       // System.out.println("Vainqueur: " + combat.winner(fMario, fLuigi));


        final Character marioFinal = mario;
        final Character luigiFinal = luigi;

     SwingUtilities.invokeLater(() -> {
            GameFrame frame = new GameFrame();
            GameController controller = new GameController(frame);

            controller.init(
                    List.of(marioFinal),   // Team 1
                    List.of(luigiFinal)    // Team 2
            );

            frame.setVisible(true);
        });






    }




    }

