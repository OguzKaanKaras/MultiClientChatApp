package chat_client;

import java.net.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class client_frame extends javax.swing.JFrame {
	String username, address = "localhost";
	ArrayList<String> users = new ArrayList();
	int port = 2222;
	Boolean isConnected = false;
	EmbedMessage a;
	Socket sock;
	BufferedReader reader;
	PrintWriter writer;
	int imageWidth;
	int imageHeight;
	String[] pixelData;
	int[] pixelDataInt;
	String userList;
	String[] userListArray;
	JList<Object> list; 
	javax.swing.GroupLayout layout;
	Object selectedValue;
	final String secretKey = "ssshhhhhhhhhhh!!!!";
	String clientIndex;
	String usernameVal;

	// --------------------------//

	public void ListenThread() {
		Thread IncomingReader = new Thread(new IncomingReader());
		IncomingReader.start();
	}

	// --------------------------//

	public void userAdd(String data) {
		users.add(data);
	}

	// --------------------------//

	public void userRemove(String data) {
		try {
			doc.insertString(doc.getLength(), data + " is now offline.\n", keyWord);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// --------------------------//

	public void writeUsers() {
		String[] tempList = new String[(users.size())];
		users.toArray(tempList);
		for (String token : tempList) {
			// users.append(token + "\n");
		}
	}

	// --------------------------//

	public void sendDisconnect() {
		String bye = (username + ": :Disconnect");
		try {
			writer.flush();
		} catch (Exception e) {
			try {
				doc.insertString(doc.getLength(), "Could not send Disconnect message.\n", keyWord);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	// --------------------------//

	public void Disconnect() {
		try {
			doc.insertString(doc.getLength(), "Disconnected.\n", keyWord);
			sock.close();
		} catch (Exception ex) {
			try {
				doc.insertString(doc.getLength(), "Failed to disconnect. \n", keyWord);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		isConnected = false;
		tf_username.setEditable(true);

	}

	public client_frame() {
		initComponents();
	}

	// --------------------------//

	public class IncomingReader implements Runnable {
		@Override
		public void run() {
			String[] data;
			String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";

			try {
				while ((stream = reader.readLine()) != null) {
					stream=AES.decrypt(stream,secretKey);
					data = stream.split(":");

					if (data[2].equals(chat)) {
						doc.insertString(doc.getLength(), data[0] + ": " + data[1] + "\n", keyWord);
						textPane.setCaretPosition(textPane.getDocument().getLength());
					} 
					else if (data[2].equals(connect)) {
						textPane.removeAll();
						userAdd(data[0]);
					} 
					else if (data[2].equals("width")) {
						imageWidth = Integer.parseInt(data[1]);

					}
					else if (data[2].equals("height")) {
						imageHeight = Integer.parseInt(data[1]);

					}
					else if (data[2].equals("Image")) {
						pixelDataInt = new int[imageWidth * imageHeight];
						pixelData = data[1].split(" ");
						for (int i = 0; i < pixelData.length; i++) {
							pixelDataInt[i] = Integer.parseInt(pixelData[i]);
						}
						
						BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
						image.setRGB(0, 0, imageWidth, imageHeight, pixelDataInt, 0, imageWidth);
						Style style2 = doc.addStyle("StyleName", null);
						StyleConstants.setIcon(style2, new ImageIcon(image));
						doc.insertString(doc.getLength(), data[0] + ": "  + "\n", keyWord);
						doc.insertString(doc.getLength(),data[0]+"\n", style2);
						decodeMessage(image);
					} 
					else if (data[2].equals("list")) {
						userList=data[1];
						userListArray=data[1].split(" ");
						String[] userListArraySon=new String[userListArray.length+1];
						userListArraySon[0]="Grup";
						for(int i=0; i<userListArray.length;i++) {
							userListArraySon[i+1]=userListArray[i];
						}
						for(int i=0;i<userListArraySon.length;i++) {
							
							usernameVal=new String(userListArraySon[i]);
							
							if(usernameVal.equals(username)) {
								clientIndex=String.valueOf(i);
							}
										
						}
						list.setListData(userListArraySon);
						list.setSelectedIndex(0);
						
						
					}else if (data[2].equals(disconnect)) {
						userRemove(data[0]);
					} else if (data[2].equals(done)) {
						// users.setText("");
						writeUsers();
						users.clear();
					}
				}
			} catch (Exception ex) {
			}
		}
	}

	// --------------------------//

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		list = new JList<Object>();
        JScrollPane spleft = new JScrollPane(list);
		lb_username = new javax.swing.JLabel();
		tf_username = new javax.swing.JTextField(10);
		b_connect = new javax.swing.JButton();
		b_disconnect = new javax.swing.JButton();
		b_image = new javax.swing.JButton();
		lb_image = new javax.swing.JLabel();

		jScrollPane1 = new JScrollPane(textPane);
		tf_chat = new javax.swing.JTextField();
		b_send = new javax.swing.JButton();
		lb_name = new javax.swing.JLabel();

		textPane = new javax.swing.JTextPane();
		doc = textPane.getStyledDocument();

		// Define a keyword attribute

		keyWord = new SimpleAttributeSet();
		StyleConstants.setForeground(keyWord, Color.RED);
		StyleConstants.setBackground(keyWord, Color.YELLOW);
		StyleConstants.setBold(keyWord, true);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Chat - Client's frame");
		setName("client"); // NOI18N
		setResizable(true);

		lb_username.setText("Username :");

		tf_username.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tf_usernameActionPerformed(evt);
			}
		});

		b_connect.setText("Connect");
		b_connect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_connectActionPerformed(evt);
			}
		});
		b_image.setText("Select Image");
		b_image.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				a = new EmbedMessage();
				javax.swing.JButton save = new JButton("Send");
				save.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						send_image();
					}
				});
				a.p.add(save);
			}
		});
		b_disconnect.setText("Disconnect");
		b_disconnect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_disconnectActionPerformed(evt);
			}
		});

		jScrollPane1.setViewportView(textPane);

		b_send.setText("SEND");
		b_send.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_sendActionPerformed(evt);
			}
		});

		lb_name.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		layout = new javax.swing.GroupLayout(getContentPane());
		
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(tf_chat, javax.swing.GroupLayout.PREFERRED_SIZE, 500,javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(spleft, 100, 100, Short.MAX_VALUE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
						
						.addGroup(layout.createSequentialGroup()
								.addComponent(tf_chat, javax.swing.GroupLayout.PREFERRED_SIZE, 500,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(b_image, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))

						.addComponent(jScrollPane1)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
										.addComponent(lb_username, javax.swing.GroupLayout.DEFAULT_SIZE, 62,
												Short.MAX_VALUE))
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)

										.addComponent(tf_username))
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)

								).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)

								).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addComponent(b_connect).addGap(2, 2, 2)
												.addComponent(b_disconnect).addGap(0, 0, Short.MAX_VALUE)))))
				.addContainerGap())
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lb_name).addGap(201, 201, 201)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)

						).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(tf_username)

								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(lb_username)

										.addComponent(b_connect).addComponent(b_disconnect)))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(tf_chat)
                                .addComponent(spleft, 100, 100, Short.MAX_VALUE)
								.addComponent(b_send, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))

						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(tf_chat)
								.addComponent(b_image, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
						
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(lb_name)));
		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void tf_usernameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tf_usernameActionPerformed

	}// GEN-LAST:event_tf_usernameActionPerformed

	private void b_connectActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_b_connectActionPerformed
		if (isConnected == false) {
			username = tf_username.getText();
			tf_username.setEditable(false);

			try {
				sock = new Socket(address, port);
				InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(streamreader);
				writer = new PrintWriter(sock.getOutputStream());
				writer.println(AES.encrypt(username + ":has connected.:Connect:0"+":"+clientIndex,secretKey));
				writer.flush();
				isConnected = true;
				userList=new String();
			} catch (Exception ex) {
				try {
					doc.insertString(doc.getLength(), "Cannot Connect! Try Again. \n", keyWord);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tf_username.setEditable(true);
			}

			ListenThread();

		} else if (isConnected == true) {
			try {
				doc.insertString(doc.getLength(), "You are already connected. \n", keyWord);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}// GEN-LAST:event_b_connectActionPerformed

	private void b_disconnectActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_b_disconnectActionPerformed
		sendDisconnect();
		Disconnect();
	}// GEN-LAST:event_b_disconnectActionPerformed

	private void b_sendActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_b_sendActionPerformed
		String nothing = "";
		if ((tf_chat.getText()).equals(nothing)) {
			tf_chat.setText("");
			tf_chat.requestFocus();
		} else {
			try {
				writer.println(AES.encrypt(username + ":" + tf_chat.getText() + ":" + "Chat:"+String.valueOf(list.getSelectedIndex())+":"+clientIndex,secretKey));
				writer.flush(); // flushes the buffer
			} catch (Exception ex) {
				try {
					doc.insertString(doc.getLength(), "Message was not sent. \n", keyWord);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			tf_chat.setText("");
			tf_chat.requestFocus();
		}

		tf_chat.setText("");
		tf_chat.requestFocus();
	}// GEN-LAST:event_b_sendActionPerformed

	public void send_image() {
		BufferedImage image = a.saveImage();
		String imageWidth = String.valueOf(image.getWidth());
		String imageHeight = String.valueOf(image.getHeight());
		imageHeight = String.valueOf(imageHeight);
		StringBuilder s = new StringBuilder();

		try {
			writer.println(AES.encrypt(username + ":" + imageHeight + ":" + "height:"+String.valueOf(list.getSelectedIndex())+":"+clientIndex,secretKey));
			writer.flush(); // flushes the buffer9
			writer.println(AES.encrypt(username + ":" + imageWidth + ":" + "width:"+String.valueOf(list.getSelectedIndex())+":"+clientIndex,secretKey));
			writer.flush(); // flushes the buffer
			int[] pixelData = new int[Integer.parseInt(imageWidth) * Integer.parseInt(imageHeight)];
			image.getRGB(0, 0, Integer.parseInt(imageWidth), Integer.parseInt(imageHeight), pixelData, 0,
					Integer.parseInt(imageWidth));
			s.append(String.valueOf((pixelData[0])));
			for (int i = 1; i < pixelData.length; i++) {
				s.append(" " + String.valueOf((pixelData[i])));

			}

			writer.println(AES.encrypt(username + ":" + s + ":" + "Image:"+String.valueOf(list.getSelectedIndex())+":"+clientIndex,secretKey));
			writer.flush(); // flushes the buffer
		} catch (Exception ex) {
			try {
				doc.insertString(doc.getLength(), "Message was not sent. \n", keyWord);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		tf_chat.setText("");
		tf_chat.requestFocus();

	}

	private void decodeMessage(BufferedImage image) {
		int len = extractInteger(image, 0, 0);
		byte b[] = new byte[len];
		for (int i = 0; i < len; i++)
			b[i] = extractByte(image, i * 8 + 32, 0);
		
		try {
			doc.insertString(doc.getLength(),"gizli mesaj: "+ new String(b)+"\n", keyWord);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private int extractInteger(BufferedImage img, int start, int storageBit) {
		int maxX = img.getWidth(), maxY = img.getHeight(), startX = start / maxY, startY = start - startX * maxY,
				count = 0;
		int length = 0;
		for (int i = startX; i < maxX && count < 32; i++) {
			for (int j = startY; j < maxY && count < 32; j++) {
				int rgb = img.getRGB(i, j), bit = getBitValue(rgb, storageBit);
				length = setBitValue(length, count, bit);
				count++;
			}
		}
		return length;
	}

	private byte extractByte(BufferedImage img, int start, int storageBit) {
		int maxX = img.getWidth(), maxY = img.getHeight(), startX = start / maxY, startY = start - startX * maxY,
				count = 0;
		byte b = 0;
		for (int i = startX; i < maxX && count < 8; i++) {
			for (int j = startY; j < maxY && count < 8; j++) {
				int rgb = img.getRGB(i, j), bit = getBitValue(rgb, storageBit);
				b = (byte) setBitValue(b, count, bit);
				count++;
			}
		}
		return b;
	}

	private int getBitValue(int n, int location) {
		int v = n & (int) Math.round(Math.pow(2, location));
		return v == 0 ? 0 : 1;
	}

	private int setBitValue(int n, int location, int bit) {
		int toggle = (int) Math.pow(2, location), bv = getBitValue(n, location);
		if (bv == bit)
			return n;
		if (bv == 0 && bit == 1)
			n |= toggle;
		else if (bv == 1 && bit == 0)
			n ^= toggle;
		return n;
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new client_frame().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton b_connect;
	private javax.swing.JButton b_disconnect;
	private javax.swing.JButton b_image;
	private javax.swing.JButton b_send;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lb_name;
	private javax.swing.JLabel lb_image;
	private javax.swing.JLabel lb_username;
	private javax.swing.JTextPane textPane;
	private javax.swing.JTextField tf_chat;
	private javax.swing.JTextField tf_username;
	StyledDocument doc;
	SimpleAttributeSet keyWord;
	// End of variables declaration//GEN-END:variables

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
