package oliveira.br.com.testetata;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import oliveira.br.com.testetata.fragments.FrgFund;
import oliveira.br.com.testetata.fragments.FrgRegister;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.conteudo, new FrgRegister());
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fund:
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.conteudo, new FrgFund());
                ft.commit();
                break;
            case R.id.register:
                FragmentTransaction ftr = getSupportFragmentManager().beginTransaction();
                ftr.replace(R.id.conteudo, new FrgRegister());
                ftr.commit();
                break;
            default:
                break;
        }
        return true;
    }

}
