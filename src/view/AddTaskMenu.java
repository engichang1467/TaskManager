/**
 * AddTaskMenu.java
 * This is the interface of Add Task Menu
 * @author Michael Chang
 */

package view;

import javax.swing.*;

import control.TaskControl;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.google.gson.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class AddTaskMenu {
    JFrame applicationFrame;
    FlowLayout experimentLayout = new FlowLayout();
    private JPanel mainPanel;
    private JLabel taskName;
    private JTextField inputTaskName;
    private JTextField inputTaskNotes;
    private JLabel taskNotes;
    private JLabel dueDate;
    private JLabel noteWarning;
    private JButton generateTask;
    private JButton createTask;
    private JButton cancelTask;
    private DateTimePicker dtp;

    /**
     * This is the GUI object of the Add Task menu
     */
    public AddTaskMenu() {
        applicationFrame = new JFrame("Add Task");
        applicationFrame.setSize(500, 300);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        // Tells what happen when the window is closed
        applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Task name Panel
        JPanel namePanel = new JPanel();
        namePanel.setLayout(experimentLayout);
        taskName = new JLabel("Name: ");
        namePanel.add(taskName);

        // user input to fill task name
        inputTaskName = new JTextField(30);
        namePanel.add(inputTaskName);
        mainPanel.add(namePanel);

        // Task note panel
        JPanel taskNotePanel = new JPanel();
        taskNotePanel.setLayout(experimentLayout);
        taskNotes = new JLabel("Note: ");
        taskNotePanel.add(taskNotes);

        // user input to fill task note
        inputTaskNotes = new JTextField(25);
        taskNotePanel.add(inputTaskNotes);
        mainPanel.add(taskNotePanel);

        // Due Date Panel
        JPanel dueDatePanel = new JPanel();
        dueDatePanel.setLayout(experimentLayout);
        dueDate = new JLabel("Due Date: ");
        dueDatePanel.add(dueDate);

        // Drop down for both date and time
        dtp = new DateTimePicker();
        dueDatePanel.add(dtp);
        mainPanel.add(dueDatePanel);

        // Extra warning
        noteWarning = new JLabel("Note: make sure to fill out Name, Due date, and Time");
        noteWarning.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(noteWarning);

        // Set up panel to put all the buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(experimentLayout);

        // Set up the Generate buttons and its action
        generateTask = new JButton("Generate");
        generateTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedReader readit;
                String output;
                StringBuffer response = new StringBuffer();
                try {
                    URL url = new URL("https://www.boredapi.com/api/activity");

                    HttpURLConnection connectAPI = (HttpURLConnection) url.openConnection();
                    connectAPI.setRequestMethod("GET");
                    connectAPI.setConnectTimeout(5000);
                    connectAPI.setReadTimeout(5000);

                    int checkResponse = connectAPI.getResponseCode();

                    if (checkResponse < 299) {
                        readit = new BufferedReader(new InputStreamReader(connectAPI.getInputStream()));
                        while ((output = readit.readLine()) != null){
                            response.append(output);
                        }
                        readit.close();
                    } else {
                        readit = new BufferedReader(new InputStreamReader(connectAPI.getErrorStream()));
                        while ((output = readit.readLine()) != null){
                            response.append(output);
                        }
                        readit.close();
                    }
                    // Parse data and convert it into JSON object
                    JsonObject randData = new JsonParser().parse(response.toString()).getAsJsonObject();
                    String nameTask = randData.get("type").getAsString();
                    String noteTask = randData.get("activity").getAsString();

                    // set it up on input
                    inputTaskName.setText(nameTask);
                    inputTaskNotes.setText(noteTask);

                } catch (IOException except) {
                    except.printStackTrace();
                }
            }
        });
        buttonsPanel.add(generateTask);

        // Set up the Create buttons and its action
        createTask = new JButton("Create");
        buttonsPanel.add(createTask);
        createTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameTask = inputTaskName.getText();
                String noteTask = inputTaskNotes.getText();

                int month = dtp.getDatePicker().getDate().getMonthValue();
                int date = dtp.getDatePicker().getDate().getDayOfMonth();
                int year = dtp.getDatePicker().getDate().getYear();

                int hour = dtp.getTimePicker().getTime().getHour();
                int minute = dtp.getTimePicker().getTime().getMinute();

                TaskControl.addTasks(nameTask, noteTask, year, month, date, hour, minute);

                applicationFrame.dispose();
                ToDoListMenu toDoListMenu = new ToDoListMenu();
            }
        });

        // Set up the Cancel buttons and its action
        cancelTask = new JButton("Cancel");
        cancelTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == cancelTask){
                    applicationFrame.dispose();
                    ToDoListMenu toDoListMenu = new ToDoListMenu();
                }
            }
        });
        buttonsPanel.add(cancelTask);
        mainPanel.add(buttonsPanel); // Add the buttons panel

        applicationFrame.add(mainPanel);
        applicationFrame.setVisible(true);
    }
}
