package com.diplomski_rad.videoteka.service.content;

import com.diplomski_rad.videoteka.model.Cartoon;
import com.diplomski_rad.videoteka.repository.content.AbstractContentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartoonService extends AbstractContentService<Cartoon>{

    public CartoonService(AbstractContentRepo<Cartoon> abstractContentRepo) {
        super(abstractContentRepo);
    }

    @Override
    public List<Cartoon> getAllContent() {
        return super.getAllContent();
    }

    @Override
    public Optional<Cartoon> getContentById(String id) {
        return super.getContentById(id);
    }

    @Override
    public Cartoon saveContent(Cartoon entitiy) {
        return super.saveContent(entitiy);
    }

    @Override
    public Cartoon updateById(String id, Cartoon entity) {
        return super.updateById(id, entity);
    }

    @Override
    public void deleteById(String id) {
        super.deleteById(id);
    }

    @Override
    public Cartoon getByName(String name) {
        return super.getByName(name);
    }

    @Override
    public List<Cartoon> findByKeyword(String keyword) {
        return super.findByKeyword(keyword);
    }
}
