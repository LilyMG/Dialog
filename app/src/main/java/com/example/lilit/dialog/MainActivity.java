package com.example.lilit.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends ActionBarActivity {
    TextView tvTime;
    ProgressBar progressBar;
    ProgressDialog pd;
    Handler h;

    AsyncTask asyncTask = new AsyncTask() {
        @Override
        protected Object doInBackground(Object[] objects) {
            for (int i = 0; i < 50; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            progressBar.setVisibility(View.GONE);
            super.onPostExecute(o);
        }
    };



    public void onclick(View v) {
        switch (v.getId()) {

            case R.id.btnHoriz:
                pd = new ProgressDialog(this);
                pd.setTitle("Title");

                pd.setMessage("Message");
                // меняем стиль на индикатор
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                // устанавливаем максимум
                pd.setMax(2148);
                // включаем анимацию ожидания
                pd.setIndeterminate(true);
                pd.show();
                pd.setContentView(R.layout.dialog_layout);
                h = new Handler() {
                    public void handleMessage(Message msg) {
                        // выключаем анимацию ожидания
                        pd.setIndeterminate(false);
                        if (pd.getProgress() < pd.getMax()) {
                            // увеличиваем значения индикаторов
                            pd.incrementProgressBy(50);
                            pd.incrementSecondaryProgressBy(75);
                            h.sendEmptyMessageDelayed(0, 100);
                        } else {
                            pd.dismiss();
                        }
                    }
                };
                h.sendEmptyMessageDelayed(0, 2000);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTime = (TextView) findViewById(R.id.show_time);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        asyncTask.execute();
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DatePickerDialog timePickerDialog = new DatePickerDialog(MainActivity.this, getMyCallBack, 12,00, 8);
//                timePickerDialog.show();
                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                adb.setTitle("exit");
                // сообщение
                adb.setMessage("save");
                // иконка
                adb.setIcon(android.R.drawable.ic_dialog_info);
                // кнопка положительного ответа
                adb.setPositiveButton("yes", myClickListener);
                // кнопка отрицательного ответа
                adb.setNegativeButton("no", myClickListener);
                // кнопка нейтрального ответа
                adb.setNeutralButton("cancel", myClickListener);
                adb.setView(R.layout.dialog_layout);
                // создаем диалог
                adb.create();
                adb.show();
            }
        });
    }

    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                // положительная кнопка
                case Dialog.BUTTON_POSITIVE:
                    saveData();
                    finish();
                    break;
                // негаитвная кнопка
                case Dialog.BUTTON_NEGATIVE:
                    finish();
                    break;
                // нейтральная кнопка
                case Dialog.BUTTON_NEUTRAL:
                    break;
            }
        }
    };

    void saveData() {
        Toast.makeText(this, "data saved", Toast.LENGTH_SHORT).show();
    }

    DatePickerDialog.OnDateSetListener getMyCallBack = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {

        }
    };

}
