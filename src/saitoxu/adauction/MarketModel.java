package saitoxu.adauction;

public class MarketModel {
	public double[] setBid(int adSpaceLength, double[][] bid, long[][] imp, long[] sum) {
		double[] price = new double[adSpaceLength];
		long imps = 0;
		
		for (int i = 0; i < adSpaceLength; i++) {
			for (int j = 0; j < bid[0].length; j++) {
				imps += imp[i][j];
				if (sum[i] <= imps || imp[i][j + 1] == 0) {
					price[i] = bid[i][j];
					break;
				}
			}
			imps = 0;
		}
		
		return price;
	}
	
	public boolean isEndCalculation(long[][] current, long[][] previous) {
		boolean b = true;
		
		for (int i = 0; i < current.length; i++) {
			for (int j = 0; j < current[0].length; j++) {
				b = b && (current[i][j] == previous[i][j]);
				if (!b) {
					return b;
				}
			}
		}
		return b;
	}
}
