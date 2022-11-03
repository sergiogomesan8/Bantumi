package es.upm.miw.bantumi.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface IMatchDAO {
    @Query("SELECT * FROM " + MatchEntity.TABLE + " ORDER BY userScore DESC, opponentScore ASC")
    LiveData<List<MatchEntity>> topTenScore();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(MatchEntity grupo);

    @Query("DELETE FROM " + MatchEntity.TABLE)
    void deleteAll();
}
