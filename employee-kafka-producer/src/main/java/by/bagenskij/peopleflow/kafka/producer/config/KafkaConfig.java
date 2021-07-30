package by.bagenskij.peopleflow.kafka.producer.config;

import by.bagenskij.peopleflow.web.dto.model.employee.SavingEmployeeDto;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
@EnableKafka
public class KafkaConfig {
  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Bean
  public Map<String, Object> producerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

    return props;
  }

  @Bean
  public ProducerFactory<String, Long> longProducerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfigs());
  }

  @Bean
  public ProducerFactory<String, SavingEmployeeDto> savingEmployeeProducerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfigs());
  }

  @Bean
  public KafkaTemplate<String, Long> longKafkaTemplate() {
    return new KafkaTemplate<>(longProducerFactory());
  }

  @Bean
  public KafkaTemplate<String, SavingEmployeeDto> savingEmployeeKafkaTemplate() {
    return new KafkaTemplate<>(savingEmployeeProducerFactory());
  }
}
