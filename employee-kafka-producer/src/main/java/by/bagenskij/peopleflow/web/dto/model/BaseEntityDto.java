package by.bagenskij.peopleflow.web.dto.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class BaseEntityDto {

  @ApiModelProperty(name = "ID", example = "1")
  private Long id;

  @ApiModelProperty(
      name = "Created",
      example = "2021-07-26T15:25:07.406191Z",
      value = "The date and time of the employee creation in the database")
  private Instant created;

  @ApiModelProperty(
      name = "Updated",
      example = "2021-07-27T15:25:07.406191Z",
      value = "The date and time of the last employee update in the database")
  private Instant updated;
}
