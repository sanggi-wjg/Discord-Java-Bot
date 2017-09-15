package Main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLoggerFactory;

import sx.blah.discord.Discord4J;
import sx.blah.discord.Discord4J.Discord4JLogger;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

public class Test {
	
	public static final Logger LOGGER = initLogger();
	
	public static IDiscordClient createClient(String token, boolean login) {
		ClientBuilder clientBuilder = new ClientBuilder();
		clientBuilder.withToken(token);
		
		try {
			if(login) {
				return clientBuilder.login();
			}
			else {
				return clientBuilder.build();
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	private static Logger initLogger() {
		if (!isSLF4JImplementationPresent()) {
			System.err.println("Discord4J: ERROR INITIALIZING LOGGER!");
			System.err.println("Discord4J: No SLF4J implementation found, reverting to the internal implementation ("+Discord4JLogger.class.getName()+")");
			System.err.println("Discord4J: It is *highly* recommended to use a fully featured implementation like logback!");
			return new Discord4JLogger(Discord4J.class.getName());
		} else {
			return LoggerFactory.getLogger(Discord4J.class);
		}
	}

	private static boolean isSLF4JImplementationPresent() {
		try {
			Class.forName("org.slf4j.impl.StaticLoggerBinder"); //First try to find the implementation
			return !(LoggerFactory.getILoggerFactory() instanceof NOPLoggerFactory); //Implementation found! Double check the logger factory
		} catch (ClassNotFoundException e) {
			return false; //No implementation found
		}
	}
}
