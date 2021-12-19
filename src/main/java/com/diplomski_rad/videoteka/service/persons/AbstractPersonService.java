package com.diplomski_rad.videoteka.service.persons;

import com.diplomski_rad.videoteka.model.Person;
import com.diplomski_rad.videoteka.repository.person.AbstractPersonRepository;

import java.util.List;
import java.util.Optional;

public abstract class AbstractPersonService<T extends Person> implements PersonService<T>{

    private final AbstractPersonRepository<T> abstractPersonRepository;

    public AbstractPersonService(AbstractPersonRepository<T> abstractPersonRepository) {
        this.abstractPersonRepository = abstractPersonRepository;
    }

    @Override
    public Optional<T> getById(String id) {
        return abstractPersonRepository.findById(id);
    }

    @Override
    public List<T> getAllPersons() {
        return abstractPersonRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        abstractPersonRepository.deleteById(id);
    }


}
