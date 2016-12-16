package ru.spb.web;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Main {
	
	static JTextArea textArea = null;
	static int index = 0;
	static String s;

	public static void main(String[] args) {
		//-------Create GUI-------//
				JFrame form = new JFrame("Finite continued fractions");
				form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				JPanel mainPanel = new JPanel();
		        mainPanel.setLayout(new BorderLayout());
		        textArea = new JTextArea(10, 20);
		        textArea.setText(".::WebClient v1.0::.\n\n Enter server address: \nf.e.: http://localhost:8080\n\n");
		        textArea.setWrapStyleWord(true);
		        textArea.setLineWrap(true);
		        textArea.setCaretPosition(0);
		        textArea.setEditable(false);
		        final JScrollPane scrollPane = new JScrollPane(textArea);
		        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		        mainPanel.add(scrollPane, BorderLayout.CENTER);
		        JPanel panel = new JPanel();
		        panel.setLayout(new FlowLayout());
		        
		        //JLabel label = new JLabel("Enter expression:");
		        //panel.add(label, BorderLayout.NORTH);
		        JTextField edit = new JTextField(20);
		        panel.add(edit);
		        JButton button = new JButton("ENTER");
		        button.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						addText(edit.getText());
						//textArea.setText(textArea.getText()+"\n"+);
						buttonClick(edit.getText());
						edit.setText("");
					}
				});
		        panel.add(button);
		        mainPanel.add(panel, BorderLayout.SOUTH);
		        form.getContentPane().add(mainPanel);
		        form.setPreferredSize(new Dimension(400, 300));
		        form.pack();
		        form.setLocationRelativeTo(null);
		        form.setVisible(true);

				
			}
			
			
			public static void addText(String str) {
				textArea.setText(textArea.getText()+"\n"+str);
			}
			
			public static void buttonClick(String str) {
					s = str;
					String url_get_page = s;
				   
				   StringBuffer result = new StringBuffer();
				   URL url;
				try {
					   url = new URL(url_get_page);			
				       HttpURLConnection connection = null;
			           connection = (HttpURLConnection) url.openConnection();           		   
				       
				       connection.setRequestMethod("GET");
				       connection.setDoOutput(true);
			           connection.setReadTimeout(10000);	       
			           connection.setRequestProperty("Host", "www.pai.pt");
			           connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; ru; rv:1.9.2) Gecko/20100115 Firefox/3.6");
			           connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			           connection.setRequestProperty("Accept-Language", "ru,en-us;q=0.7,en;q=0.3");
			           connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
			           //connection.setRequestProperty("Accept-Charset", "windows-1251,utf-8;q=0.7,*;q=0.7");
			           connection.setRequestProperty("Keep-Alive", "115");
			           connection.setRequestProperty("Connection", "keep-alive");
			           connection.setRequestProperty("Referer", "http://www.pai.pt/search.ds");
				       connection.setRequestProperty("Cookie", "MfPers=12678646695048a98819027298bf50127329f8c315e8f; vuid=8a98819027298bf50127329f8c315e8f; ptkn=40EAFA18-5758-F374-F570-A0480F306222; WT_FPC=id=174.142.104.57-1456441520.30063880:lv=1267888167073:ss=1267888167073; __utma=76091412.2059393411.1267864686.1267878351.1267891770.4; __utmz=76091412.1267864686.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); BFHost=wd-web04.osl.basefarm.net; JSESSIONID=20C8FD4414F50F3AE361C487D0E3C719; MfTrack=12678917654148a98819027298bf50127329f8c315e8f; BIGipServerwd-web-pt=285284362.20480.0000; __utmb=76091412.1.10.1267891770; __utmc=76091412");	       
				       connection.connect();
				       
				       BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF8"));
				       String line;
				       while ((line = rd.readLine()) != null) {
				           result.append(line).append("\n");
				       }
				       connection.disconnect();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					addText(result.toString());
			       System.out.println(result.toString());	
			}

}
