package swe.kne.chip08;

import com.badlogic.gdx.ApplicationAdapter;
import swe.kne.chip08.chip8.Cpu;

public class chip08mainwindow extends ApplicationAdapter {

	private Cpu cpu = new Cpu();

	
	@Override
	public void create () {
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
