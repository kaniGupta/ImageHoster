package ImageHoster.repository;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.List;

@Slf4j
@Repository
public class CommentRepository {
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    /**
     * Save comment.
     *
     * @param comment - Comment
     *
     * @return Comment
     */
    public Comment saveComment(final Comment comment) {
        log.info("Save comment.");
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(comment);
            transaction.commit();
        } catch (final Exception e) {
            log.error("Exception -> {}", e.getLocalizedMessage());
            transaction.rollback();
        }
        return comment;
    }

    /**
     * Fetch comments for an Image.
     *
     * @param image - Image
     *
     * @return List<Comment>
     */
    public List<Comment> fetchCommentsForImage(final Image image) {
        log.info("Fetch comments for the image.");
        final EntityManager em = emf.createEntityManager();
        final TypedQuery<Comment> query =
                em.createQuery("SELECT c from Comment c where c.image = :image", Comment.class)
                  .setParameter("image", image);
        final List<Comment> resultList = query.getResultList();
        return resultList;
    }
}
