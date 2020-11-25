package ImageHoster.controller;

import ImageHoster.model.Image;
import ImageHoster.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {
    private final ImageService imageService;

    @RequestMapping("/")
    public String getAllImages(final Model model) {
        log.debug("Display all Images");
        final List<Image> images = imageService.getAllImages();
        model.addAttribute("images", images);
        return "index";
    }
}