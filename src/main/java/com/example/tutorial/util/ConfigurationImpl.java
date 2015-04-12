package com.example.tutorial.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.dao.SettingsDAO;

public class ConfigurationImpl implements Configuration {
	@Inject
	private SettingsDAO settingsDAO;
	
	// Creates a configuration map containing credentials and other required configuration parameters.
	public Map<String,String> getAcctAndConfig(){
		Map<String,String> configMap = new HashMap<String,String>();
		configMap.putAll(getConfig());
				
		// Account Credential
		configMap.put("acct1.UserName", settingsDAO.getPayPalAccount().username);
		configMap.put("acct1.Password", settingsDAO.getPayPalAccount().password);
		configMap.put("acct1.Signature", settingsDAO.getPayPalAccount().signature);
		// Subject is optional, only required in case of third party permission
		//configMap.put("acct1.Subject", "");
		
		// Sample Certificate credential
		// configMap.put("acct2.UserName", "certuser_biz_api1.paypal.com");
		// configMap.put("acct2.Password", "D6JNKKULHN3G5B8A");
		// configMap.put("acct2.CertKey", "password");
		// configMap.put("acct2.CertPath", "resource/sdk-cert.p12");
		// configMap.put("acct2.AppId", "APP-80W284485P519543T");
		
		return configMap;
	}
	
	public Map<String,String> getConfig(){
		Map<String,String> configMap = new HashMap<String,String>();
		
		// Endpoints are varied depending on whether sandbox OR live is chosen for mode
		configMap.put("mode", settingsDAO.getPayPalAccount().mode);
		

		// These values are defaulted in SDK. If you want to override default values, uncomment it and add your value.
		// configMap.put("http.ConnectionTimeOut", "5000");
		// configMap.put("http.Retry", "2");
		// configMap.put("http.ReadTimeOut", "30000");
		// configMap.put("http.MaxConnection", "100");
		return configMap;
	}
}
