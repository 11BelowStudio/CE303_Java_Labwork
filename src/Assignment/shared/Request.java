package Assignment.shared;

public class Request implements RequestInterface {

    private final int code;

    private final String body;


    public Request(int request){
        this(request, "");
    }
    public Request(int request, String reqBody){
        code = request;
        body = reqBody;
    }

    public int getRequestCode(){
        return code;
    }

    public String getRequestBody(){
        return body;
    }

    public String toString(){

        String type = "unknown";
        switch (code){
            case CONNECTION_REQUEST:
                type = "connect";
                break;
            case INFO_REQUEST:
                type = "info";
                break;
            case TRADE_REQUEST:
                type = "trade";
                break;
            case DISCONNECT_REQUEST:
                type = "disconnect";
                break;
            default:
                break;
        }
        return "Request { \"type\": \""+type+"\" ,\"body\": \""+body+"\" }";
    }
}
