package ImageHoster.repository;

import ImageHoster.model.Image;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ImageRepository {

    //Get an instance of EntityManagerFactory from persistence unit with name as 'imageHoster'
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    //The method receives the Image object to be persisted in the database
    //Creates an instance of EntityManager
    //Starts a transaction
    //The transaction is committed if it is successful
    //The transaction is rolled back in case of unsuccessful transaction
    public Image uploadImage(final Image newImage) {

        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(newImage);
            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
        }
        return newImage;
    }

    //The method creates an instance of EntityManager
    //Executes JPQL query to fetch all the images from the database
    //Returns the list of all the images fetched from the database
    public List<Image> getAllImages() {
        final EntityManager em = emf.createEntityManager();
        final TypedQuery<Image> query = em.createQuery("SELECT i from Image i", Image.class);
        final List<Image> resultList = query.getResultList();

        return resultList;
    }

    //The method creates an instance of EntityManager
    //Executes JPQL query to fetch the image from the database with corresponding title
    //Returns the image in case the image is found in the database
    //Returns null if no image is found in the database
    public Image getImageByTitle(final String title) {
        final EntityManager em = emf.createEntityManager();
        try {
            final TypedQuery<Image> typedQuery =
                    em.createQuery("SELECT i from Image i where i.title =:title", Image.class)
                      .setParameter("title", title);
            return typedQuery.getSingleResult();
        } catch (final NoResultException nre) {
            return null;
        }
    }

    //The method creates an instance of EntityManager
    //Executes JPQL query to fetch the image from the database with corresponding id
    //Returns the image fetched from the database
    public Image getImage(final Integer imageId) {
        final EntityManager em = emf.createEntityManager();
        final TypedQuery<Image> typedQuery = em.createQuery("SELECT i from Image i where i.id =:imageId", Image.class)
                                               .setParameter("imageId", imageId);
        final Image image = typedQuery.getSingleResult();
        return image;
    }

    //The method receives the Image object to be updated in the database
    //Creates an instance of EntityManager
    //Starts a transaction
    //The transaction is committed if it is successful
    //The transaction is rolled back in case of unsuccessful transaction
    public void updateImage(final Image updatedImage) {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.merge(updatedImage);
            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
        }
    }

    //The method receives the Image id of the image to be deleted in the database
    //Creates an instance of EntityManager
    //Starts a transaction
    //Get the image with corresponding image id from the database
    //This changes the state of the image model from detached state to persistent state, which is very essential to
    // use the remove() method
    //If you use remove() method on the object which is not in persistent state, an exception is thrown
    //The transaction is committed if it is successful
    //The transaction is rolled back in case of unsuccessful transaction
    public void deleteImage(final Integer imageId) {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            final Image image = em.find(Image.class, imageId);
            em.remove(image);
            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
        }
    }
}
