package com.hangman.ui.dialog;

import com.hangman.service.DictionaryProvider;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.util.Locale;

/**
 * Modal shown after each round. It reveals the secret word with its definition
 * and locks all game input until the player chooses to continue or quit.
 */
public class DefinitionDialog extends JDialog {

    public enum Choice {
        CONTINUE,
        CHANGE_DIFFICULTY,
        SWITCH_PLAYER,
        QUIT
    }

    private final transient DictionaryProvider dictionary;
    private final String word;
    private final JTextArea definitionArea = new JTextArea(4, 32);
    private Choice choice = Choice.QUIT;

    public DefinitionDialog(Window owner, String outcomeMessage, String word, DictionaryProvider dictionary) {
        super(owner, "Round Complete", ModalityType.APPLICATION_MODAL);
        this.dictionary = dictionary;
        this.word = word;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        buildUi(outcomeMessage);
        pack();
        setLocationRelativeTo(owner);
        loadDefinition();
    }

    private void buildUi(String outcomeMessage) {
        JPanel content = new JPanel(new BorderLayout(12, 12));
        content.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

        JPanel header = new JPanel(new GridLayout(2, 1, 0, 4));
        JLabel wordLabel = new JLabel(word.toUpperCase(Locale.ENGLISH), SwingConstants.CENTER);
        wordLabel.setFont(wordLabel.getFont().deriveFont(Font.BOLD, 22f));
        JLabel outcome = new JLabel(outcomeMessage, SwingConstants.CENTER);
        outcome.setFont(outcome.getFont().deriveFont(Font.PLAIN, 14f));
        header.add(wordLabel);
        header.add(outcome);
        content.add(header, BorderLayout.NORTH);

        definitionArea.setEditable(false);
        definitionArea.setLineWrap(true);
        definitionArea.setWrapStyleWord(true);
        definitionArea.setOpaque(false);
        definitionArea.setFont(definitionArea.getFont().deriveFont(13f));
        definitionArea.setText("Looking up definition…");
        JScrollPane scroll = new JScrollPane(definitionArea);
        scroll.setPreferredSize(new Dimension(360, 96));
        scroll.setBorder(BorderFactory.createTitledBorder("Definition"));
        content.add(scroll, BorderLayout.CENTER);

        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(e -> finish(Choice.CONTINUE));
        JButton changeDifficultyButton = new JButton("Change difficulty");
        changeDifficultyButton.addActionListener(e -> finish(Choice.CHANGE_DIFFICULTY));
        JButton switchPlayerButton = new JButton("Switch player");
        switchPlayerButton.addActionListener(e -> finish(Choice.SWITCH_PLAYER));
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> finish(Choice.QUIT));

        JPanel buttons = new JPanel(new GridLayout(2, 2, 8, 8));
        buttons.add(continueButton);
        buttons.add(changeDifficultyButton);
        buttons.add(switchPlayerButton);
        buttons.add(quitButton);
        content.add(buttons, BorderLayout.SOUTH);

        setContentPane(content);
        getRootPane().setDefaultButton(continueButton);
    }

    private void loadDefinition() {
        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() {
                return dictionary.define(word);
            }

            @Override
            protected void done() {
                try {
                    definitionArea.setText(get());
                } catch (Exception ex) {
                    definitionArea.setText("Definition unavailable right now.");
                }
                definitionArea.setCaretPosition(0);
            }
        }.execute();
    }

    private void finish(Choice selected) {
        this.choice = selected;
        dispose();
    }

    /** Shows the modal and blocks until the player makes a choice. */
    public Choice showDialog() {
        setVisible(true);
        return choice;
    }
}
