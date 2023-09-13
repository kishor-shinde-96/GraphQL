package com.GraphQL.GraphQL.DataFeacher;

import com.GraphQL.Model.User;
import com.GraphQL.Repository.UserRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllUsersDataFetcher implements DataFetcher<List<User>> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> get(DataFetchingEnvironment environment) {
        return userRepository.findAll();
    }
}
