import java.io.*;
import java.util.HashMap;
import java.util.Map;
//Class that contains all markov chain + graph functions
//that work on FF data files
public class MakeGraph{
	/* Set where the file is */
	static String where;


	/*
	 * Generates the HashMap of HashMaps that tell the probability of one bin to the next
	 */
	public void collectiveGraph(){
		System.out.println("Starting...\n");
		String[] bins = {"Team","League","Player","Matchup","Overview","Scoring","Schedule","Standings","Trades",
				"Transactions","Other","Exit"};
		HashMap<String, HashMap<String, Integer>> theMap = new HashMap<String, HashMap<String, Integer>>();
		// PREPROCESSOR SETTING UP HASHMAP
		for(String b : bins){
			HashMap<String, Integer> temp = new HashMap<String, Integer>();
			for(String b2 : bins){
				temp.put(b2, 0);
			}
			theMap.put(b, temp);
		}
		// ******************************
		//Start the algo
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(where));

			String next = br.readLine();
			while(next != null){
				//See each line:
				//System.out.println(next);
				String[] parts = next.split("[,\\||]");
				//Goes through all the actions in each line
				int len = parts.length;
				for(int i = 0; i < len;i+=3){
					//Where are we
					String here = "";
					for(String b : bins){
						if(parts[i].contains(b)){
							here = b;
							break;
						}
					}
					//Make other a thing
					if(here.equals("")){
						here = "Other";
					}
					//I test for extra commas; uncomment to use
					if(len > i+4){
					//System.out.println("--"+parts[i+1]+"<<>>"+parts[i+4]);
					}
					//If it switches dates then we exit
					if(len < i+4 || !parts[i+1].substring(0,12).equals(parts[i+4].substring(0,12))||
							i+2> parts.length){
						//Gets the part of theMap
						HashMap<String, Integer> gettable = theMap.get(here);
						//Adds one to its exit
						gettable.put("Exit", gettable.get("Exit")+1);
						//Puts it back like a champ
						theMap.put(here, gettable);
					}else if(parts[i+3]!=null){
						String there = "";
						//Find there
						for(String b : bins){
							if(parts[i+3].contains(b)){
								there = b;
							}
						}
						//Test if other is needed
						if(there.equals("")){
							there = "Other";
						}
						//Add one to here->there
						//Gets the "here" part of theMap
						HashMap<String, Integer> gettable = theMap.get(here);
						//Adds one to its there
						gettable.put(there, gettable.get(there)+1);
						//Puts it back like a champ
						theMap.put(here, gettable);
					}
				}
				//Iterate
				next = br.readLine();
				if(next != null && !next.contains(",")){
					next = br.readLine();
				}
			}//End of while
		
			//Stores total that goes to each
			HashMap<String, Integer> goes = new HashMap<String, Integer>();
			HashMap<String, Integer> sums = new HashMap<String, Integer>();
			for(String b : bins){
				goes.put(b, 0);
				sums.put(b, 0);
			}
			//Print the map
			//Tracks the command for mathematica
			StringBuilder mathematica = 
					new StringBuilder("Graph[{\"Team\", \"League\", \"Player\", \"Matchup\", \"Overview\", \"Scoring\", \"Schedule\", \"Standings\", \"Trades\", \"Transactions\", \"Other\", \"Exit\",\"Start\"},DiscreteMarkovProcess[13,{");
			for(Map.Entry<String, HashMap<String, Integer>> mini : theMap.entrySet()){
				if(mini.getKey().equals("Exit")){
					//Fills in that exit always returns to exit
					mathematica.append("{0,0,0,0,0,0,0,0,0,0,0,1,0},");
					break;
				}
				//for mathematics
				mathematica.append("{");
				float sum = 0;
				for(Map.Entry<String, Integer> minest : mini.getValue().entrySet()){
					sum+=minest.getValue();
				}	
					sums.put(mini.getKey(),(int)sum);
					//For the output
					System.out.println("\n"+mini.getKey() + "("+(int)sum+"):");			
				for(Map.Entry<String, Integer> minest : mini.getValue().entrySet()){
					goes.put(minest.getKey(), goes.get(minest.getKey())+minest.getValue());
					//For the output
					System.out.println("\t "+minest.getKey() + " -- " +String.format("%.3f",(float)minest.getValue()/sum));
					//For mathematica
					if(!minest.getKey().equals("Exit")){
						mathematica.append(String.format("%.3f",(float)minest.getValue()/sum));
						mathematica.append(",");
					}else{
						mathematica.append(String.format("%.3f",(float)minest.getValue()/sum));
						mathematica.append(",0},");
					}
				}
			}
			//Get goes total
			float goesT = 0;
			for(Map.Entry<String, Integer> m : goes.entrySet()){
				if(m.getKey().equals("Exit")){
					break;
				}
				goesT+=(sums.get(m.getKey())-m.getValue());
			}
			//Goes:
			System.out.println("Start("+(int)goesT+"):");
			//for mathematica
			mathematica.append("{");
			for(Map.Entry<String, Integer> m : goes.entrySet()){
				if(m.getKey().equals("Exit")){
					break;
				}
				//for mathematica
				mathematica.append(String.format("%.3f", ((float)sums.get(m.getKey())-m.getValue())/goesT));
				mathematica.append(",");
				//for output
				System.out.println("\t"+m.getKey() +" -- "+String.format("%.3f", ((float)sums.get(m.getKey())-m.getValue())/goesT));
			}
			//close off mathematica
			mathematica.append("0,0}}],ImageSize->Large]");
			//Mathematics
			System.out.println("The mathematica command:");
			System.out.println(mathematica.toString());
			System.out.println("\nEncoding:");
			int i = 1;
			for(String b : bins){
				System.out.println("\t"+i+++"="+b);
			}
			System.out.println("\t"+i+++"=Start");

		} catch (IOException e) {
			//Let's see what broke in the file process
			e.printStackTrace();
		}
	}

	/*
	 * Entry point to the function and directs the action
	 */
	public static void main(String[] args){
		MakeGraph mg = new MakeGraph();
		if(args.length < 2){
			System.out.println("Usage: <file> <type>");
		}else if(args[1].equals("collective")){
			System.out.println("Generating the collective graph...\n");
			//Set the file
			where = args[0];
			mg.collectiveGraph();
			System.out.println("Completed!\n");
		}
	}
}