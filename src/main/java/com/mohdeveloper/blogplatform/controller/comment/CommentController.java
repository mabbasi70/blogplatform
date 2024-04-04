package com.mohdeveloper.blogplatform.controller.comment;

import com.mohdeveloper.blogplatform.entity.Comment;
import com.mohdeveloper.blogplatform.model.SecureUser;
import com.mohdeveloper.blogplatform.service.impl.CommentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentServiceImpl commentService;

    public CommentController(CommentServiceImpl commentService){
        this.commentService = commentService;
    }

    @PostMapping("/addComment")
    public String postAddComment(@Valid @ModelAttribute("comment")Comment comment,
                               @RequestParam("postId") Long postId,
                               @AuthenticationPrincipal SecureUser secureUser,
                               BindingResult bindingResult){
        commentService.addComment(comment, postId, secureUser);
        return "redirect:/blogs/post/" + postId;
    }
}
