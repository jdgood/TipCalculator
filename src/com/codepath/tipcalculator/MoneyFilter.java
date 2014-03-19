package com.codepath.tipcalculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.InputFilter;
import android.text.Spanned;

public class MoneyFilter implements InputFilter {
	private Pattern mPattern = Pattern.compile("(([0-9]*\\.[0-9]{0,1})||[0-9]*)?");

	@Override
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		Matcher matcher=mPattern.matcher(dest);       
        if(!matcher.matches()) {
            return "";
        }
        return null;
	}

}
