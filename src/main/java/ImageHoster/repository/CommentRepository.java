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

    public Comment saveComment(final Comment comment) {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(comment);
            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
        }
        return comment;
    }

    public List<Comment> fetchCommentsForImage(final Image image) {
        final EntityManager em = emf.createEntityManager();
        final TypedQuery<Comment> query =
                em.createQuery("SELECT c from Comment c where c.image = :image", Comment.class)
                  .setParameter("image", image);
        final List<Comment> resultList = query.getResultList();
        log.info("Comments -> {}", resultList.toString());
        return resultList;
    }
}
