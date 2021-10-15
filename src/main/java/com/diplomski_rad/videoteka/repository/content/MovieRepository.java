package com.diplomski_rad.videoteka.repository.content;

import com.diplomski_rad.videoteka.model.Movie;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends AbstractContentRepo<Movie> {
}


