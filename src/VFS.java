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
		root = new Directory();
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
		return false;
	}
	public boolean createFolder (String str) {
		//check path to the last exists'/'
		//check the Folder after '/' doesn't exist
		return false;
	}
	public boolean deleteFile (String str) {
		//check that the file exists
		return false;
	}
	public boolean deleteFolder (String str) {
		//check that the folder exists
		return false;
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
	
	public void close () {
		//implement
	}
}
