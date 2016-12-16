package ru.spb.web;

import java.io.File;
import java.util.Scanner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	static int state = 0;
	static int port = 8080;
	static String fileName = "index.html";


	public static void main(String[] args) {
		
		//-------Create GUI-------//
		JFrame form = new JFrame("Finite continued fractions");
		form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        textArea = new JTextArea(10, 20);
        textArea.setText(".::WebServer v1.0::.\n\n Enter port or config file name: \n");
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

		/*try {
			Scanner sc = new Scanner(new File("config.txt"));
			int port = sc.nextInt();
			String page = sc.next();
			System.out.println(page);
			sc.close();
			
			Scanner scPage = new Scanner(new File(page));
			String textPage = "";
			while(scPage.hasNext())
				textPage += scPage.nextLine() + "\r\n";
			scPage.close();
			HttpServer serv = new HttpServer(port, textPage);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrac*/
	}
	
	
	public static void addText(String str) {
		textArea.setText(textArea.getText()+"\n"+str);
	}
	
	public static void buttonClick(String str) {
		s = str;
		if (state == 0) {
			if (s.contains(".txt")) {
				try {
					Scanner sc = new Scanner(new File(s));
					int port = sc.nextInt();
					String page = sc.next();
					System.out.println(page);
					sc.close();
					
					Scanner scPage = new Scanner(new File(page));
					String textPage = "";
					while(scPage.hasNext())
						textPage += scPage.nextLine() + "\r\n";
					scPage.close();
					HttpServer serv = new HttpServer(port, textPage);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				port = Integer.parseInt(s);
				state++;
				addText("Enter page file name: \nf.e.: index.html\n");
				return;
			}
		}
		if (state == 1) {
				fileName = s;
			try {
				Scanner scPage = new Scanner(new File(fileName));
				String textPage = "";
				while(scPage.hasNext())
					textPage += scPage.nextLine() + "\r\n";
				scPage.close();
				HttpServer serv = new HttpServer(port, textPage);
				new Thread(serv).start();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			state++;
		}
		//System.out.println();
	}

}
