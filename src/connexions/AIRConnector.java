package connexions;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class AIRConnector {
	
	private BufferedInputStream in;
    private OutputStream out;
    private boolean open;
    private String ip;
    private int port;
    private int sleep;
    private Socket socket;

	public AIRConnector(String ip, int port, int sleep) {
		try {
			this.sleep = sleep;
			this.ip = ip;
			this.port = port;

			socket = new Socket(ip, port);
	        out = socket.getOutputStream();

			if(out != null) {
				open=true;
			}

		} catch (UnknownHostException e) {

		} catch (IOException e) {
			// if an I/O error occurs when creating the output stream or if the socket is not connected.

		} catch (Throwable e) {

		}
	}

	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}

	public void fermer() {
		try {
			socket.close();

		} catch (IOException e) {

		}
	}

	public String execute(String requete) {
        try {
        	String header = "POST /Air HTTP/1.1\n" +
            "Content-Length: "+requete.length()+"\n" +
            "Content-Type: text/xml\n" +
            "User-Agent: UGw Server/4.0/1.0\n" +
            "Host: " + ip + ":" + port + "\n" +
            "Authorization: Basic YXBwdXNlcjphcHB1c2VyMjAxMw==\n\n";

            requete = header + requete;

            byte data[] = requete.getBytes();
            out.write(data, 0, data.length);
            out.flush();

            in = new BufferedInputStream(socket.getInputStream());
            String reponse = "";
            byte[] lecteur = new byte[1024];

            while (in.available() == 0){
            	Thread.sleep(sleep);
            }

            // pause supplementaire pour s'assurer de la réception de l'entièreté de la reponse
            Thread.sleep(10);

            while (in.available() > 0) {
            	in.read(lecteur);
                reponse += new String(lecteur);
                lecteur = new byte[1024];
                }

            reponse = reponse.substring(0, reponse.indexOf(String.valueOf((char) 0)));
            int beginIndex=reponse.indexOf("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            reponse=reponse.substring(beginIndex);

            return reponse;

        } catch (InterruptedException ex) {

        } catch (IOException ex) {

        } catch (StringIndexOutOfBoundsException ex) {

        } catch (Throwable th) {

        }

        return "";
	}

}
