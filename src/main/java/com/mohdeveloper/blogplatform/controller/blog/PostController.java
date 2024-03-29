package com.mohdeveloper.blogplatform.controller.blog;


import com.mohdeveloper.blogplatform.entity.ImageData;
import com.mohdeveloper.blogplatform.entity.Post;
import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.model.SecureUser;
import com.mohdeveloper.blogplatform.repository.ImageDataRepository;
import com.mohdeveloper.blogplatform.service.impl.ImageServiceImpl;
import com.mohdeveloper.blogplatform.service.impl.PostServiceImpl;
import com.mohdeveloper.blogplatform.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
public class PostController {


    private final PostServiceImpl postService;

    @Autowired
    public PostController(PostServiceImpl postService){

        this.postService = postService;

    }
    @ModelAttribute(name = "post")
    public Post post(){
        return new Post();
    }

    @GetMapping("/newpost")
    public String getNewPost(Model model ) {
        model.addAttribute("path", "newPost");
        return "newPost";
    }

    @PostMapping("/newpost")
    public String postPost(@ModelAttribute(name = "post") Post post,
                           @RequestParam("imageFile") MultipartFile image,
                           @AuthenticationPrincipal SecureUser secureUser) throws IOException {
//        ImageData imageData = new ImageData();
//        imageData.setFilePath(imageService.saveImage(image));
//        imageDataRepository.save(imageData);
//        imageService.save(imageData);
//        post.setImageData(imageData);
//        Optional<User> optionalUser = userService.findByEmail(secureUser.getEmail());
//        if(optionalUser.isPresent()){
//            User user = optionalUser.get();
//            post.setAuthor(user);
//        }
//        postService.save(post);
        postService.createPost(post, image, secureUser);
        return "redirect:/blogs";
    }
}
