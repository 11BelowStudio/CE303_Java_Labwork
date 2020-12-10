package bank.socket;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestRequest {

	// Round-trip testing for request parsing and printing

	String[] requestStrings = new String[] { "ACCOUNTS", "BALANCE 101", "TRANSFER 101 104 100", "LOGOUT" };

	public TestRequest() {
	};

	@Test
	public void stringToRequestToString() {
		for (String requestString : requestStrings) {
			assertEquals(requestString, Request.parse(requestString).toString());
		}
	}

	@Test
	public void requestToStringToRequest() {
		for (String requestString : requestStrings) {
			Request request = Request.parse(requestString);
			assertEquals(request, Request.parse(request.toString()));
		}
	}
}
