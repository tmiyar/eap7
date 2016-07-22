package client;
import ejb3.MathRemote;
import javax.naming.*;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientConfiguration;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
import org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector;

import java.util.*;

public class StandaloneClient {
	public static void main(String args[]) {
          Context context=null;
          String JNDI_NAME="ejb:/EJB_WildFly_Https/MathBean!ejb3.MathRemote";
          
          System.setProperty("javax.net.ssl.keyStore", "/Users/tmiyar/EAP/EAP-7.0.0/standalone/configuration/client.keystore"); //change it  
          System.setProperty("javax.net.ssl.trustStore", "/Users/tmiyar/EAP/EAP-7.0.0/standalone/configuration/client.truststore"); //change it  
          System.setProperty("javax.net.ssl.keyStorePassword", "keypassword"); //change it  
          System.setProperty("javax.net.ssl.trustStorePassword", "keypassword"); //change it  
          try {
                Properties props = new Properties();
                props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
                		
				props.put("org.jboss.ejb.client.scoped.context", "true");
				props.put("remote.connection.default.connect.options.org.xnio.Options.SSL_ENABLED", "true");
				props.put("remote.connection.default.connect.options.org.xnio.Options.SSL_ENABLED_PROTOCOLS", "TLSv1.2");
				props.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");
				
				props.put("remote.connections", "default");
				props.put("remote.connection.default.host", "localhost");
				props.put("remote.connection.default.port", "8443");
				props.put("remote.connection.default.protocol", "https-remoting");

                context = new InitialContext(props);

	            System.out.println("\n\tGot initial Context: "+context);
           } catch (Exception e) {
                e.printStackTrace();
           }

           try {
		        MathRemote remote=(MathRemote)context.lookup(JNDI_NAME);
		        System.out.println("remote "+ remote);
	   	        int sum=remote.add(2,10);
	   	     System.out.println("add 2, 10 "+ sum);
	   	        String result = remote.sayHello("Hello");
		        System.out.println("\n\t remote.add(2,10) => "+ sum);
		        System.out.println("\n\t remote.sayHello('Hello') => "+ result);
    	   } catch(Exception e) {
		       e.printStackTrace();
	       }
	}
}
