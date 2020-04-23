package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Console {

    private JPanel consolePanel;
    private JTextArea consoleTextBox;
    private JScrollPane console;
    private JTextField messageBox;
    private JScrollBar verticalBar;

    public Console() {

        //Overall panel
        consolePanel = new JPanel();
        consolePanel.setSize(200, 100);

        //Text history
        consoleTextBox = new JTextArea("");
        consoleTextBox.setEditable(false);
        consoleTextBox.setColumns(20);
        consoleTextBox.setRows(10);
        consoleTextBox.setLineWrap(true);
        consoleTextBox.setWrapStyleWord(true);

        //User entry line.
        messageBox = new JTextField("");
        messageBox.setColumns(16);
        messageBox.setBounds(1, 30, 200, 20);

        messageBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(messageBox.getText()  != "") {
                    Battleship.network.sendMessage(messageBox.getText());
                    log("<ME>" + messageBox.getText());
                    
                }
            }
        });

        
        //Needed for formatting
        consolePanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        //Provides scrolling for history
        console = new JScrollPane(consoleTextBox);
        verticalBar = console.getVerticalScrollBar();
        

        //Text history formatting
        constraints.weightx = 0.5;
        constraints.weighty = 1;
        consolePanel.add(console, constraints);

        //Messagebox formatting
        constraints.weighty = 1;
        constraints.gridy = 1;
        consolePanel.add(messageBox, constraints);
    }
    
    public void log(String message) {
        consoleTextBox.append(message + "\n");
        messageBox.setText("");
        verticalBar.setValue(verticalBar.getMaximum());
    }

    public void clear() {
        consoleTextBox.setText("");
    }

    public JPanel getPanel() {
        return consolePanel;
    }
}