package com.talentquery.util;

import com.pgvector.PGvector;
import org.postgresql.util.PGobject;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.sql.SQLException;

@Converter
public class VectorConverter implements AttributeConverter<float[], Object> {

    @Override
    public Object convertToDatabaseColumn(float[] attribute) {
        if (attribute == null) return null;
        try {
            PGobject obj = new PGobject();
            obj.setType("vector");
            obj.setValue(new PGvector(attribute).toString());
            return obj;
        } catch (SQLException e) {
            throw new RuntimeException("Error converting vector for database", e);
        }
    }

    @Override
    public float[] convertToEntityAttribute(Object dbData) {
        if (dbData == null) return null;
        try {
            if (dbData instanceof String s) {
                return new PGvector(s).toArray();
            }
            return new PGvector(dbData.toString()).toArray();
        } catch (SQLException e) {
            throw new RuntimeException("Error converting vector from database", e);
        }
    }
}
