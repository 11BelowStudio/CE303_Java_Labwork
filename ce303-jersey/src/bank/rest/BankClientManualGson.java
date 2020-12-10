package bank.rest;

import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/* Banking client with "manual" invocation of Gson */

public class BankClientManualGson {

	public final WebTarget target;

	String customer;

	public BankClientManualGson(String bankUrl, String customer) {
		ClientConfig config = new ClientConfig();
		target = ClientBuilder.newClient(config).target(bankUrl);
		this.customer = customer;
	}

	public List<String> getCustomers() {
		String messageBody = target.path("customers").request().get(new GenericType<String>() {
		});
		Type t = new TypeToken<List<String>>() {
		}.getType();
		return new Gson().fromJson(messageBody, t);
	}

	public List<Integer> getAccounts() {
		String messageBody = target.path("customer").path(customer).request().get(new GenericType<String>() {
		});
		Type t = new TypeToken<List<Integer>>() {
		}.getType();
		return new Gson().fromJson(messageBody, t);

	}

	public Double getBalance(int accountNumber) {
		String messageBody = target.path("customer").path(customer).path(Integer.toString(accountNumber)).request()
				.get(new GenericType<String>() {
				});
		return Double.parseDouble(messageBody);
	}

	public String transfer(int fromAccount, int toAccount, double amount) {
		TransferRequest req = new TransferRequest(fromAccount, toAccount, amount);
		String reqBody = new Gson().toJson(req);
		return target.path("transfer").path(customer).request().post(Entity.entity(reqBody, MediaType.APPLICATION_JSON))
				.readEntity(String.class);
	}

}