package com.mohdeveloper.blogplatform.controller.blog;

import com.mohdeveloper.blogplatform.entity.Comment;
import com.mohdeveloper.blogplatform.entity.Post;
import com.mohdeveloper.blogplatform.model.SecureUser;
import com.mohdeveloper.blogplatform.service.impl.CommentServiceImpl;
import com.mohdeveloper.blogplatform.service.impl.LikeServiceImpl;
import com.mohdeveloper.blogplatform.service.impl.PostServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostServiceImpl postService;
    private final CommentServiceImpl commentService;
    private final LikeServiceImpl likeService;

    @Autowired
    public PostController(PostServiceImpl postService,
                          CommentServiceImpl commentService,
                          LikeServiceImpl likeService){
        this.postService = postService;
        this.commentService = commentService;
        this.likeService = likeService;
    }

    @GetMapping
    public String getBlogs(Model model) {
        Iterable<Post> posts = postService.findAll();
        model.addAttribute("posts",posts);
        return "posts";
    }
    
    @GetMapping("/{postId}")
    public String getMethodName(@PathVariable Long postId, Model model, @AuthenticationPrincipal SecureUser secureUser) {
        Optional<Post> optionalPost = postService.findById(postId);
        if(optionalPost.isPresent()){
            Iterable<Comment> comments = commentService.findAllByPostId(postId);
            Post post = optionalPost.get();
            List<Long> usersId = post.getLikes().stream().map(like -> like.getLikedBy().getId()).toList();
            model.addAttribute("userId", secureUser.getId());
            model.addAttribute("usersId", usersId);
            model.addAttribute("comments", comments);
            model.addAttribute("comment", new Comment());
            model.addAttribute("post", post);
            return "post";
        }
        return null;
    }

    @PostMapping("/{postId}/comments")
    public String addCommentToPost(@Valid @ModelAttribute("comment")Comment comment,
                                   @PathVariable Long postId,
                                   @AuthenticationPrincipal SecureUser secureUser,
                                   BindingResult bindingResult){
        commentService.addComment(comment, postId, secureUser);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/{postId}/toggle-like")
    public String postAddLikeOrRemoveLike(@PathVariable Long postId,
                                          @AuthenticationPrincipal SecureUser secureUser){
        likeService.addLikeOrRemoveLike(postId, secureUser);
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/new")
    public String getNewPost(Model model ) {
        model.addAttribute("path", "newPost");
        model.addAttribute("post", new Post());
        return "newPost";
    }
    @PostMapping
    public String postPost(@Valid @ModelAttribute(name = "post") Post post,
                           BindingResult bindingResult,
                           @RequestParam("imageFile") MultipartFile image,
                           @AuthenticationPrincipal SecureUser secureUser,
                           RedirectAttributes redirectAttributes
                           ) throws IOException {
        try{
            postService.createPost(post, image, secureUser);
            redirectAttributes.addFlashAttribute("messageType","success");
            redirectAttributes.addFlashAttribute("message","Your post successfully uploaded!");
            return "redirect:/posts";


        }catch (Exception e){
            redirectAttributes.addFlashAttribute("messageType", "error");
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/posts/new";
        }
    }




}
