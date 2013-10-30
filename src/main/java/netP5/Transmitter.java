	package netP5;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;

public interface Transmitter {

	public boolean send( byte[] theContent );
	
	public boolean send( byte[] theContent , Collection< InetSocketAddress > theAddress );

	public boolean send( byte[] theContent , String theHost , int thePort );
	
	public boolean send( byte[] theContent , SocketAddress ... theAddress );
	
	public boolean close();
}
