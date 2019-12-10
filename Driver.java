import java.io.File; 
import java.io.IOException;
import java.util.Scanner;

/**
 * Driver class that runs the program with try-catch for error checking
 */
public class Driver 
{
	public static void main(String args[])
	{
		File file;
		String filename = args[0];
		AdjacencyList list = new AdjacencyList();
		IndexBuilder builder = new IndexBuilder();
		BFS bfs = new BFS();
		Scanner scan = new Scanner(System.in);
		
		//trys to populate g
		try
		{
			file = new File(filename);
			builder.traverseFile(file, list);
		}
		
		//catches file not found
		catch (IOException e) 
		{
			System.out.println("File '" + filename + ".csv' does not exist");
			System.exit(1);
		}


//		System.out.println(list.printAllNeighborsAtPosition(list.getIndex("kevin bacon")));
		
		//main logic
		boolean actor1Boolean = true;
		boolean actor2Boolean = true;
		String actor1 = "";
		String actor2 = "";
		while(actor1Boolean == true)
		{
			System.out.println("Actor 1 name: ");
			actor1 = scan.nextLine().toLowerCase();
			if(list.containsActor(actor1))
			{
				actor1Boolean = false;
			}
			else
			{
				System.out.println("No such actor.");
			}
		}
		while(actor2Boolean == true)
		{
			System.out.println("Actor 2 name: ");
			actor2 = scan.nextLine().toLowerCase();
			if(actor2.equals(actor1))
			{
				System.out.println("Same actor, please input a different name.");
			}
			else
			{
				if(list.containsActor(actor2))
				{
					actor2Boolean = false;
				}
				else
				{
					System.out.println("No such actor.");
				}
			}
		}
		bfs.search(list, actor1, actor2);
		scan.close();
	}
}