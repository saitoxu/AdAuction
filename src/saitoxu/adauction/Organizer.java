package saitoxu.adauction;

public class Organizer {
	public static void main(String args[]) {
		int changeValue = 10; // •ÏX‚Å‚«‚éãŒÀ’l
		int bidLength = 2500; // “üDŠz-ƒCƒ“ƒvƒŒƒbƒVƒ‡ƒ“”ŠÖ”‚Ì“üDŠz‚Ìí—Ş‚ÌãŒÀ’l
		int ads[] = {6578, 10562, 9215}; // L‚Ìí—Ş
		int adSpaces[] = {60, 6105, 2341}; // L˜g‚Ìí—Ş
		double bid[][] = new double[adSpaces.length][bidLength]; // “üDŠzŠÖ”‚ğ“ü‚ê‚é”z—ñ
		long imp[][] = new long[adSpaces.length][bidLength]; // “üDŠz‚É‘Î‰‚µ‚½ƒCƒ“ƒvƒŒƒbƒVƒ‡ƒ“”‚ğ“ü‚ê‚é”z—ñ
		double budget[] = new double[ads.length]; // L‚²‚Æ‚Ì—\Z
		double cpa[] = new double[ads.length]; // L‚²‚Æ‚Ìcpa
		double icvr[][] = new double[ads.length][adSpaces.length]; // LCL˜g‚²‚Æ‚Ìicvr
		long start[] = new long[adSpaces.length]; // ƒCƒ“ƒvƒŒƒbƒVƒ‡ƒ“”‚Ì‰Šú’li‘¼‚ÌL‚ªŠl“¾‚µ‚Ä‚é•ªj
		double p[] = new double[adSpaces.length]; // L˜g‚Ö‚Ì“üDŠz
		long x[][] = new long[ads.length][adSpaces.length]; // ¡‚ÌƒCƒ“ƒvƒŒƒbƒVƒ‡ƒ“”
		long y[][] = new long[ads.length][adSpaces.length]; // ‘O‚ÌƒXƒeƒbƒv‚Å‚ÌƒCƒ“ƒvƒŒƒbƒVƒ‡ƒ“”
		
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
