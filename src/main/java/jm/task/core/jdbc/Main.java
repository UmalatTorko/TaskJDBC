package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Alex", "Green", (byte) 27);
        userService.saveUser("Ron", "Black", (byte) 25);
        userService.saveUser("Mary", "Long", (byte) 17);
        userService.saveUser("Emma", "Grey", (byte) 32);

        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
