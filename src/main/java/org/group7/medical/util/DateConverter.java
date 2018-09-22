package org.group7.medical.util;

import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * A customized JPA converter converting between {@code java.sql.Date} and {@code LocalDate}. 
 * 
 * @author freddyfang
 * @date Jul 22, 2015
 */
@Converter(autoApply = true)
public class DateConverter implements AttributeConverter<LocalDate, java.sql.Date> {
	

    public java.sql.Date convertToDatabaseColumn(LocalDate entityValue) {
        return entityValue == null? null : java.sql.Date.valueOf(entityValue);
    }

    public LocalDate convertToEntityAttribute(java.sql.Date dbValue) {
        return dbValue == null? null : dbValue.toLocalDate();
    }
}
