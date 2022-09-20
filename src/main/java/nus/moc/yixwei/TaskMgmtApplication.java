package nus.moc.yixwei;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import nus.moc.yixwei.core.DummyTaskRepository;
import nus.moc.yixwei.core.TaskRepository;
import nus.moc.yixwei.resources.TaskResource;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.EnumSet;

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
        // Enable CORS headers
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        DateFormat eventDateFormat = new SimpleDateFormat(configuration.getDateFormat());
        environment.getObjectMapper().setDateFormat(eventDateFormat);
        TaskRepository repository = new DummyTaskRepository();
        TaskResource taskResource = new TaskResource(repository);
        environment.jersey().register(taskResource);
    }

}
