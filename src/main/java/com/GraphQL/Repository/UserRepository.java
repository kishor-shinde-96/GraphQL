package com.GraphQL.Repository;

import com.GraphQL.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByIdAndUsernameAndEmailAndAddressAndPhone(long l, String username, String email, String address, String phone);
}
