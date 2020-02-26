import java.util.ArrayList;

public class MainClass {

	public static void main(String args[]) {

		String flightsForm = "[[?BOS?, ?SEA?], [?BOS?, ?DFW?], [?MKE?, ?SEA?], "
				+ "[?DFW?, ?MKE?], [?ATL?, ?BOS?], [?SEA?, ?ATL?]]";
		ArrayList<FlightTicket> flights = new ArrayList<FlightTicket>();
		ArrayList<ArrayList<FlightTicket>> flightPaths = new ArrayList<ArrayList<FlightTicket>>();
		String start = "?BOS?";

		for(int i = 1; i < flightsForm.length() - 1; ) {
			if(flightsForm.charAt(i)=='[') {
				flights.add(new FlightTicket(flightsForm.substring(i+1, i+6),
						(flightsForm.substring(i+8, i+13))));
				i += 13;
			}
			else i++;
		}



//		for(FlightTicket ticket : flights) {
//			if(ticket.getFrom().contentEquals(start)) {
//				getPaths(new ArrayList<FlightTicket>(flights), flightPaths, new ArrayList<FlightTicket>(), ticket);
//			}
//		}
		flights.stream().filter(t -> t.getFrom().equals(start)).
				forEach(t -> getPaths(new ArrayList<FlightTicket>(flights), flightPaths, new ArrayList<FlightTicket>(), t));

		ArrayList<FlightTicket> solution;

		if(flightPaths.size() == 0) {
			solution = null;
		} else {
			solution = flightPaths.get(0);
			ArrayList<FlightTicket> current;

			pathsLoop:
			for(int i = 1; i < flightPaths.size(); i++) {
				current = flightPaths.get(i);
				int solutionLength = solution.size(),
						currentLength = current.size();

				for(int j = 0; j < Math.min(solutionLength, currentLength); j++) {

					FlightTicket solutionTicket = solution.get(j),
							currentTicket = current.get(j);

					for( int to = 1; to < 4; to++) {
						int chcmp = Character.compare(currentTicket.getTo().charAt(to), solutionTicket.getTo().charAt(to));
						if(chcmp < 0) {
							solution = current;
							continue pathsLoop;
						}
						else if(chcmp > 0) {
							continue pathsLoop;
						}
					}
				}
			}
		}

		if(solution == null) {
			System.out.println("There are no paths to the desired destination");
		} else {
			System.out.print("["+solution.get(0).getFrom());
			for(int i = 0; i < solution.size(); i++) {
				FlightTicket current = solution.get(i);
				System.out.print(", " + current.getTo());
			}
			System.out.print("]");
		}
	}

	public static void getPaths(ArrayList<FlightTicket> flights, ArrayList<ArrayList<FlightTicket>> paths,
								ArrayList<FlightTicket> currentPath, FlightTicket currentTicket) {

		currentPath.add(currentTicket);
		flights.remove(currentTicket);

		/*for(FlightTicket nextTicket : flights) {
			if(currentTicket.getTo().contentEquals(nextTicket.getFrom())) {
				if(flights.size() == 1) {
					currentPath.add(nextTicket);
					paths.add(currentPath);
				} else {
					getPaths(new ArrayList<FlightTicket>(flights), paths, currentPath, nextTicket);
				}
			}
		}*/
		flights.stream().filter(t -> currentTicket.getTo().equals(t.getFrom())).forEach(flights.size() == 1 ? t ->
		{currentPath.add(t); paths.add(currentPath);}: t -> getPaths(new ArrayList<FlightTicket>(flights), paths, currentPath, t));
	}
}