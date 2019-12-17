package renderengine;

import java.util.Map;

import background.BackgroundRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import models.TextureModel;
import shaders.StaticShader;
import entities.Entity;
import entities.Light;
import entities.Camera;

public class MasterRenderer {

	private StaticShader shader = new StaticShader();
	private ModelRender renderer = new ModelRender(shader);
	
	private Map<TextureModel, List<Entity>> entities = new HashMap<TextureModel,List<Entity>>();
	
	private BackgroundRenderer backgroundRenderer;
	
	/*public MasterRenderer(ModelLoader loader) {
		//enableCulling();
		
	}*/
	
	public void render(Light sun, Camera camera) {
		renderer.prepare();
		shader.start();
		shader.loadLight(sun);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		entities.clear();
	}
	
	public void processEntity(Entity entity) {
		TextureModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch!=null)
			batch.add(entity);
		else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
}
