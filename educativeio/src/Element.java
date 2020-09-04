import java.time.LocalTime;
import java.util.SortedSet;
import java.util.TreeSet;

public class Element implements Comparable{
 int id;
 Element(int id){
	 this.id=id;
 }
 public int compareTo(Object obj) {
	 Element e = (Element) obj;
	 return this.id -e.id;
 }
	
 public String toString() {
	 return ""+this.id;
 }
 
 public static void main(String[] args) {
	 StringBuilder sb= new StringBuilder("buffering");
	 sb.replace(2, 7, "BUFFER");
	 sb.delete(2, 4);
	 String s= sb.substring(1,5);
	 System.out.println(s);
	 Example3 es= new Example3();
	 es.display2();
 }
}

abstract class Example2{
	   public void display2(){
	      System.out.println("display2 method");
	   }
	}

class Example3 extends Example2{

	
}