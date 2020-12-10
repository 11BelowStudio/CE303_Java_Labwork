package bank.rest;

import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;

import webserver.GsonMessageBodyHandler;

public class BankClient {

	String customer;

	public final WebTarget target;

	public BankClient(String bankUrl, String customer) {
		ClientConfig config = new ClientConfig(GsonMessageBodyHandler.class);
		target = ClientBuilder.newClient(config).target(bankUrl);
		this.customer = customer;
	}

	public List<String> getCustomers() {
		return target.path("customers").request().get(new GenericType<List<String>>() {
		});
	}

	public List<Integer> getAccounts() {
		return target.path("customer").path(customer).request().get(new GenericType<List<Integer>>() {
		});
	}

	public Double getBalance(int accountNumber) {
		return target.path("customer").path(customer).path(Integer.toString(accountNumber)).request().get(Double.class);
	}

	public String transfer(int fromAccount, int toAccount, double amount) {
		TransferRequest req = new TransferRequest(fromAccount, toAccount, amount);
		return target.path("transfer").path(customer).request().post(Entity.entity(req, MediaType.APPLICATION_JSON))
				.readEntity(String.class);
	}

	public String transferForm(int fromAccount, int toAccount, double amount) {
		Form form = new Form();
		form.param("customerId", customer);
		form.param("fromAccount", Integer.toString(fromAccount));
		form.param("toAccount", Integer.toString(toAccount));
		form.param("amount", Double.toString(amount));
		return target.path("transfer").request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED))
				.readEntity(String.class);
	}

}