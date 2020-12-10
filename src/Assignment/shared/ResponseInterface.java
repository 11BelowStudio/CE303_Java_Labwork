package Assignment.shared;

/**
 * A somewhat more lightweight interface for a response
 */
public interface ResponseInterface {

    /**
     * Returns whether or not this response was a success
     * @return true if this response is a success, false otherwise
     */
    boolean getSuccess();

    /**
     * Returns the LoggableMarketInfo that was sent with this response
     * @return the LoggableMarketInfo that this response had (can be null)
     */
    LoggableMarketInfo getMarketInfo();

    /**
     * Returns the info message that this response had
     * @return no message if there was no message
     */
    default String getInfo(){
        return "no message";
    }
}
