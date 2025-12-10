package org.example.filmservice.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, String> {

    @Override
    public String convertToDatabaseColumn(Rating attribute) {
        return attribute == null ? null : attribute.toDatabase();
    }

    @Override
    public Rating convertToEntityAttribute(String dbData) {
        return dbData == null ? null : Rating.fromDatabase(dbData);
    }
}
