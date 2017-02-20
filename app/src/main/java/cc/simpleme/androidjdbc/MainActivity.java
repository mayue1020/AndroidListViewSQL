package cc.simpleme.androidjdbc;

import android.app.Activity;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cc.simpleme.androidjdbc.util.Database;


public class MainActivity extends AppCompatActivity {
    static String ex;
    //显示的数组
    static String[] strs = {"语文","数学","英语","物理","化学","生物","政治","历史","地理"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button putSql = (Button) findViewById(R.id.putSql);
        putSql.setOnClickListener(new testPutBtn());

        Button listTest = (Button) findViewById(R.id.listTest);
        listTest.setOnClickListener(new listTest());

        Button getSql = (Button) findViewById(R.id.getSql);
        getSql.setOnClickListener(new testGetBtn() );

    }

    //监听按钮刷新listview
    class listTest implements View.OnClickListener{
        int i;
        int t = 1;
        //循环判断在获取第三个数据时结束循环并保存数据，再次点击按钮时继续循环；
        public void onClick(View v){
            List<String> listStrs = new ArrayList<String>();
            List<String> listStrs2 = new ArrayList<String>();
            List<String> listStrs3 = new ArrayList<String>();
            for(i = t;i<11;i++){
                try{
                    if(i%3==0&&i>=3){
                        listStrs.add(strs[i-1]);
                        listStrs3 = listStrs;
                        t = i+1;
                        break;
                    }else if(i%3==1&&i>=3){
                        listStrs=listStrs2;
                        listStrs.add(strs[i-1]);
                    }else {
                        listStrs.add(strs[i-1]);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    t=1;
                    System.out.print("666");
                    break;
                }

            }

            //创建arrayadapter
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_expandable_list_item_1,listStrs);
            ListView list =  (ListView) findViewById(R.id.list);
            list.setAdapter(adapter);
        }
    }

    class testGetBtn implements View.OnClickListener{
        public void onClick(View v){
            getThread m = new getThread();
            m.start();
            Toast.makeText(MainActivity.this,ex,Toast.LENGTH_SHORT).show();
        }
    }

    static class getThread extends Thread{
        public void run(){
            Database a = new Database();
            try {
                a.getSQL("select * from listview");
                ex = "select....";
            } catch (SQLException e) {
                e.printStackTrace();
                ex = ""+e;
            }

        }
    }

    class testPutBtn implements View.OnClickListener{
        @Override
        public void onClick(View v){
            putThread m = new putThread();
            m.start();
            Toast.makeText(MainActivity.this,ex,Toast.LENGTH_LONG).show();
        }
    }
    static class putThread extends Thread{
        public void run(){
            Database database = new Database();
            try {
                String a = database.putSQL("insert into listtest.dbo.listview values ('bb','bb')");
                ex = a;
            }catch (Exception e){
                e.printStackTrace();
                ex= ""+e;
            }
        }
    }

}
