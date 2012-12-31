package saitoxu.adauction;

import org.gnu.glpk.*;

public class Lp {
	private double[] price;
	private double budget;
	private double cpa;
	private double[] icvr;
	private long[] imps;
	private long[] previousImps;
	private int maxChange;
	int adSpaceLength;
	
	public Lp(double[] myPrice, long[] myPreviousImps, double myBudget, double myCpa, double[] myIcvr, int myMaxChange) {
		price = myPrice;
		budget = myBudget;
		cpa = myCpa;
		icvr = myIcvr;
		previousImps = myPreviousImps;
		maxChange = myMaxChange;
		adSpaceLength = myPreviousImps.length;
		imps = new long[adSpaceLength];
	}
	public long[] solveLp() {
		glp_prob lp;
		glp_smcp parm;
		SWIGTYPE_p_int ind;
		SWIGTYPE_p_double val;
		int ret;

		try {
		    lp = GLPK.glp_create_prob();
		    GLPK._glp_lpx_set_int_parm(lp, GLPK.LPX_K_MSGLEV, 1);
	    	GLPK.glp_set_prob_name(lp, "myProblem");

	    	// 変数の制約
	    	GLPK.glp_add_cols(lp, adSpaceLength);
	    	for (int i = 1; i <= adSpaceLength; i++) {
	    		GLPK.glp_set_col_name(lp, i, "x" + i);
	    		GLPK.glp_set_col_kind(lp, i, GLPKConstants.GLP_CV);
	    		long lowerLimit = previousImps[i - 1] - maxChange;
	    		if (lowerLimit < 0) {
	    			lowerLimit = 0;
	    		}
		    	GLPK.glp_set_col_bnds(lp, i, GLPKConstants.GLP_DB, lowerLimit, previousImps[i - 1] + maxChange);
		    	// System.out.println(lowerLimit + ", " + (previousImps[i - 1] + maxChange));
	    	}
	    	
		    // 制約条件（予算制約とCPA制約の2つ）
	    	// 制約の使い方間違ってる感あるな
	    	GLPK.glp_add_rows(lp, 2);
		    GLPK.glp_set_row_name(lp, 1, "c1");
		    GLPK.glp_set_row_bnds(lp, 1, GLPKConstants.GLP_UP, 0.0, budget);
		    System.out.println("Budget = " + budget);
	    	ind = GLPK.new_intArray(adSpaceLength + 1);
	    	for (int i = 1; i <= adSpaceLength; i++) {
	    		GLPK.intArray_setitem(ind, i, i);
	    	}
	    	
	    	val = GLPK.new_doubleArray(adSpaceLength + 1);
	    	for (int i = 1; i <= adSpaceLength; i++) {
	    		GLPK.doubleArray_setitem(val, i, price[i - 1]);
	    	}
	    	
	    	GLPK.glp_set_mat_row(lp, 1, adSpaceLength, ind, val);
		    GLPK.delete_intArray(ind);
	    	GLPK.delete_doubleArray(val);
	    	
	    	GLPK.glp_set_row_name(lp, 2, "c2");
		    GLPK.glp_set_row_bnds(lp, 2, GLPKConstants.GLP_UP, 0.0, 0.0);
	    	ind = GLPK.new_intArray(adSpaceLength + 1);
	    	for (int i = 1; i <= adSpaceLength; i++) {
	    		GLPK.intArray_setitem(ind, i, i);
	    	}
	    	
	    	val = GLPK.new_doubleArray(adSpaceLength + 1);
	    	for (int i = 1; i <= adSpaceLength; i++) {
	    		GLPK.doubleArray_setitem(val, i, price[i - 1] - cpa * icvr[i - 1]);
	    	}
	    	
	    	GLPK.glp_set_mat_row(lp, 2, adSpaceLength, ind, val);
		    GLPK.delete_intArray(ind);
	    	GLPK.delete_doubleArray(val);

	    	// 目的関数
	    	GLPK.glp_set_obj_name(lp, "z");
	    	GLPK.glp_set_obj_dir(lp, GLPKConstants.GLP_MAX);
	    	GLPK.glp_set_obj_coef(lp, 0, 0.0);
	    	for (int i = 1; i <= adSpaceLength; i++) {
	    		GLPK.glp_set_obj_coef(lp, i, 1.0);
	    	}
	    	
	    	parm = new glp_smcp();
	    	GLPK.glp_init_smcp(parm);
	    	ret = GLPK.glp_simplex(lp, parm);
	    	
	    	if (ret == 0) {
				imps = write_lp_solution(lp);
	    	} else {
				System.out.println("The problem could not be solved");
	    	}
	    	
	    	GLPK.glp_delete_prob(lp);
		} catch (GlpkException ex) {
	    	ex.printStackTrace();
		}
		
		return imps;
	}

	private long[] write_lp_solution(glp_prob lp) {
		int n;
		// String name;
		double val;
		long[] tempImps = new long[adSpaceLength];

		// name = GLPK.glp_get_obj_name(lp);
		val = GLPK.glp_get_obj_val(lp);
		// System.out.println(name + " = " + val);
		n = GLPK.glp_get_num_cols(lp);
		for (int i = 1; i <= n; i++) {
			// name = GLPK.glp_get_col_name(lp, i);
			val = GLPK.glp_get_col_prim(lp, i);
			tempImps[i - 1] = (long)val;
			// System.out.println(name + " = " + tempImps[i - 1]);
		}
		
		return tempImps;
	}
}