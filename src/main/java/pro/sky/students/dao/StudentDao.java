package pro.sky.students.dao;

import org.springframework.stereotype.Repository;
import pro.sky.students.model.Student;

@Repository
public interface StudentDao {
    Student findById(long id);
}
