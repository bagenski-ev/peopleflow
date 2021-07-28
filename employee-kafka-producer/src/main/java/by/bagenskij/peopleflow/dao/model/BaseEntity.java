package by.bagenskij.peopleflow.dao.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public abstract class BaseEntity {
  @Id
  @GeneratedValue()
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;

  @Column(
      name = "created",
      columnDefinition = "timestamp with time zone",
      nullable = false,
      updatable = false)
  @CreationTimestamp
  private Instant created;

  @Column(
      name = "updated",
      columnDefinition = "timestamp with time zone",
      nullable = false,
      updatable = false)
  @UpdateTimestamp
  private Instant updated;
}
