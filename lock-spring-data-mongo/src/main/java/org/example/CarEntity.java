package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Car")
@AllArgsConstructor
@Getter
public class CarEntity {

    @Id
    private String id;

    private int length;
}
