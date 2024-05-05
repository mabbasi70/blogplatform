package com.mohdeveloper.blogplatform.controller.blog;


import com.mohdeveloper.blogplatform.entity.Post;
import com.mohdeveloper.blogplatform.model.SecureUser;
import com.mohdeveloper.blogplatform.service.impl.PostServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/my-posts")
public class MyPostsController {

    private final PostServiceImpl postService;

    public MyPostsController(PostServiceImpl postService){
        this.postService = postService;
    }
    @GetMapping
    public String getMyPosts(@AuthenticationPrincipal SecureUser secureUser, Model model){
        Iterable<Post> posts = postService.findAllByUserId(secureUser.getId());
        model.addAttribute("posts", posts);
        return "myPosts";
    }

    @DeleteMapping("/{id}")
    public String postDeleteMyPosts(@PathVariable Long id){
            //login for check if the account is deleting the post is the owner of the post
        postService.deleteById(id);
        return "redirect:/my-posts";
    }
}
