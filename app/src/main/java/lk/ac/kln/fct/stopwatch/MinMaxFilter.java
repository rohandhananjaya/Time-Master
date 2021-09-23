/**
 * <h1>Time Master</h1>
 * This is a simple app with stopwatch and a countdown timer.
 * <p>
 *
 *
 * @author  		Amarasooriya K.R.D (CT/2017/006)
 * @version 		1.0
 * @since   		2021-09-24
 * @link            https://github.com/rohandhananjaya/Time-Master.git
 *
 */

package lk.ac.kln.fct.stopwatch;

import android.text.InputFilter ;
import android.text.Spanned ;
public class MinMaxFilter implements InputFilter {
    private int mIntMin , mIntMax ;
    public MinMaxFilter ( int minValue , int maxValue) {
        this . mIntMin = minValue ;
        this . mIntMax = maxValue ;
    }
    public MinMaxFilter (String minValue , String maxValue) {
        this . mIntMin = Integer. parseInt (minValue) ;
        this . mIntMax = Integer. parseInt (maxValue) ;
    }
    @Override
    public CharSequence filter (CharSequence source , int start , int end , Spanned dest ,int dstart , int dend) {
        try {
            int input = Integer. parseInt (dest.toString() + source.toString()) ;
            if (isInRange( mIntMin , mIntMax , input))
                return null;
        } catch (NumberFormatException e) {
            e.printStackTrace() ;
        }
        return "" ;
    }
    private boolean isInRange ( int a , int b , int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a ;
    }
}
