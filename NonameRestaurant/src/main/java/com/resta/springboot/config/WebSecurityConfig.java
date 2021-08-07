package com.resta.springboot.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sql.DataSource;
 
import com.resta.springboot.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
 
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    private UserServiceImpl userDetailsService;
 
    @Autowired
    private DataSource dataSource;
    
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }
 
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
    
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory("user-photos", registry);
    }
     
    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();
         
        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");
         
        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/"+ uploadPath + "/");
    }
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
 
        // Sét đặt dịch vụ để tìm kiếm User trong Database.
        // Và sét đặt PasswordEncoder.
    	 
          auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
 
        http.csrf().disable();
 
        // Các trang không yêu cầu login
        http.authorizeRequests().antMatchers("/", "/login","/signout", "/register","/forgotPassword","/about","/menu","/photos","/contact").permitAll();
  
      //  http.authorizeRequests().antMatchers("/").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
        
        // Trang chỉ dành cho KITCHEN
        http.authorizeRequests().antMatchers("/ingredientListK").access("hasRole('ROLE_KITCHEN')");
        
        // Trang chỉ dành cho CASHIER
        http.authorizeRequests().antMatchers("/invoiceListC").access("hasRole('ROLE_CASHIER')");
        
        // Trang chỉ dành cho WAITER
        http.authorizeRequests().antMatchers("/bookingListW").access("hasRole('ROLE_WAITER')");
 
        // Trang chỉ dành cho ADMIN
        http.authorizeRequests().antMatchers("/manageEmployee","/manageCustomerAd","/manageStockAd","/manageBookingAd","/managePaymentAd","/manageMenu","/manageStatiticsAd","/manageFeedback","/manageOffer","/viewProfileAd").access("hasRole('ROLE_ADMIN')");
 
        // Trang chỉ dành cho CUSTOMER
        http.authorizeRequests().antMatchers("/viewProfile","/newBookingCus","/viewEmptyTable","/orderMenu","/bookingPayment","/invoicePayment").access("hasRole('ROLE_USER')");
 
        
        // Khi người dùng đã login, với vai trò XX.
        // Nhưng truy cập vào trang yêu cầu vai trò YY,
        // Ngoại lệ AccessDeniedException sẽ ném ra.
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
 
        // Cấu hình cho Login Form.
        http.authorizeRequests().and().formLogin()//
                // Submit URL của trang login
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/login")//
                //.defaultSuccessUrl("/", true)//
                .successHandler(myAuthenticationSuccessHandler())
                .failureUrl("/login?error=true")// 
                .usernameParameter("email")//
                .passwordParameter("password")
                // Cấu hình cho Logout Page.
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
 
        // Cấu hình Remember Me.
        http.authorizeRequests().and() //
                .rememberMe().tokenRepository(this.persistentTokenRepository()) //
                .tokenValiditySeconds(1 * 24 * 60 * 60); // 24h
 
    }
 

    
 // Token stored in Table (Persistent_Logins)
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(this.dataSource);
        return db;
    }
     
//    // Token stored in Memory (Of Web Server).
//    @Bean
//    public PersistentTokenRepository persistentTokenRepository() {
//        InMemoryTokenRepositoryImpl memory = new InMemoryTokenRepositoryImpl();
//        return memory;
//    }
 
}
