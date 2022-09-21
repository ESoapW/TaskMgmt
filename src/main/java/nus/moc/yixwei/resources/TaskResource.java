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
    public String create(Task task) {
        taskDao.insert(taskDao.findLargestId()+1, task.getName(), task.getDate());
        return String.format("Created task %s", taskDao.findAll().size());
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
}
