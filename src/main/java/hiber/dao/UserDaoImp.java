package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   public List<User> getUsersByCarModelAndSeries(String model, int series) {
      String sql = "SELECT u.* FROM users u JOIN cars c ON u.car_id = c.id WHERE LOWER(c.model) = LOWER(:model) AND c.series = :series";
      Query<User> query = sessionFactory.getCurrentSession().createNativeQuery(sql, User.class);
      query.setParameter("model", model);
      query.setParameter("series", series);
      return query.list();
   }

}
