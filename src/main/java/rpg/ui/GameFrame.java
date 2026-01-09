package rpg.ui;

import rpg.core.Character;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameFrame extends JFrame {

    // Models + Lists
    private final DefaultListModel<Character> team1Model = new DefaultListModel<>();
    private final DefaultListModel<Character> team2Model = new DefaultListModel<>();
    private final JList<Character> team1List = new JList<>(team1Model);
    private final JList<Character> team2List = new JList<>(team2Model);

    // Buttons Team 1
    private final JButton attack1 = new JButton("Attaquer");
    private final JButton defend1 = new JButton("Défendre");
    private final JButton power1  = new JButton("Pouvoir");
    private final JButton replay1 = new JButton("Replay");

    // Buttons Team 2
    private final JButton attack2 = new JButton("Attaquer");
    private final JButton defend2 = new JButton("Défendre");
    private final JButton power2  = new JButton("Pouvoir");
    private final JButton replay2 = new JButton("Replay");

    // Log
    private final JTextArea logArea = new JTextArea(8, 60);

    public GameFrame() {
        super("RPG - Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Lists rendering
        team1List.setCellRenderer(new CharacterCellRenderer());
        team2List.setCellRenderer(new CharacterCellRenderer());

        // Log style
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // ---- CENTER : 2 panels côte à côte (plus de vide) ----
        JPanel center = new JPanel(new GridLayout(1, 2, 20, 0));
        center.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        center.add(buildTeamPanel("Team 1", team1List, hpTeam1Label,
                attack1, defend1, power1, replay1));
        center.add(buildTeamPanel("Team 2", team2List, hpTeam2Label,
                attack2, defend2, power2, replay2));

        add(center, BorderLayout.CENTER);
        add(new JScrollPane(logArea), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }



    private final JLabel hpTeam1Label = new JLabel("HP: -", SwingConstants.CENTER);
    private final JLabel hpTeam2Label = new JLabel("HP: -", SwingConstants.CENTER);

    public void setHpTeam1(String text) { hpTeam1Label.setText(text); }
    public void setHpTeam2(String text) { hpTeam2Label.setText(text); }



    private JPanel buildTeamPanel(String title, JList<Character> list, JLabel hpLabel,
                                  JButton b1, JButton b2, JButton b3, JButton b4) {

        JPanel header = new JPanel(new GridLayout(2, 1));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 14f));

        header.add(titleLabel);
        header.add(hpLabel);

        JPanel p = new JPanel(new BorderLayout(6, 6));
        p.setPreferredSize(new Dimension(280, 430));

        p.add(header, BorderLayout.NORTH);
        p.add(new JScrollPane(list), BorderLayout.CENTER);

        JPanel actions = new JPanel(new GridLayout(2, 2, 8, 8));
        actions.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        actions.add(b1);
        actions.add(b2);
        actions.add(b3);
        actions.add(b4);

        p.add(actions, BorderLayout.SOUTH);
        return p;
    }


    // --- API pour Controller ---
    public void setTeam1(List<Character> chars) {
        team1Model.clear();
        for (Character c : chars) team1Model.addElement(c);
    }

    public void setTeam2(List<Character> chars) {
        team2Model.clear();
        for (Character c : chars) team2Model.addElement(c);
    }
    public JList<Character> team1List() {
        return team1List;
    }

    public JList<Character> team2List() {
        return team2List;
    }
    public Character getSelectedTeam1() { return team1List.getSelectedValue(); }
    public Character getSelectedTeam2() { return team2List.getSelectedValue(); }

    public JButton attack1() { return attack1; }
    public JButton defend1() { return defend1; }
    public JButton power1()  { return power1; }
    public JButton replay1() { return replay1; }

    public JButton attack2() { return attack2; }
    public JButton defend2() { return defend2; }
    public JButton power2()  { return power2; }
    public JButton replay2() { return replay2; }

    public void log(String msg) {
        logArea.append(msg + "\n");
    }

    public void clearLog() {
        logArea.setText("");
    }
}
