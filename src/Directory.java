import java.util.ArrayList;

public class Directory {
	private String directoryPath;
	private ArrayList<File> files;
	private ArrayList<Directory> subDirectories;
	private boolean deleted;
	
	public Directory(String directoryPath) {
		this.directoryPath = directoryPath;
		subDirectories = new ArrayList<Directory>();
		files = new ArrayList<File>();
		deleted = false;
	}
	public void addFile(File file) {
		files.add(file);
	}
	public void addDirectory(Directory folder) {
		subDirectories.add(folder);
	}
	// ...
	Directory findDirectory (String path, int c) {
		//path = root/folder1/folder2/folder3
		// 		root/folder3
		//		root/folder4 
		//	************** Optimization
		if (path.equals(directoryPath))
			return this;
		int counter = 0;
		int index = -1;
		for (int i=0 ; i<path.length() ; i++)
			if (path.charAt(i) == '/'){
				counter ++ ;
				if (counter == c){
					index = i;
					break;
				}
			}
		if (index == -1 && counter == c-1)
			index = path.length();
		String newPath = path.substring(0, index);
	
		for (int i=0 ; i<subDirectories.size() ; i++) {
			if (subDirectories.get(i).directoryPath.equals(newPath) && subDirectories.get(i).deleted == false)
				return subDirectories.get(i).findDirectory(path, ++c);
		}
		return null;
	}
	
	void deleteFolder() {
		for (int i=0 ; i<subDirectories.size(); i++)
			subDirectories.get(i).deleteFolder();
		for (int i=0; i<files.size(); i++)
			files.get(i).deleteFile();
		deleted = true;
	}
	File findFile (String path) {
		//path = root/folder1/folder2/file3.txt
		// 		root/folder3
		//		root/folder4 
		//	************** Optimization
		
		int index = path.lastIndexOf('/');
		String newPath = path.substring(0,index);
		Directory dir = findDirectory(newPath, 2);

		if (dir == null || dir.deleted == true) return null;
		for (int i=0 ; i<dir.files.size() ; i++)
			if (dir.files.get(i).getFilePath().equals(path))
				return dir.files.get(i);
		return null;
	}
	public void printDirectoryStructure(int level) {
		
	}
	
}
