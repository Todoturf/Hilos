package com.example.hilos;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtTexto;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTexto = findViewById(R.id.txtTexto);
        handler = new Controlador();

        Hilo miHilo = new Hilo(10, 100);
        miHilo.start();

        Hilo miHilo2 = new Hilo(15, 120);
        miHilo2.start();
    }

    public class Hilo extends Thread
    {
        int maximo;
        int tiempo;

        public Hilo (int m, int t)
        {
            this.maximo = m;
            this.tiempo = t;
        }
        public void run()
        {
            //instrucciones que van a ejecutar el hilo
            for (int i = 0; i <= this.maximo; i++)
            {
                try
                {
                    Thread.sleep(tiempo);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                // Construir mensaje que va al contestador
                Message msg = handler.obtainMessage();
                // Inserta datos en el mensaje
                Bundle bundle = new Bundle();
                bundle.putInt("total", i);
                bundle.putString("thread", currentThread().toString());
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }
    }

    // Controlador para recibir mensajes del hilo
    class Controlador extends Handler
    {
        public void handleMessage(Message msg)
        {
            int total;
            total = msg.getData().getInt("total");
            String thread = msg.getData().getString("thread");
            txtTexto.append("\n" + total + "  " + thread);
        }
    }
}
