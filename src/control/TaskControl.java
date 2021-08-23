/**
 * TaskControl.java
 * Collections of operations that are used for both To-Do List and Add Task GUI menu
 * @author Michael Chang
 */

package control;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import com.google.gson.*;
import java.util.*;
import java.io.*;
import model.*;

public class TaskControl {
    /**
     * List out all the tasks
     * @param
     * @return
     */
    public static ArrayList<Tasks> setUpList() {
        File myFile = new File("src/tasksData.json");
        ArrayList<Tasks> tasksList = new ArrayList<Tasks>();
        try {
            // Access our JSON array from our JSON file
            JsonElement fileElement = JsonParser.parseReader(new FileReader(myFile));
            JsonArray fileArray = fileElement.getAsJsonArray();

            if (fileArray.size() < 1)
            {
                System.out.println("\nNo tasks to show.\n");
            }
            else
            {
                for (JsonElement taskElement : fileArray)
                {
                    JsonObject taskObj = taskElement.getAsJsonObject();

                    // Declare the element in each task
                    String taskName = taskObj.get("name").getAsString();
                    String taskNote = taskObj.get("notes").getAsString();
                    int taskYear = taskObj.get("year").getAsInt();
                    int taskMonth = taskObj.get("month").getAsInt();
                    int taskDate = taskObj.get("day").getAsInt();
                    int taskHour = taskObj.get("hour").getAsInt();
                    int taskMinute = taskObj.get("minute").getAsInt();
                    Boolean taskComplete = taskObj.get("completed").getAsBoolean();

                    Tasks task = new Tasks(taskName, taskNote, taskYear, taskMonth, taskDate, taskHour, taskMinute, taskComplete);
                    tasksList.add(task);
                }
                return tasksList;
            }

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return tasksList;
    }

    /**
     * return input with 2 digits
     * The helper function for ListTasks()
     * @param input - it can be a number (ex. hour, month, minutes)
     * @return the input with 2 digits (ex. 5 -> 05)
     */
    public static String formatTwoDigit(String input)
    {
        if (input.length() == 1) {
            return "0" + input;
        }
        return input;
    }

    /**
     * List out all the overdue tasks
     * Reference: http://tutorials.jenkov.com/java-date-time/java-util-timezone.html
     * @param
     * @return taskList - the list that contains overdue and incomplete task
     */
    public static ArrayList<Tasks> setUpOverDueList() {
        // create PST Timezone for vancouver
        TimeZone timeZone = TimeZone.getTimeZone("America/Vancouver");
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(timeZone);

        File myFile = new File("src/tasksData.json");
        ArrayList<Tasks> tasksList = new ArrayList<Tasks>();
        try {
            // Access our JSON array from our JSON file
            JsonElement fileElement = JsonParser.parseReader(new FileReader(myFile));
            JsonArray fileArray = fileElement.getAsJsonArray();

            if (fileArray.size() < 1)
            {
                System.out.println("\nNo tasks to show.\n");
            }
            else
            {
                for (JsonElement taskElement : fileArray)
                {
                    JsonObject taskObj = taskElement.getAsJsonObject();

                    // Declare the element in each task
                    String taskName = taskObj.get("name").getAsString();
                    String taskNote = taskObj.get("notes").getAsString();
                    int taskYear = taskObj.get("year").getAsInt();
                    int taskMonth = taskObj.get("month").getAsInt();
                    int taskDate = taskObj.get("day").getAsInt();
                    int taskHour = taskObj.get("hour").getAsInt();
                    int taskMinute = taskObj.get("minute").getAsInt();
                    Boolean taskComplete = taskObj.get("completed").getAsBoolean();

                    // Convert our data into a calender object so it can be compared
                    Calendar taskObjDate = new GregorianCalendar(taskYear, taskMonth, taskDate, taskHour, taskMinute);

                    if (calendar.compareTo(taskObjDate) == 1 && taskComplete == false)
                    {
                        Tasks task = new Tasks(taskName, taskNote, taskYear, taskMonth, taskDate, taskHour, taskMinute, taskComplete);
                        tasksList.add(task);
                    }
                }
                return tasksList;
            }

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return tasksList;
    }

    /**
     * List out all the upcoming tasks
     * @param
     * @return the list that contains upcoming and incomplete task
     */
    public static ArrayList<Tasks> setUpUpcomingList() {
        // create PST Timezone for vancouver
        TimeZone timeZone = TimeZone.getTimeZone("America/Vancouver");
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(timeZone);

        File myFile = new File("src/tasksData.json");
        ArrayList<Tasks> tasksList = new ArrayList<Tasks>();
        try {
            // Access our JSON array from our JSON file
            JsonElement fileElement = JsonParser.parseReader(new FileReader(myFile));
            JsonArray fileArray = fileElement.getAsJsonArray();

            if (fileArray.size() < 1)
            {
                System.out.println("\nNo tasks to show.\n");
            }
            else
            {
                for (JsonElement taskElement : fileArray)
                {
                    JsonObject taskObj = taskElement.getAsJsonObject();

                    // Declare the element in each task
                    String taskName = taskObj.get("name").getAsString();
                    String taskNote = taskObj.get("notes").getAsString();
                    int taskYear = taskObj.get("year").getAsInt();
                    int taskMonth = taskObj.get("month").getAsInt();
                    int taskDate = taskObj.get("day").getAsInt();
                    int taskHour = taskObj.get("hour").getAsInt();
                    int taskMinute = taskObj.get("minute").getAsInt();
                    Boolean taskComplete = taskObj.get("completed").getAsBoolean();

                    // Convert our data into a calender object so it can be compared
                    Calendar taskObjDate = new GregorianCalendar(taskYear, taskMonth, taskDate, taskHour, taskMinute);

                    if (taskObjDate.compareTo(calendar) == 1 && taskComplete == false)
                    {
                        Tasks task = new Tasks(taskName, taskNote, taskYear, taskMonth, taskDate, taskHour, taskMinute, taskComplete);
                        tasksList.add(task);
                    }
                }
                return tasksList;
            }

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return tasksList;
    }


    /**
     * Adding a new task into the database
     * Reference: https://www.javadoc.io/doc/com.google.code.gson/gson/latest/com.google.gson/com/google/gson/JsonArray.html
     * @param inputTaskName - task name inputted
     * @param inputTaskNote - task note inputted
     * @param taskYear - year inputted
     * @param taskMonth - month inputted
     * @param taskDate - date inputted
     * @param taskHour - hour inputted
     * @param taskMinute - minute inputted
     */
    public static void addTasks(String inputTaskName, String inputTaskNote, int taskYear, int taskMonth, int taskDate, int taskHour, int taskMinute)
    {
        // Check if the task name is empty
        if (inputTaskName.isEmpty() || inputTaskName == " ")
        {
            System.out.println("\nTask name is empty, please name your task\n");
        }
        // Adding conditional that checks if the date is valid
        if (checkInvalidDate(taskYear, taskMonth, taskDate) || taskHour < 0 || taskHour > 23 || taskMinute < 0 || taskMinute > 59)
        {
            System.out.println("\nInvalid date or time, please input a valid date or time\n");
        }
        else
        {
            // put those inputs into the task object
            Tasks result = new Tasks(inputTaskName, inputTaskNote, taskYear, taskMonth, taskDate, taskHour, taskMinute, false);
            Gson gson = new Gson();
            String resultToString = gson.toJson(result);
            JsonObject resultObj = new JsonParser().parse(resultToString).getAsJsonObject();

            // retrieve data in json
            File myFile = new File("src/tasksData.json");
            try {
                // Declare out JSON Array to prepare to write it into our JSON file
                JsonElement fileElement = JsonParser.parseReader(new FileReader(myFile));
                JsonArray fileArray = fileElement.getAsJsonArray();
                fileArray.add(resultObj);

                fileArray = new TaskSort().sortJSONArray(fileArray);

                // re-write the updated data into the json file
                FileWriter writeEmpty = new FileWriter("src/tasksData.json");
                writeEmpty.write(gson.toJson(fileArray));
                writeEmpty.close();

                System.out.println("\nTask " + result.getName() + " has been added to the list of tasks.\n");

            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Helper function for addTask() to check if the inputted date is valid
     *
     * Reference: https://howtodoinjava.com/java/date-time/date-validation/
     * @param year the year
     * @param month the month
     * @param date the date
     * @return a boolean value of whether if the date is valid or not
     */
    public static boolean checkInvalidDate(int year, int month, int date)
    {
        String dateOutput = year + "-" + formatTwoDigit(String.valueOf(month)) + "-" + formatTwoDigit(String.valueOf(date));
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd") .withResolverStyle(ResolverStyle.STRICT);

        try {
            LocalDate localdate = LocalDate.parse(dateOutput, dateFormat);
            return false;
        } catch (DateTimeParseException e) {
            return true;
        }
    }

    /**
     * remove a task
     * @param taskNum - task number
     * @param nameTask - name of the task
     * @param noteTask - notes of the task
     */
    public static void removeTasks(int taskNum, String nameTask, String noteTask)
    {
        if (taskNum == 0)
        {
            System.out.println("\nIt's cancelled\n");
        }
        if (taskNum < 0)
        {
            System.out.println("\nInvalid number, please don't put negative number\n");
        }
        else
        {
            File myFile = new File("src/tasksData.json");
            int i = 1;
            try {
                JsonElement fileElement = JsonParser.parseReader(new FileReader(myFile));
                JsonArray fileArray = fileElement.getAsJsonArray();

                if (fileArray.size() < 1)
                {
                    System.out.println("\nPlease add new task!\n");
                }
                if (taskNum > fileArray.size())
                {
                    System.out.println("\nthe number is beyond the amount item that our task list have\n");
                }
                else
                {
                    for (JsonElement taskElement : fileArray)
                    {
                        JsonObject taskObj = taskElement.getAsJsonObject();
                        String taskName = taskObj.get("name").getAsString();
                        String taskNote = taskObj.get("notes").getAsString();

                        if (taskName.equals(nameTask) && taskNote.equals(noteTask))
                        {
                            fileArray.remove(taskObj);
                            System.out.println("\nTask " + taskName + " is now removed\n");
                            break;
                        } else {
                            i++;
                        }
                    }
                    // re-write the updated data into the json file
                    fileArray = new TaskSort().sortJSONArray(fileArray);
                    FileWriter writeEmpty = new FileWriter("src/tasksData.json");
                    writeEmpty.write(new Gson().toJson(fileArray));
                    writeEmpty.close();
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Mark a task as completed
     * @param taskNum - task number
     * @param res - boolean result that will be used for updating complete status
     * @param nameTask - task name
     * @param noteTask - task note
     */
    public static void updateCompleteTasks(int taskNum, Boolean res, String nameTask, String noteTask)
    {
        if (taskNum == 0)
        {
            System.out.println("\nIt's cancelled\n");
        }
        if (taskNum < 0)
        {
            System.out.println("\nInvalid number, please don't put negative number\n");
        }
        else
        {
            File myFile = new File("src/tasksData.json");
            int i = 1;
            try {
                JsonElement fileElement = JsonParser.parseReader(new FileReader(myFile));
                JsonArray fileArray = fileElement.getAsJsonArray();

                if (fileArray.size() < 1)
                {
                    System.out.println("\nPlease add new task!\n");
                }
                if (taskNum > fileArray.size())
                {
                    System.out.println("\nthe number is beyond the amount item that our task list have\n");
                }
                else
                {
                    for (JsonElement taskElement : fileArray)
                    {
                        JsonObject taskObj = taskElement.getAsJsonObject();
                        String taskName = taskObj.get("name").getAsString();
                        String taskNote = taskObj.get("notes").getAsString();

                        if (taskName.equals(nameTask) && taskNote.equals(noteTask))
                        {

                            taskObj.remove("completed");
                            taskObj.addProperty("completed", res);
                            System.out.println("\nTask " + taskName + " is now updated\n");
                            break;
                        } else {
                            i++;
                        }
                    }
                    // re-write the updated data into the json file
                    fileArray = new TaskSort().sortJSONArray(fileArray);
                    FileWriter writeEmpty = new FileWriter("src/tasksData.json");
                    writeEmpty.write(new Gson().toJson(fileArray));
                    writeEmpty.close();
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
}
