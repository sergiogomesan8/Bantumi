package es.upm.miw.bantumi.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MatchRepository {
    IMatchDAO iMatchDAO;
    private LiveData<List<MatchEntity>> matchList;

    public MatchRepository(Application application) {
        MatchRoomDatabase db = MatchRoomDatabase.getDatabase(application);
        iMatchDAO = db.matchDAO();
        matchList = iMatchDAO.topTenScore();
    }

    public LiveData<List<MatchEntity>> getTopTen() {
        return matchList;
    }

    public long insert(MatchEntity item) {
        return iMatchDAO.insert(item);
    }

    public void deleteAll() {
        iMatchDAO.deleteAll();
    }
}
