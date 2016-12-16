package ru.spb.web;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;


public class HttpServer  implements Runnable {
	
	
	private String textPage = "null";
	private ServerSocket ss;
	
	public HttpServer(int port, String textPage)  throws Throwable {
		this.textPage = textPage;
		ss = new ServerSocket(port);
		
		
		System.out.println("Server config:");
		System.out.println("Port: "+ port);
		System.out.println("Address: http://localhost:"+port+"\n\n");
		System.out.println("Server is running...");
		Main.addText("Server config:\n");
		Main.addText("Port: "+ String.valueOf(port)+"\n");
		Main.addText("Address: http://localhost:"+String.valueOf(port)+"\n\n");
		Main.addText("Server is running...\n");
        
	}
	
	@Override
	public void run() {
		while (true) {
            Socket s;
			try {
				
				s = ss.accept();
				Main.addText("Client accepted\n");
	            System.err.println("Client accepted");
	            
	            new Thread(new SocketProcessor(s)).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
	}

    private class SocketProcessor implements Runnable {

        private Socket s;
        private InputStream is;
        private OutputStream os;

        private SocketProcessor(Socket s) throws Throwable {
            this.s = s;
            this.is = s.getInputStream();
            this.os = s.getOutputStream();
        }

        public void run() {
            try {
                readInputHeaders();
                writeResponse(textPage);
            } catch (Throwable t) {
                /*do nothing*/
            } finally {
                try {
                    s.close();
                    this.is.close();
                    this.os.close();
                } catch (Throwable t) {
                    /*do nothing*/
                }
            }
            Main.addText("Client processing finished\n");
            System.err.println("Client processing finished");
        }

        private void writeResponse(String s) throws Throwable {
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Server: YarServer/2009-09-09\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + s.length() + "\r\n" +
                    "Connection: close\r\n\r\n";
            String result = response + s;
            os.write(result.getBytes());
            os.flush();
        }

        private void readInputHeaders() throws Throwable {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while(true) {
                String s = br.readLine();
                if(s == null || s.trim().length() == 0) {
                    break;
                }
            }
        }
    }
}
