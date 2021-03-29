package listeners;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.nio.file.Files;

public class SendMail {

	private static String TO1;
	private static final String FROM = "seleniumautomation1399@gmail.com";
	private static final String USERNAME = "seleniumautomation1399@gmail.com";
	private static final String PASSWORD = "Testuser_123";
	//private static final String HOST = "mail.smtp.host";
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String SUBJECT = "Automation Script Execution";


	public void send_mail(String f_name, String email) throws IOException {
		TO1=email;
		Properties properties = System.getProperties();
		//properties.setProperty("mail.smtp.host", HOST);
		//properties.put("mail.smtp.starttls.enable", "true");  
		properties.put("mail.smtp.host", "smtp.gmail.com");  
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(properties, 
				new javax.mail.Authenticator() { 

			//override the getPasswordAuthentication method 
			protected PasswordAuthentication 
			getPasswordAuthentication() { 

				return new PasswordAuthentication(USERNAME, 
						PASSWORD); 
			} 
		});         

		//creating zip file for screenshots
		File ss = new File(f_name+".zip");
		File root = new File(System.getProperty("user.dir")+"//target");
		FilenameFilter beginswith = new FilenameFilter()
		{
			public boolean accept(File directory, String filename) {
				return filename.startsWith("screenshotof"+f_name);
			}
		};

		boolean ss_present=true;
		File[] files = root.listFiles(beginswith);

		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(ss));
		if(files.length==0)
			ss_present=false;

		// Create the ZIP file first
		try (ZipOutputStream out = new ZipOutputStream(bos)) {
			// Write files/copy to archive
			for (File file : files) {
				// Put a new ZIP entry to output stream for every file
				out.putNextEntry(new ZipEntry(file.getName()));
				Files.copy(file.toPath(), out);
				out.closeEntry();
			}
		}

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(FROM));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO1));
			//message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO2));
			//message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO3));
			message.setHeader("Content-Type", CONTENT_TYPE);


			// Set Subject: header field
			message.setSubject(f_name+" "+SUBJECT);

			// Create a multipart message
			Multipart multipart = new MimeMultipart();

			//attaching custom report
			String filename = System.getProperty("user.dir")+"//test-output//"+f_name+"_CustomReport.html";
			DataSource reportsource = new FileDataSource(filename);
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setDataHandler(new DataHandler(reportsource));
			messageBodyPart1.setFileName(filename);
			multipart.addBodyPart(messageBodyPart1);

			String body;

			//attaching screenshot zip
			if(ss_present)
			{
				String zipfile = System.getProperty("user.dir")+"//"+f_name+".zip";
				DataSource zipsource = new FileDataSource(zipfile);
				BodyPart messageBodyPart2 = new MimeBodyPart();
				messageBodyPart2.setDataHandler(new DataHandler(zipsource));
				messageBodyPart2.setFileName(zipfile);
				multipart.addBodyPart(messageBodyPart2);
				// Add body part
				body="PFA: Report and ZIP for error screenshots. <br/> Please refer error screenshots for the failed tests.";
			}

			else
			{
				// Add body part
				body="Please find attached report for the passed, skipped and failed tests.";
			}

			StringBuilder contentBuilder = new StringBuilder();
			try {
				BufferedReader in = new BufferedReader(new FileReader(System.getProperty("user.dir")+"//test-output//"+f_name+"_CustomReport.html"));
				String str;

				while ((str = in.readLine()) != null) {
					contentBuilder.append(str);
				}
				in.close();
			} 
			catch (IOException e) {
				System.out.println(e);
			}
			String content = contentBuilder.toString();

			//Creates html message
			MimeBodyPart bodyHtml = new MimeBodyPart();
			bodyHtml.setContent(body.concat("<br/><br/>").concat(content),  "text/html");

			// Set text message part
			multipart.addBodyPart(bodyHtml);

			// Send the complete message parts
			message.setContent(multipart);
			message.saveChanges();
			
			// Send message with authentication!
			/*Transport tr = session.getTransport("smtp");
			tr.connect(HOST, USERNAME, PASSWORD);
			tr.sendMessage(message, message.getAllRecipients());
			tr.close();*/

			Transport.send(message);


		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}


