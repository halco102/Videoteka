package com.diplomski_rad.videoteka.repository.content;

import com.diplomski_rad.videoteka.model.Content;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface AbstractContentRepo<T extends Content> extends CouchbaseRepository<T, String> {

    @Query("#{#n1ql.selectEntity} where name = $1 and #{#n1ql.filter}")
    T getContentByName(String name);

    @Query("#{#n1ql.selectEntity} where name Like $1 and #{#n1ql.filter}")
    List<T> findByKeyword(String name);

    @Query("#{#n1ql.selectEntity} where $1 IN genres[*].name and #{#n1ql.filter}")
    List<T> getContentByGenre(String genre);

    @Query("#{#n1ql.selectEntity} where rating > 8 and #{#n1ql.filter}")
    List<T> getPopularContent();

}
