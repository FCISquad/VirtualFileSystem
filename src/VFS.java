import java.util.ArrayList;
import java.util.HashMap;

public class VFS {
	private int blocks;
	private int allocationTechnique;
	private java.io.File file;
	private Directory root;
	private static ArrayList<Integer> SystemBlocks;
	
	
	private Integer indexBlockCounter;
	private HashMap<Integer, ArrayList<Integer>> indexBlockToBlocks;
	
	public VFS(int blocks, int allocationTechnique) throws Throwable {
		this.indexBlockCounter = 0;
		indexBlockToBlocks = new HashMap<Integer, ArrayList<Integer>>();
		
		
		this.blocks = blocks;
		this.allocationTechnique = allocationTechnique;
		root = new Directory("root");
		SystemBlocks = new ArrayList<Integer>(blocks);
		
		for (int i = 0; i < blocks; i++) 
			  SystemBlocks.add(0);
		
		java.io.File file = new java.io.File("DiskStructure.vfs");

		if (file.exists())
			file.delete();
		
		file.createNewFile();
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
				return true;
			}
			else {
				System.out.println("There is not enough memory!");
			}
		}
		else {
			ArrayList<Integer> fileAllocatedBlocks = new ArrayList<Integer>();
			for ( int i = 0 ,j  = 0; i < SystemBlocks.size() && j < size; i++ ) {
				if ( SystemBlocks.get(i) == 0 ) {
					fileAllocatedBlocks.add(i);
					SystemBlocks.set(i, 1);
					j++;
				}
			}
			
			if ( fileAllocatedBlocks.size() == size ) {
				dir.addFile(new File(path, indexBlockCounter));
				indexBlockToBlocks.put(indexBlockCounter, fileAllocatedBlocks);
				indexBlockCounter++;
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
	public boolean DisplayDiskStructure( ) {
		root.printDirectoryStructure(0);
		return true;
	}
	public VFS(java.io.File vfs) {
		this.file = vfs;
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
	public void close () {
		//implement
	}
}
