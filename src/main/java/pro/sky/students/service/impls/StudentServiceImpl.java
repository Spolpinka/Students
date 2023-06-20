package pro.sky.students.service.impls;

import org.springframework.stereotype.Service;
import pro.sky.students.dao.StudentDao;
import pro.sky.students.model.Student;
import pro.sky.students.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Student getStudentBuId(long id) {
        return studentDao.findById(id);
    }
}
