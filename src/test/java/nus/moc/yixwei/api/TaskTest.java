package nus.moc.yixwei.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static io.dropwizard.jackson.Jackson.newObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * This test is used as an example in the docs - if you update it, consider
 * updating the docs too.
 */
class TaskTest {
    private static final ObjectMapper MAPPER = newObjectMapper();

    @Test
    void serializesToJSON() throws Exception {
        final Task task = new Task(1, "Onboarding - first day", "2022-05-17T09:00+0800");

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(getClass().getResource("/fixtures/task.json"), Task.class));

        assertThat(MAPPER.writeValueAsString(task)).isEqualTo(expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final Task task = new Task(1, "Onboarding - first day", "2022-05-17T09:00+0800");
        assertThat(MAPPER.readValue(getClass().getResource("/fixtures/task.json"), Task.class).getId())
                .isEqualTo(task.getId());
        assertThat(MAPPER.readValue(getClass().getResource("/fixtures/task.json"), Task.class).getName())
                .isEqualTo(task.getName());
        assertThat(MAPPER.readValue(getClass().getResource("/fixtures/task.json"), Task.class).getDate())
                .isEqualTo(task.getDate());
    }
}