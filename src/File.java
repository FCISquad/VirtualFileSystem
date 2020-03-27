import java.util.ArrayList;

public class File {
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
	public String getFilePath() {
		return filePath;
	}
	public void deleteFile() {
		deleted = true;
	}
}
