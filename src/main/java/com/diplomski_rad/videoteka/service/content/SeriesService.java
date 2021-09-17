package com.diplomski_rad.videoteka.service.content;

import com.diplomski_rad.videoteka.model.Series;
import com.diplomski_rad.videoteka.repository.content.AbstractContentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeriesService extends AbstractContentService<Series> {
    public SeriesService(AbstractContentRepo<Series> abstractContentRepo) {
        super(abstractContentRepo);
    }

    @Override
    public List<Series> getAllContent() {
        return super.getAllContent();
    }

    @Override
    public Optional<Series> getContentById(String id) {
        return super.getContentById(id);
    }

    @Override
    public Series saveContent(Series entitiy) {
        return super.saveContent(entitiy);
    }

    @Override
    public Series updateById(String id, Series entity) {
        return super.updateById(id, entity);
    }

    @Override
    public void deleteById(String id) {
        super.deleteById(id);
    }

    @Override
    public Series getByName(String name) {
        return super.getByName(name);
    }

    @Override
    public List<Series> findByKeyword(String keyword) {
        return super.findByKeyword(keyword);
    }

    @Override
    public List<Series> searchEngine(String searchGenre, String keyword) {
        return super.searchEngine(searchGenre, keyword);
    }
}
