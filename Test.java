package test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
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
	String time1;
	String time2;
	String professor;
	String pre_code;

	ThisYear(){}
	ThisYear(String code,String subject,String time1, String time2,String professor, String pre_code){
		this.code=code;
		this.subject=subject;
		this.time1=time1;
		this.time2=time2;
		this.professor=professor;
		this.pre_code=pre_code;

	}
	void print() {
		System.out.println(code+" "+subject+" "+time1+" "+time2+" "+professor+" "+pre_code);
	}

}

public class Test {
	public static void main(String[]args) {
		File file = new File("origin12.txt");
		File file2 = new File("newsubject.txt");
		Scanner scin;
		LinkedList<LastYear> LY = new LinkedList<LastYear>();
		LinkedList<ThisYear> TY = new LinkedList<ThisYear>();

		if(file.exists()) {
			try {
				scin=new Scanner(file);
				while(scin.hasNext()) {
					String code=scin.next();
					String subject=scin.next();
					LY.add(new LastYear(code,subject));
				}
				scin.close();
			}
			catch(IOException e) {}
		}
		else {
			System.out.println("orgin12.txt not exist!!");
		}

		if(file2.exists()) {
			try {
				scin=new Scanner(file2);
				while(scin.hasNext()) {
					String code=scin.next();
					String subject=scin.next();
					String time1=scin.next(); //나중에는 time1 time2로 해서 중복제거해야함.
					String time2=scin.next();
					String professor=scin.next();
					String pre_code=scin.next(); //선수과목이 없는경우 파일내에서 NULL코드 지정안하면 NoSuchElementException 발생.
					TY.add(new ThisYear(code,subject,time1,time2,professor,pre_code));
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


		LinkedList<ThisYear> SubjectList = new LinkedList<ThisYear>();

		//선수과목 체크
		//완료 후 강의시간 체크.
		boolean chk = false;

		while(chk == false) {
			//리턴포인트. 밑에서 검증을 한다음 chk가 true면 다시 수강신청하지 않겟지만 false그대로라면, 다시 수강신청해야함.
			Scanner scin2 = new Scanner(System.in);
			System.out.println("수강 과목 코드를 입력하세요 : ");
			for (int i=0;i<7;++i) {
				String Subject = scin2.next();
				SubjectList.add(new ThisYear(Subject,"0","0","0","0","0"));
				//이렇게 할 경우 추가로 과목을 선택하지 않을때는 x 라던가 0이라던가 널값을 입력해줘야함.
			}
			ListIterator<ThisYear> iter = SubjectList.listIterator();
			while (iter.hasNext()) {
				ListIterator<LastYear> iter2 = LY.listIterator(); //LY는 매번 처음부터 돌아야하므로 이터레이터를 다시얻어와야함.
				chk=false;

				if(iter.next().pre_code=="#NULL") continue; //신청과목 선수과목이 없으면, LY계속 뒤짐.
				else {//신청과목에 선수과목이 있는 경우.
					while(iter2.hasNext()) {//LY에 있는지 없는지 찾아내야함.
						if(iter.next(). == iter2.next().code) {
							chk=true; //있다면 체크 변수가 true. 발견! 나가도됨.
							break;
						}
						else chk=false;//없다면 체크 변수가 false.
					}
				}
				if (chk==false) { //chk가 t->f로 바뀌었다면. 선수과목을 듣지 못한것.
					System.out.println("수강신청에 실패했습니다. 다시 신청해주세요");
					break;
				}

			}
		}
	}
}
