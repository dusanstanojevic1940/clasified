package com.example.tutorial.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.tapestry5.beaneditor.NonVisual;

@Entity
public class Settings {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonVisual
	@Column(name="settings_id")
	public Long id;
	
	public boolean showCaptchaOnUserRegistration = false;
	public boolean showCaptchaOnPostAds = false;
	public boolean showCaptchaOnBumpPost = false;
	public boolean allowVideoOnPost = false;
	public boolean allowPicturesOnPost = false ;
	public boolean offeringWanted = false;
	public boolean showLocationOnPostAds = false;
	public boolean showIP = true;
	
	public boolean topCodeOn = false;
	public boolean bottomCodeOn = false;
	
	@Column(length=10000)
	public String bottomCodeText = "<div style=\"margin-top:10px\"><script>"+
			"if($(\"#filterDiv\").html().trim() == \"\"){"+
			"$(\"#filterDiv\").remove();"+
		"}"+
		"</script>"+
		"<script async=\"async\" src=\"//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js\"></script>"+
	    			"<script type=\"text/javascript\">"+
		    	"var width = $(\"body\").innerWidth();"+
		    	"if (width <= 356) {"+
		    		"document.write(\"<div style='margin:0 -10px;text-align:center'>\");"+
		    		"document.write('<ins class=\"adsbygoogle\" style=\"display:inline-block;width:300px;height:250px\" data-ad-client=\"ca-pub-8542821156374773\" data-ad-slot=\"0960493412\"></ins>');"+
		    		"document.write(\"</div>\");"+
		    	"} else if (width < 740) {"+
		    		"document.write(\"<div style='margin:0 -10px;text-align:center'>\");"+
		    		"document.write('<ins class=\"adsbygoogle\" style=\"display:inline-block;width:336px;height:280px\" data-ad-client=\"ca-pub-8542821156374773\" data-ad-slot=\"5054433649\"></ins>');"+
		    		"document.write(\"</div>\");"+
		    	"} else {"+
		    		"document.write('<ins class=\"adsbygoogle\" style=\"display:inline-block;width:728px;height:90px\"  data-ad-client=\"ca-pub-8542821156374773\" data-ad-slot=\"7503889699\"></ins>');"+
		    	"}</script>"+
	"<script>(adsbygoogle = window.adsbygoogle || []).push({});</script></div>";
	
	@Column(length=10000)
	public String topCodeText = "<script>"+
	"if($(\"#filterDiv\").html().trim() == \"\"){"+
		"$(\"#filterDiv\").remove();"+
	"}"+
	"</script>"+
	"<script async=\"async\" src=\"//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js\"></script>"+
    			"<script type=\"text/javascript\">"+
	    	"var width = $(\"body\").innerWidth();"+
	    	"if (width <= 356) {"+
	    		"document.write(\"<div style='margin:0 -10px;text-align:center'>\");"+
	    		"document.write('<ins class=\"adsbygoogle\" style=\"display:inline-block;width:300px;height:250px\" data-ad-client=\"ca-pub-8542821156374773\" data-ad-slot=\"0960493412\"></ins>');"+
	    		"document.write(\"</div>\");"+
	    	"} else if (width < 740) {"+
	    		"document.write(\"<div style='margin:0 -10px;text-align:center'>\");"+
	    		"document.write('<ins class=\"adsbygoogle\" style=\"display:inline-block;width:336px;height:280px\" data-ad-client=\"ca-pub-8542821156374773\" data-ad-slot=\"5054433649\"></ins>');"+
	    		"document.write(\"</div>\");"+
	    	"} else {"+
	    		"document.write('<ins class=\"adsbygoogle\" style=\"display:inline-block;width:728px;height:90px\"  data-ad-client=\"ca-pub-8542821156374773\" data-ad-slot=\"7503889699\"></ins>');"+
	    	"}</script>"+
"<script>(adsbygoogle = window.adsbygoogle || []).push({});</script>";
	
	public boolean messageOn = false;
	@Column(length=2000)
	public String messageText = "";
	
	public int postsPerPage = 30;
	
	public int picturesPerPost = 4;
	
	public int priceTopAdCoinsPerDay = 4;
	public int priceFeaturedAdCoinsPerDay = 8;
	
	public String websiteTitle = "ADS727";
	public String mainHeaderTitle = "ADS727";
	public String email = "yourEmail@gmail.com";
	public String paypalEmail = "yourEmailForPayPal@gmail.com";
	public String imageURL = "/var/lib/tomcat7/webapps/images";
	public String backupURL = "/var/lib/tomcat7/webapps/images";
	public String imageOnWebsiteURL = "http://shichengpeiyou.com/images";
	
	public boolean singleCategoryHome = false;
	public String singleCategoryURL = "";
	
	public String helpTitle = "Help";
	
	@Column(length=2000)
	public String helpText = "\n- What are the rules for using Ads711?\n&#8194;&#8194;1. Cannot post duplicated ads.\n&#8194;&#8194;2. Cannot post unlawful ads.\n&#8194;&#8194;3. Cannot advertise for our competitors.\n&#8232;\n- How do I post an Ad？&#8194;&#8194;1. Click \"Post ad\" at the home page.\n&#8194;&#8194;2. Choose the correct category for your Ad。&#8194;&#8194;3. Fill in the form.\n&#8194;&#8194;4. Click submit button.\n&#8232;\n- How do I make my Ad sticky at the top of the list？\n&#8194;&#8194;1. Sign in。\n&#8194;&#8194;2. Open your ad and click \"Sticky\" button。\n&#8194;&#8194;3. Select the duration. If you do not have enough gold coins, you need to buy. \n&#8194;&#8194;4. Click submit button.\n&#8232;\n- How do I【bump up】/【edit】/【remove】my Ad？\n&#8194;&#8194;Open your ad and click on the corresponding button.\n\n&#8232;If you have any other questions, please drop us an email.";
	
	public String termsAndConditionsTitle = "Terms of use";
	public String termsAndConditionsSubtitle = "Terms and conditions";
	
	@Column(length=150)
	public String termsAndConditionsWarnning = "As a condition of your use of this website you agree that you will not:";
	
	@Column(length=3000)
	public String termsAndConditionsFacts = "<ol>"+
"<li><span style=\"font-size: 11px;\">violate any laws;</span></li>"+
"<li><span style=\"font-size: 11px;\">violate the Posting Rules; post any threatening, abusive, defamatory, obscene or indecent material;</span></li>"+
"<li><span style=\"font-size: 11px;\">be false or misleading;</span></li>"+
"<li><span style=\"font-size: 11px;\">infringe any third-party right;</span></li>"+
"<li><span style=\"font-size: 11px;\">distribute or contain spam, chain letters, or pyramid schemes;</span></li>"+
"<li><span style=\"font-size: 11px;\">distribute viruses or any other technologies that may harm this website or the interests or property of the users;</span></li>"+
"<li><span style=\"font-size: 11px;\">impose an unreasonable load on our infrastructure or interfere with the proper working of this website;</span></li>"+
"<li><span style=\"font-size: 11px;\">copy, modify, or distribute any other person's content without their consent;</span></li>"+
"<li><span style=\"font-size: 11px;\">use any robot spider, scraper or other automated means to access this website and collect content for any purpose without our express written permission;</span></li>"+
"<li><span style=\"font-size: 11px;\">harvest or otherwise collect information about others, including email addresses, without their consent; bypass measures used to prevent or restrict access to this website.</span></li>"+
"</ol>";
	
	public String termsAndConditionsLiabilityHeader = "liability";
	 
	@Column(length=4000)
	public String termsAndConditionsLiabilityFacts = "<p>Nothing in these Terms of Use shall limit our liability for fraudulent misrepresentation or for death or personal injury resulting from our negligence. You agree not to hold us responsible for things other users post or do.</p><p>We do not review users' postings and are not involved in the actual transactions between users.</p><p>We do not guarantee or give any warranty or make any representation as to the accuracy and content of postings or user communications or the quality, safety, or legality of what is offered. In no event do we accept liability of any description for the posting of any unlawful, threatening, abusive, defamatory, obscene or indecent information or material of any kind including any such information or material which violates or infringes upon the rights of any other person or which constitutes or encourages conduct that would be a criminal offence, give rise to civil liability or otherwise violate any applicable law. We also cannot guarantee continuous, error free or secure access to our services or that defects in the service will be corrected.</p><p>Accordingly, to the extent legally permitted, we expressly disclaim all warranties, representations and conditions, express or implied, including those of quality, merchantability, merchantable quality, durability, fitness for a particular purpose and those arising by statute. We shall not be liable for any loss, whether of money (including profit), goodwill, or reputation, or any special, indirect, or consequential damages arising out of your use of this website, even if you advise us or we could reasonably foresee the possibility of any such damage occurring.</p><p>Some jurisdictions do not allow the disclaimer of warranties or exclusion of damages, so such disclaimers and exclusions shall only apply to the extent permitted by law. We may update these Terms of Use at any time, with updates taking effect when you next use the site or after 30 days, whichever is sooner. No other amendment to these Terms of Use will be effective unless made in writing, signed by users and by us.</p><p>We have all the copyright of the posts listed on this website.</p>";
}
