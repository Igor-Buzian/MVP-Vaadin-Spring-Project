package com.example.vaadin.dao;

import com.example.vaadin.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<User> getById(Long id) {
        System.out.println("UserDaoImpl getById");
        return Optional.ofNullable(sessionFactory.getCurrentSession().find(User.class, id));
    }

    @Override
    public void save(User user) {
        System.out.println("UserDaoImpl save");
        sessionFactory.getCurrentSession().persist(user);
        sessionFactory.getCurrentSession().flush();

    }

    @Override
    public void update(User user) {
        System.out.println("UserDaoImpl update");
        sessionFactory.getCurrentSession().merge(user);
        sessionFactory.getCurrentSession().flush();

    }

    @Override
    public void delete(User user) {
        System.out.println("UserDaoImpl delete");
        sessionFactory.getCurrentSession().remove(user);
        sessionFactory.getCurrentSession().flush();

    }

    @Override
    public List<User> getAll() {
        System.out.println("UserDaoImpl getAll");
        return sessionFactory.getCurrentSession().createQuery("from User", User.class).list();
    }
}

