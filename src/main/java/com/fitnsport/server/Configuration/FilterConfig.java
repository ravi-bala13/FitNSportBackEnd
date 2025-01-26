package com.fitnsport.server.Configuration;

import com.fitnsport.server.Components.AccessTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Autowired
    private AccessTokenFilter accessTokenFilter;

    @Bean
    public FilterRegistrationBean<AccessTokenFilter> registerAccessTokenFilter() {
        FilterRegistrationBean<AccessTokenFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(accessTokenFilter);
        registrationBean.addUrlPatterns("/api/*"); // Apply only to this path
        registrationBean.setOrder(1); // Priority of the filter

        return registrationBean;
    }

}
