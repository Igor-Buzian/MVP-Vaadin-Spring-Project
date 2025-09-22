package com.example.backend.repository;

import com.example.core.interfaces.UserDao;
import com.example.core.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
@Transactional
public class UserDaoRepository implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger logger = LoggerFactory.getLogger(UserDaoRepository.class);

    @Override
    public Optional<User> getById(Long id) {
        logger.info("Try to get getById with id: "+id);

        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> getByEmail(String email) {
        logger.info("Try to get email with name: {}", email);
        List<User> result = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();

        return result.stream().findFirst();
    }


    @Override
    public void save(User user) {
        logger.info("Try to save user with " +
                "\n id: {} \n with name: {} \n with email: {}"
                ,user.getId(), user.getName(),user.getEmail());

        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        logger.info("Try to update user with " +
                        "\n id: {} \n with name: {} \n with email: {}"
                ,user.getId(), user.getName(),user.getEmail());

        entityManager.merge(user);
    }

    @Override
    public void delete(User user) {
        logger.info("Try to delete user with " +
                        "\n id: {} \n with name: {} \n with email: {}"
                ,user.getId(), user.getName(),user.getEmail());

        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
    }

    @Override
    public List<User> getAll() {
        logger.info("Try to take all users");
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public boolean existsByEmail(String email) {
        Long count = entityManager
                .createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();

        return count > 0;
    }
}
