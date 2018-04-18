package shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.helper;
import android.content.Context;
import android.media.MediaPlayer;

import shouzhuosunandyingxumu.cs301.cs.wm.edu.amazebyshouzhuosunandyingxumu.R;

public class SoundHelper {
    private MediaPlayer mMusicPlayer;
    public SoundHelper() {
    }
    public void preparePreGameMusicPlayer(Context context){
        mMusicPlayer = MediaPlayer.create(context.getApplicationContext(),
                R.raw.game_preparing_music);
        mMusicPlayer.setVolume(.5f,.5f);
        mMusicPlayer.setLooping(true);
    }
    public void prepareInGameMusicPlayer(Context context){
        mMusicPlayer = MediaPlayer.create(context.getApplicationContext(),
                R.raw.game_playing_music);
        mMusicPlayer.setVolume(.5f,.5f);
        mMusicPlayer.setLooping(true);
    }
    public void prepareFailureMusicPlayer(Context context){
        mMusicPlayer = MediaPlayer.create(context.getApplicationContext(),
                R.raw.losing_music);
        mMusicPlayer.setVolume(.5f,.5f);
    }
    public void prepareWinningMusicPlayer(Context context){
        mMusicPlayer = MediaPlayer.create(context.getApplicationContext(),
                R.raw.winning_music);
        mMusicPlayer.setVolume(.5f,.5f);
    }
    public void playMusic(){
        if (mMusicPlayer != null){
            mMusicPlayer.start();
        }
    }
    public void pauseMusic(){
        if(mMusicPlayer != null && mMusicPlayer.isPlaying()){
            mMusicPlayer.pause();
        }
    }
}
