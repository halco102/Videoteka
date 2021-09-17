package com.diplomski_rad.videoteka.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class User extends Person{

    @Field
    private String username;

    @Field
    private String password;

    @Field
    private String confirmPassword;

    @Field
    private String eMail;

    @Field
    private SortedSet<String> roles;

/*    @Field
    private Set<Role> roles = new HashSet<>();*/

    public User(String firstName, String lastName, String username, String password, String eMail) {
        super(firstName,lastName);
        this.username = username;
        this.password = password;
        this.eMail = eMail;
        this.roles = new TreeSet<>();
    }

}
