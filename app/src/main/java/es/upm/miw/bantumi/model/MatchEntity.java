package es.upm.miw.bantumi.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import es.upm.miw.bantumi.utils.Converters;

@Entity(tableName = MatchEntity.TABLE)
@TypeConverters({Converters.class})
public class MatchEntity {
    static public final String TABLE = "matchs";

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private Date date;

    private Integer userScore;
    private Integer opponentScore;


    public MatchEntity(String name, Date date, Integer userScore, Integer opponentScore) {
        this.name = name;
        this.date = date;
        this.userScore = userScore;
        this.opponentScore = opponentScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getUserScore() {
        return userScore;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    public Integer getOpponentScore() {
        return opponentScore;
    }

    public void setOpponentScore(Integer opponentScore) {
        this.opponentScore = opponentScore;
    }
}