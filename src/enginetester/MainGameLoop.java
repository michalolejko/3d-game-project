package enginetester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import audio.Music;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.PlayerShip;
import models.RawModel;
import models.TextureModel;
import renderengine.DisplayManager;
import renderengine.MasterRenderer;
import renderengine.ModelLoader;
import renderengine.OBJLoader;
import renderengine.ModelRender;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		ModelLoader modelLoader = new ModelLoader();		
		
		Light lightSource = new Light(new Vector3f(0,0,-200), new Vector3f(1,1,1));//Light(gdzie,jaki kolor RGB)
				
		List<Entity> allAsteroids = new ArrayList<Entity>();
		
		RawModel asteroidModel1 = OBJLoader.loadObjModel("Asteroid1", modelLoader);//generowanie losowych meteorytow
		RawModel asteroidModel2 = OBJLoader.loadObjModel("Asteroid2", modelLoader);
		
		//TextureModel asteroid1 = new TextureModel(asteroidModel1, new ModelTexture(modelLoader.loadTexture("AsteroidTexture1"))); // oryginalne tekstury meteorow
		//TextureModel asteroid2 = new TextureModel(asteroidModel2, new ModelTexture(modelLoader.loadTexture("AsteroidTexture2"))); // oryginalne tekstury meteorow
		TextureModel asteroid1 = new TextureModel(asteroidModel1, new ModelTexture(modelLoader.loadTexture("gray")));
		TextureModel asteroid2 = new TextureModel(asteroidModel2, new ModelTexture(modelLoader.loadTexture("gray")));
		
		TextureModel randomAsteroid;
		
		Random random = new Random();
		for (int i = 0; i<2; i++) 			
			for (int j = 0; j<200; j++) {
				float x = random.nextFloat() * 200-100;
				float y = random.nextFloat() * 200-100;
				float z = random.nextFloat() * -400;
				if(i==0)
					randomAsteroid = asteroid1;
				else
					randomAsteroid = asteroid2;
				allAsteroids.add(new Entity(randomAsteroid, new Vector3f(x,y,z), random.nextFloat()*180f, random.nextFloat()*180f,0f,1f));
			}
		
		Music music = new Music();
		try {			
			String randomMusicTrack = "Mianite";
			
			switch(random.nextInt(3)) {
			case 0:
				randomMusicTrack = "Mianite";
				break;
			case 1:
				randomMusicTrack = "Microbots";
				break;
			case 2:
				randomMusicTrack = "Nightmare";
				break;
			}
			
			music.playSound(randomMusicTrack);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		//statek gracza
		RawModel tieInterceptRawModel = OBJLoader.loadObjModel("TieInter", modelLoader);
		TextureModel tieInterceptModel = new TextureModel(tieInterceptRawModel, new ModelTexture(modelLoader.loadTexture("gray")));
		PlayerShip playerShip = new PlayerShip(tieInterceptModel, new Vector3f(0,0,-10), 0, 0, 0, 1);
		
		ModelTexture texture = tieInterceptModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
				
		MasterRenderer renderer = new MasterRenderer();
		Camera camera = new Camera(playerShip);
		
		/*RawModel testModel = OBJLoader.loadObjModel("TheCity", modelLoader);
		ModelTexture testTexture = new ModelTexture(modelLoader.loadTexture("image"));
		TextureModel testTextureModel = new TextureModel(testModel,testTexture);
		Entity testEntity = new Entity(testTextureModel, new Vector3f(0, 0, -10), 0, 0, 0, 1);*/
		
		while (!Display.isCloseRequested()) {
			camera.move();
			
			playerShip.move(); //gracz
			renderer.processEntity(playerShip);
			//renderer.processEntity(testEntity);

			
			for (Entity asteroid : allAsteroids) //meteory
				renderer.processEntity(asteroid);
			
			renderer.render(lightSource, camera);
			DisplayManager.updateDisplay();
		}

		renderer.cleanUp();
		modelLoader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
