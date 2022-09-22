package nus.moc.yixwei.db;

import nus.moc.yixwei.api.Task;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

public interface TaskDAO {
    @SqlQuery("select 1")
    int ping();

    @SqlQuery("select * from task order by date desc, id desc")
    @RegisterBeanMapper(Task.class)
    List<Task> findAll();

    @SqlQuery("select * from task where id = :id")
    @RegisterBeanMapper(Task.class)
    Optional<Task> findById(@Bind("id") int id);

    @SqlQuery("select max(id) from task")
    int findLargestId();

    @SqlUpdate("insert into task (id, name, date) values (:id, :name, :date)")
    void insert(@Bind("id") int id, @Bind("name") String name, @Bind("date") String date);

    @SqlUpdate("update task set name = :name, date = :date where id = :id")
    void update(@Bind("id") int id, @Bind("name") String name, @Bind("date") String date);

    @SqlUpdate("delete from task where id = :id")
    void delete(@Bind("id") int id);

    @SqlUpdate("DROP TABLE IF EXISTS task")
    void dropExistingTable();

    @SqlUpdate("CREATE TABLE task (id Integer NOT NULL primary key, name varchar(100) NOT NULL, date varchar(100) NOT NULL)")
    void createTaskTable();

    @SqlUpdate("insert into task (id, name, date) values (1, 'Onboarding - first day', '2022-05-17T09:00+0800'), (2, 'Offboarding - last day', '2022-09-16T18:00+0800')")
    void insertInitData();
}


