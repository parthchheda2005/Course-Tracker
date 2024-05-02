package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Data representing one individual course
public class Course implements Writable {
    private ArrayList<String> days;
    private String startTime;
    private String endTime;
    private int credits;
    private String name;

    // EFFECTS: Constructs a Course
    public Course(String name, String startTime, String endTime, int credits) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.credits = credits;
        days = new ArrayList<String>();
        EventLog.getInstance().logEvent(new Event("New course " + name + " was created"));
    }

    // MODIFIES: this
    // EFFECTS: adds a day to days
    public void addDay(String day) {
        days.add(day);
    }

    // REQUIRES: days.contains(day) == true
    // MODIFIES: this
    // EFFECTS: remove a day from days
    public void removeDay(String day) {
        days.remove(day);
    }

    public ArrayList<String> getDays() {
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
        EventLog.getInstance().logEvent(new Event("Days were changed"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        EventLog.getInstance().logEvent(new Event("Name of " + this.name + " has been changed to " + name));
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        EventLog.getInstance().logEvent(new Event("Credits for " + this.name
                + " has been changed to " + credits));
        this.credits = credits;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        EventLog.getInstance().logEvent(new Event("End Time for " + this.name
                + " has been changed to " + endTime));
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        EventLog.getInstance().logEvent(new Event("Start Time for " + this.name
                + " has been changed to " + endTime));
        this.startTime = startTime;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("startTime", startTime);
        json.put("endTime", endTime);
        json.put("credits", credits);
        JSONArray jsonArray = new JSONArray();
        for (String s : days) {
            jsonArray.put(s);
        }
        json.put("days", jsonArray);
        return json;
    }
}
