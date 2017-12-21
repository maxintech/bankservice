package bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Although it is possible to package this service as a traditional WAR file for deployment to
 * an external application server, this approach creates a standalone application. 
 * All is packed in a single, executable JAR file, driven by a Java main() method. 
 * Along the way, uses Spring’s support for embedding the Tomcat servlet container as the HTTP runtime, 
 * instead of deploying to an external instance.
 * 
 * @author Maximiliano Sanchez de Bustamante
 *
 */

/**
 * {@code @SpringBootApplication} is a convenience annotation that adds all of the following:
 * 		{@code @Configuration} tags the class as a source of bean definitions for the application context.
 * 		{@code @EnableAutoConfiguration} tells Spring Boot to start adding beans based on classpath settings, 
 * 		other beans, and various property settings.
 * 		Normally you would add {@code @EnableWebMvc} for a Spring MVC app, but Spring Boot adds it automatically 
 * 		when it sees spring-webmvc on the classpath. This flags the application as a web application and 
 * 		activates key behaviors such as setting up a DispatcherServlet.
 *		{@code @ComponentScan} tells Spring to look for other components, configurations, and services in the hello package, 
 *		allowing it to find the controllers.
 */
@SpringBootApplication
public class Application {

	/**
	 * Uses Spring Boot’s SpringApplication.run() method to launch the application.
	 * @param args
	 */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
