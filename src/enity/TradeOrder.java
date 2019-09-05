package enity;

public class TradeOrder {
	private String traderId;
	private String stockId;
	private long price;
	private int amount;
	
	public String getTraderId() {
		return traderId;
	}
	public void setTraderId(String traderId) {
		this.traderId = traderId;
	}
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public TradeOrder( String traderId, String stockId, long price, int amount) {
		this.traderId = traderId;
		this.stockId = stockId;
		this.price = price;
		this.amount = amount;
	}
	public TradeOrder() {
		
	}
	@Override
	public String toString() {
		return "TradeOrder [traderId=" + traderId + ", stockId=" + stockId + ", price=" + price + ", amount=" + amount
				+ "]";
	}
	
	
}
