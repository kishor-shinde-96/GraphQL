package com.GraphQL.GraphQL.DataFeacher;

import com.GraphQL.Model.User;
import com.GraphQL.Repository.UserRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDataFetcher implements DataFetcher<User> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User get(DataFetchingEnvironment environment) {

        String id = environment.getArgument("id");
        System.out.println(id);
       return userRepository.findById(Long.parseLong(id)).orElse(null);
    }
}