package com.example.amit.viewpagerexample;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by AMIT on 21-03-2018.
 */

public class NumbersFragment extends Fragment {
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        ListView listView= (ListView) inflater.inflate(R.layout.word_list,container,false);
        final ArrayList<Word> list=new ArrayList<>();
        list.add(new Word("one","lutti",R.drawable.number_one,R.raw.number_one));
        list.add(new Word("two","otiiko",R.drawable.number_two,R.raw.number_two));
        list.add(new Word("three","tolookuso",R.drawable.number_three,R.raw.number_three));
        list.add(new Word("four","oyyisa",R.drawable.number_four,R.raw.number_four));
        list.add(new Word("five","massokka",R.drawable.number_five,R.raw.number_five));
        list.add(new Word("six","temmoka",R.drawable.number_six,R.raw.number_six));
        list.add(new Word("seven","kenekaku",R.drawable.number_seven,R.raw.number_seven));
        list.add(new Word("eight","kavinta",R.drawable.number_eight,R.raw.number_eight));
        list.add(new Word("nine","wo'e",R.drawable.number_nine,R.raw.number_nine));
        list.add(new Word("ten","na'aacha",R.drawable.number_ten,R.raw.number_ten));


        WordAdapter itemAdapter=new WordAdapter(getContext(),list,R.color.category_numbers);
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
