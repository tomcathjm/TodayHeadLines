package com.todayheadlines.fragment.home;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.todayheadlines.R;
import com.todayheadlines.adapter.NewsTuiJianAdapter;
import com.todayheadlines.base.BaseFragment;
import com.todayheadlines.model.NewsBean;
import com.todayheadlines.utils.JsonDataLoader;
import com.todayheadlines.utils.NetWorkUtils;
import com.todayheadlines.widget.MyVideoView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by HJM on 2016/8/16.
 */
public class ReDian extends BaseFragment {

    @Bind(R.id.videoview)
    MyVideoView videoView;
    @Bind(R.id.play)
    Button play;
    @Bind(R.id.stop)
    Button stop;
    @Bind(R.id.go_play)
    Button go_play;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_redian, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @OnClick(R.id.play)
    void play() {
        playVideo(videoView);
        videoView.start();
    }
    @OnClick(R.id.stop)
    void stop() {
        videoView.stopPlayback();
    }

    private void playVideo(final VideoView video) {
        dataPath = getActivity().getCacheDir().getPath();
        String path = getStorageDirectory();
        final File file = new File(path);
        final MediaController mc = new MediaController(getActivity());       // 创建一个MediaController对象
        if (file.exists()) {
            video.setVideoPath(path);
//            video.setVideoURI(Uri.parse("http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8"));
            video.setMediaController(mc);       // 将VideoView与MediaController关联起来
            video.requestFocus();       // 设置VideoView获取焦点

            // 设置VideoView的Completion事件监听器
            video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Toast.makeText(getActivity(), "播放完毕", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "要播放的视频文件不存在", Toast.LENGTH_SHORT).show();
        }
    }

    //获取缓存目录
    private String getStorageDirectory() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? sdPath + FILE_NAME : dataPath + FILE_NAME;
    }

    // sdCard  根路径
    private String sdPath = Environment.getExternalStorageDirectory().getPath();
    // 手机内存 根路径
    private String dataPath = null;
    // 缓存文件根目录
    private String FILE_NAME = "/video/1.mp4";
}
