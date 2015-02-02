import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
//Class that contains all markov chain + graph functions
//that work on FF data files
public class MakeGraph{
	/* Set where the master-path file is */
	static String masterPath;
	static String funPath;
	static String surveyPath;
	static String viewsPath;
	/* Lets us customize which week we're at in one place */
	final int WEEK = 14;
	/* Toggle this to get additional information */
	final boolean PRINT = false;

	/*
	 * Weekly click data metrics
	 */
	public void clickData(){
		System.out.println("Start!");
		BufferedReader br;
		BufferedWriter bw;
		try {
			br = new BufferedReader(new FileReader(viewsPath));
			bw = new BufferedWriter(new FileWriter("clicks.txt"));
			br.close();
			bw.close();
		}catch(IOException e){};
		
	}
	
	/*
	 * Creates the CSV with engagement metrics
	 * 	1. Total time spent by week
	 * 	2. Number of sessions per week
	 * 	3. Number of days played per week
	 */
	public void engagementMetrics(){
		System.out.println("Start!");
		BufferedReader br;
		BufferedWriter bw;
		try {
			br = new BufferedReader(new FileReader(masterPath));
			bw = new BufferedWriter(new FileWriter("engagement.txt"));
			//Header for CSV file
			bw.write("id,week,daysperweek,sessionsperweek,clicksperweek\n");
			String next = br.readLine();
			while(next != null){
				String last = "";
				//Gets the id and the data
				while(next.startsWith("&")){
					last = next;
					next = br.readLine();
					if(next == null){
						break;
					}
				}
				if(next != null && last != ""){	
					//Splits the data
					String[] parts = next.split(",2014-");
					//Get their username without &
					last = last.substring(1);
					//Create week bins
					//Initialize to avoid out of bounds errors
					ArrayList<Set<Integer>> weeks = new ArrayList<Set<Integer>>(WEEK);
					for(int i =0; i < WEEK; i++){
						weeks.add(new HashSet<Integer>());
					}
					
					//Sessions array
					//A session is any period of more than 30 minutes of inactivity 
					int[] sess = new int[WEEK];
					//All the clicks for each week
					int[] clicks = new int[WEEK];
					//Months are not the same length and that is rough
					int lastDay = -1;
					int firstMinute = -1;
					for(int i =1; i< parts.length; i++){
						//System.out.println(parts[i]);
						int theMonth = Integer.parseInt(parts[i].substring(0,2));
						float theDay = Integer.parseInt(parts[i].substring(3,5));
						int minute = Integer.parseInt(parts[i].substring(9,11));
						//I need a longterm solution
						if(theMonth == 9 && theDay <= 7 && theDay > 0){
							weeks.get(0).add((int)theDay);
							clicks[0]++;
							if(lastDay == theDay){
								if(firstMinute > minute+30){
									sess[0]++;
								}
							}else{
								firstMinute = minute;
							}
						}else if(theMonth == 9 && theDay <= 14 && theDay > 7){
							weeks.get(1).add((int)theDay);
							clicks[1]++;
							if(lastDay == theDay){
								if(firstMinute > minute+30){
									sess[1]++;
								}
							}else{
								firstMinute = minute;
							}
						}else if(theMonth == 9 && theDay <= 21 && theDay > 14){
							weeks.get(2).add((int)theDay);
							clicks[2]++;
							if(lastDay == theDay){
								if(firstMinute > minute+30){
									sess[2]++;
								}
							}else{
								firstMinute = minute;
							}
						}else if(theMonth == 9 && theDay <= 28 && theDay > 21){
							weeks.get(3).add((int)theDay);
							clicks[3]++;
							if(lastDay == theDay){
								if(firstMinute > minute+30){
									sess[3]++;
								}
							}else{
								firstMinute = minute;
							}
						}else if((theMonth == 9 && theDay > 28) || (theMonth == 10 && theDay <= 4)){
							weeks.get(4).add((int)theDay);
							clicks[4]++;
							if(lastDay == theDay){
								if(firstMinute > minute+30){
									sess[4]++;
								}
							}else{
								firstMinute = minute;
							}
						}else if(theMonth == 10 && theDay <= 11 && theDay > 4){
							weeks.get(5).add((int)theDay);
							clicks[5]++;
							if(lastDay == theDay){
								if(firstMinute > minute+30){
									sess[5]++;
								}
							}else{
								firstMinute = minute;
							}
						}else if(theMonth == 9 && theDay <= 18 && theDay > 11){
							weeks.get(6).add((int)theDay);
							clicks[6]++;
							if(lastDay == theDay){
								if(firstMinute > minute+30){
									sess[6]++;
								}
							}else{
								firstMinute = minute;
							}
						}else if(theMonth == 10 && theDay <= 25 && theDay > 18){
							weeks.get(7).add((int)theDay);
							clicks[7]++;
							if(lastDay == theDay){
								if(firstMinute > minute+30){
									sess[7]++;
								}
							}else{
								firstMinute = minute;
							}
						}else if((theMonth == 10 && theDay <= 31 && theDay > 25) ||
								(theMonth == 11 && theDay == 1)){
							weeks.get(8).add((int)theDay);
							clicks[8]++;
							if(lastDay == theDay){
								if(firstMinute > minute+30){
									sess[8]++;
								}
							}else{
								firstMinute = minute;
							}
						}else if(theMonth == 11 && theDay <= 8 && theDay > 1){
							weeks.get(9).add((int)theDay);
							clicks[9]++;
							if(lastDay == theDay){
								if(firstMinute > minute+30){
									sess[9]++;
								}
							}else{
								firstMinute = minute;
							}
						}else if(theMonth == 11 && theDay <= 15 && theDay > 8){
							weeks.get(10).add((int)theDay);
							clicks[10]++;
							if(lastDay == theDay){
								if(firstMinute > minute+30){
									sess[10]++;
								}
							}else{
								firstMinute = minute;
							}
						}else if(theMonth == 11 && theDay <= 22 && theDay > 15){
							weeks.get(10).add((int)theDay);
							clicks[10]++;
							if(lastDay == theDay){
								if(firstMinute > minute+30){
									sess[10]++;
								}
							}else{
								firstMinute = minute;
							}
						}else if(theMonth == 11 && theDay <= 29 && theDay > 22){
							weeks.get(11).add((int)theDay);
							clicks[11]++;
							if(lastDay == theDay){
								if(firstMinute > minute+30){
									sess[11]++;
								}
							}else{
								firstMinute = minute;
							}
						}else if((theMonth == 11 && theDay == 30) || 
								(theMonth == 12 && theDay <= 6)){
							weeks.get(12).add((int)theDay);
							clicks[12]++;
							if(lastDay == theDay){
								if(firstMinute > minute+30){
									sess[12]++;
								}
							}else{
								firstMinute = minute;
							}
						}else if(theMonth == 12 && theDay <= 13 && theDay > 6){
							weeks.get(13).add((int)theDay);
							clicks[13]++;
							if(lastDay == theDay){
								if(firstMinute > minute+30){
									sess[13]++;
								}
							}else{
								firstMinute = minute;
							}
						}
						
						lastDay = (int)theDay;
					}
					//Printable
					//adds 1 to all sessions because the clock should start at 1 instead of 0
					//TODO: We should be two for loops
					if(PRINT){
						System.out.println(last+",1,"+weeks.get(0).size()+","+(sess[0]+1)+","+clicks[0]+"\n"+
								 last+",2,"+weeks.get(1).size()+","+(sess[1]+1)+","+clicks[1]+"\n"+
								 last+",3,"+weeks.get(2).size()+","+(sess[2]+1)+","+clicks[2]+"\n"+
								 last+",4,"+weeks.get(3).size()+","+(sess[3]+1)+","+clicks[3]+"\n"+
								 last+",5,"+weeks.get(4).size()+","+(sess[4]+1)+","+clicks[4]+"\n"+
								 last+",6,"+weeks.get(5).size()+","+(sess[5]+1)+","+clicks[5]+"\n"+
								 last+",7,"+weeks.get(6).size()+","+(sess[6]+1)+","+clicks[6]+"\n"+
								 last+",8,"+weeks.get(7).size()+","+(sess[7]+1)+","+clicks[7]+"\n");
					}
					bw.write(last+",1,"+weeks.get(0).size()+","+(sess[0]+1)+","+clicks[0]+"\n"+
							 last+",2,"+weeks.get(1).size()+","+(sess[1]+1)+","+clicks[1]+"\n"+
							 last+",3,"+weeks.get(2).size()+","+(sess[2]+1)+","+clicks[2]+"\n"+
							 last+",4,"+weeks.get(3).size()+","+(sess[3]+1)+","+clicks[3]+"\n"+
							 last+",5,"+weeks.get(4).size()+","+(sess[4]+1)+","+clicks[4]+"\n"+
							 last+",6,"+weeks.get(5).size()+","+(sess[5]+1)+","+clicks[5]+"\n"+
							 last+",7,"+weeks.get(6).size()+","+(sess[6]+1)+","+clicks[6]+"\n"+
							 last+",8,"+weeks.get(7).size()+","+(sess[7]+1)+","+clicks[7]+"\n");
				}
				next = br.readLine();
			}
			
			//Close streams and save it
			bw.flush();
			br.close();
			bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/*
	 * Generates the HashMap of HashMaps that tell the probability of one bin to the next
	 * this is for the Discrete Markov Process visualization
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
		//Start the algorithm
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(masterPath));

			String next = br.readLine();
			while(next != null){
				//See each line:
				//System.out.println(next);
				String[] parts = next.split("(,2014)|[|]");
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
						System.out.println("--"+parts[i+1]+"<<>>"+parts[i+4]);
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
	 * Generates the master dataset for Stata and other analysis
	 */
public void genMaster(){
	//PART 1: Read all the data sets
	BufferedReader br;
	try {
		br = new BufferedReader(new FileReader(masterPath));
	//1.1: Read the master path file for weekly engagement metrics
		
	//1.2: Read the survey file for survey data
		
	//1.3: Read the fun file for fun data
		
	//1.4: Read the views file for macro engagement data and weekly view data
		
	//Close up shop
		br.close();
	}catch(IOException e){
		System.out.println("Nonspecified error reading the file, see trace:\n");
		System.out.println(e);
	}
	
}

	/*
	 * Entry point to the program and directs the action
	 */
	public static void main(String[] args){
		MakeGraph mg = new MakeGraph();
		if(args.length < 2){
			System.out.println("Usage: <type> <master-path> [survey-path] [fun-path] [views-path]");
		}else if(args[0].equals("collective") && args.length >= 2){
			System.out.println("Generating the collective graph...\n");
			//Set the file
			masterPath = args[1];
			mg.collectiveGraph();
			System.out.println("Completed!");
		}else if(args[0].equals("master") && args.length == 5){
			System.out.println("Generating the master spreadsheet...");
			masterPath = args[1];
			surveyPath = args[2];
			funPath = args[3];
			viewsPath = args[4];
			mg.genMaster();
			System.out.println("Completed Full Spreadsheet");
		}else if(args[0].equals("engagement") && args.length == 2){
			masterPath = args[1];
			mg.engagementMetrics();
			
			System.out.println("Completed!");
		}else if(args[0].equals("master")){
			System.out.println("You need to include: <master-path> [survey-path] [fun-path] [views-path]");
		}else{
			System.out.println("Usage: <type> <master-path> [survey-path] [fun-path] [views-path]");
		}
	}
}