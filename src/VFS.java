import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;

public class VFS {
	private User currentUser;
	private static ArrayList<User> users;
	private int blocks; // to add to file 
	private int allocationTechnique; // to add to file 
	private Directory root;
	private static ArrayList<Integer> SystemBlocks; // to add to file (root+files)
		
	
	public VFS()
	{
		users = new ArrayList<User>();
		
		root = new Directory("root");	
		SystemBlocks = new ArrayList<Integer>(blocks);
		
		for (int i = 0; i < blocks; i++) 
			  SystemBlocks.add(0);
	}
	public VFS(int blocks, int allocationTechnique) throws Throwable {
		users = new ArrayList<User>();
		
		User adminUser = new User("admin" , "admin");
		users.add(adminUser);
		
		this.blocks = blocks;
		this.allocationTechnique = allocationTechnique;
		root = new Directory("root");
		SystemBlocks = new ArrayList<Integer>(blocks);
		
		for (int i = 0; i < blocks; i++) 
			  SystemBlocks.add(0);
	}
	public static void main (String [] args) throws Throwable {
		VFS vfs = new VFS(50, 1);
		vfs.DisplayDiskStatus();
	}

	public static boolean spaceManager(ArrayList<Integer> blocks , boolean allocate) {
		if (allocate) {
			for (int i=0 ; i<blocks.size() ; i++)
				SystemBlocks.set(blocks.get(i), 1);
		} else {
			for (int i=0 ; i<blocks.size() ; i++)
				SystemBlocks.set(blocks.get(i), 0);			
		}
		return true;
	}

	public boolean createFile (String path, int size) {
		//check path to the last exists'/'
		//check the file after '/' doesn't exist
		//check that size fits in the space.
		// root/folder/file
		//Call for root.find(split); that returns true or false;
		//for (int i = 20 ; i<40 ; i++)
			//SystemBlocks.set(i, 1);
		int index = path.lastIndexOf('/');
		String newPath = path.substring(0, index);
		Directory dir = root.findDirectory(newPath, 2);
		if (dir == null) {
			System.out.println("Error! Path is not correct. No such path found.");
			return false;
		}
		File f = root.findFile(path);
		if (f != null) {
			System.out.println("File already Exists!");
			return false;
		}
		if (allocationTechnique == 1) {
			int free = blocks+1, base = -1, loop = 0; //01010111111111111101
			for (int i=0 ; i<blocks; i++){
				if (SystemBlocks.get(i) == 0)
					loop++;
				else {
					if (loop < free && loop >= size){
						free = loop;
						base = i-loop;
					}
					loop = 0;
				}
			}
			if (loop < free && loop >= size){
				free = loop;
				base = blocks-loop;
			}
			if (free >= size && free <= blocks) {
				ArrayList<Integer> fileAllocatedBlocks = new ArrayList<Integer>();
				for (int i=base ; i<base+size ; i++) {
					fileAllocatedBlocks.add(i);
				}
				dir.addFile(new File(path, fileAllocatedBlocks));
				spaceManager(fileAllocatedBlocks, true);
				System.out.println( "File created Successfully" );
				return true;
			}
			else {
				System.out.println("There is not enough memory!");
			}
		}
		else {
			ArrayList<Integer> fileAllocatedBlocks = new ArrayList<Integer>();
			for ( int i = 0 ,j = 0; i < SystemBlocks.size() && j < size; i++ ) {
				if ( SystemBlocks.get(i) == 0 ) {
					fileAllocatedBlocks.add(i);
					j++;
				}
			}
			
			if ( fileAllocatedBlocks.size() == size ) {
				spaceManager(fileAllocatedBlocks, true);
				dir.addFile(new File(path,fileAllocatedBlocks));
				
				System.out.println( "File created Successfully" );
				return true;
			}
			else {
				System.out.println("There is not enough memory!");
			}
		}
		return false;
	}
	public boolean createFolder (String path) {
		Directory dir = root.findDirectory(path, 2);
		if (dir != null) {
			System.out.println("Error! Directory already exists!");
			return false;
		}
		int index = path.lastIndexOf('/');
		String newPath = path.substring(0,index); //check the directory that folder to be created in exists
		dir = root.findDirectory(newPath, 2);
		if (dir == null) {
			System.out.println("Error! Path is not correct. No such path found.");
			return false;
		}
		dir.addDirectory(new Directory(path));
		System.out.println("Directory Created Successfully!");
		return true;
	}
	public boolean deleteFile (String path) {
		File f = root.findFile(path);
		//check that the file exists
		if (f == null) {
			System.out.println("Error! File does not exist!");
			return false;
		}
		f.deleteFile();
		System.out.println("File Deleted Successfully!");
		return true;
	}
	public boolean deleteFolder (String path) {
		Directory dir = root.findDirectory(path, 2);
		if (dir == null) {
			System.out.println("Error! Folder does not exists!");
			return false;
		}
		dir.deleteFolder();
		System.out.println("Folder deleted successfully!");
		return true;
	}
	public boolean DisplayDiskStatus( ) {
		int freeSpace = 0;
		String free = "" ;
		String allocated = "";
		for (int i=0 ; i<SystemBlocks.size(); i++)
			if (SystemBlocks.get(i) == 0){
				free += " " + i;
				freeSpace++;
			}
			else {
				allocated += " " + i ;
			}
		System.out.println("Disk Status: ");
		System.out.println("1- Empty Space: " + freeSpace);
		System.out.println("2- Allocated Space: " + (blocks-freeSpace));
		System.out.println("3- Empty Blocks:" + free);
		System.out.println("4- Allocated Blocks:"+ allocated);
		
		return true;
	}
	public boolean DisplayDiskStructure( ) throws IOException {
		 root.printDirectoryStructure(0);
		  return true;
		
	}
	public void read() throws IOException
	{	
		  String st;
          FileReader fr = new FileReader("DiskStructure.vfs"); 
          BufferedReader br = new BufferedReader(fr);
          this.allocationTechnique = Integer.parseInt( br.readLine() );

          this.blocks = Integer.parseInt(br.readLine()) ;
          String[] arrOfStr = br.readLine().split(" ");
          for(int i = 0 ; i < arrOfStr.length;i++ )
          {	  
        	  SystemBlocks.add(Integer.parseInt(arrOfStr[i]));
          }
          while ((st = br.readLine()) != "End") 
          {	  
        	  arrOfStr= st.split(" ");
              if (arrOfStr.length > 2)        
              {	  
          		int index = arrOfStr[0].lastIndexOf('/');
        		String newPath = arrOfStr[0].substring(0, index);
        		Directory dir = root.findDirectory(newPath, 2);
				ArrayList<Integer> fileAllocatedBlocks = new ArrayList<Integer>();
        		for(int j = 2 ; j < Integer.parseInt(arrOfStr[1])+2;j++)
        		{	
        			fileAllocatedBlocks.add(Integer.parseInt(arrOfStr[j]));
        		}
				dir.addFile(new File(arrOfStr[0], fileAllocatedBlocks));
				spaceManager(fileAllocatedBlocks, true);
        		
              }
              else
              {    if(st.contentEquals( "End")) 
            	       break ;           	       
            	   else 
            		   this.createFolder(arrOfStr[0]);  
              }	 
          }
          br.close();
	 }
	
	/*public void test() {
		Directory dir1 = new Directory("root/folder1");
		Directory dir2 = new Directory("root/folder1/folder2");
		Directory dir3 = new Directory("root/folder2");
		Directory dir4 = new Directory("root/folder2/hussien");
		dir1.subDirectories.add(dir2);
		dir3.subDirectories.add(dir4);
		root.subDirectories.add(dir1);
		root.subDirectories.add(dir3);
		Directory temp = root.findDirectory("root", 2);
		System.out.println(temp.directoryPath);
	}
	public void testFile() { 
		Directory dir0 = new Directory("root/folder1");
		File f = new File("root/folder1/khaled.txt");
		File f2 = new File("root/folder2/temp.txt");
		File f3 = new File("root/ehap.txt");
		root.files.add(f3);
		Directory dir2 = new Directory("root/folder1/folder2");
		Directory dir3 = new Directory("root/folder2");
		Directory dir4 = new Directory("root/folder2/hussien");
		dir0.files.add(f);
		dir3.files.add(f2);
		dir0.subDirectories.add(dir2);
		dir3.subDirectories.add(dir4);
		root.subDirectories.add(dir0);
		root.subDirectories.add(dir3);
		File temp = root.findFile("root/folder1/khaled.txt");
		System.out.println(temp.getFilePath());
		temp = root.findFile("root/folder2/temp.txt");
		System.out.println(temp.getFilePath());
		DisplayDiskStructure();
	}*/
	public void close () throws IOException {
		  FileWriter writer = new FileWriter("DiskStructure.vfs");  
	      BufferedWriter buffer = new BufferedWriter(writer);  	
	      
	      buffer.write(Integer.toString(allocationTechnique));
	      buffer.newLine();
		  buffer.write(Integer.toString(blocks));
	      buffer.newLine();

		  for(int i = 0;i< blocks ; i++)
		  {	  
			  buffer.write(Integer.toString(SystemBlocks.get(i))+" ");
		  }
		  buffer.newLine();
		  root.writeFilesStructure(buffer);
		  buffer.write("End");
		  buffer.newLine();
		  root.writeDirectoryStructure(0, buffer);
		  buffer.close();
	}
	
	public void createDir(String path) {
		int index = path.lastIndexOf('/');
		
		String subPath = "root"; 
		if ( index != -1 ) {
			subPath = path.substring(0, index);
		}
		
		Directory dir1 = root.findDirectory(subPath, 2);
		String cap = dir1.getCap( currentUser.getUserName() );
		if ( currentUser.getUserName().equalsIgnoreCase("admin") || cap.charAt(0) == '1') {
			createFolder(path);
			Directory dir = root.findDirectory(path, 2);
			dir.addUser(currentUser.getUserName(), "11");
			currentUser.add(path);
		}
		else {
			System.out.println("Access Denied");
		}
	}
	
	
	public static void deleteFromUser(HashMap<String, String> hp , String path) {
		for (java.util.Map.Entry<String, String> entry : hp.entrySet()) {
		    for ( int i = 0; i < users.size(); ++i ) {
		    	if ( users.get(i).getUserName().equalsIgnoreCase(entry.getKey()) ) {
		    		users.get(i).delete(entry.getValue());
		    		break;
		    	}
		    }
		}
	}
	public void deleteDir( String path ) {		
		Directory dir = root.findDirectory(path, 2);
		String cap = dir.getCap( currentUser.getUserName() );
		if ( currentUser.getUserName().equalsIgnoreCase("admin") || cap.charAt(1) == '1') {			
			deleteFolder(path);
		}
		else {
			System.out.println("Access Denied");
		}
	}
	
	public void tellUser() {
		System.out.println(currentUser.getUserName());
	}
	public void createUser( String userName , String password ) {
		if ( currentUser.getUserName().equalsIgnoreCase("admin") ) {
			
			boolean found = false;
			for ( int i = 0; i < users.size(); i++ ) {
				if ( users.get(i).getUserName().equalsIgnoreCase(userName) ) {
					found = true;
					break;
				}
			}
			
			if (found == true) {
				System.out.println( "ERROR: Username already exists" );
			}
			else {
				User newUser = new User(userName , password);
				users.add(newUser);
			}
		}
		else {
			System.out.println( "ERROR: Current user is not Admin" );
		}
	}
	
	public void login( String userName , String password ) {
		for ( int i = 0; i < users.size(); i++ ) {
			if ( users.get(i).login(userName, password)) {
				currentUser = users.get(i);
				System.out.println( "Logged in successfully" );
				return ;
			}
		}
		System.out.println( "ERROR: Login faild" );
	}
	
	public void logout() {
		currentUser = null;
		System.out.println( "User logged out." );
	}
	
	public void deleteUser( String userName ) {
		Directory dir = null;
		if ( currentUser.getUserName().equalsIgnoreCase("admin") ) {
			for ( int i = 0; i < users.size(); ++i ) {
				if ( users.get(i).getUserName().equalsIgnoreCase(userName) ) {
					ArrayList<String> listOfDir = users.get(i).getDirectories();
					
					for ( int j = 0; j < listOfDir.size(); ++j ) {
						dir = root.findDirectory(listOfDir.get(j), 2);
						dir.deleteUser(userName);
					}
					
					users.remove(i);
					return;
				}
			}	
			System.out.println("There is no such user with this name");
		}
		else {
			System.out.println( "ERROR: Current user is not Admin" );
		}
	}
	
	public void grant(String userName , String path , String cap) {
		if ( currentUser.getUserName().equalsIgnoreCase("admin") ) {
			for ( int i = 0; i < users.size(); ++i ) {
				if ( users.get(i).getUserName().equalsIgnoreCase(userName) ){
					
					Directory dir = root.findDirectory(path, 2);
					if ( dir == null ) {
						System.out.println( "No such directory" );
					}
					else {
						dir.addUser(userName, cap);
						System.out.println( "User granted successfully" );
					}
					return;
				}
			}
			System.out.println("There is no such user with this name");
		}
		else {
			System.out.println( "ERROR: Current user is not Admin" );
		}
	}
}
