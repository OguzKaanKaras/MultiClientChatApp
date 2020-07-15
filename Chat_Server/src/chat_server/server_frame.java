package chat_server;

import java.io.*;
import java.net.*;
import java.util.*;

public class server_frame extends javax.swing.JFrame {
	ArrayList clientOutputStreams;
	ArrayList<String> users;
	final String secretKey = "ssshhhhhhhhhhh!!!!";
	static int i = 1;
	String encryptedMesage;
	
	public class ClientHandler implements Runnable {
		BufferedReader reader;
		Socket sock;
		PrintWriter client;
		String name;

		public ClientHandler(Socket clientSocket, PrintWriter user, String name) {
			this.name = name;
			client = user;
			try {
				sock = clientSocket;
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isReader);
			} catch (Exception ex) {
				ta_chat.append(AES.encrypt("Unexpected error... ",secretKey)+"\n");
			}

		}

		@Override
		public void run() {
			String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat";
			String[] data;

			try {
				while ((message = reader.readLine()) != null) {
					encryptedMesage=message;
					message = AES.decrypt(message, secretKey);
					
					ta_chat.append(AES.encrypt("Received: " + message,secretKey) + "\n");
					data = message.split(":");

					for (String token : data) {
						ta_chat.append(AES.encrypt(token,secretKey) + "\n");
					}
					if (data[3].equals("0")) {
						if (data[2].equals(connect)) {
							tellEveryone((data[0] + ":" + data[1] + ":" + chat));
							userAdd(data[0]);
						} else if (data[2].equals(disconnect)) {
							tellEveryone((data[0] + ":has disconnected." + ":" + chat));
							userRemove(data[0]);
						} else if (data[2].equals(chat)) {
							tellEveryone(message);
						} else if (data[2].equals("width")) {
							tellEveryone(message);

						} else if (data[2].equals("height")) {
							tellEveryone(message);

						} else if (data[2].equals("Image")) {
							tellEveryone(message);
						} else {
							ta_chat.append(AES.encrypt("No Conditions were met.",secretKey)+"\n");
						}
					} else {
							
							if (data[2].equals(chat)) {
								tellSelectedone(message);
							} else if (data[2].equals("width")) {
								tellSelectedone(message);

							} else if (data[2].equals("height")) {
								tellSelectedone(message);

							} else if (data[2].equals("Image")) {
								tellSelectedone(message);
							} else {
								ta_chat.append(AES.encrypt("No Conditions were met.",secretKey)+" \n");
							}
						
					}
				}
			} catch (Exception ex) {
				ta_chat.append(AES.encrypt("Lost a connection.",secretKey)+" \n");
				ex.printStackTrace();
				clientOutputStreams.remove(client);
			}
		}
	}

	public server_frame() {
		initComponents();
		
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		ta_chat = new javax.swing.JTextArea();
		b_start = new javax.swing.JButton();
		b_end = new javax.swing.JButton();
		b_users = new javax.swing.JButton();
		b_clear = new javax.swing.JButton();
		lb_name = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Chat - Server's frame");
		setName("server"); // NOI18N
		setResizable(false);

		ta_chat.setColumns(20);
		ta_chat.setRows(5);
		jScrollPane1.setViewportView(ta_chat);

		b_start.setText("START");
		b_start.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_startActionPerformed(evt);
			}
		});

		b_end.setText("END");
		b_end.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_endActionPerformed(evt);
			}
		});

		b_users.setText("Online Users");
		b_users.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_usersActionPerformed(evt);
			}
		});

		b_clear.setText("Clear");
		b_clear.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_clearActionPerformed(evt);
			}
		});

		lb_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(b_end, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(b_start, javax.swing.GroupLayout.DEFAULT_SIZE, 75,
												Short.MAX_VALUE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 291,
										Short.MAX_VALUE)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(b_clear, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(b_users, javax.swing.GroupLayout.DEFAULT_SIZE, 103,
												Short.MAX_VALUE))))
						.addContainerGap())
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lb_name).addGap(209, 209, 209)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(b_start).addComponent(b_users))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(b_clear).addComponent(b_end))
						.addGap(4, 4, 4).addComponent(lb_name)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void b_endActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_b_endActionPerformed
		try {
			Thread.sleep(5000); // 5000 milliseconds is five second.
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		tellEveryone("Server:is stopping and all users will be disconnected.\n:Chat");
		ta_chat.append(AES.encrypt("Server stopping...",secretKey)+"\n");

		ta_chat.setText("");
	}// GEN-LAST:event_b_endActionPerformed

	private void b_startActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_b_startActionPerformed
		Thread starter = new Thread(new ServerStart());
		starter.start();

		ta_chat.append(AES.encrypt("Server started...",secretKey)+"\n");
	}// GEN-LAST:event_b_startActionPerformed

	private void b_usersActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_b_usersActionPerformed
		ta_chat.append("\n"+AES.encrypt(" Online users : ",secretKey)+"\n");
		for (String current_user : users) {
			ta_chat.append(AES.encrypt(current_user,secretKey));
			ta_chat.append("\n");
		}

	}// GEN-LAST:event_b_usersActionPerformed

	private void b_clearActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_b_clearActionPerformed
		ta_chat.setText("");
	}// GEN-LAST:event_b_clearActionPerformed

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new server_frame().setVisible(true);
			}
		});
	}

	public class ServerStart implements Runnable {
		@Override
		public void run() {
			clientOutputStreams = new ArrayList();
			users = new ArrayList();

			try {
				ServerSocket serverSock = new ServerSocket(2222);

				while (true) {
					Socket clientSock = serverSock.accept();
					PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
					clientOutputStreams.add(writer);
					ClientHandler mtch=new ClientHandler(clientSock, writer, String.valueOf(i));
					Thread listener = new Thread(mtch);
					listener.start();
					i++;
					ta_chat.append("Got a connection. \n");
				}
			} catch (Exception ex) {
				ta_chat.append("Error making a connection. \n");
			}
		}
	}

	public void userAdd(String data) {
		String message, add = ": :Connect", done = "Server: :Done", name = data;
		ta_chat.append(AES.encrypt("Before " + name + " added.",secretKey)+"\n");
		users.add(name);
		ta_chat.append(AES.encrypt("After " + name + " added.",secretKey)+"\n");
		String[] tempList = new String[(users.size())];
		users.toArray(tempList);
		StringBuilder s = new StringBuilder();
		for (String token : tempList) {
			message = (token + add);
			tellEveryone(message);
			s.append(token + " ");
		}
		tellEveryone(done);
		sendEveryoneList(s);
	}

	public void userRemove(String data) {
		String message, add = ": :Connect", done = "Server: :Done", name = data;
		users.remove(name);
		String[] tempList = new String[(users.size())];
		users.toArray(tempList);

		for (String token : tempList) {
			message = (token + add);
			tellEveryone(message);
		}
		tellEveryone(done);
	}

	public void tellEveryone(String message) {
		Iterator it = clientOutputStreams.iterator();

		while (it.hasNext()) {
			try {
				PrintWriter writer = (PrintWriter) it.next();
				writer.println(AES.encrypt(message, secretKey));
				ta_chat.append(AES.encrypt("Sending: " + message,secretKey) + "\n");
				writer.flush();
				ta_chat.setCaretPosition(ta_chat.getDocument().getLength());

			} catch (Exception ex) {
				ta_chat.append(AES.encrypt("Error telling everyone.",secretKey)+"\n");
			}
		}
	}
	
	public void tellSelectedone(String message) {
		
		String[] data = message.split(":");
		int index=Integer.parseInt(data[3]);
		PrintWriter writer = (PrintWriter)clientOutputStreams.get(index-1);
		writer.println(AES.encrypt(message, secretKey));
		
		writer.flush();
		
		index=Integer.parseInt(data[4]);
		writer = (PrintWriter)clientOutputStreams.get(index-1);
		writer.println(AES.encrypt(message, secretKey));
		writer.flush();
	}

	public void sendEveryoneList(StringBuilder s) {
		Iterator it = clientOutputStreams.iterator();

		while (it.hasNext()) {
			try {
				PrintWriter writer = (PrintWriter) it.next();
				writer.println(AES.encrypt("server:" + s + ":list", secretKey));
				writer.flush();
			} catch (Exception ex) {
				ta_chat.append(AES.encrypt("Error telling everyone.",secretKey)+"\n");
			}
		}
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton b_clear;
	private javax.swing.JButton b_end;
	private javax.swing.JButton b_start;
	private javax.swing.JButton b_users;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lb_name;
	private javax.swing.JTextArea ta_chat;
	// End of variables declaration//GEN-END:variables
}
