package bank.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import bank.Bank;

import java.util.List;

@Path("/bank")
@Produces(MediaType.APPLICATION_JSON)

public class BankServer {

	private final static Bank BANK = new Bank();

	public BankServer() {
	}

	@GET
	@Path("/customers")
	public List<String> getCustomers() {
		return BANK.getCustomers();
	}
	@GET
	@Path("/customer/{customerId}") 
	public int[] getAccounts(@PathParam("customerId") String customerId) {
		return BANK.getAccounts(customerId);
	}

	@GET
	@Path("/customer/{customerId}/{account}")
	public Double getBalance(@PathParam("customerId") String customerId, @PathParam("account") int account) {
		return BANK.getBalance(customerId, account);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/transfer/{customerId}")
	public String transfer(@PathParam("customerId") String customerId, TransferRequest req) {
		return BANK.transfer(customerId, req.fromAccount, req.toAccount, req.amount);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/transfer")
	public String transferForm(@FormParam("customerId") String customerId, 
			@FormParam("fromAccount") int fromAccount, 
			@FormParam("toAccount") int toAccount, 
			@FormParam("amount") double amount) {
		return BANK.transfer(customerId, fromAccount, toAccount, amount);
	}

}
