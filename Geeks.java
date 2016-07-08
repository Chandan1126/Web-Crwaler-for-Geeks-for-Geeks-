import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.lang.model.util.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
public class Geeks {
	static int count=1;
	private void extractLink(String url) throws IOException{
		File folder;
		File file;
		Document doc = Jsoup.connect(url).get();
		org.jsoup.select.Elements anchors =  doc.select("div");
		for(Element link : anchors){
			String str=link.attr("class");
			if(str.equalsIgnoreCase("page-content")){
				org.jsoup.select.Elements ps=link.select("p");
				
				for(Element para : ps){
					String folder_name="//home//chandan//Desktop//GeekforGeek//"+para.select("strong").text().split(":")[0];
					System.out.println(para.select("strong").text().split(":")[0]);
					folder=new File(folder_name);
					if(!folder.exists())
						folder.mkdirs();
				    count=1;
				    org.jsoup.select.Elements hrefs=para.select("a");
				    for(Element hre : hrefs){
				    	 
				    	 getHtmlFile(hre.attr("href"),folder_name);
				    }
				    
				}
			}
		}
	}
 private void getHtmlFile(String url,String file_path) throws IOException{
	char last=url.charAt(url.length()-1);
	if (last!='/'){
		url=url+"/";
	}
	System.out.println(count+" "+url);
	 count++;
	 boolean success;
	 try {
        org.jsoup.Connection.Response response = Jsoup.connect(url)
                 .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                 .timeout(10000)
                 .execute();
        success= true;
        
     } catch (SocketTimeoutException e) {
         success = false;
     System.out.println("Timeout occured");
     }
	 if(success==true){
		 if(!url.contains("archives")){
	 Document doc = Jsoup.connect(url).get();
	 String[] s=url.split("/");
	 String file_name=s[s.length-1];
	 String file_content=doc.html();
	 File file = new File(file_path+"//"+file_name+".html");
	 if (!file.exists()) {
			file.createNewFile();
		
	 FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(file_content);
		bw.close(); } }
	 }}
 
   public static void main(String[] args) throws IOException{
	   new Geeks().extractLink("http://www.geeksforgeeks.org/fundamentals-of-algorithms/");
   }
}
