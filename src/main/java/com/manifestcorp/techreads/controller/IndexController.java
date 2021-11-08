package com.manifestcorp.techreads.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping("/")
    public RedirectView index() {
        return new RedirectView("books");
    }
}
