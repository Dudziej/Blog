package pl.edu.pjwstk.jaz.blog.posts;

import pl.edu.pjwstk.jaz.blog.ParamRetriever;
import pl.edu.pjwstk.jaz.blog.auth.AccountContext;
import pl.edu.pjwstk.jaz.blog.auth.AccountRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Named
@RequestScoped
public class PostController {

    @Inject
    private PostRepository postRepository;

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private AccountContext accountContext;

    @Inject
    private ParamRetriever paramRetriever;

    private PostRequest postRequest;

    public PostRequest getPostRequest() {
        if (postRequest == null) {
            postRequest = createPostRequest();
        }
        return postRequest;
    }

    private PostRequest createPostRequest() {
        if (paramRetriever.contains("postId")) {
            Long postId = null;
            try {
                postId = paramRetriever.getLong("postId");
            } catch (NumberFormatException err) {
                return new PostRequest();
            }

            Post post;
            if(postRepository.getPostById(postId).isEmpty()) {
                return new PostRequest();
            }
            else {
                post = postRepository.getPostById(postId).get();
            }

            if (!post.getAuthor().getUsername().equals(accountContext.getUsername())) {
                return new PostRequest();
            }
            return new PostRequest(post);
        }
        return new PostRequest();
    }

    public String save() {
        Post post = postRequest.toPost();
        post.setAuthor(accountRepository.getAccount(accountContext.getUsername()));

        if(post.getAddedAt().equals("")) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            post.setAddedAt(dateFormatter.format(now));
        }

        postRepository.save(post);

        return "/userPosts.xhtml?faces-redirect=true";
    }

    public List<Post> getUserPosts() {
        return postRepository.getUserPosts(accountContext.getUsername());
    }

    public List<Post> getAllPosts() {
        return postRepository.getAllPosts();
    }

}
