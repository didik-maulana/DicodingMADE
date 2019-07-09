package com.codingtive.dicodingmade.listview;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.codingtive.dicodingmade.R;

import java.util.ArrayList;

public class HeroActivity extends AppCompatActivity {

    private String[] heroName, heroDescription;
    private TypedArray imgHero;
    private HeroAdapter heroAdapter;
    private ListView lvHeroes;
    private ArrayList<Hero> heroes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);

        lvHeroes = findViewById(R.id.lv_heroes);
        heroAdapter = new HeroAdapter(this);
        lvHeroes.setAdapter(heroAdapter);

        prepare();
        addHeroes();

        lvHeroes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(HeroActivity.this, heroes.get(i).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addHeroes() {
        heroes = new ArrayList<>();

        for (int i=0; i < heroName.length; i++) {
            Hero hero = new Hero();
            hero.setImage(imgHero.getResourceId(i, -1));
            hero.setName(heroName[i]);
            hero.setDescription(heroDescription[i]);
            heroes.add(hero);
        }
        heroAdapter.setHeroes(heroes);
    }

    private void prepare() {
        heroName = getResources().getStringArray(R.array.data_name);
        heroDescription = getResources().getStringArray(R.array.data_description);
        imgHero = getResources().obtainTypedArray(R.array.data_photo);
    }
}
