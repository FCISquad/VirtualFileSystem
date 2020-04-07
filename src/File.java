import java.util.ArrayList;


public class File {
	public Integer indexBlock;
	public String filePath;
	public ArrayList<Integer> allocatedBlocks;
	private boolean deleted;
	public File(String path) {
		filePath = path;
		deleted = false;
	}
	public File(String path, ArrayList<Integer> fileAllocatedBlocks) {
		this.filePath = path;
		this.allocatedBlocks = fileAllocatedBlocks;
		deleted = false;
	}

	public Integer getIndexBlock() {
		return indexBlock;
	}
	public void setIndexBlock(Integer _indexBlock) {
		this.indexBlock = _indexBlock;
	}
	public String getFilePath() {
		return filePath;
	}
	public void deleteFile() {
		deleted = true;
		VFS.spaceManager(allocatedBlocks, false);
	}
	public boolean isDeleted () {
		return deleted;
	}
	public ArrayList<Integer> allocated() {
		return allocatedBlocks ;
	}
	
}
