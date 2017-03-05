package th.ac.buapit.buaproid.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import th.ac.buapit.buaproid.Model.NewsModel;
import th.ac.buapit.buaproid.R;


public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.VersionViewHolder> {

    Context context;
    OnItemClickListener clickListener;
    private List<NewsModel> itemList;

    public NewsRecyclerViewAdapter(Context context, List<NewsModel> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    public NewsRecyclerViewAdapter(List<NewsModel> mListNewsModel) {
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_home, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final VersionViewHolder versionViewHolder, final int position) {
        versionViewHolder.m_title.setText(itemList.get(position).getNewsTitle());
//        versionViewHolder.m_date.setText(itemList.get(position).getNewsModified());
//        versionViewHolder.m_content.setText(itemList.get(position).getNewsContent());

        Glide.with(context)
                .load(itemList.get(position).getNewsImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(versionViewHolder.m_image);

        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat destFormat = new SimpleDateFormat("dd MMM 'พ.ศ' yyyy"); //here 'a' for AM/PM

        Date date = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(sourceFormat.parse(itemList.get(position).getNewsModified()));
            cal.add(Calendar.YEAR, 543);
            date = cal.getTime();
//            date = sourceFormat.parse(itemList.get(position).getNewsModified());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = destFormat.format(date);
        versionViewHolder.m_date.setText(formattedDate);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public void clear() {
        itemList.clear();
        notifyDataSetChanged();
    }

    class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView m_title, m_date;
        TextView m_content;
        ImageView m_image;

        public VersionViewHolder(View itemView) {
            super(itemView);

            m_title = (TextView) itemView.findViewById(R.id.x_title);
            m_content = (TextView) itemView.findViewById(R.id.x_content);
            m_date = (TextView) itemView.findViewById(R.id.x_date);
            m_image = (ImageView) itemView.findViewById(R.id.title_img);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            clickListener.onItemClick(v, position);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
}

