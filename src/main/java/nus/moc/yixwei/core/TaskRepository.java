package nus.moc.yixwei.core;

import nus.moc.yixwei.api.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findAll();
    Optional<Task> findById(int id);
    Task create(Task task);
    Optional<Task> update(int id, Task task);
    void delete(int id);
}
