package eu.mobilebear.carcompany.mvp.view;

import eu.mobilebear.carcompany.mvp.model.MainType;
import java.util.List;

/**
 * @author bartoszbanczerowski@gmail.com Created on 19.01.2017.
 */

public interface MainTypeView {

  void showMainTypes(List<MainType> mainTypes);

  void showLoading();

  void showError(String message);

}
