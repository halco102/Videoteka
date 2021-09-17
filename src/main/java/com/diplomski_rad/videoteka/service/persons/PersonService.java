package com.diplomski_rad.videoteka.service.persons;

import com.diplomski_rad.videoteka.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService<T extends Person> {

    Optional<T> getById(String id);
    List<T> getAllPersons();
    void deleteById(String id);

}
