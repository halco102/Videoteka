package com.diplomski_rad.videoteka.service.persons;

import com.diplomski_rad.videoteka.model.Stars;
import com.diplomski_rad.videoteka.repository.person.AbstractPersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StarsService extends AbstractPersonService<Stars> {
    public StarsService(AbstractPersonRepository<Stars> abstractPersonRepository) {
        super(abstractPersonRepository);
    }

    @Override
    public Optional<Stars> getById(String id) {
        return super.getById(id);
    }

    @Override
    public List<Stars> getAllPersons() {
        return super.getAllPersons();
    }

    @Override
    public void deleteById(String id) {
        super.deleteById(id);
    }
}
