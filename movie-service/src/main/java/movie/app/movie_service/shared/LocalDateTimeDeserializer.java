package movie.app.movie_service.shared;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateString = p.getText().trim();
        
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        
        // Try parsing as full date-time first (e.g., "2025-01-01T10:30:00")
        try {
            return LocalDateTime.parse(dateString, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            // If that fails, try parsing as date only and default to start of day
            try {
                LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
                return date.atStartOfDay();
            } catch (DateTimeParseException e2) {
                // Try ISO format as fallback
                try {
                    return LocalDateTime.parse(dateString);
                } catch (DateTimeParseException e3) {
                    throw new IOException("Cannot deserialize value of type `java.time.LocalDateTime` from String \"" + 
                        dateString + "\". Expected format: 'yyyy-MM-ddTHH:mm:ss' or 'yyyy-MM-dd'", e3);
                }
            }
        }
    }
}

