package movie.app.movie_service.exception;

public class DuplicateAuditoriumException extends RuntimeException {
    public DuplicateAuditoriumException(String message) {
        super(message);
    }
}

