package saitoxu.adauction;

import java.io.*;

public class Organizer {
	public static void main(String args[]) throws IOException {
		int maxChange = 1000;
		int bidLength = 140000;
		int ads[] = { 6578, 9215, 10562, 11270, 11614 };
		int adSpaces[] = { 60, 1712, 2337, 2341, 6105 };
		double bid[][] = new double[adSpaces.length][bidLength];
		long imp[][] = new long[adSpaces.length][bidLength];
		double budget[] = new double[ads.length];
		double cpa[] = new double[ads.length];
		double icvr[][] = new double[ads.length][adSpaces.length];
		long start[] = new long[adSpaces.length];
		long sum[] = new long[adSpaces.length];
		double price[] = new double[adSpaces.length];
		long imps[][] = new long[ads.length][adSpaces.length];
		long upperBound[][] = new long[ads.length][adSpaces.length];

		// 実行時間計測用（開始時刻）
		long ready = System.currentTimeMillis();
		ValuesSetter setter = new ValuesSetter(ads, adSpaces);
		MarketModel mM = new MarketModel();
		bid = setter.setBid(bid[0].length);
		imp = setter.setImp(imp[0].length);
		budget = setter.setBudget();
		cpa = setter.setCpa();
		icvr = setter.setIcvr();
		start = setter.setStart();
		upperBound = setter.setUpperLimit();
		price = mM.setBid(adSpaces.length, bid, imp, start);
		Lp struct[] = new Lp[ads.length];

		for (int i = 0; i < ads.length; i++) {
			struct[i] = new Lp(budget[i], cpa[i], icvr[i], maxChange,
					upperBound[i], adSpaces.length);
		}
		// File file = new File(
		// "/Users/Yosuke/AdAuction/output20130315/11614_1712_0starts_obj_is_conv_max.csv");
		// PrintWriter pw = new PrintWriter(new BufferedWriter(
		// new FileWriter(file)));
		for (int count = 0; count < 10000; count++) {
			System.out.println("----------------------------------------"
					+ count + "----------------------------------------");
			for (int i = 0; i < imps.length; i++) {
				imps[i] = struct[i].solveLp(price, imps[i]);
			}
			sum = mM.setSum(imps);
			printLog(ads, adSpaces, imps, price, sum, icvr, budget, cpa, start);
			// pw.println(imps[2][0]);
			price = mM.setPrice(price, bid, imp, sum, start);
		}
		// pw.close();

		// 実行時間計測用（終了時刻）
		long stop = System.currentTimeMillis();
		System.out.println("実行時間:" + (stop - ready) + "ms");
	}

	private static void printLog(int[] ads, int[] adSpaces, long[][] imps,
			double[] price, long[] sum, double[][] icvr, double[] budget,
			double[] cpa, long[] start) {
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

		for (int i = 0; i < sum.length; i++) {
			System.out.print("sum[" + i + "] = " + sum[i]);
			if (i == sum.length - 1) {
				System.out.println();
			} else {
				System.out.print(", ");
			}
		}

		double[] realBudget = new double[ads.length];
		for (int i = 0; i < ads.length; i++) {
			for (int j = 0; j < adSpaces.length; j++) {
				realBudget[i] += imps[i][j] * price[j];
			}
			System.out.print("realBudget[" + i + "] = " + realBudget[i]);
			if (i == ads.length - 1) {
				System.out.println();
			} else {
				System.out.print(", ");
			}
		}

		double[] realCpa = new double[ads.length];
		double convs = 0.0;
		for (int i = 0; i < ads.length; i++) {
			for (int j = 0; j < adSpaces.length; j++) {
				convs += imps[i][j] * icvr[i][j];
			}
			if (convs != 0.0) {
				realCpa[i] = realBudget[i] / convs;
			}
			System.out.print("realCpa[" + i + "] = " + realCpa[i]);
			if (i == ads.length - 1) {
				System.out.println();
			} else {
				System.out.print(", ");
			}
			convs = 0.0;
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

		for (int i = 0; i < adSpaces.length; i++) {
			System.out.print("start[" + i + "] = " + start[i]);
			if (i == adSpaces.length - 1) {
				System.out.println();
			} else {
				System.out.print(", ");
			}
		}

		boolean flag = true;
		for (int i = 0; i < ads.length; i++) {
			if (realBudget[i] > budget[i] || realCpa[i] > cpa[i]) {
				System.out.println();
				System.out.println("Constraint is NOT satisfied.");
				flag = false;
				break;
			}
		}
		if (flag) {
			System.out.println();
			System.out.println("Constraint is satisfied.");
		}
		return;
	}
}
