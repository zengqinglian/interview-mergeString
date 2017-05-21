package fragment.submissions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.junit.Test;

public class EricZeng {
	
	 public static void main(String[] args) {

		 try (BufferedReader in = new BufferedReader(new FileReader(args[0]))) { 
			 String fragmentProblem;
             while ((fragmentProblem = in.readLine()) != null) {
                 System.out.println(reassemble(fragmentProblem));
             }

		 }catch (Exception e) {
		     e.printStackTrace();
		 }
	 } 
	 
	 private static String reassemble(String fragmentProblem){
	     String[] tokens = fragmentProblem.split(";");
	     List<String> tokenList = Arrays.asList(tokens);
	     List<String> workList = new ArrayList<String>(tokenList);
	     return reassemble(workList);
	 }
	 
	 private static String reassemble(List<String> list){
	     if(list.size()==1){
	         return list.get(0);
	     }else{
	         merge(list);
	         return reassemble(list);
	     }     
	 }
	 
	 
	 
	 private static void merge(List<String> list){
	     Pair<String,String> pair = new Pair<String,String>();;
	     int maxOverLap = 0;
	     for(int i=0; i<list.size(); i++){
	         for(int j=i+1; j<list.size(); j++){
	             int currentOverlap = calculateOverlap(list.get(i),list.get(j));
	             if(currentOverlap > maxOverLap){
	                 maxOverLap = currentOverlap;
	                 pair.left = list.get(i);
	                 pair.right = list.get(j);
	             }
	         }
	     }
	     if(maxOverLap <= 0){
	         throw new RuntimeException("According to the document, it is impossible in this test!");
	     }
	     list.remove(pair.left);
	     list.remove(pair.right);
	     list.add(mergeTwoString(pair.getLeft(),pair.getRight(),maxOverLap));
	 }
	 
	 private static String mergeTwoString(String str1, String str2, int overlapLen){
	     if(overlapLen == 0){
	         return null;
	     }
	     
	     if(str1.contains(str2)){
	         return str1;
	     }
	     if(str2.contains(str1)){
	         return str2;
	     }
	     
	     String mergedString = null;
	     if(overlapLen > 0)
	     {
	         if(str1.substring(str1.length()-overlapLen).equals(str2.substring(0,overlapLen))){
	             mergedString = str1 + str2.substring(overlapLen);
	         }else if(str2.substring(str2.length()-overlapLen).equals(str1.substring(0,overlapLen))){
	             mergedString = str2 + str1.substring(overlapLen);
	         }else{
	             return null;
	         }
	         
	     }
	     return mergedString;
	 }
	 
	 private static int calculateOverlap(String str1, String str2){
	     if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
	         return 0;
	     }
	     
	     if(str1.contains(str2)){
             return str2.length();
         }
	     
	     if(str2.contains(str1)){
             return str1.length();
         }

	     int maxOverlapStr1First = str1.length() - 1;
	     while (maxOverlapStr1First>=0 && !str1.regionMatches(str1.length() - maxOverlapStr1First, str2, 0, maxOverlapStr1First)) {
	         maxOverlapStr1First--;
	     }
	     
	     int maxOverlapStr2First = str2.length() - 1;
         while (maxOverlapStr2First>=0 && !str2.regionMatches(str2.length() - maxOverlapStr2First, str1, 0, maxOverlapStr2First)) {
             maxOverlapStr2First--;
         }
	     return (maxOverlapStr1First >= maxOverlapStr2First) ? maxOverlapStr1First : maxOverlapStr2First;
	 }
	 
	 public static class Pair<L,R>{
	     private L left;
	     private R right;
	     
	     public Pair(){
           
         }
	     
	     public Pair(L left, R right){
	         this.left = left;
	         this.right = right;
	     }
	     public L getLeft(){
	         return this.left;
	     }
	     public R getRight(){
	         return this.right;
	     }
	     
	     public void setLeft(L left) {
            this.left = left;
        }

        public void setRight(R right) {
            this.right = right;
        }

        @Override
	     public boolean equals(Object o) {
	         if(o==null){
	             return false;
	         }
	         if (!(o instanceof Pair)) {
	             return false;
	         }
	         Pair<?, ?> p = (Pair<?, ?>) o;
	         return Objects.equals(left, p.left) && Objects.equals(p.right, right);
	     }

	     @Override
	     public int hashCode() {
	         return (left == null ? 0 : left.hashCode()) ^ (right == null ? 0 : right.hashCode());
	     }
	 }
	 
	 
	 public static class testEricZeng {
	     @Test
	     public void testMergeString(){
	         String str1="kdjkajsdf";
             String str2="sfadfd";
             assertNull(EricZeng.mergeTwoString(str1, str2, EricZeng.calculateOverlap(str1, str2)));
             
	         str1 = "abcdefg";
	         str2 = "bcdef";
	         assertEquals("abcdefg",EricZeng.mergeTwoString(str1, str2, EricZeng.calculateOverlap(str1, str2)));
	         
	         str1="cdefghijk";
	         str2="abcde";
	         assertEquals("abcdefghijk",EricZeng.mergeTwoString(str1, str2, EricZeng.calculateOverlap(str1, str2)));
	         
	         str1="abcdefgh";
             str2="defghijk";
             assertEquals("abcdefghijk",EricZeng.mergeTwoString(str1, str2, EricZeng.calculateOverlap(str1, str2)));	  
             
             str1="bcd";
             str2="cdefcb";
             assertEquals("bcdefcb",EricZeng.mergeTwoString(str1, str2, EricZeng.calculateOverlap(str1, str2)));
             
             str1="badfdd";
             str2="cdefcb";
             assertEquals("cdefcbadfdd",EricZeng.mergeTwoString(str1, str2, EricZeng.calculateOverlap(str1, str2)));
             
             /*no rule mensioned in the following situations. result can be bcdefabc or cdefabcd*/
             str1="bcd";
             str2="cdefabc";
             assertEquals("bcdefabc",EricZeng.mergeTwoString(str1, str2, EricZeng.calculateOverlap(str1, str2)));
             
             str2="bcd";
             str1="cdefabc";
             assertEquals("cdefabcd",EricZeng.mergeTwoString(str1, str2, EricZeng.calculateOverlap(str1, str2)));
	     }
	     
	     @Test
	     public void testCalcuateOverlap(){
	         String str1="kdjkajsdf";
             String str2="sfadfd";
             assertEquals(0,EricZeng.calculateOverlap(str1, str2));
             
             str1 = "abcdefg";
             str2 = "bcdef";
             assertEquals(5,EricZeng.calculateOverlap(str1, str2));
             
             str1="cdefghijk";
             str2="abcde";
             assertEquals(3,EricZeng.calculateOverlap(str1, str2));
             
             str1="abcdefgh";
             str2="defghijk";
             assertEquals(5,EricZeng.calculateOverlap(str1, str2));   
             
             str1="bcd";
             str2="cdefcb";
             assertEquals(2,EricZeng.calculateOverlap(str1, str2));  
             
             str1="bcd";
             str2="cdefabc";
             assertEquals(2,EricZeng.calculateOverlap(str1, str2));  
             
             str1="badfdd";
             str2="cdefcb";
             assertEquals(1,EricZeng.calculateOverlap(str1, str2));
	     }
	     
	     @Test
	     public void testReassemble(){
	         String str = "draconia;conian devil! Oh la;h lame sa;saint!";
	         String res = "draconian devil! Oh lame saint!";
	         assertEquals(res,EricZeng.reassemble(str));
	         
	         str = "m quaerat voluptatem.;pora incidunt ut labore et d;, consectetur, adipisci velit;olore magnam aliqua;idunt ut labore et dolore magn;uptatem.;i dolorem ipsum qu;iquam quaerat vol;psum quia dolor sit amet, consectetur, a;ia dolor sit amet, conse;squam est, qui do;Neque porro quisquam est, qu;aerat voluptatem.;m eius modi tem;Neque porro qui;, sed quia non numquam ei;lorem ipsum quia dolor sit amet;ctetur, adipisci velit, sed quia non numq;unt ut labore et dolore magnam aliquam qu;dipisci velit, sed quia non numqua;us modi tempora incid;Neque porro quisquam est, qui dolorem i;uam eius modi tem;pora inc;am al";
	         res = "Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem.";
	         assertEquals(res,EricZeng.reassemble(str));
	     }
	     
	     @Test
	     public void testPairEquals(){
	         String str1 = "oh";
	         String str2 = "mygod";
	         Pair<String,String> pair1 = new Pair<String,String>(str1,str2);
	         Pair<String,String> pair2 = new Pair<String,String>(str1,str2);
	         Pair<String,String> pair3 = new Pair<String,String>(str1,str1);
	         assertTrue(pair1.equals(pair2));
	         assertFalse(pair1.equals(str1));
	         assertFalse(pair1.equals(null));
	         assertFalse(pair1.equals(pair3));
	     }
	 }
	 
	 
}
