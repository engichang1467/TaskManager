/**
 * Task Sort Object
 * This is where it sort tasks in JSON array based on their due date
 * @author Michael Chang
 */

package control;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TaskSort {
    /**
     * Using bubble sort to sort the task based on their due date
     * Reference: https://www.geeksforgeeks.org/bubble-sort/
     * @param arr input JSON array
     * @return arr a newly sorted array
     */
    public static JsonArray sortJSONArray(JsonArray arr)
    {
        int length = arr.size();
        JsonObject curr;
        JsonObject nextCurr;
        JsonObject temp;
        for (int i = 0; i < length -1; i++)
        {
            for (int j = 0; j < length - i -1; j++)
            {
                curr = getJSONObj(arr, j);
                nextCurr = getJSONObj(arr, j+1);

                // Check if the current Task is greater than the next Task
                if (getDeadLine(curr).compareTo(getDeadLine(nextCurr)) == 1)
                {
                    // Start swapping
                    temp = curr;
                    arr = insertJSONToArray(j, nextCurr, arr);
                    arr = insertJSONToArray(j+1, temp, arr);
                }
            }
        }
        return arr;
    }

    /**
     * Get the date from the task object
     * @param taskObj the task object in JSONObject
     * @return the deadline in Gregorian Calender format
     */
    public static Calendar getDeadLine(JsonObject taskObj)
    {
        int taskYear = taskObj.get("year").getAsInt();
        int taskMonth = taskObj.get("month").getAsInt();
        int taskDate = taskObj.get("day").getAsInt();
        int taskHour = taskObj.get("hour").getAsInt();
        int taskMinute = taskObj.get("minute").getAsInt();
        return new GregorianCalendar(taskYear, taskMonth-1, taskDate, taskHour, taskMinute);
    }

    /**
     * Getting a specific JSON object from a JSON Array
     * @param arr our JSON array
     * @param i the index of where the object is
     * @return the specific JSON object from the array
     */
    public static JsonObject getJSONObj(JsonArray arr, int i)
    {
        JsonElement res = arr.get(i);
        return res.getAsJsonObject();
    }

    /**
     * Inserting a JSON object into a specific position of the JSON Array
     * @param index - index of the array
     * @param jObj - json object that we want to insert
     * @param arr - json array
     * @return
     */
    public static JsonArray insertJSONToArray(int index, JsonObject jObj, JsonArray arr)
    {
        JsonArray tempArr = new JsonArray();
        for (int i = 0; i < index; i++) { tempArr.add(arr.get(i)); }
        tempArr.add(jObj);
        for (int j = index + 1; j < arr.size(); j++) { tempArr.add(arr.get(j)); }

        arr = tempArr;
        return arr;
    }
}
