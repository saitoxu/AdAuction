package saitoxu.adauction;

public class Organizer {
	public static void main(String args[]) {
		int maxChange = 500; // 変更できる上限値
		int bidLength = 2500; // 入札額-インプレッション数関数の入札額の種類の上限値
		int ads[] = {6578, 11270, 11614}; // 広告の種類
		int adSpaces[] = {60, 6105, 2341, 1712, 2337}; // 広告枠の種類
		double bid[][] = new double[adSpaces.length][bidLength]; // 入札額関数を入れる配列
		long imp[][] = new long[adSpaces.length][bidLength]; // 入札額に対応したインプレッション数を入れる配列
		double budget[] = new double[ads.length]; // 広告ごとの予算
		double cpa[] = new double[ads.length]; // 広告ごとのcpa
		double icvr[][] = new double[ads.length][adSpaces.length]; // 広告，広告枠ごとのicvr
		long start[] = new long[adSpaces.length]; // 他の広告が各広告枠で獲得しているインプレッション数
		long sum[] = new long[adSpaces.length]; // 各広告枠のインプレッション数の合計値
		double price[] = new double[adSpaces.length]; // 広告枠への入札額
		long beforeImps[][] = new long[ads.length][adSpaces.length]; // 1つ前のインプレッション数
		long before2Imps[][] = new long[ads.length][adSpaces.length]; // 2つ前のインプレッション数（終了条件の判定に使う）
		long imps[][] = new long[ads.length][adSpaces.length]; // インプレッション数
		
		ValuesSetter setter = new ValuesSetter(ads, adSpaces);
		MarketModel mM = new MarketModel();
		bid = setter.setBid(bid[0].length);
		imp = setter.setImp(imp[0].length);
		budget = setter.setBudget();
		cpa = setter.setCpa();
		icvr = setter.setIcvr();
		start = setter.setStart(); // 各広告枠のインプレッション数の初期値（他の広告が獲得してる分）
		price = mM.setBid(adSpaces.length, bid, imp, start);
		GreedySearcher struct[] = new GreedySearcher[ads.length];
		
		for (int i = 0; i < ads.length; i++) {
			struct[i] = new GreedySearcher(budget[i], cpa[i], icvr[i], maxChange, adSpaces.length);
		}
		
		while (true) {
			for (int i = 0; i < imps.length; i++) {
				before2Imps[i] = beforeImps[i];
				beforeImps[i] = imps[i];
				imps[i] = struct[i].greedySearch(price, imps[i]);
			}
			for (int i = 0; i < ads.length; i++) {
				for (int j = 0; j < adSpaces.length; j++) {
					System.out.print("imps[" + i + "][" + j + "] = " + imps[i][j]);
					if (j == adSpaces.length - 1) {
						System.out.println();
					} else {
						System.out.print(", ");
					}
				}
			}
			for (int i = 0; i < price.length; i++) {
				System.out.print("price[" + i + "] = " + price[i]);
				if (i == price.length - 1) {
					System.out.println();
				} else {
					System.out.print(", ");
				}
			}
			sum = mM.setSum(imps);
			for (int i = 0; i < sum.length; i++) {
				System.out.print("sum[" + i + "] = " + sum[i]);
				if (i == sum.length - 1) {
					System.out.println();
				} else {
					System.out.print(", ");
				}
			}
			// 価格を調整
			price = mM.setPrice(price, bid, imp, sum, start);
			// 終了判定
			if (mM.isEndCalculation(imps, before2Imps)) {
				break;
			}
		}
		
		for (int i = 0; i < ads.length; i++) {
			System.out.print("budget[" + i + "] = " + budget[i]);
			if (i == ads.length - 1) {
				System.out.println();
			} else {
				System.out.print(", ");
			}
		}
		for (int i = 0; i < ads.length; i++) {
			System.out.print("cpa[" + i + "] = " + cpa[i]);
			if (i == ads.length - 1) {
				System.out.println();
			} else {
				System.out.print(", ");
			}
		}
	}
}
