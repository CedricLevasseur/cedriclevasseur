/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.cedriclevasseur.mcexample;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cle
 */
public class Traitement
{
  /**
   * Un traitement quelconque qui retourne une liste;
   * @return a list of MC
   */
  public static List<Mc> doTraitement(){
  
    Mc mc1= new Mc("MC1", "0102030405","0102030405" );
    Mc mc2= new Mc("MC2", "0202030405","0202030405" );
    Mc mc3= new Mc("MC3", "0302030405","0302030405" );
    
    List<Mc> result = new ArrayList<Mc>();
    result.add(mc1);
    result.add(mc2);
    result.add(mc3);
    return result;
    
  }
}
