package es.upm.miw.bantumi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import es.upm.miw.bantumi.model.MatchEntity;
import es.upm.miw.bantumi.model.MatchViewModel;
import es.upm.miw.bantumi.views.MatchListAdapter;

public class MatchActivity extends AppCompatActivity {

    MatchViewModel matchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard_top_ten);

        ListView matchList = findViewById(R.id.scoreList);
        matchViewModel = new ViewModelProvider(this).get(MatchViewModel.class);
        matchViewModel.getTopTen().observe(this, new Observer<List<MatchEntity>>() {
            @Override
            public void onChanged(List<MatchEntity> matchEntities) {
                matchList.setAdapter(new MatchListAdapter(getApplicationContext(),
                        matchEntities));
            }
        });

        final Button papelera = findViewById(R.id.papelera);
        papelera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteScores();
            }
        });
    }

    public void deleteScores() {
        matchViewModel.deleteAll();
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(R.string.txtPapeleraVacia),
                Snackbar.LENGTH_LONG
        ).show();
    }
}