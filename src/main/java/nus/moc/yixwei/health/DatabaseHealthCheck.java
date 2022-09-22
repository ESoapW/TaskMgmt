package nus.moc.yixwei.health;

import com.codahale.metrics.health.HealthCheck;
import nus.moc.yixwei.db.TaskDAO;

public class DatabaseHealthCheck extends HealthCheck {
    private final TaskDAO myDAO;

    public DatabaseHealthCheck(TaskDAO testDAO) {
        this.myDAO = testDAO;
    }

    @Override
    protected Result check() throws Exception {
        if (myDAO.ping()==1) {
            return Result.healthy();
        }
        return Result.unhealthy("Can't connect to database");
    }
}
