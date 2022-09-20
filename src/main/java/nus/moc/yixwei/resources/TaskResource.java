package nus.moc.yixwei.resources;

import io.dropwizard.jersey.params.IntParam;
import io.dropwizard.jersey.params.LongParam;
import nus.moc.yixwei.api.Task;
import com.codahale.metrics.annotation.Timed;
import nus.moc.yixwei.core.TaskRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {
    private TaskRepository repository;

    public TaskResource(TaskRepository repository) {
        this.repository = repository;
    }

    @GET
    public List<Task> allTasks() {
        return repository.findAll();
    }

    @GET
    @Path("{id}")
    public Task task(@PathParam("id") IntParam id) {
        return repository.findById(id.get())
                .orElseThrow(() ->
                        new WebApplicationException("Task not found", 404));
    }

    @POST
    public Task create(Task task) {
        return repository.create(task);
    }

    @PUT
    @Path("{id}")
    public Task update(@PathParam("id") IntParam id, Task task) {
        return repository.update(id.get(), task)
                .orElseThrow(() ->
                        new WebApplicationException("Task not found", 404));
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") IntParam id) {
        repository.delete(id.get());
        return Response.ok().build();
    }
}
