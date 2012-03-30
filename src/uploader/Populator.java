/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uploader;

import java.sql.*;

/**
 *
 * @author Anurag Jain
 */
 class UploadMysql
{
    Statement stmt,stmt1,stmt2,stmt3,stmt4,stmt5;
        ResultSet rs;
   public UploadMysql() {
      try {

        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/src";
        Connection con = DriverManager.getConnection(url,"root", "*****");
        stmt = con.createStatement();
        stmt1 = con.createStatement();
        stmt2 = con.createStatement();
        stmt3=con.createStatement();
        stmt4=con.createStatement();
        stmt5=con.createStatement();
       }
      catch(Exception e) {
          System.out.println(e);
      }
    }
   public void insert()
     {
       try{
           int pid=1,sid=1,rid=1;
        String query1="select distinct program,department,semester from meta order by program";
        
        ResultSet rs1=stmt.executeQuery(query1);
            
            while(rs1.next())
            {   
                System.out.println(pid+rs1.getString(1)+"\t"+rs1.getString(2)+"\t"+rs1.getString(3));
            String  query2="insert into program_master(program_id,program,department,semester)";
                    query2+="Values ("+pid+",'"+rs1.getString(1)+"',"+"'"+rs1.getString(2)+"',"+rs1.getString(3)+")";
            System.out.println(query2+"\n");
            stmt1.executeUpdate(query2);
            String query3="Select distinct section,batch from meta where program="+"'"+rs1.getString(1)+"'and department='"+rs1.getString(2)+"' and semester="+rs1.getString(3);
            System.out.println(query3+"\n");
            ResultSet rs2=stmt2.executeQuery(query3);

                while(rs2.next())
                {
                System.out.println(sid+"\t"+rs2.getString(1)+"\t"+rs2.getString(2)+"\n");
                String query4="Insert into sectionmaster(section_id,program_id,section,batch) values("+sid+","+pid+",'"+rs2.getString(1)+"',"+rs2.getString(2)+")";
                stmt3.executeUpdate(query4);
                System.out.println(query4+"\n");
                String query5="Select regno,coursecode,staffid from meta where program='"+rs1.getString(1)+"' and department='"+rs1.getString(2)+"' and semester="+rs1.getString(3);
                       query5+=" and section='"+rs2.getString(1)+"' and batch="+rs2.getString(2);
                System.out.println(query5+"\n");
                ResultSet rs3=stmt4.executeQuery(query5);
                    while(rs3.next())
                    {
                    System.out.println(rid+"\t"+rs3.getString(1)+"\t"+rs3.getString(2)+"\t"+rs3.getString(3)+"\t"+sid);
                    String query6="insert into relation(relationid,regnumber,coursecode,staffid,section_id) values("+rid+",'"+rs3.getString(1)+"','"+rs3.getString(2)+"','"+rs3.getString(3)+"',"+sid+")";
                    System.out.println(query6+"\n");
                    stmt5.executeUpdate(query6);
                    rid++;
                    }

                sid++;
                }
            rs2.close();
             pid++;
            }
 
            rs1.close();
         }catch(SQLException e)
       {
         System.out.println(e);
         }
     }

}
public class Populator {

    public static void main(String args[])
    {
        UploadMysql q=new UploadMysql();
        q.insert();
    }

}
