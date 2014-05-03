package UserInterface;
import java.io.*;
import java.net.*;

public class Server 
{
	final static boolean DEBUG = false;
	final static int PORT = 444;
		
	public static void main(String []arg) 
	{
		try {
			Server s = new Server(); 
			s.run();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		System.out.print("Stoped!");
	}
	
	public void run( ) throws Exception
	{
		ServerSocket sSocket = new ServerSocket(PORT);
		Socket sock = sSocket.accept();
		InputStreamReader inStream = new InputStreamReader(sock.getInputStream());
		BufferedReader bReader = new BufferedReader(inStream);
		
		String message = bReader.readLine();
		System.out.println("got from client: " + message);
		
		if (message != null) {
			PrintStream ps = new PrintStream(sock.getOutputStream());
			ps.println("Message recived!");
		}
		
		sock.close();
		sSocket.close();
		
	}
}

/*
public class Server {
	final static boolean DEBUG = false;
	final static String LOG_FILE = "log.txt";
	// function that prints something awesome 
	public static void startScreen() {
		System.out.println("Welcome, human.");
	}
	public static int menu() {
		int val = 0;
		String before = null;
		System.out.println("1. Start listening");
		System.out.println("2. Config");
		System.out.println("3. Print info");
		System.out.println("4. Exit server");
		Scanner keyboard;
		try {
			keyboard  = new Scanner(System.in);
			System.out.print(": ");
			before = keyboard.nextLine();
			keyboard.nextLine();
			keyboard.close();
		} catch(InputMismatchException e) {
			System.out.println("I only accept numeric inputs!");
			e.printStackTrace();
		} catch(NoSuchElementException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			log(e.getStackTrace());
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		try {
			val = Integer.parseInt(before);
		} catch(NumberFormatException e) {
			System.out.println(e.getMessage());
		} 
			
		return val;			
	}
	
	public static void main(String[] args) {
		 startScreen();
		boolean runServer = true; 
		while(runServer) {
			// TRÅD 1
			int menuOption = menu();
							
			switch(menuOption) {
				case 1:
					System.out.println("case 1");
					// starta lyssnandet AKA starta tråden för de
					break;
				case 2: 
					System.out.println("case 2");
					// pause:a tråden för att lyssna, om den är igång
					// displaya en meny för ändringar
					// låt anv byta de han vill osv osv
					// starta upp tråden om den va igång.
					break;
				case 3:
					System.out.println("case 3");
					// visa massa information för tt de är nice gärna lite haxxor
					break;
				case 4: 
					System.out.println("case 4");
					// stäng servern
					// avsluta alla trådar
					// gör all annan avsluting skit
					// exit program.
					runServer = false;
					break;
				default: 
					// fall throw
					break;
			}
			try {
		        String os = System.getProperty("os.name");
		        if (os.contains("Windows")) {
		            Runtime.getRuntime().exec("cls");
		        } else {
		            Runtime.getRuntime().exec("clear");
		        }
		    } catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void log(StackTraceElement[] stackTraceElements) {
		PrintWriter out;
		Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    	System.out.println();
		try {
			out = new PrintWriter(LOG_FILE);
			for(StackTraceElement trace: stackTraceElements) {
				out.println( sdf.format(cal.getTime()) + " : " + trace);
			}
			out.close();
		} catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
	}
}
*/