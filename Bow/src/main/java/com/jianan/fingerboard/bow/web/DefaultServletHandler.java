package com.jianan.fingerboard.bow.web;

import org.mortbay.jetty.HttpConnection;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author by jianan.liu on 16/11/28.
 */
public class DefaultServletHandler extends AbstractHandler {

    public void handle(String s, HttpServletRequest request, HttpServletResponse response, int i)
            throws IOException, ServletException {
        Request base_request = (request instanceof Request) ? (Request) request
                : HttpConnection.getCurrentConnection().getRequest();
        base_request.setHandled(true);
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("Hello Bow!");
    }
}
