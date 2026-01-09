package rpg.ui;



import rpg.core.Character;

import javax.swing.*;
import java.awt.*;

public class CharacterCellRenderer extends JPanel implements ListCellRenderer<Character> {

    private final JLabel nameLabel = new JLabel();
    private final JLabel statsLabel = new JLabel();

    public CharacterCellRenderer() {
        setLayout(new BorderLayout());
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD));
        statsLabel.setFont(statsLabel.getFont().deriveFont(Font.PLAIN, 11f));

        add(nameLabel, BorderLayout.NORTH);
        add(statsLabel, BorderLayout.SOUTH);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Character> list,
            Character character,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        nameLabel.setText(character.getName());
        statsLabel.setText(
                "STR: " + character.getStrength()
                        + " | AGI: " + character.getAgility()
                        + " | INT: " + character.getIntelligence()
        );

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        setOpaque(true);
        return this;
    }
}
