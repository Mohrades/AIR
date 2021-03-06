package connexions;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AIRConnector {

	private BufferedInputStream in;
    private OutputStream out;
    private String ip;
    private int port;
    private int sleep;
    private Socket socket;

    private boolean available;

	public AIRConnector(String ip, int port, int sleep) {
		try {
			this.sleep = sleep;
			this.ip = ip;
			this.port = port;

			socket = new Socket(ip, port);
	        out = socket.getOutputStream();

		} catch (UnknownHostException e) {
			
		} catch (IOException e) {
			// if an I/O error occurs when creating the output stream or if the socket is not connected.
			
		} catch (Throwable e) {

		}
	}

	public boolean isOpen() {
		return (out == null) ? false : true;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public void fermer() {
		try {
			if(isOpen()){
				socket.close();
	    	}

		} catch (IOException e) {

		}
	}

	public String execute(String requete) {
        try {
        	String header = "POST /Air HTTP/1.1\n" +
            "Content-Length: " + requete.length() + "\n" +
            "Content-Type: text/xml\n" +
            "User-Agent: UGw Server/4.0/1.0\n" +
            "Host: " + ip + ":" + port + "\n" +
            "Authorization: Basic cHNhcHB1c2VyOnBzYXBwdXNlckAxMjM=\n\n";

            byte data[] = (header + requete).getBytes();
            out.write(data, 0, data.length);
            out.flush();

            in = new BufferedInputStream(socket.getInputStream());
            String reponse = "";
            byte[] lecteur = new byte[1024];

            int timeout = 0;
            while (in.available() == 0) {
            	timeout += sleep;

            	/*if(timeout >= 3000) {*/
            	if(timeout >= 4500) {
            		log(requete);
            		handleTimeoutException(timeout);
            		return "";
            	}

            	Thread.sleep(sleep);
            }

            // pause supplementaire pour s'assurer de la r�ception de l'enti�ret� de la reponse
            // Thread.sleep(10);
            Thread.sleep(15);

            while (in.available() > 0) {
            	in.read(lecteur);
                reponse += new String(lecteur);
                lecteur = new byte[1024];
            }

            reponse = reponse.substring(0, reponse.indexOf(String.valueOf((char) 0)));
            int beginIndex=reponse.indexOf("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            reponse=reponse.substring(beginIndex);

            setAvailable(true);
            return reponse;

        } catch (InterruptedException ex) {

        } catch (IOException ex) {

        } catch (StringIndexOutOfBoundsException ex) {

        } catch (Throwable th) {

        }

        log(requete);
        return "";
	}

	public void log(String requete) {
		if(requete.contains("<methodCall><methodName>Get")) ;
		else {
			Logger logger = LogManager.getLogger("logging.log4j.AirRequestLogger");
			logger.log(Level.WARN, requete);
			// logger.shutdown();
		}
	}

	public void handleTimeoutException(int timeout) {
		Logger logger = LogManager.getLogger("logging.log4j.AirAvailabilityLogger");
		logger.error("HOST = " + ip + ",   PORT = " + port + ",   TIMEOUT = " + timeout);
	}

}
