import java.util.ArrayList;

public class File {
	private String filePath;
	private ArrayList<Integer> allocatedBlocks;
	private boolean deleted;
	public File(String path) {
		filePath = path;
	}
	public String getFilePath() {
		return filePath;
	}
}
