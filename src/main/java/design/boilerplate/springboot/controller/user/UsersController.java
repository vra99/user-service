package design.boilerplate.springboot.controller.user;
import design.boilerplate.springboot.model.User;
import design.boilerplate.springboot.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UsersController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> fetchUsersList() {
        try {
            return userService.fetchUsersList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/users/{username}")
    public User fetchUserById(@PathVariable String username) {
        try {
            User user = userService.findByUsername(username);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping("/users/{username}/address")
    public User addAddress(@PathVariable String username, @RequestBody String address) {
        try {
            User user = userService.addAddress(username, address);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/users/{username}")
    public String deleteUser(@PathVariable String username) {
        try {
            userService.deleteUser(username);
            return "User deleted successfully";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update operation
    @PutMapping("/users/{id}")
    public User
    updateUser(@RequestBody User user,
                     @PathVariable("id") Long userId) {
        try {
            return userService.updateUser(
                    user, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping("/users/{id}/password")
    public User updatePassword(@RequestBody User user, @PathVariable("id") Long userId) {
        try {
            return userService.updatePassword(user, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
        return null;
    }
}
