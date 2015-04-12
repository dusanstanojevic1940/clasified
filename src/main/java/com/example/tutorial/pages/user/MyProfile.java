/**
 * 
 */
package com.example.tutorial.pages.user;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationGlobals;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.Session;

import com.example.tutorial.dao.SellingItemDAO;
import com.example.tutorial.dao.SettingsDAO;
import com.example.tutorial.dao.TransactionsDAO;
import com.example.tutorial.dao.UserDAO;
import com.example.tutorial.entities.SellingItem;
import com.example.tutorial.entities.Transaction;
import com.example.tutorial.pages.Index;
import com.example.tutorial.services.Authenticator;
import com.example.tutorial.util.Configuration;

import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutReq;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutRequestType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.ItemCategoryType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsItemType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;
import urn.ebay.apis.eBLBaseComponents.SetExpressCheckoutRequestDetailsType;

public class MyProfile {
	 @Inject
	 private Authenticator authenticator;
	 
	 @Inject
	 private Configuration configuration;
	 
	 @Property
	 private SellingItem sellingItem;
	 
	 @Inject
	 private SettingsDAO settingsDAO;
	 
	 @Inject
	 private SellingItemDAO sellingItemDAO;
	 
	 @Inject
	 private TransactionsDAO transactionsDAO;
	 
	 @Inject 
	 private Response response;
	 
	 @Inject
	 private UserDAO userDAO;
	
	 public String getCoinNumber() {
		 return userDAO.getByID(authenticator.getLoggedUser().id).coin+"";
	 }
	 
	 @Inject
	 private Request request;
	
	 @Inject
	 private ApplicationGlobals appGlob;
	 
	 public String getUsername() {
		 return authenticator.getLoggedUser().username;
	 }
	 
	 public String getPhoneNumber() {
		 return authenticator.getLoggedUser().phone;
	 }

	 public String getEmail() {
		 String email = authenticator.getLoggedUser().email;
		 int at = email.indexOf('@');
		 String toReturn = "";
		 for (int i = 0; i<email.length(); i++)
			 if (i<at)
				 toReturn+="*";
			 else
				 toReturn+=email.charAt(i);
		 return toReturn;
	 }

	 public List<SellingItem> getSellingItems() {
		 List<SellingItem> l = sellingItemDAO.all();
		 
		 if (l == null)
			 return new ArrayList<SellingItem>();
		 else 
			 return l;
	 }
	 
	 Object onSuccess() {
		 if (sellingItem == null)
			 return this;
		 
		 String itemAmount = sellingItem.itemPrice;
			
		 String itemQuantity = "1";
			
		 String itemName = sellingItem.itemName;
			
		 String brandName = settingsDAO.getPayPalAccount().brandName;
			
		 String currencyCode = sellingItem.currencyCode;
		 
		 try {	
			 Session session = request.getSession(true);
			 Map<String,String> configurationMap =  configuration.getAcctAndConfig();
			
			 PayPalAPIInterfaceServiceService service = new PayPalAPIInterfaceServiceService(configurationMap);
			
			 SetExpressCheckoutRequestType setExpressCheckoutReq = new SetExpressCheckoutRequestType();
			 SetExpressCheckoutRequestDetailsType details = new SetExpressCheckoutRequestDetailsType();
			
			 StringBuffer url = new StringBuffer();
			 url.append("http://");
			 url.append(request.getServerName());
			 url.append(":");
			 url.append(request.getServerPort());
			 url.append(request.getContextPath());
			
			 String returnURL = url.toString() + "/pp/responseok";
			 String cancelURL = url.toString() + "/pp/responsecancel";
			
			 details.setReturnURL(returnURL + "?currencyCodeType="
					+ currencyCode);
			
			 details.setCancelURL(cancelURL);
			
			 request.getSession(true).setAttribute("paymentType","Sale");
			
			 double itemTotal = 0.00;
			 double orderTotal = 0.00;
			
			 String amountItems = itemAmount;
			 String qtyItems = itemQuantity;
			 String names = itemName;
			
			 List<PaymentDetailsItemType> lineItems = new ArrayList<PaymentDetailsItemType>();
			
			 PaymentDetailsItemType item = new PaymentDetailsItemType();
			 BasicAmountType amt = new BasicAmountType();
			 amt.setCurrencyID(CurrencyCodeType.fromValue(currencyCode));
			 amt.setValue(amountItems);
			 item.setQuantity(new Integer(qtyItems));
			 item.setName(names);
			 item.setAmount(amt);
			 item.setItemCategory(ItemCategoryType.fromValue("Digital"));
			
			 lineItems.add(item);
			 
			 itemTotal += Double.parseDouble(qtyItems) * Double.parseDouble(amountItems);
			 orderTotal += itemTotal;
			
			 List<PaymentDetailsType> payDetails = new ArrayList<PaymentDetailsType>();
			 PaymentDetailsType paydtl = new PaymentDetailsType();
			 paydtl.setPaymentAction(PaymentActionCodeType.fromValue("Sale"));
			
			 BasicAmountType itemsTotal = new BasicAmountType();
			 itemsTotal.setValue(Double.toString(itemTotal));
			 itemsTotal.setCurrencyID(CurrencyCodeType.fromValue(currencyCode));
			 paydtl.setOrderTotal(new BasicAmountType(CurrencyCodeType.fromValue(currencyCode),
					Double.toString(orderTotal)));
			 paydtl.setPaymentDetailsItem(lineItems);
			
			 paydtl.setItemTotal(itemsTotal);
			
			 /*
			   	(Optional) Your URL for receiving Instant Payment Notification (IPN) 
			 	about this transaction. If you do not specify this value in the request, 
			 	the notification URL from your Merchant Profile is used, if one exists.
				Important:
				The notify URL applies only to DoExpressCheckoutPayment. 
				This value is ignored when set in SetExpressCheckout or GetExpressCheckoutDetails.
				Character length and limitations: 2,048 single-byte alphanumeric characters
			
				TODO: Mozda jednog dana implementovati ovo kao double check (bilo bi dobro do tad pratiti uplate na PayPal-u)
				
				paydtl.setNotifyURL(request.getParameter("notifyURL"));
			  */
			 
			 payDetails.add(paydtl);
			 details.setPaymentDetails(payDetails);
			
			
			 details.setReqConfirmShipping("0");
			
			 details.setAddressOverride("0");
			
			 details.setNoShipping("1");
			
			 details.setBrandName(brandName);
			
			
			 setExpressCheckoutReq.setSetExpressCheckoutRequestDetails(details);
			
			 SetExpressCheckoutReq expressCheckoutReq = new SetExpressCheckoutReq();
			 expressCheckoutReq
					.setSetExpressCheckoutRequest(setExpressCheckoutReq);
			
			 SetExpressCheckoutResponseType setExpressCheckoutResponse = service
					.setExpressCheckout(expressCheckoutReq);
			
			 if (setExpressCheckoutResponse != null) {
				 session.setAttribute("lastReq", service.getLastRequest());
				 session.setAttribute("lastResp", service.getLastResponse());
				 if (setExpressCheckoutResponse.getAck().toString().equalsIgnoreCase("SUCCESS")) {
					 Map<Object, Object> map = new LinkedHashMap<Object, Object>();
					 map.put("Ack", setExpressCheckoutResponse.getAck());
					/*
					 * A time stamped token by which you identify to PayPal that you are processing 
					 * this payment with Express Checkout. The token expires after three hours. 
					 * If you set the token in the SetExpressCheckout request, the value of the 
					 * token in the response is identical to the value in the request.
					 * Character length and limitations: 20 single-byte characters
					 */
					 
					 String token = setExpressCheckoutResponse.getToken();
					 saveTransaction(token);
					 
					 map.put("Token", token);
					 if (settingsDAO.getPayPalAccount().mode.equals("LIVE")) {
						 map.put("Redirect URL", "https://www.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=" + setExpressCheckoutResponse.getToken());
					 } else {
						 map.put("Redirect URL", "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=" + setExpressCheckoutResponse.getToken());
					 }
					 session.setAttribute("map", map);
					 
					 if (settingsDAO.getPayPalAccount().mode.equals("LIVE")) {
						 return new URL("https://www.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=" + setExpressCheckoutResponse.getToken());
					 } else {
						 return new URL("https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=" + setExpressCheckoutResponse.getToken());
					 }
				 } else {
					 session.setAttribute("Error", setExpressCheckoutResponse.getErrors());
					 return Index.class;
				 }
			 }
		 } catch(Exception e) {
			 throw new RuntimeException(e);
		 }
		 return Index.class;
	 }
	 
	 private void saveTransaction(String token) {
		 Transaction toSave = new Transaction();
		 toSave.IPN = false;
		 toSave.completed = false;
		 toSave.token = token;
		 toSave.amountPayed = sellingItem.itemPrice;
		 toSave.coins = sellingItem.coins;
		 toSave.user_id = authenticator.getLoggedUser().id;
		 toSave.created = new Date();
		 transactionsDAO.save(toSave);
	 }
}