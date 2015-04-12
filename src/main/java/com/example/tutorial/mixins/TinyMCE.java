package com.example.tutorial.mixins;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
/**
 *
 * @author Jeshurun Daniel
 *
 */
@Import(library={"context:layout/js/tinymce/jquery.tinymce.min.js",
"context:layout/js/tinymce/tinymce.min.js"})
public class TinyMCE {
	@Inject
	private JavaScriptSupport jsSupport;
	@InjectContainer
	private ClientElement element;
	@BeginRender
	void begin() {
		jsSupport.addScript("tinyMCE.init({ mode : \"exact\", elements: \"%s\" });", element.getClientId());
	}
}