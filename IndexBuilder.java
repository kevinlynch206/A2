import java.io.BufferedReader; 
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

/**
 * IndexBuilder class that reads file, parses content with JSONParser, and stores content into data structure.
 */
public class IndexBuilder 
{	
	/**
	 * File to traverse
	 */
	public void traverseFile(File file, AdjacencyList index) throws IOException
	{
		traverse(file, index);
	}
	
	/**
	 * Traverses the file, parses content, and adds name into an adjacency list
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws ParseException 
	 */
	private void traverse(File file, AdjacencyList index) throws IOException
	{
		JSONParser parser = new JSONParser();
		JSONArray jsonArr;
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String temp;
		Set<Integer> set = new HashSet<Integer>();
		int position = 0; //incrementer that assigns actor with value
		try 
		{
			reader.readLine(); //skips first line of file (categories)
			while ((temp = reader.readLine()) != null)
			{
				if(temp.contains("cast_id")) //only reads lines that contain a "cast" section since not every movie has "cast"
				{
					//excludes movie ID, movie, and "crew" portion of the line in file; this is to make it compatible with JSON parsing
					temp = temp.substring(temp.indexOf("\"[")+1, temp.lastIndexOf("[")-2).replaceAll("\"\"", "\"");
					try
					{
						jsonArr = (JSONArray)parser.parse(temp);
						for(int i = 0; i < jsonArr.size(); i++)
						{
							temp = (String)((JSONObject)jsonArr.get(i)).get("name");

							temp = temp.toLowerCase();

							if(!index.containsActor(temp))
							{
								//only adds unique actors, and adds both actor and position value to the maps respectively
								index.addToNamesMap(temp, position);
								index.addToIndexMap(position, temp);
								position++;
							}
							
							set.add(index.getIndex(temp)); //adds all the actors of a single movie into a set
						}
						index.addAllEdges(set);
						set.clear(); //clears contents so it could be used for next line in file
					}
					catch (ParseException e)
					{
						System.out.println(temp);
					}
				}
			}
		}
		finally 
		{
			reader.close();
		}
	}
}