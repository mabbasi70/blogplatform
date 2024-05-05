package com.mohdeveloper.blogplatform.service;

import com.mohdeveloper.blogplatform.entity.ImageData;
import com.mohdeveloper.blogplatform.entity.Post;
import com.mohdeveloper.blogplatform.model.SecureUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.Optional;

public interface PostService {
    Page<Post> findAll(Pageable pageable);
    Iterable<Post> findAll();

    Post save(Post post);
    Post createPost(Post post, MultipartFile image, SecureUser secureUser) throws IOException;

    Optional<Post> findById(Long id);

    Iterable<Post> findAllByUserId(Long id);

    void deleteById(Long id);
}
