package pro.sky.students.service;

import org.springframework.stereotype.Service;
import pro.sky.students.model.Student;

@Service
public interface StudentService {
    Student getStudentBuId(long id);
}
