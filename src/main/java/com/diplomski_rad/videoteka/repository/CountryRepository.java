package com.diplomski_rad.videoteka.repository;

import com.diplomski_rad.videoteka.model.Country;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CouchbaseRepository<Country, String> {
}
