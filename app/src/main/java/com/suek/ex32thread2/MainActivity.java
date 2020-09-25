package com.suek.ex32thread2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    MyThread t;   //스레드 참조변수

    //액티비티가 시작할때
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    //액티비티가 끝날때
    // 7) 액티비티가 메모리에서 삭제될때 자동으로 실행되는 메소드
    @Override
    protected void onDestroy() {
        //별도 스레드 종료
        //t.stop();  //Thread 의 stop()은 사용을 권장하지않음.
        //run()메소드가 종료되도록 하기 위해 while 문을 멈추도록..
        //t.isRun= false;   //while 문의 조건을 false 으로 바꿔 멈추게함..

        // 9) 하지만..
        //객체지향의 특빙은 니껀 니가해라!!
        //객체의 멤버변수를 직접 수정하는 것은 권장하지않음..
        t.stopThread();    // 이 메소드 안에서 isRun 을 false 변경함


        super.onDestroy();
    }//onDestroy



    // 1)
    public void clickBtn(View view) {      //절대 버튼에 반복문 쓰지말기!!!!!!!
        //무한반복함ㄴ서 현재시간을 5초마다 토스트로 보여주기!!!
        // 3) 이 작업을 수행하는 별도의 Thread 객체생성
        t= new MyThread();
        t.start();    //자동으로 run()가 발동(invoke)

    }











    // 2) 현재시간을 무한 출력하는 기능을 수행하는 별도 Thread 클래스 설계 (이너클래스로)
    //    위의 버튼을 클릭되면 발동하는  MyThread 메소드 --> 그리고나서 위로가서 Thread 객체를 생성해준다.
    class MyThread extends Thread{

        boolean isRun= true;

        @Override
        public void run() {
            while(isRun){
                // 4) 현재시간 출력
                Date now= new Date();  //객체가 생성될때의 시간을 가지고 있음
                final String s= now.toString();    //현재시간을 문자열로 리턴해줌

                // 5) 화면 변경작업은 별도 Thread 가 할 수 없음
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //이 안에서는 UI 작업 가능
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();   //String s 를 지역변수라 이너클래스안에서 쓸수없어 final 로 만들어줌
                    }
                });


                // 6) 5초동안 잠시 대기
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }//while                                 //Thread 는 일회용이라 run()이 끝나면 작업이 끝남
                                                    //하지만 while(true)으로 무한반복하면 작업이 끝나지않음
        }//run

        //  8)  스레드를 종료하는 기능 메소드..
        void stopThread(){
            isRun= false;
        }


    }
}//MainActivity


