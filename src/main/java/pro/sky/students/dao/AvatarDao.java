package pro.sky.students.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.students.model.Avatar;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvatarDao
        //extends JpaRepository<Avatar, Long>
{
    List<Avatar> getAll();

    Avatar findById(Long aLong);

    void save(Avatar avatar);

    //method update
    void update(Avatar avatar);

    void delete(long id);
}
