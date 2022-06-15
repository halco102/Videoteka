package com.diplomski_rad.videoteka.repository;

import com.diplomski_rad.videoteka.model.Genre;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends CouchbaseRepository<Genre, String> {

    @Query("#{#n1ql.selectEntity} where name = $1 and #{#n1ql.filter}")
    Optional<Genre> findGenreByName(String name);

}
