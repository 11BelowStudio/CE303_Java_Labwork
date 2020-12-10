package Assignment.shared;

/**
 * This sort of response is sent in response to a Disconnect request
 */
public class DisconnectResponse extends GenericResponse {

    /**
     * Creates the DisconnectResponse.
     */
    public DisconnectResponse() {
        super(true);
    }

    @Override
    public String toString() { return "DisconnectResponse{ \"success\": true, \"marketInfo\": who cares lol}"; }
}
