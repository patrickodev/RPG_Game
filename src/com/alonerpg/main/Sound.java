package com.alonerpg.main;

import java.applet.Applet;
import java.applet.AudioClip;


@SuppressWarnings("deprecation")
public class Sound {
	
	private AudioClip clip;
	
	public static final Sound songBackground = new Sound("/music.wav");
	
	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		}catch(Throwable e){
			
		}
	}
	
	public void Play() {
		try {
			new Thread(){
				public void run() {
					clip.play();
				}
			}.start();
		}catch(Throwable e) {}
	}
	
	public void Loop() {
		try {
			new Thread(){
				public void run() {
					clip.loop();
				}
			}.start();
		}catch(Throwable e) {}
	}
}
