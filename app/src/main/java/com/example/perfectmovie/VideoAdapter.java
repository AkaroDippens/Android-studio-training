package com.example.perfectmovie;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perfectmovie.models.Film;
import com.example.perfectmovie.models.Trailer;
import com.example.perfectmovie.models.Video;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>{

    private final Context context;
    private final ArrayList<Video> videos;

    public VideoAdapter(Context context, ArrayList<Video> videos) {
        this.context = context;
        this.videos = videos;
    }

    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video, parent, false);
        return new VideoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.ViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.textView.setText(video.getName());
        String link = video.getUrl();
        String source = video.getSite();
        String url = link.substring(link.length()-11);
        try {
            holder.videoView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    super.onReady(youTubePlayer);
                    youTubePlayer.cueVideo(url, 0);
                }
            });
        } catch (ExceptionInInitializerError e) {

        }
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        YouTubePlayerView videoView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.playerView);
            textView = itemView.findViewById(R.id.title);

        }
    }
}