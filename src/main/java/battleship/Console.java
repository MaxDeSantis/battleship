package battleship;

import javax.swing.*;

public class Console {

    private JPanel consolePanel;
    private JTextArea consoleTextBox;
    private JTextField messageBox;

    public Console() {
        consolePanel = new JPanel();
        consoleTextBox = new JTextArea(0, 1);
        messageBox = new JTextField();
        consolePanel.add(consoleTextBox);
        consolePanel.add(messageBox);
    }
    
    public void log(String message) {
        
    }

    public JPanel getPanel() {
        return consolePanel;
    }
}