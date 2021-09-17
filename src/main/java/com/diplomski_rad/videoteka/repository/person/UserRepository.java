package com.diplomski_rad.videoteka.repository.person;

import com.diplomski_rad.videoteka.model.User;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends AbstractPersonRepository<User> {

    @Query("#{#n1ql.selectEntity} where username = $1 and #{#n1ql.filter}")
    Optional<User> checkIfUserExists(String userName);


    @Query("#{#n1ql.selectEntity} where eMail = $1 and #{#n1ql.filter}")
    Optional<User> checkEmail(String email);


    @Query("#{#n1ql.selectEntity} where username = $1 and password = $2 and #{#n1ql.filter}")
    Optional<User> checkIfUserIsInDatabase(String username, String password);

}
