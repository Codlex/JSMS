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

public class LoginScreen extends JFrame {
	private boolean logged;
	private LoginPanel loginPanel;
	public LoginScreen() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Codlex oko");
		setBackground(new Color(00,0x93,0xd6));
		setPreferredSize(new Dimension(348, 528));
		setSize(348, 528);
		setResizable(false);
		loginPanel = new LoginPanel();
		add(loginPanel);
	}

	private class LoginPanel extends JPanel {
		
		private LoginInputPanel loginInput;
		private CreateAccount createAccount;
		
		public LoginInputPanel getLoginInput() {
			
			return loginInput;
		}
		
		public LoginPanel() {
			setBackground(new Color(00,0x93,0xd6));
			setBackground(null);
			setLayout(new BorderLayout());
			add(new Header(), BorderLayout.NORTH);
			loginInput = new LoginInputPanel();
			add(loginInput, BorderLayout.CENTER);
			createAccount = new CreateAccount();
			add(createAccount, BorderLayout.SOUTH);
		}
		
	}
	
	private class LoginInputPanel extends JPanel {
		
		private Username username;
		private Password password;
		private Login loginButton;
		
		public String getUsername() {
			return username.getUsername();
		}
		public String getPassword() {
			return password.getPassword();
		}
		
		public LoginInputPanel() {
			setBackground(new Color(00,0x93,0xd6));
			setLayout(new GridLayout(3, 1));
			username = new Username();
			password = new Password();
			loginButton = new Login();
			add(username);
			add(password);
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
	private class Login extends JPanel {
		private ImageIcon image;
		private JButton button;
		private static final String pathToIcon = "/resources/gui/login-login.png";
	
		public Login() {
			setBackground(new Color(00,0x93,0xd6));
		    setLayout(new FlowLayout(FlowLayout.CENTER));
		    button = new JButton(new ImageIcon(getClass().getResource(pathToIcon)));
			button.setPreferredSize(new Dimension(248, 39));
			button.setFocusPainted(false);
			button.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					String username = loginPanel.getLoginInput().getUsername();
					String password = loginPanel.getLoginInput().getPassword();
					Message response = getNICService().logIn(new BaseUser(username, password));
					if( response.getMsgCode() == MSGCode.SUCCESS ) {
						System.out.println("User logged in!");
						logged = true;
						LoginScreen.this.setVisible(false);
						new MainWindow("Codlex oko").setVisible(true);
					}
					else {
						System.out.println("Wrong username and password!");
						JOptionPane.showMessageDialog(LoginScreen.this, "Sorry try again! \n" + response.getMsgCode() );
						logged = false;
					}
				}
			});
			add(button);
			
		}
	}
	
	private class CreateAccount extends JPanel {
		private ImageIcon image;
		private JButton button;
		private static final String pathToIcon = "/resources/gui/napravi-nalog.png";
	
		public CreateAccount() {
			setLayout(new FlowLayout(FlowLayout.CENTER));
			setBackground(new Color(00,0x93,0xd6));
			button = new JButton(new ImageIcon(getClass().getResource(pathToIcon)));
			button.setBackground(new Color(00,0x93,0xd6));
			button.setPreferredSize(new Dimension(348, 79));
			button.setFocusPainted(false);
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					LoginScreen.this.setVisible(false);
					new CreateAccountScreen().setVisible(true);
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
