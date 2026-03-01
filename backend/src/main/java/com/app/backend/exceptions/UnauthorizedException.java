public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException() {
        super("You are not authorized to perform this action");
    }
}