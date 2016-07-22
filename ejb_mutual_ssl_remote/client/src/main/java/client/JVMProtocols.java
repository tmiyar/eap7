package client;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.security.NoSuchAlgorithmException;

public class JVMProtocols
{
    public static void main(String[] args) throws NoSuchAlgorithmException
    {
        try {
            SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(9999);

            String[] slist = sslserversocket.getSupportedProtocols();
            System.out.println("\nSupported Protocols:");
            for (String s : slist)
            {
                System.out.println(s);
            }

            String[] elist = sslserversocket.getEnabledProtocols();
            System.out.println("\nEnabled Protocols:");
            for (String e : slist)
            {
                System.out.println(e);
            } 

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}