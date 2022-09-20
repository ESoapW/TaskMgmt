package nus.moc.yixwei;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotEmpty;

public class TaskMgmtConfiguration extends Configuration {
    // TODO: implement service configuration
    @NotEmpty
    private String dateFormat;

    public String getDateFormat() {
        return dateFormat;
    }
}
