package com.omt.omtkosher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public  class AppsAdapter extends FirestoreRecyclerAdapter<AppModel,AppsAdapter.myviewHolder> {

    Context context;


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AppsAdapter(@NonNull FirestoreRecyclerOptions<AppModel> options, Context c) {
        super(options);
        this.context = c;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewHolder holder, int position, @NonNull AppModel model) {

        if(Objects.equals(model.getImageLink(), "") && Objects.equals(model.getLink(), "") && Objects.equals(model.getName(), "")){
            holder.namecv.setVisibility(View.INVISIBLE);

        }
        else{
            holder.title.setText(model.getName());
            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse(model.getLink());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            });
            Picasso.get()
                    .load(model.getImageLink())
                    .into(holder.img);
        }






    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.apps_display_layout,parent,false);

        return new myviewHolder(v);
    }

    static class myviewHolder extends RecyclerView.ViewHolder
    {

        TextView title;
        CardView cv;
        CardView namecv;
        ImageView img;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
           title = itemView.findViewById(R.id.titletv);
           cv = itemView.findViewById(R.id.app_card);
           img = itemView.findViewById(R.id.imgv);
           namecv = itemView.findViewById(R.id.name_cv);




        }
    }
}
