package application;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import enity.Stock;
import enity.TradeOrder;
import enity.Trader;

public class Application {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("sonnx");
		Map<String, Trader> registeredTraders = new TreeMap<String, Trader>(); // danh sách người chơi đăng ký
		Set<Trader> logginTraders = new HashSet<Trader>();
		Queue<String> mailBox = new LinkedList<String>();
		Map<String, Stock> listStock = new HashMap<String, Stock>();
		Map<String, PriorityQueue<TradeOrder>> listSell = new HashMap<String, PriorityQueue<TradeOrder>>();
		Map<String, PriorityQueue<TradeOrder>> listBuy = new HashMap<String, PriorityQueue<TradeOrder>>();

		registeredTraders.put("1", new Trader("1", "sonnx"));
		registeredTraders.put("2", new Trader("2", "kienpt"));
		registeredTraders.put("3", new Trader("3", "kientt"));
		registeredTraders.put("4", new Trader("4", "manhnh"));
		registeredTraders.put("5", new Trader("5", "hieunh"));

		logginTraders.add(new Trader("1", "sonnx"));
		logginTraders.add(new Trader("2", "kienpt"));
		logginTraders.add(new Trader("3", "kientt"));
		logginTraders.add(new Trader("5", "hieunh"));

		listStock.put("1", new Stock("1", "VNI"));
		listStock.put("2", new Stock("2", "EN"));
		listStock.put("3", new Stock("3", "JP"));
		listStock.put("4", new Stock("4", "UK"));
		listStock.put("5", new Stock("5", "US"));

		for (int i = 0; i < 5; i++) {
			TimeUnit.SECONDS.sleep(3);
			addOrder(listSell, listBuy, registeredTraders.size(), listStock.size());
			System.out.println("------Trước khớp lệnh------");
			System.out.println("listSell: " + listSell);
			System.out.println("listBuy: " + listBuy);
			System.out.println("-------------------------");
			System.out.println("\n*****Bắt đầu khớp lệnh*****");
			matchingOrder(listSell, listBuy, registeredTraders, listStock, mailBox);
			System.out.println("*****Kết thúc khớp lệnh*****\n");
			System.out.println("------Sau khớp lệnh------");
			System.out.println("listSell: " + listSell);
			System.out.println("listBuy: " + listBuy);
			System.out.println("-------------------------");
		}
		

	}

	// thêm yêu cầu bán/mua
	public static void addOrder(Map<String, PriorityQueue<TradeOrder>> listSell,
			Map<String, PriorityQueue<TradeOrder>> listBuy, int amountStock, int amountTrader) {
		Random rand = new Random();
		int amountSell = rand.nextInt(15); // số yêu cầu bán
		int amountBuy = rand.nextInt(15); // số yêu cần mua
		// mua
		for (int i = 1; i < amountBuy; i++) {
			int stockId = rand.nextInt(amountStock) + 1;
			PriorityQueue<TradeOrder> queueBuy = listBuy.get(stockId + "");
			if (queueBuy == null) {
				queueBuy = new PriorityQueue<TradeOrder>(new Comparator<TradeOrder>() {
					@Override
					public int compare(TradeOrder o1, TradeOrder o2) {
						// TODO Auto-generated method stub
						return (int) (o1.getPrice() == o2.getPrice() ? o2.getAmount() - o1.getAmount()
								: (o2.getPrice() - o1.getPrice()));
					}
				});
			}
			queueBuy.add(new TradeOrder(rand.nextInt(amountTrader) + 1 + "", stockId + "", rand.nextInt(10)+1,
					rand.nextInt(1000)+1));
			listBuy.put(stockId + "", queueBuy);
		}

		// bán
		for (int i = 1; i < amountSell; i++) {
			int stockId = rand.nextInt(amountStock) + 1;
			PriorityQueue<TradeOrder> queueSell = listSell.get(stockId + "");
			if (queueSell == null) {
				queueSell = new PriorityQueue<TradeOrder>(new Comparator<TradeOrder>() {
					@Override
					public int compare(TradeOrder o1, TradeOrder o2) {
						// TODO Auto-generated method stub
						return (int) (o1.getPrice() == o2.getPrice() ? o2.getAmount() - o1.getAmount()
								: (o1.getPrice() - o2.getPrice()));
					}
				});
			}
			queueSell.add(new TradeOrder(rand.nextInt(amountTrader) + 1 + "", stockId + "", rand.nextInt(10)+1,
					rand.nextInt(1000)+1));
			listSell.put(stockId + "", queueSell);
		}
	}

	// khớp lệnh
	public static void matchingOrder(Map<String, PriorityQueue<TradeOrder>> listSell,
			Map<String, PriorityQueue<TradeOrder>> listBuy, Map<String, Trader> registeredTraders,
			Map<String, Stock> listStock, Queue<String> mailBox) throws InterruptedException {
		if(listSell.size()==0||listBuy.size()==0) {
			return;
		}
		Set<String> setStock = listStock.keySet();
		for (String item : setStock) {
			PriorityQueue<TradeOrder> queueSell = listSell.get(item);
			PriorityQueue<TradeOrder> queueBuy = listBuy.get(item);
			if (queueSell == null || queueBuy == null) { // khong co nguoi ban hoac khong co nguoi mua
				continue;
			}
			while (true) {
				TimeUnit.SECONDS.sleep(1);
				TradeOrder itemSell = queueSell.poll();
				TradeOrder itemBuy = queueBuy.poll();
				if(itemSell==null || itemBuy==null) {
					return;
				}
				if (itemSell.getPrice() > itemBuy.getPrice()) {
					break;
				} else {
					if (itemSell.getAmount() >= itemBuy.getAmount()) {
						Trader traderSell = registeredTraders.get(itemSell.getTraderId());
						Trader traderBuy = registeredTraders.get(itemBuy.getTraderId());
						if(traderSell.getTraderId().equals(traderBuy.getTraderId()))
							return;
						itemSell.setAmount(itemSell.getAmount() - itemBuy.getAmount());
						if (itemSell.getAmount() != 0) {
							queueSell.add(itemSell);
						}
						Stock stock = listStock.get(item);
						String mail = traderBuy.getName() + " đã mua " + itemBuy.getAmount() + " cổ phiếu "
								+ stock.getName() + " với giá " + itemSell.getPrice() + "$ của " + traderSell.getName()
								+ ".";
						mailBox.add(mail);
						System.out.println(mail);
					} else {
						Trader traderSell = registeredTraders.get(itemSell.getTraderId());
						Trader traderBuy = registeredTraders.get(itemBuy.getTraderId());
						if(traderSell.getTraderId().equals(traderBuy.getTraderId()))
							return;
						itemBuy.setAmount(itemBuy.getAmount() - itemSell.getAmount());
						queueBuy.add(itemBuy);						
						Stock stock = listStock.get(item);
						String mail = traderBuy.getName() + " đã mua " + itemSell.getAmount() + " cổ phiếu "
								+ stock.getName() + " với giá " + itemSell.getPrice() + "$ của " + traderSell.getName()
								+ ".";
						mailBox.add(mail);
						System.out.println(mail);
					}
				}
			}
		}

	}
}
