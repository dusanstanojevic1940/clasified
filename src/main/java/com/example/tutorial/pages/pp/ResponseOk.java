/**
 * 
 */
package com.example.tutorial.pages.pp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;

import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentReq;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentRequestType;
import urn.ebay.api.PayPalAPI.DoExpressCheckoutPaymentResponseType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsReq;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsRequestType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.DoExpressCheckoutPaymentRequestDetailsType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsItemType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;

import com.example.tutorial.dao.TransactionsDAO;
import com.example.tutorial.dao.UserDAO;
import com.example.tutorial.entities.Transaction;
import com.example.tutorial.entities.User;
import com.example.tutorial.services.Authenticator;
import com.example.tutorial.util.Configuration;

/*

import java.io.IOException;
import java.util.List;


import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsReq;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsRequestType;
import urn.ebay.api.PayPalAPI.GetExpressCheckoutDetailsResponseType;
import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.apis.eBLBaseComponents.ErrorType;
*/
/**
 * @author dusanstanojevic
 *
 */
public class ResponseOk {
	@Property
	private String token;
	
	@Property
	private String tVal;
	
	@Inject 
	private Response response;
	
	@Inject
	private Request request;
	
	@Property
	private String payerID;
	
	@Inject
	private Configuration configuration;
	
	@Inject
	private UserDAO userDAO;
	
	@Inject
	private Authenticator authenticator;
	
	@Inject
	private TransactionsDAO transactionsDAO;
	
	@Inject
	private ApplicationGlobals appGlob;
	
	void onActivate(@RequestParameter("token") String token, @RequestParameter("PayerID") String payerID) throws IOException {
		this.token = token;
		this.payerID = payerID;
		
		if (token == null || token.equals(""))
			response.sendRedirect(appGlob.getServletContext().getContextPath()+"/ResponseCancel");
		
		if (payerID == null || payerID.equals(""))
			response.sendRedirect(appGlob.getServletContext().getContextPath()+"/ResponseCancel");
		
		Transaction transaction = transactionsDAO.getByToken(token);
		
		if (transaction==null)
			response.sendRedirect(appGlob.getServletContext().getContextPath()+"/ResponseCancel");
		
		Map<String,String> configurationMap =  configuration.getAcctAndConfig();
		
		PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(configurationMap);
		
		try {
			GetExpressCheckoutDetailsReq req = new GetExpressCheckoutDetailsReq();
			GetExpressCheckoutDetailsRequestType reqType = new GetExpressCheckoutDetailsRequestType(token);
			req.setGetExpressCheckoutDetailsRequest(reqType);
			
			
			GetExpressCheckoutDetailsResponseType resp = service.getExpressCheckoutDetails(req);
			if (resp != null) {
				tVal = resp.getGetExpressCheckoutDetailsResponseDetails().getPaymentDetails().get(0).getItemTotal().getValue();		
				
				DoExpressCheckoutPaymentRequestType doCheckoutPaymentRequestType = new DoExpressCheckoutPaymentRequestType();
				DoExpressCheckoutPaymentRequestDetailsType details = new DoExpressCheckoutPaymentRequestDetailsType();

				details.setToken(token);
				
				details.setPayerID(payerID);

				details.setPaymentAction(PaymentActionCodeType.fromValue("Sale"));
				
				PaymentDetailsType paymentDetails = new PaymentDetailsType();
				BasicAmountType orderTotal = new BasicAmountType();
				orderTotal.setValue(tVal);
				
				orderTotal.setCurrencyID(resp.getGetExpressCheckoutDetailsResponseDetails().getPaymentDetails().get(0).getItemTotal().getCurrencyID());

				paymentDetails.setOrderTotal(orderTotal);

				BasicAmountType itemTotal = new BasicAmountType();
				itemTotal.setValue(tVal);
				itemTotal.setCurrencyID(resp.getGetExpressCheckoutDetailsResponseDetails().getPaymentDetails().get(0).getItemTotal().getCurrencyID());
				
				paymentDetails.setItemTotal(itemTotal);

				List<PaymentDetailsItemType> paymentItems = new ArrayList<PaymentDetailsItemType>();
				PaymentDetailsItemType paymentItem = new PaymentDetailsItemType();
				
				paymentItem.setName(resp.getGetExpressCheckoutDetailsResponseDetails().getPaymentDetails().get(0).getPaymentDetailsItem().get(0).getName());
				
				paymentItem.setQuantity(1);
				
				BasicAmountType amount = new BasicAmountType();
				amount.setValue(tVal);
				
				amount.setCurrencyID(resp.getGetExpressCheckoutDetailsResponseDetails().getPaymentDetails().get(0).getItemTotal().getCurrencyID());
				paymentItem.setAmount(amount);
				paymentItems.add(paymentItem);
				paymentDetails.setPaymentDetailsItem(paymentItems);
				
				List<PaymentDetailsType> payDetailType = new ArrayList<PaymentDetailsType>();
				payDetailType.add(paymentDetails);
				
				details.setPaymentDetails(payDetailType);

				doCheckoutPaymentRequestType.setDoExpressCheckoutPaymentRequestDetails(details);
				
				DoExpressCheckoutPaymentReq doExpressCheckoutPaymentReq = new DoExpressCheckoutPaymentReq();
				doExpressCheckoutPaymentReq
						.setDoExpressCheckoutPaymentRequest(doCheckoutPaymentRequestType);

				DoExpressCheckoutPaymentResponseType doCheckoutPaymentResponseType = service
						.doExpressCheckoutPayment(doExpressCheckoutPaymentReq);
				
				if (doCheckoutPaymentResponseType != null) {
					if (doCheckoutPaymentResponseType.getAck().toString().equalsIgnoreCase("SUCCESS")) {
						transaction.completed = true;
						transactionsDAO.save(transaction);
						User toSave = userDAO.getByID(authenticator.getLoggedUser().id);
						toSave.coin += transaction.coins;
						userDAO.update(toSave);
					} else {
						throw new RuntimeException("So Cloz");
					}
				}
			}
						
		} catch (Exception e) {
			response.sendRedirect(appGlob.getServletContext().getContextPath()+"/ResponseCancel");
		}
	}
}