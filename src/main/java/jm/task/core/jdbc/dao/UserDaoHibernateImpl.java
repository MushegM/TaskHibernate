package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {



    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction tx = null;
        String  create = """
                CREATE TABLE if not exists `user_test_db`.`user` (
                  `id` INT NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(45) NOT NULL,
                  `lastname` VARCHAR(45) NOT NULL,
                  `age` INT(3) NULL,
                  PRIMARY KEY (`id`))
                ENGINE = InnoDB
                DEFAULT CHARACTER SET = utf8;""";
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createSQLQuery(create).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }


    }

    @Override
    public void dropUsersTable() {
        String drop = "DROP TABLE if exists User";
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createSQLQuery(drop).executeUpdate();
            tx.commit();
        }  catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User user = (User) session.load(User.class, id);
            session.delete(user);
            tx.commit();
        }

    }

    @Override
    public List<User> getAllUsers() {
        String getAll = "SELECT u FROM User u";

        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            tx.commit();
            return  session.createQuery(getAll, User.class).getResultList();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
return new ArrayList<>();
    }

    @Override
    public void cleanUsersTable() {
        String clean ="TRUNCATE `user_test_db`.`User`";
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createSQLQuery(clean).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }
}
