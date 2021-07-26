package kr.tracom.bms.ftp;


import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SftpManager {

	
    public static ChannelSftp getSftpChannel(String host, int port, String userName, String password) {

    	JSch jsch = new JSch();
    	Channel channel = null;
    	ChannelSftp channelSftp = null;
    	
    	try {
			Session session = jsch.getSession(userName, host, port);
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
		} catch (JSchException e) {
			e.printStackTrace();
		}
    	
    	return channelSftp;
    }
    
	
}
