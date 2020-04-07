import java.io.BufferedWriter;
import java.io.IOException;
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
		String s = "";
		for (int i=0; i < level; i++) {
			s += "     ";
		}
		if (this.deleted == true)
			return;
		s += "* ";
		int index = directoryPath.lastIndexOf('/');
		String newPath;
		if (index != -1)
			newPath = directoryPath.substring(index+1,directoryPath.length());
		else
			newPath = directoryPath;
		s += newPath;
		System.out.println(s);
		s = "";
		String temp = "";
		
		for (int i=0; i < files.size(); i++) {
			if (files.get(i).isDeleted())
				continue;
			for (int j=0; j < level+1; j++) {
				s += "     ";
			}
			s+= "* ";
			temp = files.get(i).getFilePath();
			index = temp.lastIndexOf('/');
			newPath = temp.substring(index+1,temp.length());
			s += newPath;
			System.out.println(s);
			s = "";
		}
		for (int i=0; i< subDirectories.size(); i++) {
			subDirectories.get(i).printDirectoryStructure(level+1);
		}
		
	}
	public void writeDirectoryStructure(int level, BufferedWriter buffer) throws IOException {

		String s = "";
		for (int i=0; i < level; i++) {
			s += "     ";
		}
		if (this.deleted == true)
			return;
		s += "* ";
		int index = directoryPath.lastIndexOf('/');
		String newPath;
		if (index != -1)
			newPath = directoryPath.substring(index+1,directoryPath.length());
		else
			newPath = directoryPath;
		s += newPath;
	    buffer.write(s);
	    buffer.newLine();

		s = "";
		String temp = "";
		
		for (int i=0; i < files.size(); i++) {
			if (files.get(i).isDeleted())
				continue;
			for (int j=0; j < level+1; j++) {
				s += "     ";
			}
			s+= "* ";
			temp = files.get(i).getFilePath();
			index = temp.lastIndexOf('/');
			newPath = temp.substring(index+1,temp.length());
			s += newPath;
			buffer.write(s);
			buffer.newLine();
			s = "";
		}
		for (int i=0; i< subDirectories.size(); i++) {
			subDirectories.get(i).writeDirectoryStructure(level+1,buffer);
		}
		
	}
	public void writeFilesStructure(BufferedWriter buffer) throws IOException
	{	
       for (int i = 0 ; i<subDirectories.size();i++)
       {	
    	if (!subDirectories.get(i).deleted) {   
	    buffer.write(subDirectories.get(i).directoryPath+ " ");
	    buffer.newLine();
	    }
      }   

	   for (int i=0; i< subDirectories.size(); i++) {
		  subDirectories.get(i).writeFilesStructure( buffer);
	   }
	for (int i = 0 ; i < files.size();i++)
	    {	
		  if(!files.get(i).isDeleted())
		  {		  
             ArrayList<Integer> arr = new ArrayList<Integer>();
              arr = files.get(i).allocated() ;
              buffer.write(files.get(i).getFilePath()+ " ");
              buffer.write(Integer.toString(arr.size())+ " ");
              for(int j = 0 ; j < arr.size(); j++)
              {	  
	            buffer.write(Integer.toString(arr.get(j))+ " "); 
              }
	  
              buffer.newLine();
          }	
 
	    }
	
    }
	public int sizeOfRoot(BufferedWriter buffer, int s) throws IOException
	{
		s += files.size() ;		
		s += subDirectories.size() ;
		for (int i=0; i< subDirectories.size(); i++) {
			s+= subDirectories.get(i).files.size();
			s+=subDirectories.get(i).subDirectories.size() ;
		}
		return s ;	
	}

	
	
	
	
}
