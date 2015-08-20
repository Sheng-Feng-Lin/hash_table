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
	 static String temp =""; //�ΨӼȦs��J���r��
	 static StringTokenizer stoken;//�ΨӤ���Ū�J���r��
	 static int table_size =0;//�x�stable�j�p 
	 static LinkedList <Hash_info> filedata = new  LinkedList <Hash_info>();//�Ψ��x�sŪ�J�ɮת����e
	 static LinkedList <Hash_info> hashdata; //�ΨӪ�l�Ʊ��s��ArrayList����
	 static ArrayList <LinkedList> hash_table = new ArrayList <LinkedList>();//�Ψӷ�hashtable,�B�Ȭ�LinkedList�Ӧs�����
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		loadfile();//Ū�J�ɮת����e
		execute();//����hashtable
		print_table();//print hashtable
		search();//�j�Mpage#��ܹ�����frame#
	}
	
	public static void loadfile(){
		//Ū���ɮת���m
		File FileName = new File("C://OSIN.txt");
		//using try....catch...deal with exception 
	    try(FileReader fr =new FileReader(FileName)){//�p�G�줣���ɮ׫hexception
	    	BufferedReader br = new BufferedReader(fr);//�Ψ�Ū���ɮפ��e
	    	//�N�ɮ׸̪���Ƨ���X��
			while( br.ready()){//�T�{Ū�J���ɮפ��O�_�٦����
				hash_info = new Hash_info();//��new �O�����m��Hash_info �ӨѦs��Hash info
				//�N��X�Ӫ��@�C��mtemp���r�ꤤ
				temp = br.readLine();
				//�ϥ�stringToken�ӰϧO�ɮרC�@�C
				stoken = new StringTokenizer(temp, " ");
				while( stoken.hasMoreTokens()){ //�T�{�P�@��O�_�٦���ƨS��i��
					hash_info.page = stoken.nextToken();//�N�Ĥ@����i�Ӫ���Ʃ�ipage�o�ӭȤ�
					hash_info.frame = stoken.nextToken();//�N�ĤG����i�Ӫ���Ʃ�iframe�o�ӭȤ�
					//�N�C�@��hash infoŪ�������mhashdata��arraylist��
					filedata.add(hash_info);
					table_size++;//�O���ɮפ����X��page
				}	
			}
			//�ɮ�����
			br.close();
		
		}	
		catch(FileNotFoundException e){//�䤣����ɮשҲ��ͪ�exception
				System.out.println("�ҥ~�o��,�䤣����ɮ�");
				System.exit(0);//�������{��
		}
		catch(final IOException e){//�ɮ�Ū�������D����exception
				System.out.println("�ҥ~�o��,�ɮצs�����~");
				System.exit(0);//�������{��
		}			    
	}	
	//hash function
	public static int hashfunction(int x ){
		int prime =2;
		//�D���
		for(int i = 2; i <= table_size; i++){//�ΰj��ӴM����
			 boolean isPrime=true;    //�P�_�O�_����ƪ����L�ܼ�
			   for(int j=2;j<i;j++){     //�����k�B�⪺���j��..��2�}�l��p���J����..�C�@�ӥh�@���k�B��
				   if(i%j==0){              //�l�Ƭ�0��ܥi�H�㰣 
					   isPrime=false;         //�N���O���..break���X�j��
					   break;				
				   }
			   }
			    	if(isPrime)           //�Y�O���..�L�X�Ӽƭ�..
			    		prime = i;			//�Ni��Jprime�ܼƤ�  	
			   }	
		return x %= prime;//�^�ǶǤJ��x mod prime ����  	
	}
	
	//����Hashtable���B��
	public static void execute(){
		int value ;
		//�إ�table_size�ӼƤj�p��LinkedList��m�@��ArrayList���s��
		for(int i = 0; i < table_size; i++){
			hashdata = new LinkedList <Hash_info>();// new Linkedlist object
			hash_table.add(hashdata);//�Narraylist�i���l�ưʧ@	
		}
		//��for�j��N�ȩ�Jhashtable��
		for(int i = 0; i < table_size; i++){
			value = hashfunction(Integer.parseInt(filedata.get(i).page));//page mode prime���Ȧs�Jvalue
			hash_table.get(value).add(filedata.get(i));//��value���ȨM�w��J��ArrayList��index�B�N�۹�����hash_info object��J
		}	
	}
	
	//print hashtable data
	public static void print_table(){
		System.out.println("# page#\tframe#");//
		for(int i = 0; i < table_size; i++){//�~�h�j����table���`�@size
			System.out.print(i+" "); //�����s��
			for(int j = 0; j < hash_table.get(i).size(); j++){//���h�j�鬰�ҹ�����LinekedList��size
				System.out.print(((Hash_info)hash_table.get(i).get(j)).page+"\t"+((Hash_info)hash_table.get(i).get(j)).frame +"--->");
			}
			System.out.print("null");//�̫�ҫ��V��null
			System.out.println();//����
		}
		
	}
	
	//�M��page# ���frame#
	public static void search(){
		Scanner input=new Scanner(System.in);//�Ω��console��J��
		System.out.print("�d��Page#�G");
		String page=input.nextLine();//��J���Ȭ��r��B�N�ȩ�Jpage��
		boolean notfound = true; //��ȧ��ɫh�]��false,�Ω󴣦����}�j��
		
		for(int i = 0; i < table_size && notfound; i++){//�~�h�j����table���`�@size
			for(int j = 0; j < hash_table.get(i).size(); j++){//���h�j�鬰�ҹ�����LinekedList��size
				if(page.equals(((Hash_info)hash_table.get(i).get(j)).page)){//�N��J��page#�h�����O�_�btable���O�_���ۦP��page#
					System.out.println("Frame# ���ȡG"+((Hash_info)hash_table.get(i).get(j)).frame);//�p�G���h��X�۹�����frame#	
					notfound = false; //���ȤF �Nnotfound�ȳ]��false,�ΨӴ������X�j��
					break; //���X���h�j��
				}
			}
		}
			if(notfound)//�p�Gnotfound��true,�h�N��å��������
				System.out.println("page error");//���page error	
	}			
}


