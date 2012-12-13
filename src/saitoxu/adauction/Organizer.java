package saitoxu.adauction;

public class Organizer {
	public static void main(String args[]) {
		int maxChange = 100; // •ÏX‚Å‚«‚éãŒÀ’l
		int bidLength = 2500; // “üDŠz-ƒCƒ“ƒvƒŒƒbƒVƒ‡ƒ“”ŠÖ”‚Ì“üDŠz‚Ìí—Ş‚ÌãŒÀ’l
		int ads[] = {6578, 10562, 9215}; // L‚Ìí—Ş
		int adSpaces[] = {60, 6105, 2341}; // L˜g‚Ìí—Ş
		double bid[][] = new double[adSpaces.length][bidLength]; // “üDŠzŠÖ”‚ğ“ü‚ê‚é”z—ñ
		long imp[][] = new long[adSpaces.length][bidLength]; // “üDŠz‚É‘Î‰‚µ‚½ƒCƒ“ƒvƒŒƒbƒVƒ‡ƒ“”‚ğ“ü‚ê‚é”z—ñ
		double budget[] = new double[ads.length]; // L‚²‚Æ‚Ì—\Z
		double cpa[] = new double[ads.length]; // L‚²‚Æ‚Ìcpa
		double icvr[][] = new double[ads.length][adSpaces.length]; // LCL˜g‚²‚Æ‚Ìicvr
		long sum[] = new long[adSpaces.length]; // ŠeL˜g‚ÌƒCƒ“ƒvƒŒƒbƒVƒ‡ƒ“”‚Ì‡Œv’l
		double price[] = new double[adSpaces.length]; // L˜g‚Ö‚Ì“üDŠz
		long imps[][] = new long[ads.length][adSpaces.length]; // ƒCƒ“ƒvƒŒƒbƒVƒ‡ƒ“”
		
		ValuesSetter setter = new ValuesSetter(ads, adSpaces);
		MarketModel mM = new MarketModel();
		bid = setter.setBid(bid[0].length);
		imp = setter.setImp(imp[0].length);
		budget = setter.setBudget();
		cpa = setter.setCpa();
		icvr = setter.setIcvr();
		sum = setter.setStart(); // ŠeL˜g‚ÌƒCƒ“ƒvƒŒƒbƒVƒ‡ƒ“”‚Ì‰Šú’li‘¼‚ÌL‚ªŠl“¾‚µ‚Ä‚é•ªj
		price = mM.setBid(adSpaces.length, bid, imp, sum);
		GreedySearcher struct[] = new GreedySearcher[ads.length];
		
		for (int i = 0; i < ads.length; i++) {
			struct[i] = new GreedySearcher(budget[i], cpa[i], icvr[i], maxChange, adSpaces.length);
		}
		
		for (int i = 0; i < imps.length; i++) {
			imps[i] = struct[i].greedySearch(price, imps[i]);
		}
		// for (int i = 0; i < imps.length; i++) {
		// 	for (int j = 0; j < imps[0].length; j++) {
		// 		System.out.println("imps[" + i + "][" + j + "] = " + imps[i][j]);
		// 	}
		// }
		// ˆÈ‰ºƒfƒoƒbƒO—p
		// for (int i = 0; i < ads.length; i++) {
		// 	current[i] = struct[i].greedySearch(price);
		// 	System.out.println("current[" + i + "][0] = " + current[i][0]);
		// }
		// for (int i = 0; i < ads.length; i++) {
		// 	System.out.println("budget[" + i + "] = " + budget[i] + ", cpa[" + i + "] = " + cpa[i]);
		// }
		// for (int i = 0; i < ads.length; i++) {
		// 	for (int j = 0; j < adSpaces.length; j++) {
		// 		System.out.println("icvr[" + i + "][" + j + "] = " + icvr[i][j]);
		// 	}
		// }
		// for (int i = 0; i < adSpaces.length; i++) {
		// 	System.out.println("sum[" + i + "] = " + sum[i]);
		// }
		// for (int i = 0; i < price.length; i++) {
		// 	System.out.println("price[" + i + "] = " + price[i]);
		// }
		// System.out.println("boolean = " + mM.isEndCalculation(current, previous));
	}
}
