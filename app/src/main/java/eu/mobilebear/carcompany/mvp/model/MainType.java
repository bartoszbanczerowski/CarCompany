package eu.mobilebear.carcompany.mvp.model;

/**
 * @author bartoszbanczerowski@gmail.com Created on 16.01.2017.
 */
public class MainType extends Item {

  private boolean isCheckedForSearch;

  public MainType(String id, String name) {
    super(id, name);
    this.isCheckedForSearch = false;
  }

  public boolean isCheckedForSearch() {
    return isCheckedForSearch;
  }

  public void setCheckedForSearch(boolean checkedForSearch) {
    isCheckedForSearch = checkedForSearch;
  }

}
