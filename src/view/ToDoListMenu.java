/**
 * ToDoListMenu.java
 * This is the GUI interface of the To-Do List
 * @author Michael Chang
 */

package view;
import control.*;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ToDoListMenu {
    JFrame applicationFrame;
    JPanel mainPanel;
    JPanel listPanel;
    JTabbedPane tabbedPane;

    JButton addTask;

    JScrollPane tasksScroll;

    /**
     * The GUI object for the To-Do List Menu
     * @param
     * @return
     */
    public ToDoListMenu() {
        applicationFrame = new JFrame("My To-Do List");
        applicationFrame.setSize(400, 300);
        // Tells what happen when the window is closed
        applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // create tabs
        tabbedPane  = new JTabbedPane();

        JComponent panel1 = AllListPanel("All");
        tabbedPane.addTab("All", null, panel1,
                "Does nothing");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent panel2 = OverDueListPanel("Overdue");
        tabbedPane.addTab("Overdue", null, panel2,
                "Does twice as much nothing");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        JComponent panel3 = UpComingListPanel("Upcoming");
        tabbedPane.addTab("Upcoming", null, panel3,
                "Still does nothing");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        mainPanel.add(tabbedPane);

        addTask = new JButton("Add Task");
        addTask.setAlignmentX(Component.CENTER_ALIGNMENT);
        addTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addTask){
                    applicationFrame.dispose();
                    AddTaskMenu addTaskMenu = new AddTaskMenu();
                }
            }
        });
        mainPanel.add(addTask);


        applicationFrame.add(mainPanel);
        applicationFrame.setVisible(true);
    }

    /**
     * Create a panel that list out all the tasks
     * @param text - the title of the panel
     * @return panel that list out all the tasks
     */
    protected JComponent AllListPanel(String text) {
        JPanel panel = new JPanel(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.add(filler);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));
        int taskNum = 1;

        ArrayList<Tasks> tasksList = TaskControl.setUpList();

        // Check if the list is empty or not
        if (tasksList.size() == 0){
            JLabel nothing = new JLabel("No tasks to show");
            listPanel.add(nothing);
        } else {
            for (Tasks task : tasksList) {
                JComponent sampleTask = taskPanel(taskNum, task.getName(), task.getNotes(), task.getYear(), task.getMonth(), task.getDay(), task.getHour(), task.getMinute(), task.isCompleted());
                listPanel.add(sampleTask);
                taskNum++;
            }
        }

        // create scroll
        tasksScroll = new JScrollPane(listPanel);
        tasksScroll.setPreferredSize(new Dimension(250, 80));
        tasksScroll.getVerticalScrollBar().getUnitIncrement(20);
        panel.add(tasksScroll);

        return panel;
    }

    /**
     * Create a panel that list out all the over due and incomplete tasks
     * @param text - the title of the panel
     * @return panel that list out all the over due and incomplete tasks
     */
    protected JComponent OverDueListPanel(String text) {
        JPanel panel = new JPanel(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.add(filler);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));
        int taskNum = 1;

        ArrayList<Tasks> tasksList = TaskControl.setUpOverDueList();

        if (tasksList.size() == 0){
            JLabel nothing = new JLabel("No tasks to show");
            listPanel.add(nothing);
        } else {
            for (Tasks task : tasksList) {
                JComponent sampleTask = taskPanel(taskNum, task.getName(), task.getNotes(), task.getYear(), task.getMonth(), task.getDay(), task.getHour(), task.getMinute(), task.isCompleted());
                listPanel.add(sampleTask);
                taskNum++;
            }
        }

        // create scroll
        tasksScroll = new JScrollPane(listPanel);
        tasksScroll.setPreferredSize(new Dimension(250, 80));
        tasksScroll.getVerticalScrollBar().getUnitIncrement(20);
        panel.add(tasksScroll);

        return panel;
    }

    /**
     * Create a panel that list out all the upcoming and incomplete tasks
     * @param text - the title of the panel
     * @return panel that list out all the upcoming and incomplete tasks
     */
    protected JComponent UpComingListPanel(String text) {
        JPanel panel = new JPanel(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.add(filler);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));
        int taskNum = 1;

        ArrayList<Tasks> tasksList = TaskControl.setUpUpcomingList();

        if (tasksList.size() == 0){
            JLabel nothing = new JLabel("No tasks to show");
            listPanel.add(nothing);
        } else {
            for (Tasks task : tasksList) {
                JComponent sampleTask = taskPanel(taskNum, task.getName(), task.getNotes(), task.getYear(), task.getMonth(), task.getDay(), task.getHour(), task.getMinute(), task.isCompleted());
                listPanel.add(sampleTask);
                taskNum++;
            }
        }

        // create scroll
        tasksScroll = new JScrollPane(listPanel);
        tasksScroll.setPreferredSize(new Dimension(250, 80));
        tasksScroll.getVerticalScrollBar().getUnitIncrement(20);
        panel.add(tasksScroll);

        return panel;
    }

    /**
     * Create a panel for each individual task
     * @param num - the task number
     * @param inputTaskName - user inputted task name
     * @param inputTaskNote - user inputted task note
     * @param taskYear - year inputted
     * @param taskMonth - month inputted
     * @param taskDate - date inputted
     * @param taskHour - hour inputted
     * @param taskMinute - minute inputted
     * @param taskComplete - value if the task is complete
     * @return the panel that shows the individual list
     */
    protected JComponent taskPanel(int num, String inputTaskName, String inputTaskNote, int taskYear, int taskMonth, int taskDate, int taskHour, int taskMinute, Boolean taskComplete) {
        JPanel panel = new JPanel(false);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel taskNum = new JLabel("Task " + num);
        panel.add(taskNum);

        JLabel taskName = new JLabel("Name: "+ inputTaskName);
        panel.add(taskName);
        JLabel taskNotes = new JLabel("Note: " + inputTaskNote);
        panel.add(taskNotes);
        JLabel dueDate = new JLabel("Due Date: " + taskYear + "-"
                + TaskControl.formatTwoDigit(String.valueOf(taskMonth)) + "-"
                + TaskControl.formatTwoDigit(String.valueOf(taskDate)) + " "
                + TaskControl.formatTwoDigit(String.valueOf(taskHour)) + ":"
                + TaskControl.formatTwoDigit(String.valueOf(taskMinute)));
        panel.add(dueDate);

        JCheckBox complete = new JCheckBox("complete");
        if (taskComplete) {
            complete.setSelected(true);
        } else {
            complete.setSelected(false);
        }
        complete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (complete.isSelected()){
                    TaskControl.updateCompleteTasks(num, true, inputTaskName, inputTaskNote);
                    complete.setSelected(true);
                } else {
                    TaskControl.updateCompleteTasks(num, false, inputTaskName, inputTaskNote);
                    complete.setSelected(false);
                }
                applicationFrame.dispose();
                ToDoListMenu toDoListMenu = new ToDoListMenu();
            }
        });

        panel.add(complete);
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TaskControl.removeTasks(num, inputTaskName, inputTaskNote);
                applicationFrame.dispose();
                ToDoListMenu toDoListMenu = new ToDoListMenu();
            }
        });
        panel.add(removeButton);

        return panel;
    }
}
