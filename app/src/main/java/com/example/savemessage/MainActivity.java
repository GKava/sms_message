package com.example.savemessage;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MY LOG ";
    private RecyclerView recyclerView;
    private LinearLayoutManager verticalLinearLayoutManager;
    private CustomLisrtAdapter adapter;
    private EditText editText;
    String sendText;
    String messRepeat="";
    int numberRepeat=0;
    SharedPreferences sPref;
    final String SAVED_TEXT = "saved_text";
    Handler handler;
    int delayHandler=2000;
    AppDatabase appDatabase;

    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES_COUNTER = "counter";
    public static final String APP_PREFERENCES = "mysettings";
    private int iDs;

    private static final List<String> sMomoMsg = new ArrayList<>();
    static {
        sMomoMsg.add("Зря ты это затеял");
        sMomoMsg.add("Уже поздно, тебе не спастить");
        sMomoMsg.add("Я знаю где ты живешь");
        sMomoMsg.add("Уже поздно");
        sMomoMsg.add("Зря ты это затеял");
        sMomoMsg.add("Скоро увидимся ");
        sMomoMsg.add("Тебе не сбежать ");
        sMomoMsg.add("Согодня я тебя видела");
        sMomoMsg.add("Думаешь у тебя получится скрыться от меня?");
        sMomoMsg.add("Оглядывайся почаще ");
        sMomoMsg.add("Посмотри в камеру, ты меня не видишь, зато я вижу тебя");
        sMomoMsg.add("Не бойся, страах тебе не поможет");
    }
    private static final List<Integer> randTimeHandler = new ArrayList<>();
    static {
        randTimeHandler.add(2000);
        randTimeHandler.add(2300);
        randTimeHandler.add(2700);
        randTimeHandler.add(3100);
        randTimeHandler.add(1900);
        randTimeHandler.add(1500);
        randTimeHandler.add(1700);
        randTimeHandler.add(1100);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        editText = (EditText) findViewById(R.id.editText);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        verticalLinearLayoutManager = new LinearLayoutManager(this);
        verticalLinearLayoutManager.setStackFromEnd(false);
        recyclerView.setLayoutManager(verticalLinearLayoutManager);
        adapter = new CustomLisrtAdapter();
        recyclerView.setAdapter(adapter);
        loadText();

        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        List<ModelItems> authors =  appDatabase.authorDao().getAllAuthor(); // список со всеми авторами
        AuthorDao  authorDao = appDatabase.authorDao();

        List<ModelItems> allAuthor = authorDao.getAllAuthor();
        if (allAuthor!=null) {
            adapter.addAll(allAuthor);
        }

        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "click"+" ", Toast.LENGTH_LONG).show();
              // adapter.deleteMessageAll();
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(APP_PREFERENCES_COUNTER, iDs);
        editor.apply();

        saveText();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mSettings.contains(APP_PREFERENCES_COUNTER)) {
            // Получаем число из настроек
            iDs = mSettings.getInt(APP_PREFERENCES_COUNTER, 0);
            // Выводим на экран данные из настроек

        }
    }

    public void click(View view) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());



        if (editText.getText().toString().equals("")) {
            //
        } else {
            sendText = editText.getText().toString();
            //adapter.addMessage(new ModelItems("You: \n" +sendText, true));
            adapter.addMessage(new ModelItems("        "+sendText + "\n"+ strDate, true));
            //add to database

                AuthorDao  authorDao = appDatabase.authorDao();
                Author author = new Author();
                author.setId(iDs);
                author.setAuthor("        "+sendText);
                author.setPhoneUser(true);
                authorDao.inserAllAuthor(author);
                iDs++;



            handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    messageRepeat();
                    writeMomoAnimation();
                }
            }, randomTimeHandler());
            recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
            editText.setText("");
        }
    }
//
//    public void addAndDeleteMessage(){
//        adapter.addMessage( new ModelItems ("Momo: \n" + "Я тебя убью..", false));
//        handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//
//
//            }
//        }, 1000);
//    }

    public int randomTimeHandler(){
        Random random = new Random();
        int a = random.nextInt(randTimeHandler.size());
        delayHandler = randTimeHandler.get(a);
        return delayHandler;
    }

    public void writeMomoAnimation(){

    }


    // Реакция на одинаковые смс
   public void messageRepeat(){
        if (sendText.equals(messRepeat)) {
           numberRepeat++;
           if (numberRepeat == 1){
              adapter.addMessage(new ModelItems("             "+"Я всё поняла с первого раза", false));

           }
           if(numberRepeat ==2){
               adapter.addMessage(new ModelItems("             "+"Сколько можно повторять", false));
           }
           if (numberRepeat>=3){
               adapter.addMessage(new ModelItems( "             "+"...", false));
           }
       } else {

            String momoSaveMess = sMomoMsg.get(new Random().nextInt( sMomoMsg.size() - 1));
                    adapter.addMessage(new ModelItems("             " +momoSaveMess, false));
           messRepeat = sendText;
           numberRepeat =0;


            AuthorDao  authorDao = appDatabase.authorDao();
            Author author = new Author();
            author.setId(iDs);
            author.setAuthor("             "+momoSaveMess);
            author.setPhoneUser(false);
            authorDao.inserAllAuthor(author);
            iDs++;



        }

   }


    @Override
    protected void onStop() {
        super.onStop();
        saveText();

    }

    // СОХРАНЕНИЕ НЕ РАБОТАЕТ
//    void saveId() {
//        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putInt("your_int_key", iDs);
//        editor.commit();
//    }
//
//    void loadId() {
//        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
//        int iDs = sp.getInt("your_int_key", -1);
//    }

    void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, editText.getText().toString());
        ed.commit();
       // Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
    }

    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");
        editText.setText(savedText);
       // Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show();
    }

    // извлечение поэлементно через For
    public static List<ModelItems> getSaveMessages(){
        ArrayList<ModelItems> itemList = new ArrayList<>();
        itemList.add(new ModelItems("One message",false));
        itemList.add(new ModelItems("Two message",true));
        return itemList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.delete_message :
                for (int a=0; a<100;a++) { // сделать всю длинну или большое число сойдёт но больше затрат

    AuthorDao authorDao = appDatabase.authorDao();
    Author author = new Author();
    author.setId(a);
    adapter.deleteMessageAll();
    authorDao.deleteAll(author);

                }
                iDs =0; //обнуляем айдишник
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Переписка удалена!", Toast.LENGTH_SHORT);
                toast.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
