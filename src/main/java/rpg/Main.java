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
import rpg.core.Character;
import rpg.dao.CharacterDao;
import rpg.dao.InMemoryCharacterDao;
import rpg.decorator.Telepathy;
import rpg.settings.GameSettings;
import rpg.ui.GameController;
import rpg.ui.GameFrame;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // US1.1 Builder
        Character mario = new CharacterBuilder()
                .setName("mario")
                .setStrength(5)
                .setAgility(8)
                .setIntelligence(7)
                .build();

        Character luigi = new CharacterBuilder()
                .setName("luigi")
                .setStrength(7)
                .setAgility(8)
                .setIntelligence(7)
                .build();

        //  Peach
        Character peach = new CharacterBuilder()
                .setName("peach")
                .setStrength(4)
                .setAgility(7)
                .setIntelligence(9)
                .build();

        //  Donkey Kong
        Character donkeyKong = new CharacterBuilder()
                .setName("donkey kong")
                .setStrength(10)
                .setAgility(4)
                .setIntelligence(3)
                .build();

        //  5e perso pour Team 2 (au choix)
        Character yoshi = new CharacterBuilder()
                .setName("yoshi")
                .setStrength(6)
                .setAgility(9)
                .setIntelligence(5)
                .build();

        // US 2.1 Singleton
        GameSettings settings = GameSettings.getInstance();
        settings.setMaxStatPoints(30);

        System.out.println("Personnage valide ? " + settings.isValid(mario));
        System.out.println("Personnage valide ? " + settings.isValid(luigi));
        System.out.println("Personnage valide ? " + settings.isValid(peach));
        System.out.println("Personnage valide ? " + settings.isValid(donkeyKong));
        System.out.println("Personnage valide ? " + settings.isValid(yoshi));

        // US 1.2 Decorator (ex: sur Luigi)
        luigi = new Telepathy(luigi);

        // US 1.3 DAO
        CharacterDao dao = new InMemoryCharacterDao();
        dao.save(mario);
        dao.save(luigi);
        dao.save(peach);
        dao.save(donkeyKong);
        dao.save(yoshi);

        // US 1.4 Composite (ex: Team 1 seulement ici)
        GroupComponent marioLeaf = new CharacterLeaf(mario);
        GroupComponent luigiLeaf = new CharacterLeaf(luigi);

        Party team1 = new Party("Team 1");
        team1.add(marioLeaf);
        team1.add(luigiLeaf);

        Army army = new Army("Red Army");
        army.add(team1);

        army.printDetails("");
        System.out.println("Army Power = " + army.getPower());

        // ===== JOUR 2 : TEST COMMAND + COMBAT (console) =====
        System.out.println("\n===== JOUR 2 : COMBAT =====");

        FighterState fMario = new FighterState(mario, 50);
        FighterState fLuigi = new FighterState(luigi, 50);

        CombatService combat = new CombatService();
        CommandManager manager = new CommandManager(System.out::println);

        manager.execute(new AttackCommand(combat, fMario, fLuigi));
        manager.execute(new DefendCommand(combat, fLuigi));
        manager.execute(new AttackCommand(combat, fMario, fLuigi));
        manager.execute(new AttackCommand(combat, fMario, fLuigi));

        System.out.println("HP Mario=" + fMario.getHp());
        System.out.println("HP Luigi=" + fLuigi.getHp());

        manager.replay();

        // ===== SWING =====
        final Character marioFinal = mario;
        final Character luigiFinal = luigi;
        final Character peachFinal = peach;
        final Character donkeyFinal = donkeyKong;
        final Character yoshiFinal = yoshi;

        SwingUtilities.invokeLater(() -> {
            GameFrame frame = new GameFrame();
            GameController controller = new GameController(frame);

            controller.init(
                    List.of(marioFinal, luigiFinal),                 //  Team 1 = 2
                    List.of(peachFinal, donkeyFinal, yoshiFinal)     //  Team 2 = 3
            );

            frame.setVisible(true);
        });
    }
}
