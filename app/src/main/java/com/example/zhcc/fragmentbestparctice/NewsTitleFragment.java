package com.example.zhcc.fragmentbestparctice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by ZHCC on 2018/8/23
 */
public class NewsTitleFragment extends Fragment {

    private boolean isTwoPane;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_title_frag, container, false);

        RecyclerView newsTitleRecyclerView = (RecyclerView) view.findViewById(R.id.news_title_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        newsTitleRecyclerView.setLayoutManager(layoutManager);
        NewsAdapter adapter = new NewsAdapter(getNews());
        newsTitleRecyclerView.setAdapter(adapter);

        return view;
    }

    private List<News> getNews() {

        List<News> newsList = new ArrayList<>();
        for(int i=1;i<=50;i++) {
            News news=new News();
            news.setTitle("this is title " + i);
            news.setContent("dfjssldkjfsdfls");
            newsList.add(news);
        }

        return newsList;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_layout) != null) {
            isTwoPane = true; //双页
        } else {
            isTwoPane=false;//单页
        }
    }

     class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

        private List<News> mNewsList;

        @Override
        public  void onBindViewHolder(ViewHolder holder, int position){

            News news = mNewsList.get(position);
            holder.newsTitleText.setText(news.getTitle());
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            TextView newsTitleText;

            public ViewHolder(View view){
                super(view);
                newsTitleText = (TextView) view.findViewById(R.id.news_title);
            }
        }

        public NewsAdapter(List<News> newsList) {
            mNewsList=newsList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate((R.layout.news_item), parent, false);
            final ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    News news = mNewsList.get(holder.getAdapterPosition());
                    if (isTwoPane) {
                        //双页
                        NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager().findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(news.getTitle(), news.getContent());
                    } else {
                        //单页
                        NewsContentActivity.actionStart(getActivity(),news.getTitle(),news.getContent());
                    }
                }
            });
            return holder;
        }


        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }
}
