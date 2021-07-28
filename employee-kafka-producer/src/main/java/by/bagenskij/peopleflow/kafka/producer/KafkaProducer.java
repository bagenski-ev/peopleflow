package by.bagenskij.peopleflow.kafka.producer;

import by.bagenskij.peopleflow.web.dto.model.employee.SavingEmployeeDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class KafkaProducer {

  private final KafkaTemplate<String, Long> longKafkaTemplate;
  private final KafkaTemplate<String, SavingEmployeeDto> savingEmployeeKafkaTemplate;

  public KafkaProducer(
      KafkaTemplate<String, Long> longKafkaTemplate,
      KafkaTemplate<String, SavingEmployeeDto> savingEmployeeKafkaTemplate) {
    this.longKafkaTemplate = longKafkaTemplate;
    this.savingEmployeeKafkaTemplate = savingEmployeeKafkaTemplate;
  }

  public ListenableFuture<SendResult<String, Long>> send(String topic, Long payload) {
    return longKafkaTemplate.send(topic, payload);
  }

  public ListenableFuture<SendResult<String, SavingEmployeeDto>> send(
      String topic, SavingEmployeeDto payload) {
    return savingEmployeeKafkaTemplate.send(topic, payload);
  }
}
