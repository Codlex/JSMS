package com.codlex.jsms.client.gui;

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

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.users.BaseUser;

import static com.codlex.jsms.networking.NICS.CentralizedServerNIC.*;

public class LoginScreen extends JFrame {
	private boolean logged;
	private LoginPanel loginPanel;
	public LoginScreen() {
		setPreferredSize(new Dimension(100, 100));
		setSize(100, 100);
		loginPanel = new LoginPanel();
		add(loginPanel);
		
	}
	public boolean isLoggedIn() {
		return logged;
	}
	private class LoginPanel extends JPanel {
		private LoginInputPanel loginInput;
		private LoginButtonsPanel buttonsPanel;
		
		public LoginInputPanel getLoginInput() {
			return loginInput;
		}
		
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
		
		public String getUsername() {
			return username.getText();
		}
		public String getPassword() {
			return password.getText();
		}
		
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
					String username = loginPanel.getLoginInput().getUsername();
					String password = loginPanel.getLoginInput().getPassword();
					Message response = getNICService().logIn(new BaseUser(username, password));
					if( response.getMsgCode() == MSGCode.SUCCESS ) {
						System.out.println("User logged in!");
						JOptionPane.showInputDialog("Correct, welcome!");
						logged = true;
						LoginScreen.this.setVisible(false);
					}
					else {
						System.out.println("Wrong username and password!");
						JOptionPane.showInputDialog("Sorry try again! \n" + response.getMsgCode() );
						logged = false;
					}
				}
			});
		}
		
	}

}
