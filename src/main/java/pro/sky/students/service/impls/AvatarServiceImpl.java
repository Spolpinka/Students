package pro.sky.students.service.impls;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.students.dao.AvatarDao;
import pro.sky.students.model.Avatar;
import pro.sky.students.model.Student;
import pro.sky.students.service.AvatarService;
import pro.sky.students.service.StudentService;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {

    @Value("${student.avatar.dir.path}")
    private String avatarDir;

    private final AvatarDao avatarDao;
    private final StudentService studentService;

    public AvatarServiceImpl(AvatarDao avatarDao, StudentService studentService) {
        this.avatarDao = avatarDao;
        this.studentService = studentService;
    }

    @Override
    public List<Avatar> getAll() {
        return avatarDao.getAll();
    }

    @Override
    public void uploadAvatar(long id, MultipartFile avatar) throws IOException {
        Student student = studentService.getStudentBuId(id);

        //создаем имя файла на сервере
        Path path = Path.of(avatarDir, id + getExtension(avatar.getOriginalFilename()));
        //создаем или перевоздаем новый файл с этим именем
        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);

        //записываем в файл, сначала простые потоки, потом оборачиваем в буфферизированные потоки
        try (InputStream is = avatar.getInputStream();
             OutputStream os = Files.newOutputStream(path, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        Avatar avatarObj = getAvatarById(id);
        avatarObj.setStudent(student);
        avatarObj.setFilePath(path.toString());
        avatarObj.setFileSize(avatarObj.getFileSize());
        avatarObj.setMediaType(avatar.getContentType());
        avatarObj.setData(generateAvatarPreview(path));

        avatarDao.save(avatarObj);
    }

    private byte[] generateAvatarPreview(Path path) {
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(path), 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {
            //загружаем картинку
            BufferedImage image = ImageIO.read(bis);
            //
            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();

            ImageIO.write(preview, getExtension(path.getFileName().toString()), baos);
            return baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Avatar getAvatarById(Long id) {
        return avatarDao.findById(id);
    }

    @Override
    public Avatar findStudentAvatar(long id) {
        return studentService.getStudentBuId(id).getAvatar();
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
