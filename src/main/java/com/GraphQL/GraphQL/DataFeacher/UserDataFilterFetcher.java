package com.GraphQL.GraphQL.DataFeacher;

import com.GraphQL.Model.User;
import com.GraphQL.Repository.UserRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDataFilterFetcher implements DataFetcher<List<User>> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<User> get(DataFetchingEnvironment environment) {
        String username = environment.getArgument("username");
        String email = environment.getArgument("email");
        String address = environment.getArgument("address");
        String phone = environment.getArgument("phone");

        // Create CriteriaBuilder and CriteriaQuery
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);

        // Create Root and define the entity to query
        Root<User> userRoot = criteriaQuery.from(User.class);

        // Create a list to hold predicates (conditions)
        List<Predicate> predicates = new ArrayList<>();

        // Add conditions based on the provided arguments
        if (username != null && !username.isEmpty()) {
            predicates.add(criteriaBuilder.equal(userRoot.get("username"), username));
        }

        if (email != null && !email.isEmpty()) {
            predicates.add(criteriaBuilder.equal(userRoot.get("email"), email));
        }

        if (address != null && !address.isEmpty()) {
            predicates.add(criteriaBuilder.equal(userRoot.get("address"), address));
        }

        if (phone != null && !phone.isEmpty()) {
            predicates.add(criteriaBuilder.equal(userRoot.get("phone"), phone));
        }

        // Combine predicates with AND operator
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        // Execute the query
        List<User> users = entityManager.createQuery(criteriaQuery).getResultList();
        return users;
    }
}
