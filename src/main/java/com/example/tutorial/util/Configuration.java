package com.example.tutorial.util;


import java.util.Map;

/**
 *  For a full list of configuration parameters refer in wiki page.(https://github.com/paypal/sdk-core-java/wiki/SDK-Configuration-Parameters) 
 */
public interface Configuration {	
	public Map<String,String> getAcctAndConfig();
	
	public Map<String,String> getConfig();
}
