package biz.cedriclevasseur.mcexample;

/**
 * Mc is a pure POJO
 * @author cle
 */
public class Mc
{

  String name;
  
  String phone;
  
  String fax;
 
  public Mc(String name, String phone, String fax){
    
    this.name=name;
    this.phone=phone;
    this.fax=fax;
    
  }

  public String getName()
  {
    return name;
  }

  public void setName( String name )
  {
    this.name = name;
  }

  public String getPhone()
  {
    return phone;
  }

  public void setPhone( String phone )
  {
    this.phone = phone;
  }

  public String getFax()
  {
    return fax;
  }

  public void setFax( String fax )
  {
    this.fax = fax;
  }
  /**
   * toString is used to populate a JS Array. Must be compatible JSON. 
   * @return a string compatible JSON
   */
  public String toString(){
    String result= "{ \"name\" : \""+name+ "\"";
    result += ", \"phone\" : \""+phone+"\"";
    result += ", \"fax\" : \""+fax+"\"";
    result += "}";
    
    return result;
  }
}
