package lab5;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/calculator")
public class CalculatorService {

    /**
     * Adds x and y (via /calculator/add/{x}/{y}),
     * and returns the result
     * @param x the first number
     * @param y the number being added to x
     * @return the sum of x and y
     */
    @GET
    @Path("/add/{x}/{y}")
    @Produces(MediaType.APPLICATION_JSON)
    public Integer addPath(@PathParam("x") int x, @PathParam("y") int y){
        return (x + y);
    }


    /**
     * Adds x and y (via /calculator/add?x={int}&y={int}),
     * and returns the result
     * @param x the first number
     * @param y the number being added to x
     * @return the sum of x and y
     */
    @GET
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Integer addGetQuery(@QueryParam("x") int x, @QueryParam("y") int y){ return (x+y); }
}
