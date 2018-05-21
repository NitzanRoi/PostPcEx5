package f.s.myimgur;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private MainActivity mMain;

    Adapter(MainActivity mMain) {
        this.mMain = mMain;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Picasso
            .with(mMain.getApplicationContext())
            .load("https://i.imgur.com/" + mMain.getPhotos().get(position).id + ".jpg")
            .into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return mMain.getPhotos().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView photo;
        MyViewHolder(View view) {
            super(view);
            photo = (ImageView) view.findViewById(R.id.iv);
        }
    }
}

