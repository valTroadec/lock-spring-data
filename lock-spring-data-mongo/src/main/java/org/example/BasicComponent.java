package org.example;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class BasicComponent {

    private final Map<String, CarEntity> carEntityMap = new HashMap<>();

    private final BasicMongoRepository basicMongoRepository;

    public BasicComponent(final BasicMongoRepository basicMongoRepository, final MongoTemplate mongoTemplate) {
        this.basicMongoRepository = basicMongoRepository;
        for(int i = 0; i < 1000; i++) {
            mongoTemplate.save(this.buildCar(i));
        }
        this.listAndCache();
    }
    public List<CarEntity> listAndCache() {
        Map<String, CarEntity> carEntityMap1 = IntStream.rangeClosed(0, 1000)
                .parallel()
                .mapToObj(i -> {
                    return this.basicMongoRepository.findById(String.valueOf(i)).orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(CarEntity::getId, Function.identity()));
        this.carEntityMap.putAll(carEntityMap1);
        return new ArrayList<>(this.carEntityMap.values());
    }

    private CarEntity buildCar(int i) {
        return new CarEntity(String.valueOf(i), i);
    }

}
