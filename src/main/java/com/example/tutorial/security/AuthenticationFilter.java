package com.example.tutorial.security;

import java.io.IOException;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.Response;

import com.example.tutorial.annotations.AdminPage;
import com.example.tutorial.annotations.AnonymousAccess;
import com.example.tutorial.pages.Index;
import com.example.tutorial.pages.user.Register;
import com.example.tutorial.pages.user.SignIn;
import com.example.tutorial.services.Authenticator;

//import com.tap5.hotelbooking.annotations.AnonymousAccess;
//import com.tap5.hotelbooking.pages.Search;
//import com.tap5.hotelbooking.pages.Signin;
//import com.tap5.hotelbooking.pages.Signup;
//import com.tap5.hotelbooking.services.Authenticator;

public class AuthenticationFilter implements ComponentRequestFilter
{

    private final PageRenderLinkSource renderLinkSource;

    private final ComponentSource componentSource;

    private final Response response;

    private final Authenticator authenticator;

    private String defaultPage = Index.class.getSimpleName();

    private String signinPage = SignIn.class.getSimpleName();

    private String registerPage = Register.class.getSimpleName();

    public AuthenticationFilter(PageRenderLinkSource renderLinkSource,
            ComponentSource componentSource, Response response, Authenticator authenticator)
    {
        this.renderLinkSource = renderLinkSource;
        this.componentSource = componentSource;
        this.response = response;
        this.authenticator = authenticator;
    }

    public void handleComponentEvent(ComponentEventRequestParameters parameters,
            ComponentRequestHandler handler) throws IOException
    {

        if (dispatchedToLoginPage(parameters.getActivePageName())) { return; }

        handler.handleComponentEvent(parameters);

    }

    public void handlePageRender(PageRenderRequestParameters parameters,
            ComponentRequestHandler handler) throws IOException
    {

        if (dispatchedToLoginPage(parameters.getLogicalPageName())) { return; }

        handler.handlePageRender(parameters);
    }

    private boolean dispatchedToLoginPage(String pageName) throws IOException
    {

        if (authenticator.isLoggedIn())
        {
            // Logged user should not go back to Signin or Signup
            if (signinPage.equalsIgnoreCase(pageName) || registerPage.equalsIgnoreCase(pageName))
            {
                Link link = renderLinkSource.createPageRenderLink(defaultPage);
                response.sendRedirect(link);
                return true;
            }
            Component page = componentSource.getPage(pageName);
            if (!authenticator.getLoggedUser().isAdmin && page.getClass().isAnnotationPresent(AdminPage.class)) {
            	Link link = renderLinkSource.createPageRenderLink(Index.class);
            	response.sendRedirect(link);
            	return true;
            }
            return false;
        }

        Component page = componentSource.getPage(pageName);

        if (page.getClass().isAnnotationPresent(AnonymousAccess.class)) { return false; }

        Link link = renderLinkSource.createPageRenderLink("user/SignIn");

        response.sendRedirect(link);

        return true;
    }
}
