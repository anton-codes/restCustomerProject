/*****************************************************************c******************o*******v******id********
 * File: HttpErrorAsJSONServlet.java
 * Course materials (20F) CST 8277
 * @author Mike Norman
 * @date 2020 10
 * @author Anton Hrytsyk (updated)
 */
package com.algonquincollege.cst8277.utils;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.MOVED_PERMANENTLY;
import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.fromStatusCode;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import com.algonquincollege.cst8277.rest.HttpErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet({"/http-error-as-json-handler"})
public class HttpErrorAsJSONServlet extends HttpServlet implements Serializable {
    private static final long serialVersionUID = 1L;


    static ObjectMapper objectMapper;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int result = response.getStatus();
        if (result >= OK.getStatusCode() && result < (MOVED_PERMANENTLY.getStatusCode()-1)) {
            super.service(request, response);
        }

        else {
            response.setContentType(APPLICATION_JSON);
            Response.Status status = fromStatusCode(result);
            HttpErrorResponse httpErrorResponse = new HttpErrorResponse(result, status.getReasonPhrase());
            String httpErrorResponseStr = getObjectMapper().writeValueAsString(httpErrorResponse);
            try (PrintWriter writer = response.getWriter()) {
                writer.write(httpErrorResponseStr);
                writer.flush();
            }
        }
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static void setObjectMapper(ObjectMapper objectMapper) {
        HttpErrorAsJSONServlet.objectMapper = objectMapper;
    }

}