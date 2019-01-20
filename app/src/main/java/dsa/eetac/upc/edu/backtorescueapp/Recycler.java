package dsa.eetac.upc.edu.backtorescueapp;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Recycler extends RecyclerView.Adapter<Recycler.ViewHolder>  {
    private List<Player> data;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView usernameTextView;
        private TextView damageTextView;
        private TextView defenseTextView;
        private TextView healthTextView;
        private TextView levelTextView;
        private TextView manaTextView;
        private TextView moneyTextView;
        private ConstraintLayout constraintLayout;

        public ViewHolder(View v) {
            super(v);
            usernameTextView=v.findViewById(R.id.namerec);
            damageTextView=v.findViewById(R.id.damagerec);
            defenseTextView=v.findViewById(R.id.defenserec);
            healthTextView=v.findViewById(R.id.healthrec);
            levelTextView=v.findViewById(R.id.levelrec);
            manaTextView=v.findViewById(R.id.manarec);
            moneyTextView=v.findViewById(R.id.moneyrec);
            constraintLayout = v.findViewById(R.id.constraintLayout);
        }
    }

    public void addElements(List<Player> playerList) {
        data.addAll(playerList);
        notifyDataSetChanged();
    }

    public Recycler(Context context) {
        this.data = new ArrayList<>();
        this.context = context;
    }

    @Override
    public Recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Recycler.ViewHolder holder, int position) {
        Player characterdata = ((Player) data.get(position));
        holder.usernameTextView.setText(characterdata.username);
        holder.damageTextView.setText("Damage: " +characterdata.damage);
        holder.defenseTextView.setText("Defense: " +characterdata.defense);
        holder.healthTextView.setText("Health: " +characterdata.health);
        holder.levelTextView.setText("Level: " +characterdata.level);
        holder.moneyTextView.setText("Money: " +characterdata.money);
        holder.manaTextView.setText("Mana: " +characterdata.mana);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
