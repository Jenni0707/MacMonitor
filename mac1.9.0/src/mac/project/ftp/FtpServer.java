package mac.project.ftp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FtpServer
{
	public static final String rootDir = System.getProperty("user.dir") + File.separator  + "ftproot";	//default ftp directory
	private final int ftpPort = 21;
	private final String username = "anonymous";
	private final String password = "123456";

	public FtpServer() {
		start();
	}

	public void start() {
		System.out.println("My Ftp Sever Starting...");
		ServerSocket serverSocket = null;
    	try {
			serverSocket =  new ServerSocket(ftpPort, 1);
    	}
    	catch (IOException e) {
      		e.printStackTrace();
      		System.exit(1);
    	}

		System.out.println("sever started");
	
	    // Loop waiting for a request
		while (true) {
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			try {
				socket = serverSocket.accept();
				System.out.println("receive a new request");

				input = socket.getInputStream();
				output = socket.getOutputStream();
	
				// create Request object and parse
				Request request = new Request(input);
	
				// create Response object
				Response response = new Response(output);
				response.setRequest(request);
				response.setWorkingDir(rootDir);				
	
				response.sendEventMsg(Response.WELCOME);		// send welcome massage
				request.getMsg();								// get user name
				if (request.getRequestType() != Request.LOGIN_USER) {
					response.sendEventMsg(Response.ERROR_CMD);
					socket.close();
					continue;					
				}
				response.sendEventMsg(Response.GET_PSW);		// get user password
				request.getMsg();
				if (request.getRequestType() != Request.LOGIN_PASS) {
					response.sendEventMsg(Response.ERROR_CMD);
					socket.close();
					continue;					
				}
				if (!request.getUser().equals(username) || !request.getPsw().equals(password)) {	// user password uncorrect
					response.sendEventMsg(Response.PSW_ERROR);
					socket.close();
					continue;
				}
				response.sendEventMsg(Response.LOGIN_SUCC);	// login
				
				while(request.getRequestType()!=Request.LOGOUT) {
					request.getMsg();
					response.sendEventMsg(request.getRequestType());
	    		}

        		// Close the socket
        		socket.close();
      		}
      		catch (Exception e) {
        		e.printStackTrace();
        		try {
        			if (!socket.isClosed()) socket.close();
        		}catch (Exception e2) {}
        		continue;
      		}
    	}
  	}

	/*************测试主函数**************/
//	public static void main(String[] args)  {
//    	FtpServer server = new FtpServer();
//	}
}
