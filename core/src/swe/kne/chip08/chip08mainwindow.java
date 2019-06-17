package swe.kne.chip08;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import swe.kne.chip08.chip8.*;

import java.io.File;

public class chip08mainwindow extends ApplicationAdapter {

	private Messages messages;
	private Cpu cpu;
	private Memory memory;
	private Graphics graphics;
	private OrthographicCamera camera;
	ShapeRenderer sr;
	public static final int resMulti = 8; // so, turn 64 * 32 into 512 * 256

	@Override
	public void create () {
		messages = new Messages();
		cpu = new Cpu(messages);
		//cpu = new Cpu(new File("roms/test_opcode.ch8"));
		memory = new Memory(messages);
		graphics = new Graphics(messages);
		messages.addComponents(cpu, graphics, memory);
		cpu.resetMachine();
		messages.memoryLoadRom(new File("roms/pong.rom"), cpu.getProgramCounter());
		memory.loadFont(0x00);


		camera = new OrthographicCamera();
		camera.setToOrtho(true, 64 * resMulti, 32 * resMulti); //Y is down, 0 at top etc.
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clears screen
		sr = new ShapeRenderer();
	}

	@Override
	public void render () {
		super.render();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clears screen

		while (cpu.getRunning()) {
			cpu.tick();
			cpu.debugLoggingOutput();
			if (graphics.wantRenderScreen()) {
				drawFullScreen();
			}
		}
		drawFullScreen();
	}
	
	@Override
	public void dispose () {
		;
	}


	public void drawFullScreen() {
		camera.update();
		sr.setProjectionMatrix(camera.combined);

		sr.begin(ShapeRenderer.ShapeType.Filled);
		boolean[] pixels = graphics.getAllPixels();

		for (int height = 0; height < 32; height++) {
			for (int width = 0; width < 64; width++) {
				int currentPos = height * 64 + width;
				if (pixels[currentPos]) {
					sr.setColor(Color.WHITE);
					sr.rect((width * resMulti), (height * resMulti), resMulti, resMulti);
				}
			}
		}
		sr.end();
	}
}
