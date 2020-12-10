package Assignment.shared;

public class GiveStockResponse extends GenericResponse {


    public final static int NOT_STOCK_OWNER = 0;
    public final static int RECIPIENT_DOESNT_EXIST = 1;
    public final static int GAVE_STOCK = 2;
    public final static int WOW_GREEDY = 3;

    public GiveStockResponse(boolean success, MarketInfo marketInfo, int responseMessage){
        super(success, marketInfo, responseMessage);
    }


    @Override
    public String getInfo(){
        switch (code){
            case NOT_STOCK_OWNER:
                return "you can't trade stocks that you don't own.";
            case RECIPIENT_DOESNT_EXIST:
                return "you can't give stocks to traders that don't exist.";
            case GAVE_STOCK:
                return "successfully gave stock!";
            case WOW_GREEDY:
                return "congrats you gave your stock to yourself. kinda greedy ngl.";
            default:
                return "okay something went wrong here";
        }

    }
}
