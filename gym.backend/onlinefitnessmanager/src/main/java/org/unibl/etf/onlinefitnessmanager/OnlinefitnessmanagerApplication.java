package org.unibl.etf.onlinefitnessmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@SpringBootApplication
@EnableScheduling
public class OnlinefitnessmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlinefitnessmanagerApplication.class, args);
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:11080"));
		corsConfiguration.setAllowedHeaders(Arrays.asList(
				"Origin", "Access-Control-Allow-Origin", "Content-Type", "Accept", "Authorization",
				"Origin", "Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers",
				"Content-Disposition", "Access-Control-Allow-Origin"));
		corsConfiguration.setExposedHeaders(Arrays.asList(
				"Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials",
				"Content-Disposition"));
		corsConfiguration.setAllowedMethods(Arrays.asList(
				"GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}


}
