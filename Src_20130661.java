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

public class Src_20130661 {
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
		for (ThisYear x : TY) {
			x.print();
		}
		//파일읽기는 이제 종료.




		//선수과목 체크
		//완료 후 강의시간 체크.
		boolean chk = true;
		boolean chk2 = true;
		boolean fin=false;
		while(fin==false) {//중단조건을 재설정하는 것도 괜찮겟다......
			//리턴포인트. 밑에서 검증을 한다음 chk가 true면 다시 수강신청하지 않겟지만 false그대로라면, 다시 수강신청해야함.
			Scanner scin2 = new Scanner(System.in);
			System.out.println("수강 과목 코드를 입력하세요 : ");
			LinkedList<ThisYear> SubjectList = new LinkedList<ThisYear>(); //여기서 해야 돌아올때마다 쌓이지않음.
			for (int i=0;i<8;++i) {
				String Subject = scin2.next();
				for(int j=0;j<TY.size();j++) {
					if (Subject.equals(TY.get(j).code)) {
						SubjectList.add(TY.get(j));
					}
				}

			}

			//TY와 SubjectList 관계 다시생각해보고, 시간표 겹치는 문제는 이 while문 밖에서 해결해야할건데, 어떻게할지생각.
			//SubjectList 는 단순히 연결리스트인데, TY 객체를 담는 연결리스트이다.
			//SubjectList에 들어간 ThisYear의 과목명을 TY클래스에서 확인해서, pre_code를 조회해야..
			for(int i=0;i<SubjectList.size();i++) {
				chk=true;
				chk2=true;
				String code = SubjectList.get(i).code;
				String chk_code="#NULL";
				for(int j=0;j<TY.size();j++) {
					if(code.equals(TY.get(j).code)) {//신청과목이 올해개설과목에 있는 경우.
						chk_code=TY.get(j).pre_code;
					}
				}


				if(chk_code.equals("#NULL")) {
					chk2=false;
					continue;//즉, 신청과목 리스트의 과목에 선수과목이 없다면, 다음 신청과목검증을한다.//SL과TY사이의 비교
				}
				else {//선수과목이 있다면, 이제 선수과목을 작년에 들엇는지를 검증한다.//SL과 LY사이의 비교
					for(int j=0;j<LY.size();j++) {
						if(chk_code.equals(LY.get(j).code)) {
							chk=true;
							break; //선수과목에 있네, 포문탈출. //있는데 들은경우
						}
						else chk=false; //없으면 chk는 t->f로 변경. //있는데 안들은경우
					}
					if (chk==false) { //chk가 t->f로 바뀌었다면. 선수과목을 듣지 못한것.
						System.out.println("선수과목 조건을 충족하지 못했습니다. 다시 신청해주세요");
						//chk=true;//무한루프 끝내면 안됨.
						break;//과목코드 다시 입력하러 가야함.
					}
				}
			}
			//수강신청과목중 선수과목이 모두없는경우, 있는데들은경우, 있는데 못들은경우.
			//선수과목은 위에서 거를 수 있다. 다만 선수과목이 하나도 없는경우와 선수과목 조건을 충족한 경우에는 밑으로 진행이되어야한다.
			//새 변수가 필요한가?
			System.out.println(chk);
			if (chk==true && chk2==true) {////chk2가 어떤상태일때 여기를 넘어가도록. 선수과목이 모두없는경우 여기를 넘어갈수있어야함.
				System.out.println("선수과목에걸렷어"); //모든과목에 선수과목이 없어도 여기는 걸리게됨.. chk=true가 초기값이어서.
				continue; //선수과목 조건에 걸렸다면 시간표 체크를 수행할 필요가 없음. 리턴포인트로돌아감.
			}
			else {//선수과목 조건을 통과했다면 이제 시간표검증을해야함. //6*5 배열을 만들어서 마킹하는 식으로 하든지, 무식하게 String 비교로하든지.
				//for(ThisYear x : SubjectList) x.print();
				for(int i=0;i<SubjectList.size();i++) {
					String t1 =	SubjectList.get(i).time1;
					String t2 = SubjectList.get(i).time2;

					/*System.out.println(t1+" "+t2);*/

					for(int j=i+1;j<SubjectList.size();j++) {
						if(t1.equals(SubjectList.get(j).time1)||t1.equals(SubjectList.get(j).time2)||t2.equals(SubjectList.get(j).time1)||t2.equals(SubjectList.get(j).time2)) {
							System.out.println("겹치는 시간표가 있습니다.");
							chk=true;
							break;
						}


					}
					if (chk==true) break;

					//else chk=false;//chk=false 인상태에서, 겹치는 시간표가 없다면 끝 //최종검증 끝. 무한루프 탈출
				}
			System.out.println(chk);



			}

			//모든 조건을 통과하면 무한루프가 끝나고, 밖으로 나가서 subjectlist 출력.
		}
		//for(ThisYear x : SubjectList) x.print();
	}
}
