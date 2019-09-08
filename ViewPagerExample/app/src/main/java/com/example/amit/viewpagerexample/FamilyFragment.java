package com.example.amit.viewpagerexample;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Fragment that displays "Monday".
 */
public class FamilyFragment extends Fragment {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ListView listView= (ListView) inflater.inflate(R.layout.word_list,container,false);

        final ArrayList<Word> list=new ArrayList<>();
        list.add(new Word("father","әpә",R.drawable.family_father,R.raw.family_father));
        list.add(new Word("mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        list.add(new Word("son","angsi",R.drawable.family_son,R.raw.family_son));
        list.add(new Word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        list.add(new Word("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        list.add(new Word("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        list.add(new Word("older sister","teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        list.add(new Word("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        list.add(new Word("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        list.add(new Word("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));

        WordAdapter itemAdapter=new WordAdapter(getContext(),list,R.color.category_family);
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
