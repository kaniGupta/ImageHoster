package ImageHoster.repository;

import ImageHoster.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

@Repository
public class UserRepository {
    //Get an instance of EntityManagerFactory from persistence unit with name as 'imageHoster'
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    //The method receives the User object to be persisted in the database
    //Creates an instance of EntityManager
    //Starts a transaction
    //The transaction is committed if it is successful
    //The transaction is rolled back in case of unsuccessful transaction
    public void registerUser(final User newUser) {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            //persist() method changes the state of the model object from transient state to persistence state
            em.persist(newUser);
            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
        }
    }

    //The method receives the entered username and password
    //Creates an instance of EntityManager
    //Executes JPQL query to fetch the user from User class where username is equal to received username and password
    // is equal to received password
    //Returns the fetched user
    //Returns null in case of NoResultException
    public User checkUser(final String username, final String password) {
        try {
            final EntityManager em = emf.createEntityManager();
            final TypedQuery<User> typedQuery =
                    em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password",
                                   User.class);
            typedQuery.setParameter("username", username);
            typedQuery.setParameter("password", password);

            return typedQuery.getSingleResult();
        } catch (final NoResultException nre) {
            return null;
        }
    }
}