package swe.kne.chip08;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import swe.kne.chip08.chip8.Cpu;
import swe.kne.chip08.chip8.DebugSprite;

import java.io.File;

public class chip08mainwindow extends ApplicationAdapter {

	private Cpu cpu;
	private OrthographicCamera camera;
	ShapeRenderer sr;
	public static final int resolutionMultiplicity = 8; // so, turn 64 * 32 into 512 * 256

	
	@Override
	public void create () {
		cpu = new Cpu(new File("roms/pong.rom"));
		//cpu = new Cpu(new File("roms/test_opcode.ch8"));
		camera = new OrthographicCamera();
		camera.setToOrtho(true, (64 * resolutionMultiplicity), (32 * resolutionMultiplicity));
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
		sr = new ShapeRenderer();
	}

	@Override
	public void render () {
		super.render();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.

		while (cpu.getRunning()) {
			cpu.tick();
			cpu.debugLoggingOutput();
			if (cpu.checkIfRenderScreen && cpu.renderWholeScreen) {
				// hela skärmem måste ritas om
				;
			}
			else if (cpu.checkIfRenderScreen) {
				// rendera bara en sprite
				//Sprite spr = cpu.getLatestSprite();
			}
			else {
				// gör nada, kan nog tas bort.
				;
			}

			for (DebugSprite ds : cpu.dsal) {
				debugDrawCustomSprite(ds.pixels, ds.x, ds.y);
			}
		}

		// This is used to keep the sprites on screen after the emulator has "crashed".
		for (DebugSprite ds : cpu.dsal) {
			debugDrawCustomSprite(ds.pixels, ds.x, ds.y);
		}
	}
	
	@Override
	public void dispose () {
		;
	}

	public void debugDrawCustomSprite(char[] pixels, int x, int y) {
		camera.update();

		sr.setProjectionMatrix(camera.combined);
		int height = (pixels.length / 8);

		sr.begin(ShapeRenderer.ShapeType.Filled);

		for (int iRow = 0; iRow < (height - 1); iRow++) {
			for (int iCol = 0; iCol < 8; iCol++) {
				int currentPos = ((iRow * 8) + (iCol));
				if (pixels[currentPos] == '1') {
					sr.setColor(Color.WHITE);
					sr.rect(((iCol + x) * 8), ((iRow + y) * 8), 8, 8);
				}
				else if (pixels[currentPos] == '0') {
					sr.setColor(Color.BLACK);
					sr.rect(((iCol + x) * 8), ((iRow + y) * 8), 8, 8);
				}
			}

		}
		sr.end();
		cpu.debugDraw = false;
	}
}
