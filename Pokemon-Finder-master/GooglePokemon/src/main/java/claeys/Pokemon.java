package claeys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.rickylaishram.googlepokemon.googlepokemon.R;

import java.util.ArrayList;
import java.util.List;


public class Pokemon extends Activity {

    private Context ctx;
    private EditText search;
    private ListView list;

    private List<String> pokemons;
    private List<String> current;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        ctx = this;

        setUi();
        setData();
    }

    private void setData() {
        pokemons = pokemonList("");

        current = pokemonList("");

        adapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1, current);

        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setUi() {
        search = (EditText) findViewById(R.id.search);
        list = (ListView) findViewById(R.id.list);

        search.addTextChangedListener(searchHandler);
        list.setOnItemClickListener(clickHandler);
    }

    private List<String> pokemonList(String filter) {
        List<String> pokemon = new ArrayList<String>();
        String[] all_pokemon = ctx.getResources().getStringArray(R.array.pokemon);

        for (int i = 0; i < all_pokemon.length - 1; i++) {
            if(all_pokemon[i].toLowerCase().contains(filter.toLowerCase())) {
                pokemon.add(all_pokemon[i]);
            }
        }


        return pokemon;
    }

    private TextWatcher searchHandler = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            current = pokemonList(s.toString());

            adapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1, current);

            list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private AdapterView.OnItemClickListener clickHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final String EXTRA_LENG = "urls";
            final String EXTRA_LAT = "lat";
            final String EXTRA_NAME = "name";

            int i = getIndex(current.get(position));
            String[] urls = ctx.getResources().getStringArray(R.array.location);
            String[] latitu = ctx.getResources().getStringArray(R.array.Lati);
            String[] name = ctx.getResources().getStringArray(R.array.pokemon);

           double tempUrls = Double.valueOf(urls[i]);
           double tempsLat = Double.valueOf(latitu[i]);
           String tempName = String.valueOf(name[i]);

            Intent mIntent = new Intent(Pokemon.this, GoogleMapPokemon.class);
            mIntent.putExtra(EXTRA_LENG,tempUrls);
            mIntent.putExtra(EXTRA_LAT,tempsLat);
            mIntent.putExtra(EXTRA_NAME,tempName);
            startActivity(mIntent);

        }
    };

    private int getIndex(String name) {
        for (int i =0; i < pokemons.size(); i++) {
            if (pokemons.get(i).equals(name)) {
                return i;
            }
        }

        return 0;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.pokemon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
