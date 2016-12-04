package com.jianan.fingerboard.bow.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jianan.fingerboard.bow.store.DataStoreHandler;

/**
 * @author by jianan.liu on 16/11/28.
 */
public class BowDataServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(BowDataServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String query = DataStoreHandler.query();
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println("<h1>" + "Country" + "</h1>");
            response.getWriter().print(query);
        } catch (Exception e) {
            logger.error("get error", e);
        }
    }
}
