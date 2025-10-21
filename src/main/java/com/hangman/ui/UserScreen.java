package com.hangman.ui;

import com.hangman.model.User;
import com.hangman.persistence.ScoreTracker;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.function.Consumer;

/**
 * Lets players pick an existing profile or create a new one.
 */
public class UserScreen extends JPanel {

    public UserScreen(ScoreTracker tracker, Consumer<User> onUserSelected) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Select or Create User", SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        DefaultListModel<User> model = new DefaultListModel<>();
        tracker.getAllUsernames()
                .stream()
                .sorted(String::compareToIgnoreCase)
                .map(User::new)
                .forEach(model::addElement);

        JList<User> userList = new JList<>(model);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(userList), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout());
        JTextField inputField = new JTextField(12);
        JButton createButton = new JButton("Create");
        JButton selectButton = new JButton("Select");
        inputPanel.add(inputField);
        inputPanel.add(createButton);
        inputPanel.add(selectButton);
        add(inputPanel, BorderLayout.SOUTH);

        createButton.addActionListener(e -> {
            String username = inputField.getText().trim();
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a username.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            boolean exists = tracker.getAllUsernames().stream()
                    .anyMatch(existing -> existing.equalsIgnoreCase(username));
            if (exists) {
                JOptionPane.showMessageDialog(this, "User already exists.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }

            tracker.initializeUser(username);
            tracker.save();
            User user = new User(username);
            model.addElement(user);
            userList.setSelectedValue(user, true);
            onUserSelected.accept(user);
        });

        selectButton.addActionListener(e -> {
            User selected = userList.getSelectedValue();
            if (selected != null) {
                onUserSelected.accept(selected);
            }
        });
    }
}
