package nus.moc.yixwei.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {
    private int id;
    private String name;
    private String date;

    public Task() {
        // Jackson deserialization
    }

    public Task(int id, String name, String date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    @JsonProperty
    public int getId() {
        return id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public String getDate() {
        return date;
    }

    @JsonProperty
    public void setId(int newId) {
        this.id = newId;
    }

    @JsonProperty
    public void setName(String newName) {
        this.name = newName;
    }

    @JsonProperty
    public void setDate(String newDate) {
        this.date = newDate;
    }

    public void updateTask(Task task)  {
        this.name = task.name;
        this.date = task.date;
    }
}
