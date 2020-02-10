
public class FlightTicket {

	private String from, to;
	
	public FlightTicket(String from, String to){
		this.from = from;
		this.to = to;
	}
	
	public String getFrom() {
		return from;
	}
	
	public String getTo() {
		return to;
	}
	
	public String getFlight() {
		return "[" + from + ", " + to + "]";
	}
	
}
