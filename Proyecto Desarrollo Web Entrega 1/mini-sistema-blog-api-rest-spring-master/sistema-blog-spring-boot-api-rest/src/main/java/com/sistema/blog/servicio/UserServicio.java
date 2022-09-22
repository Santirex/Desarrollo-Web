package com.sistema.blog.servicio;

import java.util.List;

import javax.transaction.Transactional;

import com.sistema.blog.entidades.User;
import com.sistema.blog.repositorio.UserRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServicio {

    @Autowired
    private UserRepositorio repo;

    public List<User> listAll() {
        return repo.findAll();
    }

    public void save(User user) {
        repo.save(user);
    }

    public User get(Integer id) {
        return repo.findById(Long.valueOf(id)).get();
    }

    public void delete(Integer id) {
        repo.deleteById(Long.valueOf(id));
    }
}