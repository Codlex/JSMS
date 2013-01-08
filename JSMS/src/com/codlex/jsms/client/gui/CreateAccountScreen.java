package com.codlex.jsms.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.jar.JarInputStream;
import java.util.prefs.BackingStoreException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.codlex.jsms.networking.MSGCode;
import com.codlex.jsms.networking.Message;
import com.codlex.jsms.networking.users.BaseUser;

import static com.codlex.jsms.networking.NICS.CentralizedServerNIC.*;

public class CreateAccountScreen extends JFrame {
	private boolean logged;
	private CreateAccountPanel createAccountPanel;
	public CreateAccountScreen() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Codlex oko :: Create account");
		setBackground(new Color(00,0x93,0xd6));
		setPreferredSize(new Dimension(348, 580));
		setSize(348, 580);
		setResizable(false);
		createAccountPanel = new CreateAccountPanel();
		add(createAccountPanel);
	}

	private class CreateAccountPanel extends JPanel {
		
		private CreateAccountInputPanel createAccountInputPanel;
		private Back createAccount;
		
		public CreateAccountInputPanel getAccountInput() {
			
			return createAccountInputPanel;
		}
		
		public CreateAccountPanel() {
			setBackground(new Color(00,0x93,0xd6));
			setBackground(null);
			setLayout(new BorderLayout());
			add(new Header(), BorderLayout.NORTH);
			createAccountInputPanel = new CreateAccountInputPanel();
			add(createAccountInputPanel, BorderLayout.CENTER);
			createAccount = new Back();
			add(createAccount, BorderLayout.SOUTH);
		}
		
	}
	
	private class CreateAccountInputPanel extends JPanel {
		
		private Username username;
		private Password password;
		private Password password2;
		private Email email;
		private CreateAccountButton loginButton;
		
		public String getUsername() {
			return username.getUsername();
		}
		public String getPassword() {
			return password.getPassword();
		}
		public String getPassword2() {
			return password2.getPassword();
		}
		public String getEmail() {
			return email.getEmail();
		}
		
		public CreateAccountInputPanel() {
			setBackground(new Color(00,0x93,0xd6));
			setLayout(new GridLayout(5, 1));
			username = new Username();
			password = new Password();
			password2 = new Password();
			email = new Email();
			loginButton = new CreateAccountButton();
			add(username);
			add(password);
			add(password2);
			add(email);
			add(loginButton);
		}
	}
	

	private class Username extends JPanel {
		JTextField text;
		JLabel label;
		JPanel wrapper;
		public Username() {
			setLayout(new FlowLayout(FlowLayout.CENTER));
			setBackground(new Color(00,0x93,0xd6));
			label = new JLabel("username:");
			label.setForeground(Color.WHITE);
			label.setBackground(new Color(00,0x93,0xd6));
			text = new JTextField();
			wrapper = new JPanel();
			wrapper.setBackground(new Color(00,0x93,0xd6));
			wrapper.setLayout(new GridLayout(2,1));
			wrapper.setPreferredSize(new Dimension(248,50));
			wrapper.add(label);
			wrapper.add(text);
			add(wrapper);
		}
		
		public String getUsername() {
			return text.getText();
		}
		
	}
	
	private class Email extends JPanel {
		JTextField text;
		JLabel label;
		JPanel wrapper;
		public Email() {
			setLayout(new FlowLayout(FlowLayout.CENTER));
			setBackground(new Color(00,0x93,0xd6));
			label = new JLabel("e-mail:");
			label.setForeground(Color.WHITE);
			label.setBackground(new Color(00,0x93,0xd6));
			text = new JTextField();
			wrapper = new JPanel();
			wrapper.setBackground(new Color(00,0x93,0xd6));
			wrapper.setLayout(new GridLayout(2,1));
			wrapper.setPreferredSize(new Dimension(248,50));
			wrapper.add(label);
			wrapper.add(text);
			add(wrapper);
		}
		
		public String getEmail() {
			return text.getText();
		}
		
	}
	
	private class Password extends JPanel {
		JPasswordField text;
		JLabel label;
		JPanel wrapper;
		public Password() {
			setLayout(new FlowLayout(FlowLayout.CENTER));
			setBackground(new Color(00,0x93,0xd6));
			label = new JLabel("password:");
			label.setForeground(Color.WHITE);
			label.setBackground(new Color(00,0x93,0xd6));
			text = new JPasswordField();
			wrapper = new JPanel();
			wrapper.setBackground(new Color(00,0x93,0xd6));
			wrapper.setLayout(new GridLayout(2,1));
			wrapper.setPreferredSize(new Dimension(248,50));
			wrapper.add(label);
			wrapper.add(text);
			add(wrapper);
		}
		
		public String getPassword() {
			return new String(text.getPassword());
		}
	}
	private class CreateAccountButton extends JPanel {
		private ImageIcon image;
		private JButton button;
		private static final String pathToIcon = "/resources/gui/create-account.png";
	
		public CreateAccountButton() {
			setBackground(new Color(00,0x93,0xd6));
		    setLayout(new FlowLayout(FlowLayout.CENTER));
		    button = new JButton(new ImageIcon(getClass().getResource(pathToIcon)));
			button.setPreferredSize(new Dimension(248, 39));
			button.setFocusPainted(false);
			button.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					String username = createAccountPanel.getAccountInput().getUsername();
					String password = createAccountPanel.getAccountInput().getPassword();
					String password2 = createAccountPanel.getAccountInput().getPassword2();
					String email = createAccountPanel.getAccountInput().getEmail();
					if(!password.equals(password2)) {
						JOptionPane.showMessageDialog(CreateAccountScreen.this, "Passwords don't match!");
						return;
					}
					Message response = getNICService().createAccount(new BaseUser(username, password, email));
					if( response.getMsgCode() == MSGCode.SUCCESS ) {
						System.out.println("User created in!");
						JOptionPane.showMessageDialog(CreateAccountScreen.this, "User created, you will be logged in!");
						logged = true;
						CreateAccountScreen.this.setVisible(false);
						new MainWindow("Codlex oko").setVisible(true);
					}
					else {
						System.out.println("Username already exists");
						JOptionPane.showMessageDialog(CreateAccountScreen.this, "" + response.getMsgCode() );
						logged = false;
					}
				}
			});
			add(button);
			
		}
	}
	
	private class Back extends JPanel {
		private ImageIcon image;
		private JButton button;
		private static final String pathToIcon = "/resources/gui/back.png";
	
		public Back() {
			setLayout(new FlowLayout(FlowLayout.CENTER));
			setBackground(new Color(00,0x93,0xd6));
			button = new JButton(new ImageIcon(getClass().getResource(pathToIcon)));
			button.setBackground(new Color(00,0x93,0xd6));
			button.setPreferredSize(new Dimension(348, 79));
			button.setFocusPainted(false);
			
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					CreateAccountScreen.this.setVisible(false);
					new LoginScreen().setVisible(true);					
				}
			});
			add(button);
		}
	}
	
	private class Header extends JPanel {
		Image image;
		String backgroundLocation = "/resources/gui/header-login.png";
		public Header() {
			setBackground(new Color(00,0x93,0xd6));
			setPreferredSize(new Dimension(348,137));
			try {
				image = ImageIO.read(getClass().getResource(backgroundLocation));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		@Override
		public void paint(Graphics g) {
			Graphics2D gg = (Graphics2D) g;
			gg.drawImage(image, 0, 0, this);
		}
	}

}
