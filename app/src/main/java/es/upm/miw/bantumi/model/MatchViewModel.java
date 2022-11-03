package es.upm.miw.bantumi.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MatchViewModel extends AndroidViewModel {

    private MatchRepository matchRepository;

    private LiveData<List<MatchEntity>> matchList;

    public MatchViewModel(Application application) {
        super(application);
        matchRepository = new MatchRepository(application);
        matchList = matchRepository.getTopTen();
    }

    public LiveData<List<MatchEntity>> getTopTen() {
        return matchList;
    }

    public void insert(MatchEntity item) {
        matchRepository.insert(item);
    }

    public void deleteAll() {
        matchRepository.deleteAll();
    }
}
