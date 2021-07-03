package com.backbase.assignment.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Component
public class AuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            clearAuthenticationAttributes(request);
            // Use the DefaultSavedRequest URL
            String targetUrl = savedRequest.getRedirectUrl();
            logger.debug("Redirecting to DefaultSavedRequest UrlService: " + targetUrl);
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
            return;
        }
        returnROleWithUserName(authentication, response);
    }

    private boolean returnROleWithUserName(Authentication authentication, HttpServletResponse response) throws IOException {
        String role = ((List<GrantedAuthority>) authentication.getAuthorities()).get(0).getAuthority();
        String userName = authentication.getName();
        switch (role) {
            case "ADMIN":
                response.getWriter().write("{\"ROLE\":\"ADMIN\",\"userName\":\"Admin - " + userName + "\"}");
                response.getWriter().flush();
                response.getWriter().close();
                return true;
            case "USER":
                response.getWriter().write("{\"ROLE\":\"USER\",\"userName\":\"USER - " + userName + "\"}");
                response.getWriter().flush();
                response.getWriter().close();
                return true;
            default:
                logger.debug("Role: " + role);
        }
        return false;
    }

}
