package Assignment.trader_client;

import Assignment.shared.LoggableMarketInfo;
import Assignment.shared.ResponseInterface;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Set;

public class ClientGUI implements ClientLoggerInterface {

    private final GraphicalClient theClient;

    private final JFrame theFrame;

    private final JLabel userIDLabel;

    String userID;

    boolean youHaveTheStock = false;

    private final JLabel haveStockLabel;

    JPanel traderListPanel;


    private final JLabel stockholderLabel;


    //private SwingWorker<JPanel, Object> marketInfoUpdater;

    LoggableMarketInfo marketInfo;


    public ClientGUI(GraphicalClient c){
        theClient = c;

        theFrame = new JFrame("STONKS");

        theFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        theFrame.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        quitPrompt();
                    }
                }
        );

        theFrame.setLayout(new BorderLayout());

        JPanel userInfoPanel = new JPanel(new GridLayout(3,1));
        userInfoPanel.add(new JLabel("Your ID:"));
        userInfoPanel.add (userIDLabel = new JLabel());
        userInfoPanel.add(haveStockLabel = new JLabel());

        theFrame.add(userInfoPanel, BorderLayout.NORTH);

        JPanel marketInfoPanel = new JPanel();
        marketInfoPanel.setLayout(new GridLayout(1,1));
        marketInfoPanel.setBorder(
                new TitledBorder(
                        BorderFactory.createEtchedBorder(),
                        "Traders"
                )
        );

        traderListPanel = new JPanel();
        traderListPanel.setLayout(new BoxLayout(traderListPanel, BoxLayout.Y_AXIS));
        traderListPanel.add(new JLabel("No traders to display!"));


        JScrollPane traderListScroll = new JScrollPane(
                traderListPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        traderListScroll.setMinimumSize(new Dimension(64,512));

        marketInfoPanel.add(traderListScroll);

        theFrame.add(marketInfoPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1,2));

        JPanel stockInfoPanel = new JPanel(new GridLayout(2,1));
        stockInfoPanel.add(new JLabel("Trader who has the stock:"));
        stockInfoPanel.add (stockholderLabel = new JLabel());

        bottomPanel.add(stockInfoPanel);

        JPanel exitPanel = new JPanel();

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> quitPrompt());

        exitPanel.add(quitButton);

        bottomPanel.add(exitPanel);

        theFrame.add(bottomPanel,BorderLayout.SOUTH);


        /*
        marketInfoUpdater = new MarketInfoDisplayer(this);
        marketInfoUpdater.addPropertyChangeListener(
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if (evt.getNewValue().equals(SwingWorker.StateValue.DONE){

                        }
                    }
                }
        );
         */


        refresh();
        theFrame.setVisible(true);

    }

    /**
     * Ask if the user is sure that they want to quit
     */
    public void quitPrompt(){
        if (JOptionPane.showConfirmDialog(
                theFrame,
                "<html><p>"+
                        "Are you sure you want to disconnect?<br>"+
                        "You will not be able to log back in!</p>",
                "Are you sure?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        ) == JOptionPane.YES_OPTION){
            theFrame.dispose();
            theClient.close();
        }
    }

    @Override
    public void logYourId(String userID) {
        this.userID = userID;
        userIDLabel.setText(userID);
    }

    @Override
    public void logResponse(ResponseInterface theResponse) {
        logMarketInfo(theResponse.getMarketInfo());
    }

    @Override
    public void logGiveResponse(ResponseInterface theResponse) {
        logMarketInfo(theResponse.getMarketInfo());
        JOptionPane.showMessageDialog(
                theFrame,
                theResponse.getInfo(),
                "trade-related info",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void logWhetherOrNotYouOwnTheStock(boolean isMine) {
        youHaveTheStock = isMine;
        if (youHaveTheStock){
            haveStockLabel.setText("You have the stock. Please select a trader to give it to.");
        } else{
            haveStockLabel.setText("You do not have the stock.");
        }
    }

    @Override
    public void logMarketInfo(LoggableMarketInfo info) {
        marketInfo = info;
        logWhetherOrNotYouOwnTheStock(marketInfo.getStockholder().equalsIgnoreCase(userID));
        stockholderLabel.setText(marketInfo.getStockholder());
        new MarketInfoDisplayer(this).execute();
    }

    void sendStockTo(String sendTo){
        theClient.sendStock(sendTo);
    }


    void replaceTraderInfo(ArrayList<JPanel> newTraderInfo) {
        traderListPanel.removeAll();

        for(JPanel p: newTraderInfo){
            traderListPanel.add(p);
        }

        refresh();
    }


    private void refresh(){
        theFrame.pack();
        theFrame.revalidate();
    }

}


/**
 * This basically refreshes the panel showing all the Traders in the background,
 * using the SwingWorker stuff Java has.
 *
 * This is done in the background because, if there's a lot of traders that need to be updated,
 * that could take a really long time, and having the clientGUI freeze up when waiting for that
 * could get pretty darn annoying.
 */
class MarketInfoDisplayer extends SwingWorker<ArrayList<JPanel>, Object> {

    private final ClientGUI client;


    MarketInfoDisplayer(ClientGUI c){
        client = c;
    }


    @Override
    protected ArrayList<JPanel> doInBackground() throws Exception {
        //System.out.println("Worker started");

        ArrayList<JPanel> newPanels = new ArrayList<>();
        //JPanel newTraderListPanel = new JPanel();
        //newTraderListPanel.setLayout(new BoxLayout(newTraderListPanel, BoxLayout.Y_AXIS));

        Set<String> traders = client.marketInfo.getTraders();
        if (traders.isEmpty()) {
            JPanel placeholderPanel = new JPanel();
            placeholderPanel.add(new JLabel("apparently nobody's in the market. Not even you!"));
            placeholderPanel.setBorder(BorderFactory.createEtchedBorder());
        } else {
            boolean canInteract = client.youHaveTheStock;
            String stockHolder = client.marketInfo.getStockholder();
            String you = client.userID;
            boolean lookingForYou = true;
            boolean lookingForStockholder = true;
            Border etched = BorderFactory.createEtchedBorder();
            for (String s: traders) {
                JPanel traderPanel = new JPanel(new GridLayout(1,1));
                traderPanel.setBorder(etched);
                traderPanel.setBackground(Color.CYAN);
                String panelText = s;
                if (lookingForYou && s.equalsIgnoreCase(you)){
                    panelText = panelText.concat(" (you)");
                    lookingForYou = false;
                    traderPanel.setBackground(Color.GREEN);
                }
                if (lookingForStockholder && s.equalsIgnoreCase(stockHolder)){
                    panelText = panelText.concat(" (stockholder)");
                    lookingForStockholder = false;
                    traderPanel.setBackground(Color.YELLOW);
                }
                traderPanel.add(new JLabel(panelText));
                if (canInteract){
                    traderPanel.addMouseListener(
                            new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    client.sendStockTo(s);
                                }
                            }
                    );
                }
                newPanels.add(traderPanel);
            }
        }
        //System.out.println("Worker stopped");
        return newPanels;
    }


    @Override
    protected void done() {
        //System.out.println("doneStart");
        try {
            client.replaceTraderInfo(get());
            //System.out.println("done");
        } catch (Exception ignored){}
    }
}
