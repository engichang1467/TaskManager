/**
 * main.java
 * This is the main file where everything starts
 * @author Michael Chang
 */

import view.*;
import javax.swing.*;

public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ToDoListMenu();
            }
        });
    }
}
