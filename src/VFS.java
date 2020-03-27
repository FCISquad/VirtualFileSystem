import java.util.ArrayList;

public class VFS {
	private int blocks;
	private int allocationTechnique;
	private java.io.File file;
	private Directory root;
	ArrayList<Integer> SystemBlocks;
	
	public VFS(int blocks, int allocationTechnique) throws Throwable {
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
	public boolean createFile (String str, int size) {
		//check path to the last exists'/'
		//check the file after '/' doesn't exist
		//check that size fits in the space.
		// root/folder/file
		//Call for root.find(split); that returns true or false;
		
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
	}*/
	public void close () {
		//implement
	}
}
