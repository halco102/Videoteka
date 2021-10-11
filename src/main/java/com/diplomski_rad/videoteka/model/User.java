package com.diplomski_rad.videoteka.model;


import com.diplomski_rad.videoteka.payload.response.BoughtContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import javax.validation.constraints.*;
import java.util.*;

@Data
@AllArgsConstructor
@Document
public class User extends Person{

    @Field
    @NotEmpty(message = "Cannot be empty")
    @NotNull(message = "Cannot be null")
    @Size(min = 5, max = 20, message = "Size must be between 5 and 20")
    private String username;

    @Field
    @NotEmpty(message = "Cannot be empty")
    @NotNull(message = "Cannot be null")
    @Size(min = 8, max = 20, message = "Size must be between 5 and 20")
    private String password;

    @Field
    @NotEmpty(message = "Cannot be empty")
    @NotNull(message = "Cannot be null")
    @Size(min = 8, max = 20, message = "Size must be between 5 and 20")
    private String confirmPassword;

    @Field
    @Email(message = "Use valid email")
    @NotBlank(message = "Email field cannot be blank")
    private String eMail;

    @Field
    private SortedSet<String> roles;

/*    @Field
    private List<Content> ownedItems;*/
    @Field
    private List<BoughtContent> ownedItems;

    @Field
    private int money;

/*    @Field
    private Set<Role> roles = new HashSet<>();*/

    public User(String firstName, String lastName, String username, String password, String eMail) {
        super(firstName,lastName);
        this.username = username;
        this.password = password;
        this.eMail = eMail;
        this.roles = new TreeSet<>();
        this.ownedItems = new ArrayList<>();
    }

    public User() {
        this.ownedItems = new ArrayList<>();
    }

}
