package Assignment.market_server;



import Assignment.shared.MarketInfo;
import Assignment.shared.GiveStockResponse;

import java.util.HashMap;
import java.util.Map;


public class Market {

    private final Map<String, TraderHandler> traders;

    private String stockholder;

    private final MarketInfo currentMarket;

    private final ServerLoggerInterface theLogger;


    /**
     * This initialises the 'Market' object.
     * the 'traders' map is initially empty, 'stockholder' is initially unknown,
     * and 'currentMarket' is set up appropriately
     */
    Market(ServerLoggerInterface logger){
        theLogger = logger;
        traders = new HashMap<>();
        stockholder = "unknown";
        currentMarket = new MarketInfo(traders.keySet(),stockholder);
        theLogger.logMarketInfo(currentMarket);
    }


    void addNewTrader(TraderHandler newTrader){
        synchronized(traders){
            synchronized (currentMarket) {
                traders.put(newTrader.getId(), newTrader);
                theLogger.logConnect(newTrader.getId());
                checkRemainingTraders();
                updateMarketInfo();
            }
        }
    }

    void removeTrader(TraderHandler traderToYeet){
        synchronized (traders){
            synchronized (currentMarket) {
                traders.remove(traderToYeet.getId());
                theLogger.logDisconnect(traderToYeet.getId());
                checkRemainingTraders();
                updateMarketInfo();
            }
        }
    }

    private void checkRemainingTraders(){
        if (!traders.containsKey(stockholder)){
            if (!traders.isEmpty()){
                stockholder = traders.keySet().stream().findFirst().get();
                theLogger.logServerGaveStock(stockholder); //log that the stockholder has been given the stock
            }
        }

    }

    private void updateMarketInfo(){
        currentMarket.updateMarketInfo(traders.keySet(),stockholder);
        theLogger.logMarketInfo(currentMarket);
    }


    public MarketInfo getCurrentMarketInfo(){
        synchronized (currentMarket) {
            return currentMarket.copyThis();
        }
    }

    GiveStockResponse giveStock(TraderHandler sender, String recipientID){
        GiveStockResponse response;
        String sendID = sender.getId();
        synchronized (traders) {
			synchronized (currentMarket){
				if (sendID.equalsIgnoreCase(stockholder)) {
					if (traders.containsKey(recipientID)){
						if (sendID.equalsIgnoreCase(recipientID)){
							response = new GiveStockResponse(true, currentMarket.copyThis(), GiveStockResponse.WOW_GREEDY);
							theLogger.logTrade(sendID,recipientID); //log that the stockholder gave it to themselves
						} else{
							//synchronized (currentMarket) {
								stockholder = recipientID;
								currentMarket.updateStockholder(stockholder);
								response = new GiveStockResponse(true, currentMarket.copyThis(), GiveStockResponse.GAVE_STOCK);
								theLogger.logTrade(sendID,recipientID); //log that the stockholder gave the stock to someone else
								theLogger.logMarketInfo(currentMarket);
							//}
						}
					} else{
						response = new GiveStockResponse(false, currentMarket.copyThis(), GiveStockResponse.RECIPIENT_DOESNT_EXIST);
					}
				} else {
					response = new GiveStockResponse(false, currentMarket.copyThis(), GiveStockResponse.NOT_STOCK_OWNER);
				}
			}
        }
        return response;
    }

}
