package saitoxu.adauction;

public class Organizer {
	public static void main(String args[]) {
		int maxChange = 100; // �ύX�ł������l
		int bidLength = 2500; // ���D�z-�C���v���b�V�������֐��̓��D�z�̎�ނ̏���l
		int ads[] = {6578, 10562, 9215}; // �L���̎��
		int adSpaces[] = {60, 6105, 2341}; // �L���g�̎��
		double bid[][] = new double[adSpaces.length][bidLength]; // ���D�z�֐�������z��
		long imp[][] = new long[adSpaces.length][bidLength]; // ���D�z�ɑΉ������C���v���b�V������������z��
		double budget[] = new double[ads.length]; // �L�����Ƃ̗\�Z
		double cpa[] = new double[ads.length]; // �L�����Ƃ�cpa
		double icvr[][] = new double[ads.length][adSpaces.length]; // �L���C�L���g���Ƃ�icvr
		long sum[] = new long[adSpaces.length]; // �e�L���g�̃C���v���b�V�������̍��v�l
		double price[] = new double[adSpaces.length]; // �L���g�ւ̓��D�z
		long imps[][] = new long[ads.length][adSpaces.length]; // �C���v���b�V������
		
		ValuesSetter setter = new ValuesSetter(ads, adSpaces);
		MarketModel mM = new MarketModel();
		bid = setter.setBid(bid[0].length);
		imp = setter.setImp(imp[0].length);
		budget = setter.setBudget();
		cpa = setter.setCpa();
		icvr = setter.setIcvr();
		sum = setter.setStart(); // �e�L���g�̃C���v���b�V�������̏����l�i���̍L�����l�����Ă镪�j
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
		// �ȉ��f�o�b�O�p
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
