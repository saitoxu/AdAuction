package saitoxu.adauction;

public class Organizer {
	public static void main(String args[]) {
		int changeValue = 10; // 変更できる上限値
		int bidLength = 2500; // 入札額-インプレッション数関数の入札額の種類の上限値
		int ads[] = {6578, 10562, 9215}; // 広告の種類
		int adSpaces[] = {60, 6105, 2341}; // 広告枠の種類
		double bid[][] = new double[adSpaces.length][bidLength]; // 入札額関数を入れる配列
		long imp[][] = new long[adSpaces.length][bidLength]; // 入札額に対応したインプレッション数を入れる配列
		double budget[] = new double[ads.length]; // 広告ごとの予算
		double cpa[] = new double[ads.length]; // 広告ごとのcpa
		double icvr[][] = new double[ads.length][adSpaces.length]; // 広告，広告枠ごとのicvr
		long sum[] = new long[adSpaces.length]; // 各広告枠のインプレッション数の合計値
		double price[] = new double[adSpaces.length]; // 広告枠への入札額
		long current[][] = new long[ads.length][adSpaces.length]; // 今のインプレッション数
		long previous[][] = new long[ads.length][adSpaces.length]; // 前のステップでのインプレッション数
		
		ValuesSetter setter = new ValuesSetter(ads, adSpaces);
		MarketModel mM = new MarketModel();
		bid = setter.setBid(bid[0].length);
		imp = setter.setImp(imp[0].length);
		budget = setter.setBudget();
		cpa = setter.setCpa();
		icvr = setter.setIcvr();
		sum = setter.setStart(); // 各広告枠のインプレッション数の初期値（他の広告が獲得してる分）
		price = mM.setBid(adSpaces.length, bid, imp, sum);
		
		for (int i = 0; i < ads.length; i++) {
			System.out.println("budget[" + i + "] = " + budget[i] + ", cpa[" + i + "] = " + cpa[i]);
		}
		for (int i = 0; i < ads.length; i++) {
			for (int j = 0; j < adSpaces.length; j++) {
				System.out.println("icvr[" + i + "][" + j + "] = " + icvr[i][j]);
			}
		}
		for (int i = 0; i < adSpaces.length; i++) {
			System.out.println("sum[" + i + "] = " + sum[i]);
		}
		for (int i = 0; i < price.length; i++) {
			System.out.println("price[" + i + "] = " + price[i]);
		}
		System.out.println("boolean = " + mM.isEndCalculation(current, previous));
//		for (int i = 0; i < adSpaces.length; i++) {
//			for (int j = 0; bid[i][j] != 0; j++) {
//				System.out.println("bid[" + i + "][" + j + "] = " + bid[i][j] + ", imp[" + i + "][" + j + "] = "  + imp[i][j]);
//			}
//		}
	}
}
