package pro.sky.students.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import pro.sky.students.model.Avatar;
import pro.sky.students.model.Student;

public class HibernateEmployeeSessionFactoryUtils {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration config = new Configuration().configure("hibernate.cfg.xml");
            config.addAnnotatedClass(Student.class);
            config.addAnnotatedClass(Avatar.class);
            sessionFactory = config.buildSessionFactory(
                    new StandardServiceRegistryBuilder()
                            .applySettings(config.getProperties()).build());
        }
        return sessionFactory;

    }
}
