/**
 * @file SoundManager.java
 * @Package com.domker.androidtoy.sound
 * @Project AndroidToy
 * Description: TODO 
 * 
 * @author wanlipeng
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2016 下午5:56:21
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2014
 *       wanlipeng. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n wanlipeng | 2016 下午5:56:21 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * wanlipeng |2016 下午5:56:21 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.sound;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * 声音管理
 * 
 * @ClassName: SoundManager
 * @author wanlipeng
 * @date 2016年1月20日 下午5:56:21
 */
public class SoundManager {
	private Context mContext = null;
	private SoundPool mSoundPool = null;
	private float volume = 1;
	private Map<String, Integer> soundResMap = new HashMap<String, Integer>();

	public SoundManager(Context mContext) {
		this.mContext = mContext;
		initSoundPool();
	}

	/**
	 * 播放自定义声音
	 * 
	 * @param volume
	 */
	public void playSound(String pianoKey) {
		if (mSoundPool == null) {
			initSoundPool();
		}
		int soundId = getSoundId(pianoKey);
		mSoundPool.play(soundId, volume, volume, 0, 0, 1);
	}

	/**
	 * 播放声音的池
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private void initSoundPool() {
		if (mSoundPool == null) {
			// 判断系统sdk版本，如果版本超过21，调用第一种
//			if (Build.VERSION.SDK_INT >= 21) {
//				SoundPool.Builder builder = new SoundPool.Builder();
//				builder.setMaxStreams(2);// 传入音频数量
//				// AudioAttributes是一个封装音频各种属性的方法
//				AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
//				attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);// 设置音频流的合适的属性
//				builder.setAudioAttributes(attrBuilder.build());// 加载一个AudioAttributes
//				mSoundPool = builder.build();
//			} else {
				mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
//			}
		}
	}

	/**
	 * 根据这个key返回声音的id
	 * 
	 * @param key
	 * @return int
	 */
	private int getSoundId(String key) {
		if (soundResMap.containsKey(key)) {
			return soundResMap.get(key);
		}
		AssetFileDescriptor afd;
		try {
			afd = mContext.getAssets().openFd("piano/" + key + ".mp3");
			int soundId = mSoundPool.load(afd, 1);
			soundResMap.put(key, soundId);
			return soundId;
		} catch (IOException e) {
			return 0;
		}
	}
}
