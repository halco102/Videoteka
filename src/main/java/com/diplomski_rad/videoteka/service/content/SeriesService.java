package com.diplomski_rad.videoteka.service.content;

import com.diplomski_rad.videoteka.model.Genre;
import com.diplomski_rad.videoteka.model.Series;
import com.diplomski_rad.videoteka.repository.content.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeriesService extends AbstractContentService<Series> {
    @Autowired
    SeriesRepository seriesRepository;

    public SeriesService(SeriesRepository seriesRepository) {
        super(seriesRepository);
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

    public String submitAdminForm(Series series, List<Genre> genres) {

        if (series.getId() != null) {
            var oldSeries = seriesRepository.findById(series.getId()).get();

            if (genres != null) {
                series.getGenres().clear();
                series.getGenres().addAll(genres);
            }else {
                series.getGenres().addAll(oldSeries.getGenres());
            }

            seriesRepository.save(series);
            return "redirect:/api/v1/videoteka/series";
        }
        seriesRepository.save(series);
        return "redirect:/api/v1/videoteka/admin/series";
    }

}
