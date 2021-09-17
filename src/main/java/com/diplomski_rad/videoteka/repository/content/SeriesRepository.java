package com.diplomski_rad.videoteka.repository.content;

import com.diplomski_rad.videoteka.model.Series;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesRepository extends AbstractContentRepo<Series>{
}
/*public interface SeriesRepository extends CouchbaseRepository<Series, String> {
}*/
