package org.group7.medical.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * A customized JPA converter converting between {@code Timestamp} and {@code LocalDateTime}. 
 * 
 * @author freddyfang
 * @date Jul 22, 2015
 */
@Converter(autoApply = true)
public class DateTimeConverter implements AttributeConverter<LocalDateTime, java.sql.Timestamp> {

    public java.sql.Timestamp convertToDatabaseColumn(LocalDateTime entityValue) {
        return entityValue == null? null : java.sql.Timestamp.valueOf(entityValue);
    }

    public LocalDateTime convertToEntityAttribute(Timestamp dbValue) {
        return dbValue == null? null : dbValue.toLocalDateTime();
    }
}
