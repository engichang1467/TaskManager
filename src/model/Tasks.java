/**
 * Tasks.java
 * This is where we create the Task object so that we can convert it into our JSON file
 * @author michaelchang
 */

package model;

public class Tasks {
    // Fields for Tasks
    private String name;
    private String notes;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private boolean completed;

    /**
     * This is the constructor of Task object
     * @param name - name of the task
     * @param notes - notes of the task
     * @param year - year of the due date
     * @param month - month of the due date
     * @param day - day of the due date
     * @param hour - hour of the due date
     * @param minute - minute of the due date
     * @param completed - whether if the task is completed or not
     */
    public Tasks(String name, String notes, int year, int month, int day, int hour, int minute, boolean completed) {
        this.name = name;
        this.notes = notes;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.completed = completed;
    }

    /**
     * It gets the name of the task
     * @return name - the name of the task
     */
    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public boolean isCompleted() {
        return completed;
    }

    /**
     * It outputs the information within the Task object
     * @return information within the selected task object
     */
    @Override
    public String toString() {
        return "Tasks {" +
                "name='" + name + '\'' +
                ", notes='" + notes + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                ", completed='" + completed + '\'' +
                '}';
    }
}
