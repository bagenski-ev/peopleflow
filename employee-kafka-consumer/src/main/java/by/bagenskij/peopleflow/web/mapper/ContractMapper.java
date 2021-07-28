package by.bagenskij.peopleflow.web.mapper;

import by.bagenskij.peopleflow.dao.model.Contract;
import by.bagenskij.peopleflow.web.dto.model.contract.ContractDto;
import org.mapstruct.Mapper;

@Mapper
public interface ContractMapper {
  Contract contractInformationDtoToContractInformation(ContractDto contractDto);
}
