package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);


      Car car1 = new Car("BMW", 600);
      Car car2 = new Car("GAZ", 66);

      Session session = context.getBean(SessionFactory.class).openSession();
      Transaction tx = null;
      try {
         tx = session.beginTransaction();
         session.save(car1);
         session.save(car2);
         tx.commit();

         userService.add(new User("User1", "Lastname1", "user1@mail.ru", car1));
         userService.add(new User("User2", "Lastname2", "user2@mail.ru", car2));
         userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
         userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

      } catch (Exception e) {
         if (tx != null) tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }

      /*userService.add(new User("User1", "Lastname1", "user1@mail.ru", car1));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru", car2));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));*/





      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         Car userCar = user.getCar();
         if (userCar != null) {
            System.out.println("Car Model = " + userCar.getModel());
            System.out.println("Car Series = " + userCar.getSeries());
      }
         System.out.println();
      }

      /*List<Car> cars = carService.listCars();
      for (Car car : cars) {
         System.out.println("Id = "+ car.getId());
         System.out.println("Model = "+car.getModel());
         System.out.println("Series = "+car.getSeries());
         System.out.println();
      }*/

      List<User> usersByCar = userService.getUsersByCarModelAndSeries("BMW", 600);
      if (!usersByCar.isEmpty()) {
         System.out.println("Пользователи, владеющие BMW 600-й серии:");
         for (User user : usersByCar) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
         }
      } else {
         System.out.println("Пользователи, владеющие BMW 600-й серии, не найдены.");
      }

      List<User> usersByCar1 = userService.getUsersByCarModelAndSeries("GAZ", 66);
      if (!usersByCar1.isEmpty()) {
         System.out.println("Пользователи, владеющие ГАЗ-66:");
         for (User user : usersByCar1) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
         }
      } else {
         System.out.println("Пользователи, владеющие ГАЗ-66, не найдены.");
      }
      context.close();
   }
}
