package Assignment.trader_client;

import Assignment.shared.ResponseInterface;

import javax.swing.*;
import java.util.concurrent.*;


/**
 * A graphical client for the trading of stocks and such
 */
public class GraphicalClient extends GenericClient {

    private ResponseInterface lastResponse;


    /**
     * long story short, this is the solution I found for allowing the GUI to control the
     * sending of trade requests and such whilst still allowing the automatic market status queries
     * to be passively sent.
     *
     * I called it hackyBarrier because it kinda does feel a bit hacky.
     *
     * but for all I know it might be entirely what I'm supposed to be doing or something idk
     */
    private static CyclicBarrier hackyBarrier = new CyclicBarrier(2);



    /**
     *
     * @param hostname the hostname of the server that this client is connecting to
     * @param port the port number used by the server
     * @throws Exception if something goes awry
     */
    public GraphicalClient(String hostname, int port) throws Exception {
        super(hostname, port);



        log = new ClientGUI(this);

        log.logYourId(id);

        log.logMarketInfo(currentMarket);

    }


    public void runTheClient(){
        try {
            try {
                connected = true;
                while (connected) {
                    if (youHaveTheStock) {
                        //if user has the stock, let them send a command manually.
                        try {
                            //Wait for 15 seconds for the stockholder to choose a person to give the stock to
                            hackyBarrier.await(15, TimeUnit.SECONDS);

                            //if they gave the stock, log that the stock has been given
                            log.logGiveResponse(lastResponse);
                            //and also update local copy of market info stuff
                            currentMarket = lastResponse.getMarketInfo();
                            youHaveTheStock = currentMarket.getStockholder().equals(id);
                        } catch (TimeoutException | BrokenBarrierException e){
                            //if more than 15 seconds pass without the stockholder giving the stock (or hackyBarrier breaks otherwise),
                            //remake the hackyBarrier.
                            hackyBarrier = new CyclicBarrier(2);
                            //and also request updated market info
                            requestRefresh();
                        }
                    } else{
                        //if you don't have the stock, automatically request a refresh for market info
                        requestRefresh();
                    }
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
        } finally {
            //once user disconnects
            try {
                //attempt to send the disconnect message
                Thread disconnectThread = new Thread( () -> theClient.disconnect());
                disconnectThread.start();
                //give the thread up to 5 seconds to finish before giving up
                disconnectThread.join(5000);
            } catch (Exception ignored) {}
            try {
                //close the client connection
                theClient.close();
            } catch (Exception ignored){}
        }
    }


    void requestRefresh(){
        lastResponse = theClient.getMarketInfo();
        currentMarket = lastResponse.getMarketInfo();
        youHaveTheStock = currentMarket.getStockholder().equals(id);
        log.logResponse(lastResponse);
    }


    /**
     * This is called when sending a GiveStock request
     * @param sendTo the trader to send the stock to
     */
    void sendStock(String sendTo){
        lastResponse = theClient.giveStock(sendTo);
        try {
            hackyBarrier.await();
        } catch (Exception e){
            e.printStackTrace();
            hackyBarrier = new CyclicBarrier(2);
        }

    }



    void close(){

        connected = false;
        hackyBarrier.reset();
    }



    /**
     * The main method for the GraphicalClient.
     * Firstly it'll ask the user for a hostname and a port number.
     * Then it'll create a GraphicalClient that can connect there.
     * @param args the command line arguments are unused.
     * @throws Exception because connections do be like that sometimes
     */
    public static void main(String[] args) throws Exception{
        String host = JOptionPane.showInputDialog(
                "Please enter a hostname to use",
                "localhost"
        );

        String pNum = JOptionPane.showInputDialog(
                "Please enter a port number to use",
                "8888"
        );

        int port = 8888;

        //make sure the portnumber is valid
        try{
            port = Integer.parseInt(pNum);
        } catch (NumberFormatException e) {
            // >:( angery
            JOptionPane.showMessageDialog(
                    null,
                    pNum + " isn't a port number so imma connect to port 8888 anyway",
                    "not stonks",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        new GraphicalClient(host, port).runTheClient();

		System.out.println("closed");
		System.exit(0);
    }
}

