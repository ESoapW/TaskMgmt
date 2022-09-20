package nus.moc.yixwei.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import nus.moc.yixwei.api.Task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class DummyTaskRepository implements TaskRepository {
    private static final String DATA_SOURCE = "task_data.json";
    private List<Task> tasks;

    // prohibit concurrent IO
    public DummyTaskRepository() {
        try {
            initData();
        } catch (IOException e) {
            throw new RuntimeException(DATA_SOURCE + "missing or unreadable", e);
        }
    }

    private void initData() throws IOException {
        URL url = Resources.getResource(DATA_SOURCE);
        String json = Resources.toString(url , Charsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        CollectionType type = mapper
                .getTypeFactory()
                .constructCollectionType(List.class, Task.class);
        tasks = mapper.readValue(json, type);
    }

//    private void updateData() throws IOException{
//        URL url = Resources.getResource(DATA_SOURCE);
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(new File(Resources.getResource(DATA_SOURCE).toString()), this.tasks);
//    }

    @Override
    public List<Task> findAll() {
        return tasks;
    }

    @Override
    public Optional<Task> findById(int id) {
        return tasks.stream().filter(t -> t.getId() == id).findFirst();
    }

    @Override
    public Task create(Task task) {
        Optional<Integer> maxId = tasks.stream()
                .map(Task::getId)
                .max(Integer::compare);
        int nextId = maxId.map(x -> x+1).orElse(1);
        task.setId(nextId);
        tasks.add(task);
//        try {
//            updateData();
//        } catch (IOException e) {
//            throw new RuntimeException(DATA_SOURCE + "missing or unreadable", e);
//        }
        return task;
    }

    @Override
    public Optional<Task> update(int id, Task task) {
        Optional<Task> existingTask = findById(id);
        existingTask.ifPresent(t -> t.updateTask(task));
        return existingTask;
    }

    @Override
    public void delete(int id) {
        tasks.removeIf(t -> t.getId() == id);
    }
}
