import java.io.File;

public class VFS {
	private int blocks;
	private int allocationTechnique;
	private File file;
	public VFS(int blocks, int allocationTechnique) throws Throwable {
		this.blocks = blocks;
		this.allocationTechnique = allocationTechnique;
		File file = new File("DiskStructure.vfs");

		if (file.exists())
			file.delete();
		
		file.createNewFile();
	}
	public VFS(File vfs) {
		this.file = vfs;
	}
	
	public void close () {
		//implement
	}
}
