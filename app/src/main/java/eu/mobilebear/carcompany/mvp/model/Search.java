package eu.mobilebear.carcompany.mvp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author bartoszbanczerowski@gmail.com Created on 21.01.2017.
 */
public class Search extends RealmObject {

  @PrimaryKey
  private String idItem;

  public Search() {
  }

  public Search(String idItem) {
    this.idItem = idItem;
  }

  public String getIdItem() {
    return idItem;
  }

  public void setIdItem(String idItem) {
    this.idItem = idItem;
  }
}
