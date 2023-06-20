package pro.sky.students.dao.impls;

import org.hibernate.Session;
import pro.sky.students.dao.AvatarDao;
import pro.sky.students.model.Avatar;
import pro.sky.students.utils.HibernateEmployeeSessionFactoryUtils;

import javax.persistence.Query;
import java.util.List;

public class AvatarDaoImpl implements AvatarDao {
    @Override
    public List<Avatar> getAll() {
        try (Session session = HibernateEmployeeSessionFactoryUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Avatar", Avatar.class).list();
        }
    }

    @Override
    public Avatar findById(Long aLong) {
        try (Session session = HibernateEmployeeSessionFactoryUtils.getSessionFactory().openSession()) {
            return session.get(Avatar.class, aLong);
        }
    }

    @Override
    public void save(Avatar avatar) {
        try (Session session = HibernateEmployeeSessionFactoryUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(avatar);
            session.getTransaction().commit();
        }
    }

    //method update
    @Override
    public void update(Avatar avatar) {
        try (Session session = HibernateEmployeeSessionFactoryUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(avatar);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(long id) {
        try (Session session = HibernateEmployeeSessionFactoryUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createNativeQuery("DELETE FROM avatar WHERE id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }

}
