package test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class LastYear{
	String code;
	String subject;
	LastYear(){}
	LastYear(String code,String subject){
		this.code=code;
		this.subject=subject;
	}
	void print() {
		System.out.println(code+" "+subject);
	}
}

class ThisYear{
	String code;
	String subject;
	String time;
	String professor;
	String pre_code;

	ThisYear(){}
	ThisYear(String code,String subject,String time,String professor, String pre_code){
		this.code=code;
		this.subject=subject;
		this.time=time;
		this.professor=professor;
		this.pre_code=pre_code;

	}
	void print() {
		System.out.println(code+" "+subject+" "+time+" "+professor+" "+pre_code);
	}

}

public class Test {
	public static void main(String[]args) {
		File file = new File("origin12.txt");
		File file2 = new File("newsubject.txt");
		Scanner scin;
		LastYear L[]=new LastYear[13];
		ThisYear TY[]=new ThisYear[19];
		int i=0;

		if(file.exists()) {
			try {
				scin=new Scanner(file);
				while(scin.hasNext()) {
					String code=scin.next();
					String subject=scin.next();
					L[i++]=new LastYear(code,subject);
				}
				scin.close();
			}
			catch(IOException e) {}
		}
		else {
			System.out.println("orgin12.txt not exist!!");
		}

		i=0;
		if(file2.exists()) {
			try {
				scin=new Scanner(file2);
				while(scin.hasNext()) {
					String code=scin.next();
					String subject=scin.next();
					String time=scin.next(); //나중에는 time1 time2로 해서 중복제거해야함.
					String professor=scin.next();
					String pre_code=scin.next(); //선수과목이 없는경우 파일내에서 NULL코드 지정안하면 NoSuchElementException 발생.
					TY[i++]=new ThisYear(code,subject,time,professor,pre_code);
				}
				scin.close();
			}
			catch(IOException e) {}
		}
		else {
			System.out.println("orgin12.txt not exist!!");
		}
		System.out.println("List of subjects : ");
		for (ThisYear x : TY) { //TY객체 사이즈를 100으로 했더니 NULLPOINTEREXCEPTION, 19로 줄여줫더니 해결.
			x.print();
		}
		//파일읽기는 이제 종료.

		Scanner scin2 = new Scanner(System.in);
		System.out.println("수강 과목 코드를 입력하세요 : ");
		//제네릭스 어래이배열 써보기. 동적이니까 만약 3과목 아래면 에러, 8과목 위여도 에러. 하계상계가 있는 배열?

		ArrayList <String> SubjectArr = new ArrayList<String>(3);
		for (i=0;i<7;++i) {
			String Subject = scin2.next();
			SubjectArr.add(Subject);//이렇게 할 경우 추가로 과목을 선택하지 않을때는 x 라던가 0이라던가 널값을 입력해줘야함.
			for(int j=0;j<11;++j) {
				if (f)
			}
		}
		for (String x : SubjectArr) System.out.println(x);
	}
}
