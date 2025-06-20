import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserScreen extends JPanel {
    public interface UserSelectedListener {
        void onUserSelected(User user);
    }

    public UserScreen(ScoreTracker scoreTracker, UserSelectedListener listener) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Select or Create User", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        DefaultListModel<User> userListModel = new DefaultListModel<>();
        List<String> usernames = scoreTracker.getAllUsernames();
        for (String name : usernames) {
            userListModel.addElement(new User(name));
        }
        JList<User> userJList = new JList<>(userListModel);
        userJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(userJList), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout());
        JTextField userField = new JTextField(10);
        JButton createBtn = new JButton("Create");
        JButton selectBtn = new JButton("Select");
        inputPanel.add(userField);
        inputPanel.add(createBtn);
        inputPanel.add(selectBtn);
        add(inputPanel, BorderLayout.SOUTH);

        createBtn.addActionListener(e -> {
            String name = userField.getText().trim();
            if (!name.isEmpty() && usernames.stream().noneMatch(existing -> existing.equalsIgnoreCase(name)))
                {
                scoreTracker.initializeUser(name);
                scoreTracker.save();
                User newUser = new User(name);
                userListModel.addElement(newUser);
                listener.onUserSelected(newUser);
            }
        });

        selectBtn.addActionListener(e -> {
            User selected = userJList.getSelectedValue();
            if (selected != null) {
                listener.onUserSelected(selected);
            }
        });
    }
}
