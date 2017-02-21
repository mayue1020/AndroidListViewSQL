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


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cc.simpleme.androidjdbc.util.Database;


public class MainActivity extends AppCompatActivity {
    static String ex;
    static List<String> list = new ArrayList<String>();
    //显示的数组
    static String[] strs = {"语文","数学","英语","物理","化学","生物","政治","历史","地理"};
    static ArrayAdapter<String> adapter ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_expandable_list_item_1,list);
        ListView list =  (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        Button putSql = (Button) findViewById(R.id.putSql);
        putSql.setOnClickListener(new testPutBtn());

        Button listTest = (Button) findViewById(R.id.listTest);
        listTest.setOnClickListener(new listTest());

        Button getSql = (Button) findViewById(R.id.getSql);
        getSql.setOnClickListener(new testGetBtn() );

        Button refresh = (Button) findViewById(R.id.refresh);
        refresh.setOnClickListener(new refreshBtn());



    }


    //监听按钮刷新listview
    class listTest implements View.OnClickListener{
        int i;
        int t = 1;
        //循环判断在获取第三个数据时结束循环并保存数据，再次点击按钮时继续循环；
        public void onClick(View v){
//            List<String> list = new ArrayList<String>();
            List<String> listStrs2 = new ArrayList<String>();
            List<String> listStrs3 = new ArrayList<String>();
            for(i = t;i<11;i++){
                try{
                    if(i%3==0&&i>=3){
                        list.add(strs[i-1]);
                        listStrs3 = list;
                        t = i+1;
                        break;
                    }else if(i%3==1&&i>=3){
                        list=listStrs2;
                        list.add(strs[i-1]);
                    }else {
                        list.add(strs[i-1]);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    t=1;
                    System.out.print("666");
                    break;
                }

            }

            //创建arrayadapter
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_expandable_list_item_1,listStrs);
            //注释掉上面改为调用主类的adapter和list
//            ListView list =  (ListView) findViewById(R.id.list);
//            list.setAdapter(adapter);
        }
    }
    public void refresh(ArrayAdapter<String> adapter) {
        adapter.notifyDataSetChanged();
    }
    //刷新界面
    class refreshBtn implements View.OnClickListener{
        public void onClick(View v){
            refresh(adapter);
        }
    }
    class testGetBtn implements View.OnClickListener{


        public void onClick(View v){
            getThread m = new getThread();
            m.start();
//            m.stop();
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_expandable_list_item_1,list);
//            ListView listview =  (ListView) findViewById(R.id.list);
//            listview.setAdapter(adapter);
//            Toast.makeText(MainActivity.this,list.get(1),Toast.LENGTH_SHORT).show();
        }
    }

    static class getThread extends Thread{
        int i = 0;
        public void run(){
            String SQL = "select * from listview";
            Database a = new Database();
            a.getConn();
            ResultSet rs = null;
            rs = a.getSQL(SQL);
            List<String> list1 = new ArrayList<String>();
            List<String> list2 = new ArrayList<String>();
            List<String> list3 = new ArrayList<String>();
            try {
                while(rs.next()){
                    list.add(rs.getString(1));
                    i++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
