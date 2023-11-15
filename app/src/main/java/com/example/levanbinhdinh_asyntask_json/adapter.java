package com.example.levanbinhdinh_asyntask_json;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class adapter extends BaseAdapter {
    private Context context;
    private ArrayList<contact>list;
    int layout;

    public adapter(Context context, ArrayList<contact> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view==null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout,null);

            viewHolder.img = view.findViewById(R.id.image);
            viewHolder.txtTitle = view.findViewById(R.id.name);
            viewHolder.txtDesc = view.findViewById(R.id.desc);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.txtTitle.setText(list.get(position).getName());
        viewHolder.txtDesc.setText( list.get(position).getDescription());

        new LoadImageTask(viewHolder.img).execute(list.get(position).getImage());

        return view;
    }

    class ViewHolder{
        TextView txtTitle;
        TextView txtDesc;
        ImageView img;
    }
    ViewHolder viewHolder;


    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        Bitmap bitmap;
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                //InputStream lay data tren internet về
                InputStream inpurstream = url.openConnection().getInputStream();
                bitmap = BitmapFactory.decodeStream(inpurstream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(bitmap);
            bmImage.setImageBitmap(result);
        }
    }
}
