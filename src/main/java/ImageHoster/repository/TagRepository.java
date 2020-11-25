package ImageHoster.repository;

import ImageHoster.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

@Repository
public class TagRepository {
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    public Tag createTag(final Tag tag) {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(tag);
            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
        }
        return tag;
    }

    public Tag findTag(final String tagName) {
        final EntityManager em = emf.createEntityManager();
        try {
            final TypedQuery<Tag> typedQuery = em.createQuery("SELECT t from Tag t where t.name =:tagName", Tag.class)
                                                 .setParameter("tagName", tagName);
            return typedQuery.getSingleResult();
        } catch (final NoResultException nre) {
            return null;
        }
    }
}
