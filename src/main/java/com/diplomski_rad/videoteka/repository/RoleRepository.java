package com.diplomski_rad.videoteka.repository;

import com.diplomski_rad.videoteka.model.Role;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CouchbaseRepository<Role, String> {
}
