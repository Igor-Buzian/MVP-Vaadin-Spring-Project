package com.example.backend.impl;

import com.example.core.dao.UserDao;
import com.example.core.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> getById(Long id) {
        System.out.println("UserDaoImpl getById");
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public void save(User user) {
        System.out.println("UserDaoImpl save");
        entityManager.persist(user);
    }

    @Override
    public void update(User user) {
        System.out.println("UserDaoImpl update");
        entityManager.merge(user);
    }

    @Override
    public void delete(User user) {
        System.out.println("UserDaoImpl delete");
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
    }

    @Override
    public List<User> getAll() {
        System.out.println("UserDaoImpl getAll");
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }
}
