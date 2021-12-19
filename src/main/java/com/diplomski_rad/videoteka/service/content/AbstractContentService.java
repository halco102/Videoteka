package com.diplomski_rad.videoteka.service.content;

import com.diplomski_rad.videoteka.model.*;
import com.diplomski_rad.videoteka.repository.content.AbstractContentRepo;
import com.diplomski_rad.videoteka.service.GenreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractContentService<T extends Content> implements ContentService<T> {

    private final AbstractContentRepo<T> abstractContentRepo;

    @Autowired
    GenreService genreService;

    private final List<String> types = new ArrayList<>(Arrays.asList("movies", "series"));

    public AbstractContentService(AbstractContentRepo<T> abstractContentRepo) {
        this.abstractContentRepo = abstractContentRepo;
    }


    @Override
    public List<T> getAllContent() {
        return abstractContentRepo.findAll();
    }

    @Override
    public Optional<T> getContentById(String id) {
        return abstractContentRepo.findById(id);
    }

    @Override
    public T saveContent(T entitiy) {
        return abstractContentRepo.save(entitiy);
    }

    @Override
    public T updateById(String id, T entity) {
        var temp = abstractContentRepo.findById(id);
        if(temp.isPresent()){
            return abstractContentRepo.save(entity);
        }
        return null;
    }

    @Override
    public void deleteById(String id) {
        abstractContentRepo.deleteById(id);
    }

    @Override
    public T getByName(String name) {
        return abstractContentRepo.getContentByName(name);
    }

    @Override
    public List<T> findByKeyword(String keyword) {
        return abstractContentRepo.findByKeyword(keyword);
    }


    public List<T> searchEngine(String keyword) {

        if(keyword != null && !keyword.isBlank()) {
            keyword = "%" + keyword + "%";
            return abstractContentRepo.findByKeyword(keyword);
        }

        return abstractContentRepo.findAll();
    }



    public List<String> getType(String type){
        List<String> temp = new ArrayList<>();
        for (String s: types
             ) {
            if (!s.matches(type.toLowerCase())) {
                temp.add(s);
            }
        }
        return temp;
    }

    public List<T> getPopularContent(Class t) {
        if (t.getClass().isInstance(Movie.class)){
            return this.abstractContentRepo.getPopularContent();
        }else if (t.getClass().isInstance(Series.class)){
            return this.abstractContentRepo.getPopularContent();
        }else {
            log.info("Unexpected error");
            return null;
        }
    }



}
