package pl.edu.pjwstk.jaz.blog.posts;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PostRepository {

    @PersistenceContext
    private EntityManager em;

    public Optional<Post> getPostById(Long postId) {
        var post = em.find(Post.class, postId);
        return Optional.ofNullable(post);
    }

    @Transactional
    public List<Post> getUserPosts(String username) {
        return em.createQuery("FROM Post WHERE author.username = :username ORDER BY addedAt DESC", Post.class)
                .setParameter("username", username).getResultList();
    }

    @Transactional
    public void save(Post post) {
        if(post.getId() == null) {
            em.persist(post);
        }
        else {
            em.merge(post);
        }
    }

    public List<Post> getAllPosts() {
        return em.createQuery("FROM Post ORDER BY addedAt DESC", Post.class).getResultList();
    }
}