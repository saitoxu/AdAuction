package saitoxu.adauction;

import java.io.*;
import java.util.*;

public class ValuesSetter {
	private int ads[];
	private int adSpaces[];
	private String metaUri = "/Users/Yosuke/documents/workspace/adauction/src/";
	private String uri = "/Users/Yosuke/documents/workspace/adauction/src/adspaces/20130327/";
	private String fileTailName = "bid.csv";

	public ValuesSetter(int[] outerAds, int[] outerAdSpaces) {
		ads = outerAds;
		adSpaces = outerAdSpaces;
	}

	public double[][] setBid(int bidMaxLength) {
		double bid[][] = new double[adSpaces.length][bidMaxLength];
		try {
			for (int i = 0; i < bid.length; i++) {
				FileReader fR = new FileReader(uri + adSpaces[i] + fileTailName);
				BufferedReader bR = new BufferedReader(fR);
				String str = bR.readLine();
				for (int j = 0; str != null; j++) {
					StringTokenizer sT = new StringTokenizer(str, ",");
					bid[i][j] = Double.parseDouble(sT.nextToken());
					str = bR.readLine();
				}
				bR.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bid;
	}

	public long[][] setImp(int impMaxLength) {
		long imp[][] = new long[adSpaces.length][impMaxLength];
		try {
			for (int i = 0; i < imp.length; i++) {
				FileReader fR = new FileReader(uri + adSpaces[i] + fileTailName);
				BufferedReader bR = new BufferedReader(fR);
				String str = bR.readLine();
				for (int j = 0; str != null; j++) {
					StringTokenizer sT = new StringTokenizer(str, ",");
					sT.nextToken();
					imp[i][j] = Long.parseLong(sT.nextToken());
					// imp[i][j] = (long)(2 * imp[i][j]);
					str = bR.readLine();
				}
				bR.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imp;
	}

	public double[] setBudget() {
		double budget[] = new double[ads.length];
		try {
			FileReader fR = new FileReader(metaUri + "start.csv");
			BufferedReader bR = new BufferedReader(fR);
			String str = bR.readLine();
			while (str != null) {
				StringTokenizer sT = new StringTokenizer(str, ",");
				int ad = Integer.parseInt(sT.nextToken());
				int adSpace = Integer.parseInt(sT.nextToken());
				for (int k = 0; k < ads.length; k++) {
					for (int l = 0; l < adSpaces.length; l++) {
						if ((ad == ads[k]) && (adSpace == adSpaces[l])) {
							sT.nextToken();
							budget[k] += Double.parseDouble(sT.nextToken());
						}
					}
				}
				str = bR.readLine();
			}
			bR.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return budget;
	}

	public double[] setCpa() {
		double cpa[] = new double[ads.length];
		try {
			FileReader fR = new FileReader(metaUri + "ads.csv");
			BufferedReader bR = new BufferedReader(fR);
			String str = bR.readLine();
			while (str != null) {
				StringTokenizer sT = new StringTokenizer(str, ",");
				int temp = Integer.parseInt(sT.nextToken());
				for (int k = 0; k < ads.length; k++) {
					if (temp == ads[k]) {
						sT.nextToken();
						cpa[k] = Double.parseDouble(sT.nextToken());
					}
				}
				str = bR.readLine();
			}
			bR.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cpa;
	}

	public double[][] setIcvr() {
		double icvr[][] = new double[ads.length][adSpaces.length];
		try {
			FileReader fR = new FileReader(metaUri + "icvr.csv");
			BufferedReader bR = new BufferedReader(fR);
			String str = bR.readLine();
			while (str != null) {
				for (int i = 0; i < icvr.length; i++) {
					for (int j = 0; j < icvr[0].length; j++) {
						StringTokenizer sT = new StringTokenizer(str, ",");
						if ((Integer.parseInt(sT.nextToken()) == ads[i])
								&& (Integer.parseInt(sT.nextToken()) == adSpaces[j])) {
							icvr[i][j] = Double.parseDouble(sT.nextToken());
						}
					}
				}
				str = bR.readLine();
			}
			bR.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return icvr;
	}

	public long[] setStart() {
		long start[] = new long[adSpaces.length];
		try {
			FileReader fR = new FileReader(metaUri + "start.csv");
			BufferedReader bR = new BufferedReader(fR);
			String str = bR.readLine();
			while (str != null) {
				for (int i = 0; i < ads.length; i++) {
					for (int j = 0; j < adSpaces.length; j++) {
						StringTokenizer sT = new StringTokenizer(str, ",");
						int temp = Integer.parseInt(sT.nextToken());
						if (Integer.parseInt(sT.nextToken()) == adSpaces[j]) {
							if (temp == 0) {
								start[j] += Long.parseLong(sT.nextToken());
							} else if (temp == ads[i]) {
								start[j] -= ads.length
										* Long.parseLong(sT.nextToken());
							}
						}
					}
				}
				str = bR.readLine();
			}
			bR.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < start.length; i++) {
			start[i] /= ads.length;
		}
		// 初期インプレッション数を0にする
		// for (int i = 0; i < start.length; i++) {
		// start[i] = 0;
		// }
		return start;
	}

	public long[][] setUpperLimit() {
		long upper[][] = new long[ads.length][adSpaces.length];
		try {
			FileReader fR = new FileReader(metaUri + "start.csv");
			BufferedReader bR = new BufferedReader(fR);
			String str = bR.readLine();
			while (str != null) {
				for (int i = 0; i < upper.length; i++) {
					for (int j = 0; j < upper[0].length; j++) {
						StringTokenizer sT = new StringTokenizer(str, ",");
						if ((Integer.parseInt(sT.nextToken()) == ads[i])
								&& (Integer.parseInt(sT.nextToken()) == adSpaces[j])) {
							sT.nextToken();
							sT.nextToken();
							upper[i][j] = Long.parseLong(sT.nextToken());
						}
					}
				}
				str = bR.readLine();
			}
			bR.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return upper;
	}
}
