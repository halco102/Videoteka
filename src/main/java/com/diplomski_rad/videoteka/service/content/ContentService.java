package com.diplomski_rad.videoteka.service.content;

import com.diplomski_rad.videoteka.model.Content;

import java.util.List;
import java.util.Optional;

public interface ContentService<T extends Content> {

    List<T> getAllContent();
    Optional<T> getContentById(String id);
    T saveContent(T entitiy);
    T updateById(String id, T entity);
    void deleteById(String id);
    T getByName(String name);
    List<T> findByKeyword(String keyword);
    List<T> searchEngine(String keyword);

}
