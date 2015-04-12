package com.example.tutorial.components.security;

import org.apache.tapestry5.corelib.base.AbstractConditional;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.tutorial.services.Authenticator;


public class Authenticated extends AbstractConditional
{

    @Inject
    private Authenticator authenticator;

    @Override
    protected boolean test()
    {
        return authenticator.isLoggedIn();
    }

}
