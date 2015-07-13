package com.epages.demo.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Value("#{environment.INSTALL_LINK ?: 'https://www.epagesdemo.de/epages/TrialEpagesD20150529T084132R81.admin/?ViewAction=MBO-ViewAppDetails&appID=54f46f318732110bd85f41c7'}")
    private String installLink;

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("installLink", installLink);
        return "home";
    }
}
