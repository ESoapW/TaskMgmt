package nus.moc.yixwei.api;

import java.util.Date;

public class Task {
    private int id;
    private String name;
    private Date date;

//    public Task(int id, String name, Date date) {
//        this.id = id;
//        this.name = name;
//        this.date = date;
//    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int newId) {
        this.id = newId;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setDate(Date newDate) {
        this.date = newDate;
    }

    public void updateTask(Task task)  {
        this.name = task.name;
        this.date = task.date;
    }
}
