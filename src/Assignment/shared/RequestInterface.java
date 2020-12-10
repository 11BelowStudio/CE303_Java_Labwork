package Assignment.shared;

public interface RequestInterface {

    int CONNECTION_REQUEST = 0;
    int INFO_REQUEST = 1;
    int TRADE_REQUEST = 2;
    int DISCONNECT_REQUEST = 3;

    /**
     * Get the request number for this request
     * @return the request number for this request
     */
    int getRequestCode();

    /**
     * Get the request body string for this request
     * @return the request body string for this request
     */
    String getRequestBody();
}
