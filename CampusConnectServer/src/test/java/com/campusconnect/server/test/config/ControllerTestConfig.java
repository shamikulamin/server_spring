package com.campusconnect.server.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration		// We indicate to Spring that it’s an ApplicationContext configuration class by this
@Profile("test")	// We indicate the profile (in this case the test profile) that the beans configured in this class belong to
public class ControllerTestConfig {
}