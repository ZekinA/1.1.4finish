package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import java.util.List;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException{

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Howard","Lovecraft",(byte) 46);
        System.out.println("User с именем - Howard Lovecraft в базу данных");
        userService.saveUser("Edgard","Poe",(byte) 40);
        System.out.println("User с именем - Edgard Poe в базу данных");
        userService.saveUser("Clive","Barker",(byte) 68);
        System.out.println("User с именем - Clive Barker в базу данных");
        userService.saveUser("Stephen","King",(byte) 73);
        System.out.println("User с именем - Stephen King в базу данных");

        List<User> userList = userService.getAllUsers();
        for (User user : userList){
            System.out.println(user.toString());
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}