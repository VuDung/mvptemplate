package com.tnc.template.data.util;

import android.text.TextUtils;

/**
 * Created by CUSDungVT on 1/17/2017.
 */

public class StringUtils {

  public static String upperCaseFirstCharacter(String input){
    if(TextUtils.isEmpty(input)){
      return input;
    }
    if(input.length() > 1) {
      //input text has >1 character
      return input.substring(0, 1).toUpperCase() + input.substring(1, input.length());
    }else{
      //input text has 1 character
      return input.toUpperCase();
    }
  }
}
