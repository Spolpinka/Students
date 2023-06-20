package pro.sky.students.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.students.model.Avatar;

import java.io.IOException;
import java.util.List;

@Service
public interface AvatarService {
    List<Avatar> getAll();

    void uploadAvatar(long id, MultipartFile avatar) throws IOException;

    Avatar findStudentAvatar(long id);
}
