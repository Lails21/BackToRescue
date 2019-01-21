package dsa.eetac.upc.edu.backtorescueapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Recycler2 extends RecyclerView.Adapter<Recycler2.ViewHolder>{
    private List<Objeto> data2;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTextView;
        private TextView imageTextView;
        private ConstraintLayout constraintLayout;
        public ViewHolder(View v){
            super(v);
            nameTextView=v.findViewById(R.id.nomobjeto);
            imageTextView=v.findViewById(R.id.imageobjeto);
            constraintLayout = v.findViewById(R.id.constraintLayout2);
        }

    }

    public void addObject (List<Objeto> objList)
    {
        data2.addAll(objList);
        notifyDataSetChanged();
    }
    public Recycler2 (Context context){
        this.data2=new ArrayList<>();
        this.context=context;
    }
    @Override
    public Recycler2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v=LayoutInflater.from(parent.getContext()).inflate(R.layout.item2_layout, parent, false);
        return new Recycler2.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Recycler2.ViewHolder holder, int position) {
        Objeto objetodata = ((Objeto) data2.get(position));
        holder.nameTextView.setText("Name: " +objetodata.name);
        holder.imageTextView.setText("Image: " +objetodata.image);
    }

    @Override
    public int getItemCount() {
        return data2.size();    }
}
