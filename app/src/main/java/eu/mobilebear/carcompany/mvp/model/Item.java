package eu.mobilebear.carcompany.mvp.model;

import io.realm.RealmModel;

/**
 * @author bartoszbanczerowski@gmail.com Created on 19.01.2017.
 */
class Item implements RealmModel {

  private String id;
  private String name;

  Item(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
