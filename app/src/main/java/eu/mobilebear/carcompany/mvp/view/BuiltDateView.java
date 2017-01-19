package eu.mobilebear.carcompany.mvp.view;

import eu.mobilebear.carcompany.mvp.model.BuiltDate;
import java.util.List;

/**
 * @author bartoszbanczerowski@gmail.com Created on 19.01.2017.
 */
public interface BuiltDateView {

  void showBuiltDates(List<BuiltDate> builtDates);

  void showLoading();

  void showError(String message);
}
