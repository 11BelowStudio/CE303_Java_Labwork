package Assignment.shared;

public class ConnectResponse extends GenericResponse {

    private final String id;

    public ConnectResponse(boolean wasSuccess, MarketInfo info, String newId) {
        super(wasSuccess, info);
        id = newId;
    }

    @Override
    public String getInfo() {
        return id;
    }


    public String toString(){
        return "ConnectResponse{ \"success\":" + this.getSuccess() + ", \"marketInfo\":" + this.getMarketInfo() + ", \"id\": = \"" + id +"\"}";
    }
}
