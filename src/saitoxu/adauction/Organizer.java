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
		long start[] = new long[adSpaces.length]; // インプレッション数の初期値（他の広告が獲得してる分）
		double p[] = new double[adSpaces.length]; // 広告枠への入札額
		long x[][] = new long[ads.length][adSpaces.length]; // 今のインプレッション数
		long y[][] = new long[ads.length][adSpaces.length]; // 前のステップでのインプレッション数
		
		ValuesSetter setter = new ValuesSetter(ads, adSpaces);
		bid = setter.setBid(bid[0].length);
		imp = setter.setImp(imp[0].length);
		budget = setter.setBudget();
		cpa = setter.setCpa();
		icvr = setter.setIcvr();
		start = setter.setStart();
		
		System.out.println(bid[0][0] + ", " + imp[0][0]);
		System.out.println(bid[1][0] + ", " + imp[1][0]);
		System.out.println(bid[2][0] + ", " + imp[2][0]);
		System.out.println(budget[0] + ", " + budget[1] + ", " + budget[2]);
		System.out.println(cpa[0] + ", " + cpa[1] + ", " + cpa[2]);
		System.out.println(icvr[2][0] + ", " + icvr[2][1] + ", " + icvr[2][2]);
		System.out.println(start[0] + ", " + start[1] + ", " + start[2]);
	}
}
