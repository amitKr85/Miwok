package com.example.amit.viewpagerexample;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by AMIT on 21-03-2018.
 */

public class ColorsFragment extends Fragment {
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }else if (focusChange==AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            }else if (focusChange==AudioManager.AUDIOFOCUS_LOSS){
                mediaPlayer.stop();
                releaseMedia();
            }

        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ListView listView= (ListView) inflater.inflate(R.layout.word_list,container,false);

        final ArrayList<Word> list=new ArrayList<>();
        list.add(new Word("red","weṭeṭṭi",R.drawable.color_red,R.raw.color_red));
        list.add(new Word("green","chokokki",R.drawable.color_green,R.raw.color_green));
        list.add(new Word("brown","ṭakaakki",R.drawable.color_brown,R.raw.color_brown));
        list.add(new Word("gray","ṭopoppi",R.drawable.color_gray,R.raw.color_gray));
        list.add(new Word("black","kululli",R.drawable.color_black,R.raw.color_black));
        list.add(new Word("white","kelelli",R.drawable.color_white,R.raw.color_white));
        list.add(new Word("dusty yellow","ṭopiisә",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        list.add(new Word("mustard yellow","chiwiiṭә",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));

        WordAdapter itemAdapter=new WordAdapter(getContext(),list,R.color.category_colors);
        listView.setAdapter(itemAdapter);

        audioManager= (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);

        final MediaPlayer.OnCompletionListener onCompletionListener=new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                releaseMedia();
            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word item=list.get(position);
                //to release previous media resource before loading current resource
                releaseMedia();
                int response=audioManager.requestAudioFocus(onAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(response==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaPlayer = MediaPlayer.create(getContext(), item.getmAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });

        return listView;
    }
    public void releaseMedia(){
        if(mediaPlayer!=null) {
            mediaPlayer.release();
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
            mediaPlayer = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMedia();
    }
}
