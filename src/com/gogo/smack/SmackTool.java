package com.gogo.smack;

import java.io.IOException;
import java.util.Collection;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import com.gogo.domain.User;

public class SmackTool {
	
	private String serverName;
	
	private XMPPConnection connect;
	
	public String getServerName(){
		if(serverName==null){
			this.serverName = "pc201401011027";
		}
		return serverName;
	}
	
	public XMPPConnection getConnect(User user) throws SmackException, IOException, XMPPException {
		if(connect == null){
			ConnectionConfiguration config = new ConnectionConfiguration(
					serverName, 5222);
			config.setCompressionEnabled(true);  
			config.setSecurityMode(SecurityMode.disabled);//TODO 不设置会报错：Socket closed
			XMPPConnection connection = new XMPPTCPConnection(config);
			connection.connect();
			connection.login(user.getName(),user.getPassword());
			String userjid = connection.getUser(); //userJID
			System.out.println("用户"+userjid+"已登陆");
			this.connect = connection;
		}
		return connect;
	}
	
	/**
	 * 获得好友信息
	 * @return
	 */
	public Collection<RosterEntry> getFriend(){
		
			Roster roster = connect.getRoster();
		
		roster.addRosterListener(new RosterListener() {
			public void presenceChanged(Presence presence) {
				System.out.println("Presence changed: " + presence.getFrom() + " " + presence);
			}
			public void entriesUpdated(Collection<String> arg0) {}
			public void entriesDeleted(Collection<String> arg0) {}
			public void entriesAdded(Collection<String> arg0) {}
		});
		//好友列表
		Collection<RosterEntry> entries = roster.getEntries();
		return entries;
	}
	
	/**
	 * 建立和user的回话
	 * @param user
	 * @return
	 */
	public Chat createChat(User user){
		String userJid=user.getAliasName()+"";
		//建立会话
		ChatManager chatmanager = ChatManager.getInstanceFor(connect);
		
		
		ChatManagerListener linstener = new ChatManagerListener() {
			public void chatCreated(Chat arg0, boolean arg1) {
				System.out.println();
			}
		};
		chatmanager.addChatListener(linstener);
		
		Chat chat = chatmanager.createChat(userJid,new MessageListener() {
					public void processMessage(Chat chat, Message message) {
						System.out.println("Received message: " + message);
					}
				});	
		return chat;
	}
	
	
	public void sendMessage(Chat chat,String message) throws NotConnectedException{
		Message newMessage = new Message();
		newMessage.setBody("Howdy!");
		chat.sendMessage(newMessage);
	}
}
