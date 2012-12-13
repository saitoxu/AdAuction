package saitoxu.adauction;

public class GreedySearcher {
	private double budget;
	private double cpa;
	private double[] icvr;
	private long[] imps;
	private int maxChange;
	
	// コンストラクタ
	public GreedySearcher(double myBudget, double myCpa, double[] myIcvr, int maxChangeValue, int adSpaceLength) {
		budget = myBudget;
		cpa = myCpa;
		icvr = myIcvr;
		maxChange = maxChangeValue;
		imps = new long[adSpaceLength];
	}
	
	public long[] greedySearch(double[] price, long[] previousImps) {
		Lp lp = new Lp(price, previousImps, budget, cpa, icvr, maxChange);
		imps = lp.solveLp();
		return imps;
	}
}
