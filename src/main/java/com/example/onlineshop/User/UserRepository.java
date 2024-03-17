package com.example.onlineshop.User;

import com.example.onlineshop.User.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Integer> {
    public User getUserByUsername(String username);
    public User getUserByEmail(String email);


}
