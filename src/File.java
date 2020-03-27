import java.util.ArrayList;

public class File {
	private Integer indexBlock;
	private String filePath;
	private ArrayList<Integer> allocatedBlocks;
	private boolean deleted;
	public File(String path) {
		filePath = path;
		deleted = false;
	}
	public File(String path, ArrayList<Integer> fileAllocatedBlocks) {
		// TODO Auto-generated constructor stub
	}
	public File(String _path, Integer _indexBlock) {
		this.filePath = _path;
		this.indexBlock = _indexBlock;
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
	}
}
