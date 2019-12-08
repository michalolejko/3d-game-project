package enginetester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import models.RawModel;
import models.TextureModel;
import renderengine.DisplayManager;
import renderengine.ModelLoader;
import renderengine.OBJLoader;
import renderengine.RenderModel;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		ModelLoader modelLoader = new ModelLoader();
		StaticShader shader = new StaticShader();
		RenderModel modelRenderer = new RenderModel(shader);
		


		//RawModel model = modelLoader.loadToVAO(vertices, textureCoords, indices);
		RawModel model = OBJLoader.loadObjModel("TheCity", modelLoader);
		
		//ModelTexture texture = new ModelTexture(modelLoader.loadTexture("image"));
		//TextureModel texturedModel = new TextureModel(model, new ModelTexture(modelLoader.loadTexture("image")));
		TextureModel cityModel = new TextureModel(model, new ModelTexture(modelLoader.loadTexture("cityTexture")));
		//Entity entity = new Entity(texturedModel, new Vector3f(0,0,-1),0,0,0,1);
		Entity entity = new Entity(cityModel, new Vector3f(0,-100,-1000),0,0,0,1);
		
		Camera camera = new Camera();
		
		while (!Display.isCloseRequested()) {
			//entity.increasePosition(0, 0, -0.01f);
			//entity.increaseRotation(0, 0.1f, 0);
			camera.move();
			modelRenderer.prepare();
			shader.start();
			shader.loadViewMatrix(camera);
			modelRenderer.render(entity,shader);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		
		shader.cleanUp();
		modelLoader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
