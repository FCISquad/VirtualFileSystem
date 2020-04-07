
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Throwable {
		int choice = 0 ;
		
		System.out.println("Choose: \n 1- Create New VFS File \n 2- Load existing VFS File");
		Scanner in = new Scanner(System.in);
		VFS v ;
		   while (true) {
			choice = in.nextInt();
			if (choice == 1) {
				System.out.print("Enter number of blocks: ");
				int blocks = in.nextInt();
				System.out.println("Enter allocation technique: \n 1- Contiguous \n 2- Indexed");
				choice = in.nextInt();
				v = new VFS(blocks, choice);
				break;
			} else if (choice == 2) {
				//changing print stream
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PrintStream ps = new PrintStream(baos);
				PrintStream old = System.out;
				System.setOut(ps);
				v = new VFS();
				v.read();
				//returning to old stream
				System.out.flush();
				System.setOut(old);
				break;
			}
			else {
				System.out.println("Enter a valid input");
			}	
		 }
	   
		String command;
		in.nextLine();
		while(true) {
			System.out.print("\nuser>>");
			command = in.nextLine();
			if (command.equalsIgnoreCase("Exit")){
				v.close();
				break;
			}
			String [] split = command.split(" ");
			if (split[0].equalsIgnoreCase("CreateFile")) {
				v.createFile(split[1], Integer.parseInt(split[2]));
				
			} else if (split[0].equalsIgnoreCase("CreateFolder")) {
				
				v.createFolder(split[1]);
				
			} else if (split[0].equalsIgnoreCase("DeleteFile")) {
				
				v.deleteFile(split[1]);
				
			} else if (split[0].equalsIgnoreCase("DeleteFolder")) {
				
				v.deleteFolder(split[1]);
				
			} else if (split[0].equalsIgnoreCase("DisplayDiskStatus")) {
				
				v.DisplayDiskStatus();
				
			} else if (split[0].equalsIgnoreCase("DisplayDiskStructure")) {
				
				v.DisplayDiskStructure(); 
				
			} else {
				System.out.print("Command Not Valid");
			}
			
		}
		in.close();
	}

}
