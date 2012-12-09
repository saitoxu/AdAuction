package saitoxu.adauction;

public class Organizer {
	public static void main(String args[]) {
		int changeValue = 10; // �ύX�ł������l
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
		long current[][] = new long[ads.length][adSpaces.length]; // ���̃C���v���b�V������
		long previous[][] = new long[ads.length][adSpaces.length]; // �O�̃X�e�b�v�ł̃C���v���b�V������
		
		ValuesSetter setter = new ValuesSetter(ads, adSpaces);
		MarketModel mM = new MarketModel();
		bid = setter.setBid(bid[0].length);
		imp = setter.setImp(imp[0].length);
		budget = setter.setBudget();
		cpa = setter.setCpa();
		icvr = setter.setIcvr();
		sum = setter.setStart(); // �e�L���g�̃C���v���b�V�������̏����l�i���̍L�����l�����Ă镪�j
		price = mM.setBid(adSpaces.length, bid, imp, sum);
		
		for (int i = 0; i < ads.length; i++) {
			System.out.println("budget[" + i + "] = " + budget[i] + ", cpa[" + i + "] = " + cpa[i]);
		}
		for (int i = 0; i < ads.length; i++) {
			for (int j = 0; j < adSpaces.length; j++) {
				System.out.println("icvr[" + i + "][" + j + "] = " + icvr[i][j]);
			}
		}
		for (int i = 0; i < adSpaces.length; i++) {
			System.out.println("sum[" + i + "] = " + sum[i]);
		}
		for (int i = 0; i < price.length; i++) {
			System.out.println("price[" + i + "] = " + price[i]);
		}
		System.out.println("boolean = " + mM.isEndCalculation(current, previous));
//		for (int i = 0; i < adSpaces.length; i++) {
//			for (int j = 0; bid[i][j] != 0; j++) {
//				System.out.println("bid[" + i + "][" + j + "] = " + bid[i][j] + ", imp[" + i + "][" + j + "] = "  + imp[i][j]);
//			}
//		}
	}
}
