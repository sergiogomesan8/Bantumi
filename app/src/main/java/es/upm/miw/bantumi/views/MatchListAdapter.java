package es.upm.miw.bantumi.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import es.upm.miw.bantumi.R;
import es.upm.miw.bantumi.model.MatchEntity;

public class MatchListAdapter extends BaseAdapter {
    Context context;
    List<MatchEntity> matchEntityList;
    LayoutInflater inflater;

    public MatchListAdapter(Context applicationContext, List<MatchEntity> matchEntityList) {
        this.context = applicationContext;
        this.matchEntityList = matchEntityList;
        inflater = (LayoutInflater.from(applicationContext));
    }

    static class MatchViewHolder {
        TextView name;
        TextView userScore;
        TextView rivalScore;
        TextView date;
        TextView hour;
    }

    @Override
    public int getCount() {
        return matchEntityList != null ?
                matchEntityList.size()
                : 0;
    }

    @Override
    public Object getItem(int i) {
        return matchEntityList != null ?
                matchEntityList.get(i)
                : null;
    }

    @Override
    public long getItemId(int i) {
        return matchEntityList != null ?
                matchEntityList.get(i).hashCode()
                : -1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MatchViewHolder matchViewHolder;
        if(view == null) {
            matchViewHolder = new MatchViewHolder();
            view = inflater.inflate(R.layout.score_item, null);
            matchViewHolder.name = view.findViewById(R.id.name);
            matchViewHolder.userScore = view.findViewById(R.id.userScore);
            matchViewHolder.rivalScore = view.findViewById(R.id.opponentScore);
            matchViewHolder.date = view.findViewById(R.id.dateScore);
            matchViewHolder.hour = view.findViewById(R.id.hourScore);
        } else {
            matchViewHolder = (MatchViewHolder) view.getTag();
        }
        if(matchEntityList != null) {
            int pos = i + 1;
            String usernamePosition = pos + ". - " + matchEntityList.get(i).getName();

            matchViewHolder.name.setText(usernamePosition);
            matchViewHolder.userScore.setText(String.valueOf(matchEntityList.get(i).getUserScore()));
            matchViewHolder.rivalScore.setText(String.valueOf(matchEntityList.get(i).getOpponentScore()));

            Date date = matchEntityList.get(i).getDate();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");

            matchViewHolder.date.setText(dateFormat.format(date));
            matchViewHolder.hour.setText(hourFormat.format(date));
        }
        return view;
    }


}
