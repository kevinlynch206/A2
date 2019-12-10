import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public class BFS
{


	public void search(AdjacencyList adjList, String actor1, String actor2)
	{

		int indexOfActor1 = adjList.getIndex(actor1);
		int indexOfactor2 = adjList.getIndex(actor2);

		boolean[] checked = new boolean[adjList.numOfElements()];


		String nameOfActor1 = adjList.getName(indexOfActor1);
		String nameOfActor2 = adjList.getName(indexOfactor2);
		Queue<Integer> queue = new LinkedList<Integer>();
		Map<Integer, Integer> path = new HashMap<>();

		if(adjList.containsNeighborOfIndex(indexOfActor1, indexOfactor2)) //check if both actors are in the same movie
		{
			printPathSimple(adjList, nameOfActor1, nameOfActor2);
			return;
		}

		queue.add(indexOfActor1);
		checked[indexOfActor1] = true;
		while(!queue.isEmpty())
		{
			int index = queue.poll();
			Iterator<Integer> it = adjList.getNeighbors(index).iterator(); //get all the neighboring vertices from the value dequeued
			while(it.hasNext())
			{
				int current = it.next();
				if(!checked[current])
				{
					checked[current] = true;
					queue.add(current);
					path.put(current, index); //stores the "neighbor" index with the index that was dequeued. This is to keep track of the path
				}

				if (current == indexOfactor2){
					printPathComplicated(path, current, adjList, nameOfActor1, nameOfActor2);
					return;
				}
			}
		}
		System.out.println("path Not found");
	}

	public void printPathSimple(AdjacencyList adjList, String nameOfActor1, String nameOfActor2){

		System.out.println("path between " + nameOfActor1 + " and " + nameOfActor2 + ": ");
		System.out.println(nameOfActor1 + " --> " + nameOfActor2);

	}

	public void printPathComplicated(Map<Integer, Integer> path, int current, AdjacencyList adjList, String nameOfActor1, String nameOfActor2){

		String output = "";
		int x = current;
		while(path.get(x) != null) //backtracks through map until it reaches the root actor that was previously dequeued
		{
			output = " --> " + adjList.getName(x) + output;
			x = path.get(x);
		}
		System.out.println("Path between " + nameOfActor1 + " and " + nameOfActor2 + ": ");
		System.out.println(nameOfActor1 + output);


	}

}
