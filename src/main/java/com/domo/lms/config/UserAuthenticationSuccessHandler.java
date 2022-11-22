package com.domo.lms.config;

import com.domo.lms.entity.MemberLog;
import com.domo.lms.repository.MemberLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;


public class UserAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private MemberLogRepository memberLogRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("user-agent");
        String userName = authentication.getName();
        memberLogRepository.save(MemberLog.builder()
                .userName(userName)
                .ip(ip)
                .agent(userAgent)
                .accessDate(LocalDateTime.now())
                .build());

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
