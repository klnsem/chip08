package swe.kne.chip08;

import com.badlogic.gdx.ApplicationAdapter;
import swe.kne.chip08.chip8.Cpu;

import java.io.File;

public class chip08mainwindow extends ApplicationAdapter {

	private Cpu cpu;

	
	@Override
	public void create () {
		cpu = new Cpu(new File("roms/pong.rom"));
		cpu.debugCpu();
		;
	}

	@Override
	public void render () {
		;
	}
	
	@Override
	public void dispose () {
		;
	}
}
