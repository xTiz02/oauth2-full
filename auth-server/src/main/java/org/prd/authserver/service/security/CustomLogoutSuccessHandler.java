package org.prd.authserver.service.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;


public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectUri = request.getParameter("post_logout_redirect_uri");
        //String clientId = request.getParameter("client_id");

        System.out.println("Se entro al handler de logout-----------------------:" + redirectUri);
        if (redirectUri != null && isValidRedirectUri(redirectUri,"clientId")) { // Verifica que la URI es válida
            response.sendRedirect(redirectUri);
            //response.setStatus(HttpServletResponse.SC_OK);
            //json response
            //response.getWriter().write("{\"status\":\"ok\"}");
        } else {
            //response.setStatus(HttpServletResponse.SC_OK);
            //json response
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\"}");
        }
    }


    private boolean isValidRedirectUri(String redirectUri, String clientId) {
        //Nota: Aquí se puede solicitar el clientId para saber si
        //el cliente de donde proviene la solicitud tiene permitido
        //hacer redirect a la url de la solicitud
        return true; // Por simplicidad, permitimos cualquier URI válida
    }
}