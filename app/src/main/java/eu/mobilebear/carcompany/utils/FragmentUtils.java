package eu.mobilebear.carcompany.utils;

import android.support.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author bartoszbanczerowski@gmail.com Created on 19.01.2017.
 */
public class FragmentUtils {

  public static final String MANUFACTURER_FRAGMENT = "manufactuersFragment";
  public static final String MAIN_TYPES_FRAGMENT = "mainTypesFragment";
  public static final String BUILT_DATES_FRAGMENT = "builtDatesFragment";
  public static final String CAR_SEARCH_FRAGMENT = "carSearchFragment";
  public static final String CAR_FRAGMENT = "carFragment";

  @StringDef({MANUFACTURER_FRAGMENT, MAIN_TYPES_FRAGMENT, BUILT_DATES_FRAGMENT,
      CAR_SEARCH_FRAGMENT, CAR_FRAGMENT})
  @Retention(RetentionPolicy.SOURCE)
  public @interface TagFragment {

  }

}
