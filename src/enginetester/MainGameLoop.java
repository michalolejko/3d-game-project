package enginetester;

import org.lwjgl.opengl.Display;

import renderengine.DisplayManager;
import renderengine.ModelLoader;
import renderengine.RawModel;
import renderengine.RenderModel;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();

		ModelLoader modelLoader = new ModelLoader();
		RenderModel modelRenderer = new RenderModel();

		float[] vertices = { -0.5f, 0.5f, 0f, -0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f,

				0.5f, -0.5f, 0f, 0.5f, 0.5f, 0f, -0.5f, 0.5f, 0f };

		RawModel model = modelLoader.loadToVAO(vertices);

		while (!Display.isCloseRequested()) {

			modelRenderer.prepare();

			modelRenderer.render(model);

			DisplayManager.updateDisplay();
		}

		modelLoader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
