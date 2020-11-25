package ImageHoster.controller;

import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.model.UserProfile;
import ImageHoster.service.ImageService;
import ImageHoster.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Controller
public class UserController {
    private final UserService userService;
    private final ImageService imageService;

    @Autowired
    public UserController(final UserService userService, final ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    //This controller method is called when the request pattern is of type 'users/registration'
    //This method declares User type and UserProfile type object
    //Sets the user profile with UserProfile type object
    //Adds User type object to a model and returns 'users/registration.html' file
    @RequestMapping("users/registration")
    public String registration(final Model model) {
        log.info("registration");
        final User user = new User();
        final UserProfile profile = new UserProfile();
        user.setProfile(profile);
        model.addAttribute("User", user);
        return "users/registration";
    }

    //This controller method is called when the request pattern is of type 'users/registration' and also the incoming
    // request is of POST type
    //This method calls the business logic and after the user record is persisted in the database, directs to login page
    @RequestMapping(value = "users/registration", method = RequestMethod.POST)
    public String registerUser(final User user, final Model model) {
        log.info("registerUser - {}", user.toString());

        if (isValidPassword(user.getPassword())) {
            userService.registerUser(user);
            return "redirect:/users/login";
        } else {
            final User newUser = new User();
            final UserProfile profile = new UserProfile();
            newUser.setProfile(profile);
            model.addAttribute("User", newUser);
            model.addAttribute("passwordTypeError",
                               "Password must contain atleast 1 alphabet, 1 number & 1 special character");
            return "users/registration";
        }
    }

    //This controller method is called when the request pattern is of type 'users/login'
    @RequestMapping("users/login")
    public String login() {
        log.info("login");
        return "users/login";
    }

    //This controller method is called when the request pattern is of type 'users/login' and also the incoming
    // request is of POST type
    //The return type of the business logic is changed to User type instead of boolean type. The login() method in
    // the business logic checks whether the user with entered username and password exists in the database and
    // returns the User type object if user with entered username and password exists in the database, else returns null
    //If user with entered username and password exists in the database, add the logged in user in the Http Session
    // and direct to user homepage displaying all the images in the application
    //If user with entered username and password does not exist in the database, redirect to the same login page
    @RequestMapping(value = "users/login", method = RequestMethod.POST)
    public String loginUser(final User user, final HttpSession session) {
        log.info("loginUser User -> {}", user.toString());
        final User existingUser = userService.login(user);
        if (existingUser != null) {
            session.setAttribute("loggeduser", existingUser);
            return "redirect:/images";
        } else {
            return "users/login";
        }
    }

    //This controller method is called when the request pattern is of type 'users/logout' and also the incoming
    // request is of POST type
    //The method receives the Http Session and the Model type object
    //session is invalidated
    //All the images are fetched from the database and added to the model with 'images' as the key
    //'index.html' file is returned showing the landing page of the application and displaying all the images in the
    // application
    @RequestMapping(value = "users/logout", method = RequestMethod.POST)
    public String logout(final Model model, final HttpSession session) {
        log.info("logout");
        session.invalidate();
        final List<Image> images = imageService.getAllImages();
        model.addAttribute("images", images);
        return "index";
    }

    private boolean isValidPassword(final String password) {
        final String regex = "^(?=.*[0-9])((?=.*[a-z])|(?=.*[A-Z]))(?=.*[@#$%^&+=]).{3,}$";
        final Pattern pattern = Pattern.compile(regex);
        if (password == null) {
            return false;
        }
        final Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
