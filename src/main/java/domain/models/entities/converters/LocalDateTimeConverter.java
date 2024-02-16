package domain.models.entities.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

        @Override
        public Timestamp convertToDatabaseColumn(LocalDateTime locDateTime) {
            return locDateTime == null ? null : Timestamp.valueOf(locDateTime);
        }

        @Override
        public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
            return sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime();
        }
}

