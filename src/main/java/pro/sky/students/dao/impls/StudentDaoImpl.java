package pro.sky.students.dao.impls;

import org.hibernate.Session;
import pro.sky.students.dao.StudentDao;
import pro.sky.students.model.Student;
import pro.sky.students.utils.HibernateEmployeeSessionFactoryUtils;

public class StudentDaoImpl implements StudentDao {
    @Override
    public Student findById(long id) {
        try (Session session = HibernateEmployeeSessionFactoryUtils.getSessionFactory().openSession()) {
            return session.get(Student.class, id);
        }
    }
}
