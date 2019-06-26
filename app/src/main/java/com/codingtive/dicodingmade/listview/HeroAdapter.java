package com.codingtive.dicodingmade.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codingtive.dicodingmade.R;

import java.util.ArrayList;

public class HeroAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Hero> heroes;

    public void setHeroes(ArrayList<Hero> heroes) {
        this.heroes = heroes;
    }

    public HeroAdapter(Context context) {
        this.context = context;
        heroes = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return heroes.size();
    }

    @Override
    public Object getItem(int position) {
        return heroes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_hero, parent, false);
        }

        HeroViewHolder viewHolder = new HeroViewHolder(view);
        Hero hero = (Hero) getItem(position);
        viewHolder.bindHero(hero);
        return view;
    }

    private class HeroViewHolder {
        private TextView tvName;
        private TextView tvDescription;
        private ImageView imgHero;

        HeroViewHolder(View view) {
            tvName = view.findViewById(R.id.tv_hero_name);
            tvDescription = view.findViewById(R.id.tv_hero_desc);
            imgHero = view.findViewById(R.id.img_hero);
        }

        void bindHero(Hero hero) {
            tvName.setText(hero.getName());
            tvDescription.setText(hero.getDescription());
            imgHero.setImageResource(hero.getImage());
        }
    }
}
