package ImageHoster.service;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    /**
     * Save comment.
     *
     * @param comment - Comment
     */
    public void saveComment(final Comment comment) {
        commentRepository.saveComment(comment);
    }

    /**
     * List Comments for an Image.
     *
     * @param image - Image
     *
     * @return List<Comment>
     */
    public List<Comment> getCommentsForImage(final Image image) {
        return commentRepository.fetchCommentsForImage(image);
    }
}
