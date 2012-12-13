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
	    	GLPK.glp_set_prob_name(lp, "myProblem");

	    	// ïœêîÇÃêßñÒ
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
		    // GLPK.glp_set_col_name(lp, 1, "x1");
		    // GLPK.glp_set_col_kind(lp, 1, GLPKConstants.GLP_CV);
	    	// GLPK.glp_set_col_bnds(lp, 1, GLPKConstants.GLP_DB, 0, 0.5);
	    	// GLPK.glp_set_col_name(lp, 2, "x2");
		    // GLPK.glp_set_col_kind(lp, 2, GLPKConstants.GLP_CV);
		    // GLPK.glp_set_col_bnds(lp, 2, GLPKConstants.GLP_DB, 0, 0.5);

		    // êßñÒèåèÅió\éZêßñÒÇ∆CPAêßñÒÇÃ2Ç¬Åj
	    	GLPK.glp_add_rows(lp, 2);
		    GLPK.glp_set_row_name(lp, 1, "c1");
		    GLPK.glp_set_row_bnds(lp, 1, GLPKConstants.GLP_DB, 0, 0.2);
	    	ind = GLPK.new_intArray(3);
		    GLPK.intArray_setitem(ind, 1, 1);
		    GLPK.intArray_setitem(ind, 2, 2);
	    	val = GLPK.new_doubleArray(3);
		    GLPK.doubleArray_setitem(val, 1, 1.);
		    GLPK.doubleArray_setitem(val, 2, -1.);
	    	GLPK.glp_set_mat_row(lp, 1, 2, ind, val);
		    GLPK.delete_intArray(ind);
	    	GLPK.delete_doubleArray(val);

	    	// ñ⁄ìIä÷êî
	    	GLPK.glp_set_obj_name(lp, "z");
	    	GLPK.glp_set_obj_dir(lp, GLPKConstants.GLP_MAX);
	    	GLPK.glp_set_obj_coef(lp, 0, 0.0);
	    	for (int i = 1; i <= adSpaceLength; i++) {
	    		GLPK.glp_set_obj_coef(lp, i, 1.0);
	    	}
	    	// GLPK.glp_set_obj_coef(lp, 1, 1.0);
	    	// GLPK.glp_set_obj_coef(lp, 2, 1.0);

	    	parm = new glp_smcp();
	    	GLPK.glp_init_smcp(parm);
	    	ret = GLPK.glp_simplex(lp, parm);

	    	if (ret == 0) {
				write_lp_solution(lp);
	    	} else {
				System.out.println("The problem could not be solved");
	    	}

	    	GLPK.glp_delete_prob(lp);
		} catch (GlpkException ex) {
	    	ex.printStackTrace();
		}
		
		return imps;
	}

	private void write_lp_solution(glp_prob lp) {
		int n;
		String name;
		double val;

		name = GLPK.glp_get_obj_name(lp);
		val = GLPK.glp_get_obj_val(lp);
		System.out.println(name + " = " + val);
		n = GLPK.glp_get_num_cols(lp);
		for (int i = 1; i <= n; i++) {
			name = GLPK.glp_get_col_name(lp, i);
			val = GLPK.glp_get_col_prim(lp, i);
			System.out.println(name + " = " + val);
		}
	}
}
