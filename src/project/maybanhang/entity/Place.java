package project.maybanhang.entity;

import java.util.ArrayList;

public class Place {
     public int Place_id;
     public String Name;
     public boolean IsActive=false;
     public ArrayList<Table> Tables=new ArrayList<Table>();
     @Override
    public String toString() {
    	return "Name:"+Name+"Place_id:"+Place_id;
    }
}
