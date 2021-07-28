package by.bagenskij.peopleflow.service;

import java.util.List;

public interface IService<ENTITY, ID> {
  ENTITY save(ENTITY entity);

  List<ENTITY> findAll();

  ENTITY findById(ID id);
}
