package com.mohdeveloper.blogplatform.service.impl;

import com.mohdeveloper.blogplatform.entity.Friendship;
import com.mohdeveloper.blogplatform.entity.User;
import com.mohdeveloper.blogplatform.entity.UserTokenExpiry;
import com.mohdeveloper.blogplatform.model.SecureUser;
import com.mohdeveloper.blogplatform.model.SignupUser;
import com.mohdeveloper.blogplatform.repository.UserRepository;
import com.mohdeveloper.blogplatform.service.EmailService;
import com.mohdeveloper.blogplatform.service.FriendshipService;
import com.mohdeveloper.blogplatform.service.UserService;
import com.mohdeveloper.blogplatform.service.UserTokenExpiryService;
import com.mohdeveloper.blogplatform.util.Utility;
import jakarta.mail.MessagingException;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final FriendshipService friendshipService;
    private final UserTokenExpiryService userTokenExpiryService;

    public UserServiceImpl(UserRepository userRepository,
                           @Lazy FriendshipService friendshipService,
                           EmailService emailService,
                           UserTokenExpiryService userTokenExpiryService) {
        this.emailService = emailService;
        this.friendshipService = friendshipService;
        this.userRepository = userRepository;
        this.userTokenExpiryService = userTokenExpiryService;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User createUser(SignupUser signupUser) {
        Optional<User> existingUser = userRepository.findByEmail(signupUser.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("An Account Exists with provided email!");
        } else if (!signupUser.getPassword().equals(signupUser.getConfirmPassword())) {
            throw new RuntimeException("Passwords Are Not Match");
        } else {
            User user = signupUser.toUser();
            Friendship selfFriendship = new Friendship();
            selfFriendship.setRequester(user);
            selfFriendship.setReceiver(user);
            selfFriendship.setStatus(Friendship.Status.YOURSELF);
            selfFriendship.setRespondAt(LocalDateTime.now());
            friendshipService.save(selfFriendship);
            final String token = Utility.generateToken(24);
            final LocalDateTime tokenExpiration = LocalDateTime.now().plusHours(24);
            final String baseUrl = "http://localhost:2000/signup/verify-email";
//            final String baseUrl = "https://tl2brjvb-2000.use.devtunnels.ms/verify-email";
            final String url = Utility.createEncodedURL(baseUrl, token);
            user.setToken(token);
            user.setTokeExpiration(tokenExpiration);
            user.setUsername(user.getEmail().split("@")[0]);
            final User savedUser = userRepository.save(user);
            try {

                emailService.sendHtmlEmail(user.getEmail(),
                        "Email Confirmation",
                        """
                                <html>
                                    <head>
                                        <style>
                                            body { font-family: 'Arial', sans-serif; }
                                            .header { color: #333366; font-size: 24px; font-weight: bold; }
                                            .content { margin-top: 20px; font-size: 16px; }
                                            .button {
                                                background-color: #4CAF50;
                                                border: none;
                                                color: white;
                                                padding: 15px 32px;
                                                text-align: center;
                                                text-decoration: none;
                                                display: inline-block;
                                                font-size: 16px;
                                                margin: 4px 2px;
                                                cursor: pointer;
                                                border-radius: 5px;
                                            }
                                        </style>
                                    </head>
                                    <body>
                                       <h1 class="header">Welcome to Blog Platform</h1>
                                       <p class="content">Please complete your sign-up process by clicking the link below. You have 24 hours to confirm your email.</p>
                                       <a href='%s' class="button">Confirm!</a>
                                    </body>
                                </html>
                                """.formatted(url));

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            return savedUser;
        }

    }

    @Override
    @Transactional
    public void verifyEmailAndSetTokenToNull(String token) {
        Optional<User> user = userRepository.findByToken(token);
        if (user.isPresent() && Objects.equals(user.get().getToken(), token)) {
            user.get().setToken(null);
            user.get().setTokeExpiration(null);
            user.get().setEnabled(true);
        } else {
            throw new RuntimeException("zart!");
        }
    }

    @Override
    public Page<User> findUserChats(SecureUser secureUser, Long currentUserId, Pageable pageable) {
        return userRepository.findUserChats(currentUserId, pageable);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findUsersByActivityStatusOnline() {
        return userRepository.findUsersByActivityStatusOnline();
    }


    @Override
    public Page<User> findUserByUsername(String username, Pageable pageable) {
        return userRepository.findByUsernameContainingIgnoreCase(username, pageable);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user;
        } else {
            throw new RuntimeException("User Does Not Exist!");
        }
    }

    @Override
    public Optional<User> findByToken(String token) {
        return userRepository.findByToken(token);
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(usernameOrEmail)
                .map(SecureUser::new)
                .orElseGet(() -> userRepository
                        .findByEmail(usernameOrEmail)
                        .map(SecureUser::new)
                        .orElseThrow(() -> new UsernameNotFoundException("There is no username with this username or email: " + usernameOrEmail)));
    }


    @Scheduled(fixedRate = 300000)
    public void verifyActiveSessions() {
        List<User> users = userRepository.findUsersByActivityStatusOnline();
        Instant threshold = Instant.now().minusMillis(15 * 60 * 1000);

        users.forEach(user -> {
            // Check last activity logic here, depending on how you track user activity
            if ((user.getLastActivity() == null) || user.getLastActivity().isBefore(threshold)) {
                user.setActivityStatus(User.ActivityStatus.OFFLINE);
                userRepository.save(user);
            }
        });
    }


    @Transactional
    @Override
    public Boolean send6CharToken(String email){
        Optional<User> optionalUser = findByEmail(email);
        if(optionalUser.isPresent() && Objects.equals(email, optionalUser.get().getEmail())){
            Optional<UserTokenExpiry> optionalToken = userTokenExpiryService.findByUser(optionalUser.get());
            final String token = Utility.generate6CharToken();
            UserTokenExpiry newToken;
            newToken = optionalToken.orElseGet(UserTokenExpiry::new);

            newToken.setToken(token);
            newToken.setUser(optionalUser.get());
            newToken.setTokenExpiry(LocalDateTime.now().plusMinutes(30));
            userTokenExpiryService.save(newToken);
            try{
                emailService.sendSimpleEmail(email,"verification code",token);
            }catch (Exception e){
                throw new RuntimeException("Email was not sent.please try again later");
            }
            return true;
        }else {
            throw new UsernameNotFoundException("not found!");
        }

    }

    @Override
    public Boolean checkToken(String email,String token) {
        Optional<User> optionalUser = findByEmail(email);
        if(optionalUser.isEmpty() || !Objects.equals(email, optionalUser.get().getEmail())) {
            throw new RuntimeException("This account is not yours!");
        }
        Optional<UserTokenExpiry> optionalToken = userTokenExpiryService.findByUser(optionalUser.get());
        if(LocalDateTime.now().isAfter(optionalToken.get().getTokenExpiry())){
            throw new RuntimeException("tokek is expired");
        }
        return Objects.equals(optionalToken.get().getToken(), token);
    }

    @Transactional
    public void changePass(String email, String password){
        Optional<User> optionalUser = findByEmail(email);
        User retreivedUser = optionalUser.orElseThrow(()-> new RuntimeException("no such user!"));
        retreivedUser.setPassword(password);
    }


}