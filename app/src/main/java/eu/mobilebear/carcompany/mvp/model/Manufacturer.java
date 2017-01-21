package eu.mobilebear.carcompany.mvp.model;

/**
 * @author bartoszbanczerowski@gmail.com Created on 19.01.2017.
 */

public class Manufacturer extends Item {

  private boolean isCheckedForSearch;

  public Manufacturer(String id, String name) {
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
