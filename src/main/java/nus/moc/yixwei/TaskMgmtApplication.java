package nus.moc.yixwei;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import nus.moc.yixwei.core.DummyTaskRepository;
import nus.moc.yixwei.core.TaskRepository;
import nus.moc.yixwei.resources.TaskResource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TaskMgmtApplication extends Application<TaskMgmtConfiguration> {

    public static void main(final String[] args) throws Exception {
        new TaskMgmtApplication().run(args);
    }

    @Override
    public String getName() {
        return "TaskMgmt";
    }

    @Override
    public void initialize(final Bootstrap<TaskMgmtConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final TaskMgmtConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        DateFormat eventDateFormat = new SimpleDateFormat(configuration.getDateFormat());
        environment.getObjectMapper().setDateFormat(eventDateFormat);
        TaskRepository repository = new DummyTaskRepository();
        TaskResource taskResource = new TaskResource(repository);
        environment.jersey().register(taskResource);
    }

}
