package saitoxu.adauction;

import java.io.*;
import java.util.*;

public class ValuesSetter {
	private int ads[];
	private int adSpaces[];
	
	public ValuesSetter(int[] outerAds, int[] outerAdSpaces) {
		ads = outerAds;
		adSpaces = outerAdSpaces;
	}
	
	public double[][] setBid(int bidMaxLength) {
		double bid[][] = new double[adSpaces.length][bidMaxLength];
		try {
			for (int i = 0; i < bid.length; i++) {
				FileReader fR = new FileReader("/Users/Yosuke/documents/workspace/adauction/src/adspaces/" + adSpaces[i] + ".csv");
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
				FileReader fR = new FileReader("/Users/Yosuke/documents/workspace/adauction/src/adspaces/" + adSpaces[i] + ".csv");
				BufferedReader bR = new BufferedReader(fR);
				String str = bR.readLine();
				for (int j = 0; str != null; j++) {
					StringTokenizer sT = new StringTokenizer(str, ",");
					sT.nextToken();
					imp[i][j] = Long.parseLong(sT.nextToken());
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
			FileReader fR = new FileReader("/Users/Yosuke/documents/workspace/adauction/src/ads.csv");
			BufferedReader bR = new BufferedReader(fR);
			String str = bR.readLine();
			while (str != null) {
				StringTokenizer sT = new StringTokenizer(str, ",");
				int temp = Integer.parseInt(sT.nextToken());
				for (int k = 0; k < ads.length; k++) {
					if (temp == ads[k]) {
						budget[k] = Double.parseDouble(sT.nextToken());
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
			FileReader fR = new FileReader("/Users/Yosuke/documents/workspace/adauction/src/ads.csv");
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
			FileReader fR = new FileReader("/Users/Yosuke/documents/workspace/adauction/src/icvr.csv");
			BufferedReader bR = new BufferedReader(fR);
			String str = bR.readLine();
			while (str != null) {
				for (int i = 0; i < icvr.length; i++) {
					for (int j = 0; j < icvr[0].length; j++) {
						StringTokenizer sT = new StringTokenizer(str, ",");
						if ((Integer.parseInt(sT.nextToken()) == ads[i]) && (Integer.parseInt(sT.nextToken()) == adSpaces[j])) {
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
			FileReader fR = new FileReader("/Users/Yosuke/documents/workspace/adauction/src/start.csv");
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
								start[j] -= 3 * Long.parseLong(sT.nextToken());
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
			start[i] /= start.length;
		}
		return start;
	}
}
