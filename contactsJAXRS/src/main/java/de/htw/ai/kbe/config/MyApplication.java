package de.htw.ai.kbe.config;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

import de.htw.ai.kbe.auth.OwnRequestFilter;
import de.htw.ai.kbe.di.DependencyBinder;
import de.htw.ai.kbe.exceptions.UnhandledExceptionFilter;

@ApplicationPath("/rest")
public class MyApplication extends ResourceConfig { // ResourceConfig substitutes web.xml
	public MyApplication() {
		register(new DependencyBinder());
		register(OwnRequestFilter.class);
        register(UnhandledExceptionFilter.class);
		packages("de.htw.ai.kbe.services");
	}
}