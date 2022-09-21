package nus.moc.yixwei.db;

import nus.moc.yixwei.api.Task;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

public interface TaskDAO {
    @SqlQuery("select * from task order by date desc, id desc")
    @RegisterBeanMapper(Task.class)
    List<Task> findAll();

    @SqlQuery("select * from task where id = :id")
    @RegisterBeanMapper(Task.class)
    Optional<Task> findById(@Bind("id") int id);

    @SqlUpdate("insert into task (id, name, date) values (:id, :name, :date)")
    void insert(@Bind("id") int id, @Bind("name") String name, @Bind("date") String date);

    @SqlUpdate("update task set name = :name, date = :date where id = :id")
    void update(@Bind("id") int id, @Bind("name") String name, @Bind("date") String date);

    @SqlUpdate("delete from task where id = :id")
    void delete(@Bind("id") int id);
}


