package nus.moc.yixwei.resources;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import nus.moc.yixwei.api.Task;
import nus.moc.yixwei.db.TaskDAO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.dropwizard.jackson.Jackson.newObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class TaskResourceTest {
    private static final int TEST_TASK_ID = 1;
    private static final String TEST_TASK_NAME = "Onboarding - first day";
    private static final String TEST_TASK_DATE = "2022-05-17T09:00+0800";
    private static final TaskDAO DAO = mock(TaskDAO.class);
    private static final ResourceExtension EXT = ResourceExtension.builder()
            .addResource(new TaskResource(DAO))
            .build();
    private Task task;

    @BeforeEach
    void setup() {
        task = new Task(TEST_TASK_ID, TEST_TASK_NAME, TEST_TASK_DATE);
    }

    @AfterEach
    void tearDown() {
        reset(DAO);
    }

    @Test
    void getTaskSuccess() {
        when(DAO.findById(1)).thenReturn(Optional.of(task));

        Task found = EXT.target("/tasks/1").request().get(Task.class);

        assertThat(found.getId()).isEqualTo(task.getId());
        assertThat(found.getName()).isEqualTo(task.getName());
        assertThat(found.getDate()).isEqualTo(task.getDate());
        verify(DAO).findById(1);
    }

    @Test
    void getTaskNotFound() {
        when(DAO.findById(2)).thenReturn(Optional.empty());
        final Response response = EXT.target("/tasks/2").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(DAO).findById(2);
    }

    @Test
    void getAllTaskSuccess() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        when(DAO.findAll()).thenReturn(tasks);

        List found = EXT.target("/tasks").request().get(List.class);
        Object foundTask = found.get(0);

        assertThat(found.size()).isEqualTo(1);
        // System.out.println(foundTask.toString());
        // request() cannot get class of List<Task>, assert String instead
        assertThat(foundTask.toString().substring(4, 5)).isEqualTo(String.valueOf(task.getId()));
        assertThat(foundTask.toString().substring(12, 34)).isEqualTo(task.getName());
        assertThat(foundTask.toString().substring(41, 62)).isEqualTo(task.getDate());
        verify(DAO).findAll();
    }

    @Test
    void createTaskSuccess() {
        String found = EXT.target("/tasks").request().post(Entity.entity(task, MediaType.APPLICATION_JSON_TYPE), String.class);

        assertThat(found).isEqualTo("Created task 1");
        verify(DAO).insert(TEST_TASK_ID, TEST_TASK_NAME, TEST_TASK_DATE);
    }

    @Test
    void updateTaskSuccess() {
        when(DAO.findById(1)).thenReturn(Optional.of(task));

        String task2 =  "{\"name\": \"Updated\", \"date\": \"2022-06-17T09:00+0800\"}";
        Task foundUpdate = EXT.target("/tasks/1").request().put(Entity.entity(task2, MediaType.APPLICATION_JSON_TYPE), Task.class);

        assertThat(foundUpdate.getId()).isEqualTo(task.getId());
        assertThat(foundUpdate.getName()).isEqualTo("Updated");
        assertThat(foundUpdate.getDate()).isEqualTo("2022-06-17T09:00+0800");
        verify(DAO).update(TEST_TASK_ID, "Updated", "2022-06-17T09:00+0800");
    }

    @Test
    void updateTaskNotFound() {
        when(DAO.findById(2)).thenReturn(Optional.empty());
        DAO.update(2, "Updated", "2022-06-17T09:00+0800" );
        String task2 =  "{\"name\": \"Updated\", \"date\": \"2022-06-17T09:00+0800\"}";
        final Response response = EXT.target("/tasks/2").request().put(Entity.entity(task2, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(DAO).update(2, "Updated", "2022-06-17T09:00+0800" );
    }

    @Test
    void deleteTaskSuccess() {
        when(DAO.findById(1)).thenReturn(Optional.of(task));

        String found = EXT.target("/tasks/1").request().delete(String.class);

        assertThat(found).isEqualTo(String.format("Deleted task %s", task.getId()));
        verify(DAO).delete(TEST_TASK_ID);
    }

}
