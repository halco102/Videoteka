package com.diplomski_rad.videoteka.repository.person;

import com.diplomski_rad.videoteka.model.Person;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractPersonRepository<T extends Person> extends CouchbaseRepository<T, String> {
}
