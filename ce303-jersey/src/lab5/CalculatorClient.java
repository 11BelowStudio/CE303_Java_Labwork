package lab5;

import webserver.TomcatServer;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class CalculatorClient {

    public WebTarget webTarget;

    public CalculatorClient(){
        webTarget = ClientBuilder.newClient().target(TomcatServer.CALC_URL);
    }


    public Integer addPathParam(int x, int y){
        return webTarget.path("add").path(String.valueOf(x)).path(String.valueOf(y)).request().get(Integer.class);
    }

    public Integer addGetQuery(int x, int y){
        return webTarget.path("add").queryParam("x",x).queryParam("y",y).request().get(Integer.class);
    }


    public static void main(String[] args){
        CalculatorClient calc = new CalculatorClient();
        System.out.println("2 plus 2 is " + calc.addPathParam(2,2));
        System.out.println("minus one that's " + calc.addGetQuery(4,-1));
        System.out.println("quick maffs");
    }

}
