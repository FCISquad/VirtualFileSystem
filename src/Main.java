import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Throwable {
		int choice = 0 ;
		System.out.println("Choose: \n 1- Create New VFS File \n 2- Load existing VFS File \\n");
		Scanner in = new Scanner(System.in);
		VFS v ;
		while (true) {
			choice = in.nextInt();
			if (choice == 1) {
				System.out.println("Enter number of blocks: ");
				int blocks = in.nextInt();
				System.out.println("Enter allocation technique: \n 1- Contiguous \n 2- Indexed \n");
				choice = in.nextInt();
				v = new VFS(blocks, choice);
				break;
			} else if (choice == 2) {
			
				break;
			}
			else {
				System.out.println("Enter a valid input");
			}
			
		}
		in.close();
	}

}
