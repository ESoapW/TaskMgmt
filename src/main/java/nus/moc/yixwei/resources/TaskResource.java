package nus.moc.yixwei.resources;

import nus.moc.yixwei.api.Task;
import nus.moc.yixwei.db.TaskDAO;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {
    TaskDAO taskDao;

    public TaskResource(Jdbi jdbi) {
        taskDao = jdbi.onDemand(TaskDAO.class);
    }

    public TaskResource(TaskDAO newTaskDao) {
        taskDao = newTaskDao;
    }

    @GET
    public List<Task> allTasks() {
        return taskDao.findAll();
    }

    @GET
    @Path("/{id}")
    public Task task(@PathParam("id") Integer id) {
        return taskDao.findById(id)
                .orElseThrow(() ->
                        new WebApplicationException("Task not found", 404));
    }

    @POST
    // This input will be deserialized from json, task id will be 0 because it is not included in the input.
    // The input task id does not matter because it will never be used.
    public String create(Task task) {
        int cur_largest_id = taskDao.findLargestId();
        taskDao.insert(++cur_largest_id, task.getName(), task.getDate());
        return String.format("Created task %s", cur_largest_id);
    }

    @PUT
    @Path("/{id}")
    public Task update(@PathParam("id") Integer id, Task task) {
        Optional<Task> existingTask = taskDao.findById(id);
        existingTask.ifPresent(t -> {
                t.updateTask(task);
                taskDao.update(id, t.getName(), t.getDate());
        });
        return existingTask
                .orElseThrow(() ->
                        new WebApplicationException("Task not found", 404));
    }

    @DELETE
    @Path("/{id}")
    public String delete(@PathParam("id") int id) {
        taskDao.delete(id);
        return String.format("Deleted task %s", id);
    }

    @POST
    @Path("/init")
    public String initData() {
        taskDao.dropExistingTable();
        taskDao.createTaskTable();
        taskDao.insertInitData();
        return "Initialized tasks data";
    }
}
