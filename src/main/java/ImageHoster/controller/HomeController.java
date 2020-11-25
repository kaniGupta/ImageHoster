package ImageHoster.controller;

import ImageHoster.model.Image;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ImageService imageService;

    @Autowired
    public HomeController(final ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping("/")
    public String getAllImages(final Model model) {
        final List<Image> images = imageService.getAllImages();
        model.addAttribute("images", images);
        return "index";
    }
}