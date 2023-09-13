package com.GraphQL.GraphQL;

import com.GraphQL.GraphQL.DataFeacher.AllUsersDataFetcher;
import com.GraphQL.GraphQL.DataFeacher.CreateUserDataFetcher;
import com.GraphQL.GraphQL.DataFeacher.UserDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class Provider {
    @Autowired
    UserDataFetcher userDataFetcher;

    @Autowired
    CreateUserDataFetcher createUserDataFetcher;

    @Autowired
    AllUsersDataFetcher allUsersDataFetcher;

    @Value("classpath:schema.graphql")
    private Resource resource;
    private GraphQL graphQL;



    @PostConstruct
    public void init() throws IOException {
        InputStream inputStream = resource.getInputStream();
        String schema = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schema);
        RuntimeWiring runtimeWiring = buildRuntimeWiring();
        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }
    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("getUser", userDataFetcher)
                        .dataFetcher("getAllUsers", allUsersDataFetcher))
                .type("Mutation", typeWiring -> typeWiring
                        .dataFetcher("createUser", createUserDataFetcher))
                .build();
    }


    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

}
