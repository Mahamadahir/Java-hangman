import javax.swing.*;
import java.awt.*;

public class UserScreen extends JPanel {
    public interface UserSelectedListener {
        void onUserSelected(User user);
    }

    public UserScreen(UserManager userManager, UserSelectedListener listener) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Select or Create User", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        DefaultListModel<User> userListModel = new DefaultListModel<>();
        for (User user : userManager.getAllUsers()) {
            userListModel.addElement(user);
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
            if (!name.isEmpty()) {
                userManager.addUser(name);
                User newUser = userManager.getCurrentUser();
                userListModel.addElement(newUser);
                listener.onUserSelected(newUser);
            }
        });

        selectBtn.addActionListener(e -> {
            User selected = userJList.getSelectedValue();
            if (selected != null) {
                userManager.selectUser(selected);
                listener.onUserSelected(selected);
            }
        });
    }
}