package saitoxu.adauction;

public class Organizer {
	public static void main(String args[]) {
		int maxChange = 1000; // �ύX�ł������l
		int bidLength = 2500; // ���D�z-�C���v���b�V�������֐��̓��D�z�̎�ނ̏���l
		int ads[] = {10562, 11270, 6578, 9215, 11614}; // �L���̎��
		int adSpaces[] = {2341, 2337, 60, 6105, 1712}; // �L���g�̎��
		double bid[][] = new double[adSpaces.length][bidLength]; // ���D�z�֐�������z��
		long imp[][] = new long[adSpaces.length][bidLength]; // ���D�z�ɑΉ������C���v���b�V������������z��
		double budget[] = new double[ads.length]; // �L�����Ƃ̗\�Z
		double cpa[] = new double[ads.length]; // �L�����Ƃ�cpa
		double icvr[][] = new double[ads.length][adSpaces.length]; // �L���C�L���g���Ƃ�icvr
		long start[] = new long[adSpaces.length]; // ���̍L�����e�L���g�Ŋl�����Ă���C���v���b�V������
		long sum[] = new long[adSpaces.length]; // �e�L���g�̃C���v���b�V�������̍��v�l
		double price[] = new double[adSpaces.length]; // �L���g�ւ̓��D�z
		long beforeImps[][] = new long[ads.length][adSpaces.length]; // 1�O�̃C���v���b�V������
		long before2Imps[][] = new long[ads.length][adSpaces.length]; // 2�O�̃C���v���b�V�������i�I�������̔���Ɏg���j
		long imps[][] = new long[ads.length][adSpaces.length]; // �C���v���b�V������
		
		ValuesSetter setter = new ValuesSetter(ads, adSpaces);
		MarketModel mM = new MarketModel();
		bid = setter.setBid(bid[0].length);
		imp = setter.setImp(imp[0].length);
		budget = setter.setBudget();
		cpa = setter.setCpa();
		icvr = setter.setIcvr();
		start = setter.setStart(); // �e�L���g�̃C���v���b�V�������̏����l�i���̍L�����l�����Ă镪�j
		price = mM.setBid(adSpaces.length, bid, imp, start);
		GreedySearcher struct[] = new GreedySearcher[ads.length];
		
		for (int i = 0; i < ads.length; i++) {
			struct[i] = new GreedySearcher(budget[i], cpa[i], icvr[i], maxChange, adSpaces.length);
		}
		
		for (int count = 0; count < 10000; count++) {
			for (int i = 0; i < imps.length; i++) {
				before2Imps[i] = beforeImps[i];
				beforeImps[i] = imps[i];
				imps[i] = struct[i].greedySearch(price, imps[i]);
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
			
			// ���i�𒲐�
			price = mM.setPrice(price, bid, imp, sum, start);
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
			
			// �I������
			if (mM.isEndCalculation(imps, before2Imps)) {
				break;
			}
		}

		// �Ȃ񂩃��������[�N�����ŏC���i���܂Ƀo�O��L���E�L���g�̑g�ݍ��킹����j
		// ���ۂ̃C���v���b�V�������Ɣ�r
	}
}
