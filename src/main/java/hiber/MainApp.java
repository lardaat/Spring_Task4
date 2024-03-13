package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import hiber.service.CarService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);
      CarService carService = context.getBean(CarService.class);

      /*Car car1 = new Car("BMW", 600);
      Car car2 = new Car("GAZ", 66);
      carService.add(car1);
      carService.add(car2);

      userService.add(new User("User1", "Lastname1", "user1@mail.ru", car1));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru", car2));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));*/

//      carService.add(new Car("BMW", 600));
//      carService.add(new Car("GAZ", 66));



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

      List<Car> cars = carService.listCars();
      for (Car car : cars) {
         System.out.println("Id = "+ car.getId());
         System.out.println("Model = "+car.getModel());
         System.out.println("Series = "+car.getSeries());
         System.out.println();
      }

      User userByCar = userService.getUserByCarModelAndSeries("BMW", 600);
      if (userByCar != null) {
         System.out.println("Пользователь, владеющий BMW 600-й серии:");
         System.out.println("Id = " + userByCar.getId());
         System.out.println("First Name = " + userByCar.getFirstName());
         System.out.println("Last Name = " + userByCar.getLastName());
         System.out.println("Email = " + userByCar.getEmail());
      } else {
         System.out.println("Пользователь, владеющей BMW 600-й серии не найден.");
      }

      User userByCar1 = userService.getUserByCarModelAndSeries("GAZ", 66);
      if (userByCar1 != null) {
         System.out.println("Пользователь, владеющий ГАЗ-66:");
         System.out.println("Id = " + userByCar1.getId());
         System.out.println("First Name = " + userByCar1.getFirstName());
         System.out.println("Last Name = " + userByCar1.getLastName());
         System.out.println("Email = " + userByCar1.getEmail());
      } else {
         System.out.println("Пользователь, владеющей ГАЗ-66 не найден.");
      }

      context.close();
   }
}
