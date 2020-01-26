package edu.udacity.java.nano.chat;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Controller
public class ChatRoomController {

    @RequestMapping("/login")
    public String login(@RequestParam("username") String userName) {
        System.out.println("Inside login");
        return "redirect:/chat/"+userName;
    }

    @RequestMapping("/chat/{username}")
    public String chat(@PathVariable("username") String userName) {
        ModelAndView view = new ModelAndView();
        view.addObject("username", userName);
        return "chat";
    }
}
