package com.diplomski_rad.videoteka.service.content;

import com.diplomski_rad.videoteka.model.Content;
import com.diplomski_rad.videoteka.repository.content.AbstractContentRepo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractContentService<T extends Content> implements ContentService<T> {

    private final AbstractContentRepo<T> abstractContentRepo;

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


    public List<T> searchEngine(String searchGenre, String keyword) {

        if(keyword != null && searchGenre == null) {
            keyword = keyword+"%";

            return abstractContentRepo.findByKeyword(keyword);
        }else if (keyword == null && searchGenre != null){
            var temp = abstractContentRepo.findAll()
                    .stream()
                    .filter(movie -> movie.getGenres().stream().anyMatch(g -> g.getName().matches(searchGenre)))
                    .collect(Collectors.toList());

            return temp;
        }

        return abstractContentRepo.findAll();
    }

}
