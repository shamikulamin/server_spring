package com.campusconnect.server.controller;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.campusconnect.server.test.config.ControllerTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)	// Belongs to JUnit: indicates the runner classes used to execute the test case
@ContextConfiguration(classes = {ControllerTestConfig.class}) // indicates to the Spring JUnit runner on the configuration to be loaded. classes attribute, which indicates that configuration was defined in the provided Java classes
@ActiveProfiles("test") // indicates to Spring that beans belonging to the test profile should be loaded
public abstract class AbstractControllerTest {

}
