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
		long start[] = new long[adSpaces.length]; // �C���v���b�V�������̏����l�i���̍L�����l�����Ă镪�j
		double p[] = new double[adSpaces.length]; // �L���g�ւ̓��D�z
		long x[][] = new long[ads.length][adSpaces.length]; // ���̃C���v���b�V������
		long y[][] = new long[ads.length][adSpaces.length]; // �O�̃X�e�b�v�ł̃C���v���b�V������
		
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
