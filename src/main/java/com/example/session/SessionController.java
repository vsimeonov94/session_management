package com.example.session;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class SessionController {

    private static final String SESSION_MESSAGES = "MY_SESSION_MESSAGES";

    private static final String SESSION_OBJECT = "MY_SESSION_OBJECT";

    @GetMapping("/")
    public String process(Model model, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute(SESSION_MESSAGES);
        if (messages == null) {
            messages = new ArrayList<>();
        }

        Foo foo = (Foo) session.getAttribute(SESSION_OBJECT);
        if (foo == null) {
            foo = new Foo();
        }

        System.out.println(foo.getBar());
        System.out.println(foo.getBaz());

        model.addAttribute("sessionMessages", messages);
        model.addAttribute("sessionObject", foo);

        return "index";
    }

    @PostMapping("/persistMessage")
    public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) request.getSession().getAttribute(SESSION_MESSAGES);
        if (messages == null) {
            messages = new ArrayList<>();
            request.getSession().setAttribute(SESSION_MESSAGES, messages);
        }
        messages.add(msg);
        request.getSession().setAttribute(SESSION_MESSAGES, messages);
        return "redirect:/";
    }

    @PostMapping(value = "/persistObject")
    public String persistObject(@ModelAttribute(value="foo") Foo foo,  HttpServletRequest request) {
        System.out.println(foo.getBar());
        System.out.println(foo.getBaz());
        request.getSession().setAttribute(SESSION_OBJECT, foo);

        return "redirect:/";
    }

    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }
}
