package saitoxu.adauction;

import org.gnu.glpk.*;

public class Lp {
	private double budget;
	private double cpa;
	private double[] icvr;
	private long[] imps;
	private int maxChange;
	int adSpaceLength;
	long[] upper;

	public Lp(double myBudget, double myCpa, double[] myIcvr, int myMaxChange,
			long[] upperBound, int length) {
		budget = myBudget;
		cpa = myCpa;
		icvr = myIcvr;
		maxChange = myMaxChange;
		adSpaceLength = length;
		upper = upperBound;
		imps = new long[adSpaceLength];
	}

	public long[] solveLp(double[] price, long[] previousImps) {
		glp_prob lp;
		glp_smcp parm;
		SWIGTYPE_p_int ind;
		SWIGTYPE_p_double val;
		int ret;
		try {
			lp = GLPK.glp_create_prob();
			GLPK._glp_lpx_set_int_parm(lp, GLPK.LPX_K_MSGLEV, 1);
			GLPK.glp_set_prob_name(lp, "myProblem");

			GLPK.glp_add_cols(lp, adSpaceLength);
			for (int i = 1; i <= adSpaceLength; i++) {
				GLPK.glp_set_col_name(lp, i, "x" + i);
				GLPK.glp_set_col_kind(lp, i, GLPKConstants.GLP_CV);
				long lowerLimit = previousImps[i - 1] - maxChange;
				long upperLimit = previousImps[i - 1] + maxChange;
				if (lowerLimit < 0) {
					lowerLimit = 0;
				}
				// 実際の入札回数でキャップ
				// if (upperLimit > upper[i - 1]) {
				// upperLimit = upper[i - 1];
				// }
				GLPK.glp_set_col_bnds(lp, i, GLPKConstants.GLP_DB, lowerLimit,
						upperLimit);
			}

			GLPK.glp_add_rows(lp, 2);

			// constraint1
			GLPK.glp_set_row_name(lp, 1, "c1");
			GLPK.glp_set_row_bnds(lp, 1, GLPKConstants.GLP_UP, 0.0, budget);
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

			// constraint2
			GLPK.glp_set_row_name(lp, 2, "c2");
			GLPK.glp_set_row_bnds(lp, 2, GLPKConstants.GLP_UP, 0.0, 0.0);
			ind = GLPK.new_intArray(adSpaceLength + 1);
			for (int i = 1; i <= adSpaceLength; i++) {
				GLPK.intArray_setitem(ind, i, i);
			}
			val = GLPK.new_doubleArray(adSpaceLength + 1);
			for (int i = 1; i <= adSpaceLength; i++) {
				// icvrないやつは0入れたらいいんちゃう？
				// icvrとcpa等倍にしても結果いっしょ？
				if (icvr[i - 1] == 1) {
					GLPK.doubleArray_setitem(val, i, 0);
				} else {
					GLPK.doubleArray_setitem(val, i, price[i - 1] - cpa
							* icvr[i - 1]);
				}
			}
			GLPK.glp_set_mat_row(lp, 2, adSpaceLength, ind, val);
			GLPK.delete_intArray(ind);
			GLPK.delete_doubleArray(val);

			// obj
			GLPK.glp_set_obj_name(lp, "z");
			GLPK.glp_set_obj_dir(lp, GLPKConstants.GLP_MAX);
			GLPK.glp_set_obj_coef(lp, 0, 0.0);
			for (int i = 1; i <= adSpaceLength; i++) {
				// インプレッション数最大化
				GLPK.glp_set_obj_coef(lp, i, 1.0);
				// コンバージョン数最大化
				// GLPK.glp_set_obj_coef(lp, i, icvr[i - 1]);
			}
			parm = new glp_smcp();
			GLPK.glp_init_smcp(parm);
			ret = GLPK.glp_simplex(lp, parm);
			if (ret == 0) {
				imps = write_lp_solution(lp, price);
			} else {
				System.out.println("The problem could not be solved");
			}
			GLPK.glp_delete_prob(lp);
		} catch (GlpkException ex) {
			ex.printStackTrace();
		}
		return imps;
	}

	private long[] write_lp_solution(glp_prob lp, double[] price) {
		int n;
		double val;
		long[] tempImps = new long[adSpaceLength];
		val = GLPK.glp_get_obj_val(lp);
		n = GLPK.glp_get_num_cols(lp);
		for (int i = 1; i <= n; i++) {
			val = GLPK.glp_get_col_prim(lp, i);
			tempImps[i - 1] = (long) val;
		}
		return tempImps;
	}
}