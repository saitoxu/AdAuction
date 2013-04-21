package saitoxu.adauction;

public class MarketModel {
	public double[] setBid(int adSpaceLength, double[][] bid, long[][] imp,
			long[] sum) {
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

	public long[] setSum(long[][] imps) {
		long[] sum = new long[imps[0].length];
		for (int i = 0; i < imps[0].length; i++) {
			for (int j = 0; j < imps.length; j++) {
				sum[i] += imps[j][i];
			}
		}
		return sum;
	}

	public double[] setPrice(double[] beforePrice, double[][] bid,
			long[][] imp, long[] sum, long[] start) {
		double[] price = new double[beforePrice.length];
		for (int i = 0; i < price.length; i++) {
			if (sum[i] + start[i] > getSupply(beforePrice[i], bid[i], imp[i])) {
				price[i] = changePrice(beforePrice[i], bid[i], "up");
			} else {
				price[i] = changePrice(beforePrice[i], bid[i], "down");
			}
		}
		return price;
	}

	private double changePrice(double beforePrice, double[] bid, String str) {
		for (int i = 0; i < bid.length; i++) {
			if (bid[i] > beforePrice) {
				if (str.equals("up")) {
					return bid[i];
				} else if (str.equals("down") && (i - 2 >= 0)) {
					return bid[i - 2];
				} else {
					return bid[0];
				}
			}
		}
		return beforePrice;
	}

	private long getSupply(double beforePrice, double[] bid, long[] imp) {
		long amount = 0;

		for (int i = 0; i < bid.length; i++) {
			if (bid[i] > beforePrice) {
				return amount;
			}
			amount += imp[i];
		}

		return 0;
	}

	public boolean isEndCalculation(long[][] imps, long[][] before2Imps) {
		boolean b = true;

		for (int i = 0; i < imps.length; i++) {
			for (int j = 0; j < imps[0].length; j++) {
				b = b && (imps[i][j] == before2Imps[i][j]);
				if (!b) {
					return b;
				}
			}
		}
		return b;
	}
}
