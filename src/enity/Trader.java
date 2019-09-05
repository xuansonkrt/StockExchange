package enity;

public class Trader {
	private String traderId;
	private String name;
	public String getTraderId() {
		return traderId;
	}
	public void setTraderId(String traderId) {
		this.traderId = traderId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public Trader(String traderId, String name) {
		this.traderId = traderId;
		this.name = name;
	}
	public Trader() {
	}
	
	
	
	
}
