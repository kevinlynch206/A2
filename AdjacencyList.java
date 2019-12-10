import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * Class that contains the adjacency list index and all functions associated with it.

 */
public class AdjacencyList
{
	private final HashMap<String, Integer> actorNamesMap;
	private final HashMap<Integer, String> actorIndexMap;
	private final LinkedList<Integer>[] adjList;
	
	/**
	 * Initialize constructor
	 */
	public AdjacencyList()
	{
		actorNamesMap = new HashMap<String, Integer>();
		actorIndexMap = new HashMap<Integer, String>();
		adjList = new LinkedList[54202];
		for(int i = 0; i < adjList.length; i++)
		{
			adjList[i] = new LinkedList<>();
		}
	}
	
	/**
	 * Creates edge between actor in position with v2; actors are represented by position and v2
	 * Makes sure no duplicates are added
	 * @param position
	 * @param v2
	 * @throws Exception
	 */
	public void addEdge(int position, int v2) throws Exception
	{
		if(!actorIndexMap.containsKey(position) || position < 0)
		{
			throw new Exception("Map does not contain index and/or position out of bounds");
		}
		
		if(position > adjList.length)
		{
			resize();
		}
		
		if(!adjList[position].contains(v2))
		{
			adjList[position].add(v2);
		}
			
		if(!adjList[v2].contains(position))
		{
			adjList[v2].add(position);
		}	
	}
	
	/**
	 * Adds all actors from list to certain actor position in adjacency list
	 * Removes itself so that each actor won't contain itself as a neighbor
	 * @param set
	 */
	public void addAllEdges(Set<Integer> set)
	{
		for(int index : set)
		{
			adjList[index].addAll(set);
			Iterator<Integer> it = getNeighbors(index).iterator();
			while(it.hasNext())
			{
				int temp = it.next();
				if(temp == index)
				{
					removeEdge(index, temp);
				}
			}
		}
	}
	
	/**
	 * Removes edge from adjacency list
	 * @param pos
	 * @param index
	 * @return
	 */
	public boolean removeEdge(int pos, Object index)
	{
		if(pos < 0 || pos > adjList.length)
		{
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		
		if(!adjList[pos].contains(index))
		{
			throw new NullPointerException("Does not contain index " + index);
		}
		return adjList[pos].remove(index);
	}
	
	/**
	 * Resizes array when capacity is full
	 */
	public void resize()
	{
		LinkedList<Integer>[] newArr = new LinkedList[adjList.length * 2];
		System.arraycopy(adjList, 0, newArr, 0, adjList.length);
		newArr = adjList;
	}
	
	/**
	 * Prints all the neighboring vertices from inputed position in adjaency list.
	 * @param pos
	 */
	public String printAllNeighborsAtPosition(int pos)
	{
		String result = "";
		Iterator<Integer> it = adjList[pos].iterator();
		result += "All neighboring vertices at index: " + pos + "\n";
		while(it.hasNext())
		{
			result += it.next();
			if(it.hasNext())
			{
				result +=" --> ";
			}
		}
		return result;
	}
	
	/**
	 * Returns boolean of whether there actor1 shares an edge with actor2
	 * @param actor1
	 * @param actor2
	 * @return
	 */
	public boolean containsNeighborOfIndex(int actor1, int actor2)
	{
		return adjList[actor1].contains(actor2);
	}

	/**
	 * Similar to function "containsNeighborOfIndex" , this function does the exact same thing except it takes String parameters
	 * rather than the index of the actors
	 * @param actor1
	 * @param actor2
	 * @return
	 */
	public boolean containsNeighbor(String actor1, String actor2)
	{
		return adjList[actorNamesMap.get(actor1)].contains(actorNamesMap.get(actor2));
	}
	
	/**
	 * Prints to console the entire adjacency list
	 */
	public void printAdjacencyList()
	{
		for(int i = 0; i < actorIndexMap.size(); i++)
		{
			System.out.println("Index " + i + ": ");
			Iterator<Integer> it = adjList[i].iterator();
			while(it.hasNext())
			{
				System.out.print(it.next());
				if(it.hasNext())
				{
					System.out.print(" --> ");
				}
			}
			System.out.println();
		}
	}

	/**
	 * Method that gets all the neighboring vertices from position 'v' of the adjacency list
	 * @param v
	 * @return
	 */
	public LinkedList<Integer> getNeighbors(int v)
	{
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		Iterator<Integer> it = adjList[v].iterator();
		while(it.hasNext())
		{
			linkedList.add(it.next());
		}
		return linkedList;
	}
		
	/**
	 * Map method that puts actor name as key and index as value
	 * @param name
	 * @param position
	 */
	public void addToNamesMap(String name, int position)
	{
		actorNamesMap.put(name, position);
	}
	
	/**
	 * Map method that puts actor index
	 * @param position
	 * @param name
	 */
	public void addToIndexMap(int position, String name)
	{
		actorIndexMap.put(position, name);
	}
	
	/**
	 * Gets index of the actor
	 * @param name
	 * @return
	 */
	public int getIndex(String name)
	{
		if(!actorNamesMap.containsKey(name))
		{
			throw new NullPointerException(name + " not found in index.");
		}
		return actorNamesMap.get(name);
	}
	
	/**
	 * Gets actor name given the index
	 * @param position
	 * @return
	 */
	public String getName(int position)
	{
		if(!actorIndexMap.containsKey(position))
		{
			throw new NullPointerException(position + " not found.");
		}
		return actorIndexMap.get(position);
	}
	
	/**
	 * Map fuction that checks to see if map contains String "name" in the key set
	 * @param name
	 * @return
	 */
	public boolean containsActor(String name)
	{
		return actorNamesMap.containsKey(name);
	}
	
	/**
	 * Map function that checks to see if the map contains int "index" in the key set
	 * @param index
	 * @return
	 */
	public boolean containsIndex(int index)
	{
		return actorIndexMap.containsKey(index);
	}
	
	/**
	 * Returns string of all actors
	 * @return
	 */
	public String printAllActors()
	{
		String result = "";
		for(String key : actorNamesMap.keySet())
		{
			result = result + key + "\n";
		}
		return result;
	}

	/**
	 * Returns number of elements in the adjacency list (map size represent this)
	 * @return
	 */
	public int numOfElements()
	{
		return actorNamesMap.size();
	}
	
	/**
	 * Returns total size or length of adjacency list
	 * @return
	 */
	public int sizeOfList()
	{
		return adjList.length;
	}
}
