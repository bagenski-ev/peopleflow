package by.bagenskij.peopleflow.service.impl;

import by.bagenskij.peopleflow.dao.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class AbstractService<
    ENTITY extends BaseEntity, ID, REPOSITORY extends JpaRepository<ENTITY, ID>> {

  protected final REPOSITORY repository;

  protected AbstractService(REPOSITORY repository) {
    this.repository = repository;
  }

  @Transactional
  public ENTITY save(ENTITY entity) {
    return repository.save(entity);
  }

  public List<ENTITY> findAll() {

    return new ArrayList<>(repository.findAll());
  }

  public ENTITY findById(ID id) {
    return repository.findById(id).orElseThrow();
  }
}
