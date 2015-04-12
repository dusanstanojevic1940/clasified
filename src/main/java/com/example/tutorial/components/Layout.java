package com.example.tutorial.components;

import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.SymbolConstants;

import com.example.tutorial.dao.SettingsDAO;
import com.example.tutorial.services.Authenticator;

/**
 * Layout component for pages of application tutorial1.
 */


//@Import(library={"context:js/layout/assets/3f120c89/jquery.min.js",
//"context:layout/external/jquery.masonry.min.js","context:layout/js/jquery.cookie.js", "context:layout/external/bootstrap/js/bootstrap.min.js"})

//@Import(library={"context:layout/js/tinymce/jquery.tinymce.min.js","context:layout/js/tinymce/tinymce.min.js"})
public class Layout {	
    @Inject
    private SettingsDAO settingsDAO;
    
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    private String pageName;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String sidebarTitle;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Block sidebar;

    @Inject
    private ComponentResources resources;

    @Property
    @Inject
    @Symbol(SymbolConstants.APPLICATION_VERSION)
    private String appVersion;
    
    @Inject
    private Authenticator authenticator;

    @Property
    @Parameter(required = false, defaultPrefix = BindingConstants.LITERAL)
    private String selected;
    
    public String getIsSelected(String t) {
    	return t.equals(selected)?"active":"";
    }
    
    public String getWebsiteName() {
    	return settingsDAO.getSettings().websiteTitle;
    }
    
    public String getEmail() {
    	return settingsDAO.getSettings().email;
    }
    
    public String getClassForPageName()
    {
        return resources.getPageName().equalsIgnoreCase(pageName)
                ? "current_page_item"
                : null;
    }

    public boolean getMessageOn() {
    	return settingsDAO.getSettings().messageOn;
    }
    
    public String getStyleIfMessage() {
    	if (settingsDAO.getSettings().messageOn && !settingsDAO.getSettings().topCodeOn)
    		return "margin-top:-10px;";
    	return "";
    }
    
    public boolean getTopCodeOn() {
    	return settingsDAO.getSettings().topCodeOn;
    }

    public String getTopCodeText() {
    	return settingsDAO.getSettings().topCodeText;
    }
    
    public boolean getBottomCodeOn() {
    	return settingsDAO.getSettings().bottomCodeOn;
    }

    public String getBottomCodeText() {
    	return settingsDAO.getSettings().bottomCodeText;
    }
    
    public String getMessageText() {
    	return settingsDAO.getSettings().messageText;
    }
    
    public boolean isAdmin()
    {
        return authenticator.isLoggedIn()?authenticator.getLoggedUser().isAdmin:false;
    }
    
    public boolean isLoggedIn()
    {
        return authenticator.isLoggedIn();
    }
    void onAction() {
    	authenticator.logout();
    }
}
