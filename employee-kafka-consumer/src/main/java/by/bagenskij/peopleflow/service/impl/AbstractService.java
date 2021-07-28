package by.bagenskij.peopleflow.service.impl;

import by.bagenskij.peopleflow.dao.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public class AbstractService<
    ENTITY extends BaseEntity, ID, REPOSITORY extends JpaRepository<ENTITY, ID>> {

  protected final REPOSITORY repository;

  protected AbstractService(REPOSITORY repository) {
    this.repository = repository;
  }

  public ENTITY findById(ID id) {
    return repository.findById(id).orElseThrow();
  }

  @Transactional
  public ENTITY save(ENTITY entity) {
    return repository.save(entity);
  }
}
