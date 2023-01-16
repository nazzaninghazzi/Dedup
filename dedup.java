import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;


public class dedup {
   public static void main(String args[]) {
      String host="3.235.170.15";
      String port = "5432";
      String db_name = "gis";
      String username = "guest";
      String password = "U8OPtddp";
      

      try {
         Class.forName("org.postgresql.Driver");
         Connection c = DriverManager
            .getConnection("jdbc:postgresql://"+host+":"+port+"/"+db_name+"", ""+username, ""+password);
                     
            ArrayList<Object> result = getTableA(c);
            HashMap<String, Integer> mapping = (HashMap<String, Integer>) result.get(0);
            ArrayList<ArrayList<String>> table_a =  (ArrayList<ArrayList<String>>) result.get(1);
            ArrayList<ArrayList<String>> table_b = getTableB(c);
            ArrayList<ArrayList<String>> table_not_duplicate = getNotDuplicateTable(c, mapping);
            System.out.println(table_a);
            System.out.println(table_b);
            System.out.println(table_not_duplicate);

         
         } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
         }
      
   }

   public static ArrayList<Object> getTableA(Connection c) throws SQLException, IOException{
      
      // result stores the mapping and table_a
      ArrayList<Object> result = new ArrayList<Object>();
      HashMap<String, Integer> mapping = new HashMap<String, Integer>();
      ArrayList<ArrayList<String>> table_a = new ArrayList<ArrayList<String>>();
      
      Statement stmt = c.createStatement();
      
      ResultSet rs = stmt.executeQuery( "SELECT * FROM table_a;" );
      int id = 0;
      while ( rs.next() ) {
         String  address = rs.getString("address");
         String  city = rs.getString("city");
         String state = rs.getString("state");
         ArrayList<String> row_a = new ArrayList<String>();
         row_a.add(address);
         row_a.add(city);
         row_a.add(state);
         // store row of table_a into table_a    
         table_a.add(row_a);
         // System.out.println(" address: " + address + " city: " + city + " state: " + state);
         String placeKey = getPlaceKey(address, city, state);
            
         mapping.put(placeKey, id);
         id++;

      }
      result.add(mapping);
      result.add(table_a);
      rs.close();
      stmt.close();

      return result; 
   }

   public static ArrayList<ArrayList<String>> getTableB(Connection c) throws SQLException, IOException{
      
      
      ArrayList<ArrayList<String>> table_b = new ArrayList<ArrayList<String>>();
      
      Statement stmt = c.createStatement();
      
      ResultSet rs = stmt.executeQuery( "SELECT * FROM table_b;" );
      
      while ( rs.next() ) {
         String  address = rs.getString("address");
         String  city = rs.getString("city");
         String state = rs.getString("state");
         ArrayList<String> row_b = new ArrayList<String>();
         row_b.add(address);
         row_b.add(city);
         row_b.add(state);
         // store row of table_b into table_b
         table_b.add(row_b);
         // System.out.println(" address: " + address + " city: " + city + " state: " + state);
        
      }
      rs.close();
      stmt.close();
      return table_b; 
   }


   public static ArrayList<ArrayList<String>> getNotDuplicateTable(Connection c, HashMap<String, Integer> mapping) throws SQLException, IOException{
      
      ArrayList<ArrayList<String>> not_duplicate_table = new ArrayList<ArrayList<String>>();
      
      Statement stmt = c.createStatement();
      
      ResultSet rs = stmt.executeQuery( "SELECT * FROM table_a;" );
      
      while ( rs.next() ) {
         String  address = rs.getString("address");
         String  city = rs.getString("city");
         String state = rs.getString("state");
         ArrayList<String> row_a = new ArrayList<String>();
         row_a.add(address);
         row_a.add(city);
         row_a.add(state);
         // store row of table_a into not_duplicate_table
         not_duplicate_table.add(row_a);
         // System.out.println(" address: " + address + " city: " + city + " state: " + state);
        
      }

      rs = stmt.executeQuery( "SELECT * FROM table_b;" );
      
      while ( rs.next() ) {
         String  address = rs.getString("address");
         String  city = rs.getString("city");
         String state = rs.getString("state");

         String placeKey = getPlaceKey(address, city, state);

         if(mapping.get(placeKey) == null){
            ArrayList<String> row_b = new ArrayList<String>();
            row_b.add(address);
            row_b.add(city);
            row_b.add(state);
            // store row of table_a into not_duplicate_table
            not_duplicate_table.add(row_b);
         }
      }
      rs.close();
      stmt.close();
      return not_duplicate_table; 
   }


   public static String getPlaceKey(String addr, String city, String region) throws IOException {
      String placekey = "";
	   String POST_URL = "https://api.placekey.io/v1/placekey/";
      String apikey = "PLKX0SWZcrZlO893E1DmiuWDrWV5nAI5";
      
      String body = "{\"query\": {\"street_address\": \" " + addr+ "\",\"city\": \" "+ city + "\",\"region\": \"" + region + "\",\"postal_code\": \"94102\",\"iso_country_code\": \"US\"}}";
      
      try {
         URL url = new URL(POST_URL);
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         
         conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
         conn.setRequestProperty("apikey", apikey);
         conn.setDoOutput(true);
         conn.setDoInput(true);
         conn.setRequestMethod("POST");
         OutputStream os = conn.getOutputStream();
         os.write(body.getBytes("UTF-8"));
         os.close(); 
         
         // read the response
         int num_quote = 0;
         
         try(BufferedReader br = new BufferedReader(
         new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
               response.append(responseLine.trim());
            }
            
            for(int i = 0; i < response.length(); i++){
               if(num_quote == 7 && response.charAt(i) != '"'){
                  placekey += response.charAt(i);
               }
               if(response.charAt(i) == '"'){
                  num_quote++;
               }
            }
         
         }
         
      } catch (Exception e) {
          System.out.println(e);
      }
      
      
      return placekey;
   }
}