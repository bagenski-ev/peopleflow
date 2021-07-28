package by.bagenskij.peopleflow.service;

public interface IService<ENTITY, ID> {
  ENTITY save(ENTITY entity);

  ENTITY findById(ID id);
}
