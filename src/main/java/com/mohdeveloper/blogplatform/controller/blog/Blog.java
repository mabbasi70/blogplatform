package com.mohdeveloper.blogplatform.controller.blog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;





@Controller
public class Blog {

    @GetMapping("/blogs")
    public String getBlogs() {
        return "blog";
    }
    
    @GetMapping("/post")
    public String getMethodName() {
        return "post";
    }

    @GetMapping("/newpost")
    public String getNewPost(Model model ) {
        model.addAttribute("path", "newPost");
        return "newPost";
    }  
}
