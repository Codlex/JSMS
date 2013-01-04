package com.codlex.JSMS.dclient;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.jar.JarInputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginScreen extends JFrame {
	
	public LoginScreen() {
		setPreferredSize(new Dimension(100, 100));
		setSize(100, 100);
		add(new LoginPanel());
		
	}
	
	private class LoginPanel extends JPanel {
		private LoginInputPanel loginInput;
		private LoginButtonsPanel buttonsPanel;
		
		public LoginPanel() {
			setLayout(new BorderLayout());
			add(new JLabel("Dobrodosli u CodlexOKO"), BorderLayout.NORTH);
			loginInput = new LoginInputPanel();
			add(loginInput, BorderLayout.CENTER);
			buttonsPanel = new LoginButtonsPanel();
			add(buttonsPanel, BorderLayout.SOUTH);
		}
		
	}
	
	private class LoginInputPanel extends JPanel {
		
		private JTextField username;
		private JTextField password;
		
		public LoginInputPanel() {
			setLayout(new GridLayout(2, 1));
			username = new JTextField();
			password = new JTextField();
			add(username);
			add(password);
		}
	}
	
	private class LoginButtonsPanel extends JPanel {
		private JButton login;
		private JButton createAccount;
		public LoginButtonsPanel() {
			setLayout(new GridLayout(1,2));
			
			login = new JButton("Login");
			createAccount = new JButton("Create account");
			
			add(createAccount);
			add(login);
			login.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showInputDialog("Hello");
				}
			});
		}
		
	}

}
