package com.querychain.mainapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.ByteArrayInputStream;
import java.util.List;
import android.view.ViewGroup;


public class CertificationImageAdapter extends RecyclerView.Adapter<CertificationImageAdapter.MyViewHolder> {

    private List<Certifications> certificationsList;
    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        public TextView txtTitle, resultID;
        public ImageView imgIcon;
        public CardView mCardView;
        public MyViewHolder(View view) {
            super(view);
            txtTitle = view.findViewById(R.id.txtTitle);
            imgIcon = view.findViewById(R.id.imgIcon);
            mCardView = view.findViewById(R.id.card_view);
            resultID = view.findViewById(R.id.LibraryResultID);
            mCardView.setOnClickListener(this);
            imgIcon.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            resultID.setText(Integer.toString(position));
            Intent intent = new Intent(view.getContext(), ViewNote.class);
            intent.putExtra("dbKey", resultID.getText());
            view.getContext().startActivity(intent);
        }
    }

    public CertificationImageAdapter(Context mContext, List<Certifications> certificationsList) {
        this.certificationsList = certificationsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.screen_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Certifications certifications = certificationsList.get(position);
        holder.txtTitle.setText(certifications.name);
        byte[] outImage = certifications.image;
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        holder.imgIcon.setImageBitmap(theImage);
        holder.mCardView.setTag(position);
        holder.imgIcon.setTag(position);
    }

    @Override
    public int getItemCount() {
                return certificationsList.size();
    }
}


