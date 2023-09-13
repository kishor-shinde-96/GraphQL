package com.GraphQL.GraphQL.DataFeacher;

import com.GraphQL.Model.User;
import com.GraphQL.Repository.UserRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateUserDataFetcher implements DataFetcher<User> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User get(DataFetchingEnvironment environment) {
        String username = environment.getArgument("username");
        String email = environment.getArgument("email");
        String address = environment.getArgument("address");
        String phone = environment.getArgument("phone");

        // Create a new User entity
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setAddress(address);
        user.setPhone(phone);

        // Save the user to the database
        return userRepository.save(user);
    }
}
