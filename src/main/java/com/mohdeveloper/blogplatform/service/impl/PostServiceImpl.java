package com.mohdeveloper.blogplatform.service.impl;

import com.mohdeveloper.blogplatform.entity.ImageData;
import com.mohdeveloper.blogplatform.entity.Post;
import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.model.SecureUser;
import com.mohdeveloper.blogplatform.repository.PostRepository;
import com.mohdeveloper.blogplatform.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {


    private final PostRepository postRepository;
    private final ImageServiceImpl imageService;

    private final UserServiceImpl userService;


    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           ImageServiceImpl imageService,
                           UserServiceImpl userService){
        this.imageService = imageService;
        this.postRepository = postRepository;
        this.userService = userService;
    }
    @Override
    public Page<Post> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Iterable<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    @Transactional
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Post createPost(Post post, MultipartFile image, SecureUser secureUser) throws IOException {
        ImageData imageData = new ImageData();
        imageData.setFilePath(imageService.saveImage(image));
        imageService.save(imageData);
        post.setImageData(imageData);
        Optional<User> optionalUser = userService.findByEmail(secureUser.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            post.setAuthor(user);
        }
        return this.save(post);
    }
}
