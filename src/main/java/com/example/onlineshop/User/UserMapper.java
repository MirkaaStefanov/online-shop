package com.example.onlineshop.User;

import com.example.onlineshop.Config.BCrypt;
import com.example.onlineshop.Employee.Employee;
import com.example.onlineshop.Employee.EmployeeRepository;
import com.example.onlineshop.Employee.EmployeeUserDto;
import com.example.onlineshop.ShoppingCart.ShoppingCart;
import com.example.onlineshop.ShoppingCart.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private BCrypt bCrypt;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public User toEntity(UserDto userDto) {
        User user = new User();
        ShoppingCart shoppingCart = new ShoppingCart();
        user.setUsername(userDto.getUsername());
        user.setPassword(bCrypt.passwordEncoder().encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setEnabled(true);
        user.setRole("User");
        shoppingCartRepository.save(shoppingCart);
        user.setShoppingCart(shoppingCart);

        return user;
    }

    public User employee(EmployeeUserDto userDto) {
        Employee employee = new Employee();
        employee.setFirstName(userDto.getFirstName());
        employee.setLastName(userDto.getLastName());
        employee.setEmployeeAge(userDto.getEmployeeAge());
        employee.setSalary(userDto.getSalary());
        employeeRepository.save(employee);

        User user = new User();
        user.setEnabled(true);
        user.setPassword(bCrypt.passwordEncoder().encode(userDto.getPassword()));
        user.setUsername(Integer.toString(employee.getId()));
        user.setEmployee(employee);
        user.setRole("Employee");
        return user;
    }

}
