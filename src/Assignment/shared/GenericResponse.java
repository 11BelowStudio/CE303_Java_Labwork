package Assignment.shared;

public class GenericResponse implements ResponseInterface {

    private final boolean success;

    private final MarketInfo marketInfo;

    final int code;

    public GenericResponse(boolean wasSuccess){
        this(wasSuccess,new MarketInfo(), -1);
    }

    public GenericResponse(boolean wasSuccess, MarketInfo info){
        this(wasSuccess,info, -1);

    }

    public GenericResponse(boolean wasSuccess, MarketInfo info, int response){
        success = wasSuccess;
        marketInfo = info;
        code = response;
    }

    @Override
    public boolean getSuccess(){
        return success;
    }

    @Override
    public LoggableMarketInfo getMarketInfo(){
        return marketInfo;
    }

    public String toString(){
        return "GenericResponse{ \"success:\"" + success + ", \"marketInfo\": "+marketInfo +"}";
    }
}
