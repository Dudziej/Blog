package pl.edu.pjwstk.jaz.blog.posts;

public class PostRequest {

    private Long id;
    private String title;
    private String content;
    private String addedAt;

    public PostRequest() {}

    public PostRequest(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.addedAt = post.getAddedAt();
    }

    public Post toPost() {
        return new Post(id, title, content, addedAt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(String addedAt) {
        this.addedAt = addedAt;
    }
}
