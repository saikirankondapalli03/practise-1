package com.basicjava.multithreading;

import java.util.Base64;
import java.util.UUID;
import java.io.UnsupportedEncodingException;

public class Base64Encoding {

   public static void main(String args[]) {

      try {
		
         // Encode using basic encoder
         String base64encodedString = Base64.getEncoder().encodeToString(
            "e39b9c178b2c9be4e99b141d956c6ff6".getBytes("utf-8"));
         System.out.println("Base64 Encoded String (Basic) :" + base64encodedString);
		
      } catch(UnsupportedEncodingException e) {
         System.out.println("Error :" + e.getMessage());
      }
   }
}