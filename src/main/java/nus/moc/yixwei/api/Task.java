package nus.moc.yixwei.api;

public class Task {
    private int id;
    private String name;
    private String date;

//    public Task(int id, String name, String date) {
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

    public String getDate() {
        return date;
    }

    public void setId(int newId) {
        this.id = newId;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setDate(String newDate) {
        this.date = newDate;
    }

    public void updateTask(Task task)  {
        this.name = task.name;
        this.date = task.date;
    }
}
