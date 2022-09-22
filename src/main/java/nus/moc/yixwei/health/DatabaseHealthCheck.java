package nus.moc.yixwei.health;

import com.codahale.metrics.health.HealthCheck;
import nus.moc.yixwei.db.TaskDAO;

public class DatabaseHealthCheck extends HealthCheck {
    private final TaskDAO DAO;

    public DatabaseHealthCheck(TaskDAO testDAO) {
        this.DAO = testDAO;
    }

    @Override
    protected Result check() throws Exception {
        if (DAO.ping()==1) {
            return Result.healthy();
        }
        return Result.unhealthy("Can't connect to database");
    }
}
