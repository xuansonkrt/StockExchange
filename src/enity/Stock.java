package enity;

public class Stock {
	private String stockId;
	private String name;
	
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public Stock(String stockId, String name) {
		this.stockId = stockId;
		this.name = name;
	}
	
	public Stock() {
	}
	
	
	
}
