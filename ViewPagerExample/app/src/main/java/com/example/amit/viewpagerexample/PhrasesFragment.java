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

public class PhrasesFragment extends Fragment {
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
        list.add(new Word("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        list.add(new Word("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        list.add(new Word("My name is...","oyaaset...",R.raw.phrase_my_name_is));
        list.add(new Word("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        list.add(new Word("I’m feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        list.add(new Word("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        list.add(new Word("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        list.add(new Word("I’m coming.","әәnәm",R.raw.phrase_im_coming));
        list.add(new Word("Let’s go.","yoowutis",R.raw.phrase_lets_go));
        list.add(new Word("Come here.","әnni'nem",R.raw.phrase_come_here));

        WordAdapter itemAdapter=new WordAdapter(getContext(),list,R.color.category_phrases);
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
