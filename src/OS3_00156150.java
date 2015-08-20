import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class OS3_00156150 {
	
	 static Hash_info hash_info; //Hash_info object
	 static String temp =""; //用來暫存抓入的字串
	 static StringTokenizer stoken;//用來分割讀入的字串
	 static int table_size =0;//儲存table大小 
	 static LinkedList <Hash_info> filedata = new  LinkedList <Hash_info>();//用來儲存讀入檔案的內容
	 static LinkedList <Hash_info> hashdata; //用來初始化欲存放ArrayList的值
	 static ArrayList <LinkedList> hash_table = new ArrayList <LinkedList>();//用來當hashtable,且值為LinkedList來存取資料
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		loadfile();//讀入檔案的內容
		execute();//執行hashtable
		print_table();//print hashtable
		search();//搜尋page#顯示對應之frame#
	}
	
	public static void loadfile(){
		//讀取檔案的位置
		File FileName = new File("C://OSIN.txt");
		//using try....catch...deal with exception 
	    try(FileReader fr =new FileReader(FileName)){//如果抓不到檔案則exception
	    	BufferedReader br = new BufferedReader(fr);//用來讀取檔案內容
	    	//將檔案裡的資料抓取出來
			while( br.ready()){//確認讀入的檔案中是否還有資料
				hash_info = new Hash_info();//需new 記憶體位置給Hash_info 來供存放Hash info
				//將抓出來的一列放置temp此字串中
				temp = br.readLine();
				//使用stringToken來區別檔案每一列
				stoken = new StringTokenizer(temp, " ");
				while( stoken.hasMoreTokens()){ //確認同一行是否還有資料沒抓進來
					hash_info.page = stoken.nextToken();//將第一筆抓進來的資料放進page這個值中
					hash_info.frame = stoken.nextToken();//將第二筆抓進來的資料放進frame這個值中
					//將每一個hash info讀取完後放置hashdata此arraylist中
					filedata.add(hash_info);
					table_size++;//記錄檔案中有幾筆page
				}	
			}
			//檔案關閉
			br.close();
		
		}	
		catch(FileNotFoundException e){//找不到該檔案所產生的exception
				System.out.println("例外發生,找不到該檔案");
				System.exit(0);//結束此程式
		}
		catch(final IOException e){//檔案讀取有問題產生exception
				System.out.println("例外發生,檔案存取錯誤");
				System.exit(0);//結束此程式
		}			    
	}	
	//hash function
	public static int hashfunction(int x ){
		int prime =2;
		//求質數
		for(int i = 2; i <= table_size; i++){//用迴圈來尋找質數
			 boolean isPrime=true;    //判斷是否為質數的布林變數
			   for(int j=2;j<i;j++){     //做除法運算的內迴圈..由2開始到小於輸入的值..每一個去作除法運算
				   if(i%j==0){              //餘數為0表示可以整除 
					   isPrime=false;         //就不是質數..break跳出迴圈
					   break;				
				   }
			   }
			    	if(isPrime)           //若是質數..印出該數值..
			    		prime = i;			//將i放入prime變數中  	
			   }	
		return x %= prime;//回傳傳入的x mod prime 之值  	
	}
	
	//執行Hashtable之運算
	public static void execute(){
		int value ;
		//建立table_size個數大小的LinkedList放置一個ArrayList中存取
		for(int i = 0; i < table_size; i++){
			hashdata = new LinkedList <Hash_info>();// new Linkedlist object
			hash_table.add(hashdata);//將arraylist進行初始化動作	
		}
		//用for迴圈將值放入hashtable中
		for(int i = 0; i < table_size; i++){
			value = hashfunction(Integer.parseInt(filedata.get(i).page));//page mode prime的值存入value
			hash_table.get(value).add(filedata.get(i));//由value的值決定放入的ArrayList之index且將相對應的hash_info object放入
		}	
	}
	
	//print hashtable data
	public static void print_table(){
		System.out.println("# page#\tframe#");//
		for(int i = 0; i < table_size; i++){//外層迴圈表示table的總共size
			System.out.print(i+" "); //給予編號
			for(int j = 0; j < hash_table.get(i).size(); j++){//內層迴圈為所對應的LinekedList的size
				System.out.print(((Hash_info)hash_table.get(i).get(j)).page+"\t"+((Hash_info)hash_table.get(i).get(j)).frame +"--->");
			}
			System.out.print("null");//最後皆指向到null
			System.out.println();//換行
		}
		
	}
	
	//尋找page# 顯示frame#
	public static void search(){
		Scanner input=new Scanner(System.in);//用於由console輸入值
		System.out.print("查詢Page#：");
		String page=input.nextLine();//輸入的值為字串且將值放入page中
		boolean notfound = true; //當值找到時則設為false,用於提早離開迴圈
		
		for(int i = 0; i < table_size && notfound; i++){//外層迴圈表示table的總共size
			for(int j = 0; j < hash_table.get(i).size(); j++){//內層迴圈為所對應的LinekedList的size
				if(page.equals(((Hash_info)hash_table.get(i).get(j)).page)){//將輸入的page#去做比對是否在table中是否有相同的page#
					System.out.println("Frame# 的值："+((Hash_info)hash_table.get(i).get(j)).frame);//如果有則輸出相對應的frame#	
					notfound = false; //找到值了 將notfound值設為false,用來提早跳出迴圈
					break; //跳出內層迴圈
				}
			}
		}
			if(notfound)//如果notfound為true,則代表並未找到任何值
				System.out.println("page error");//顯示page error	
	}			
}


