package com.game.desktop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class LoadModelsTest implements ApplicationListener {

    private Camera cam;
    private ModelBatch modelBatch;
    private AssetManager assets;
    private ModelInstance space;
    private Array<ModelInstance> instances = new Array<ModelInstance>();
    private Array<ModelInstance> blocks = new Array<ModelInstance>();
    private Array<ModelInstance> invaders = new Array<ModelInstance>();
    private Environment environment;
    private boolean loading;
    private CameraInputController camController;

    @Override
    public void create () {
        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0f, 5f, 10f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();

        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);

        assets = new AssetManager();
        assets.load("ship.obj", Model.class);
        assets.load("block.obj", Model.class);
        assets.load("invader.obj", Model.class);
        assets.load("spacesphere.obj", Model.class);
        loading = true;
    }

    private void doneLoading() {
        Model ship = assets.get("ship.obj", Model.class);
        Model block = assets.get("block.obj", Model.class);
        Model invader = assets.get("invader.obj", Model.class);

        //Player
        ModelInstance shipInstance = new ModelInstance(ship);
        shipInstance.transform.setToRotation(Vector3.Y, 180).trn(0f,0f,5f);
        instances.add(shipInstance);

        //Blocks
        for(float x = -5f; x <= 5f; x += 2f){
            ModelInstance blockInstance = new ModelInstance(block);
            blockInstance.transform.setToTranslation(x, 0, 3f);
            instances.add(blockInstance);
            blocks.add(blockInstance);
        }

        //Enemies
        for (float x = -5f; x <= 5f; x += 2f) {
            for (float z = 1f; z >= -9f; z -= 2f) {
                ModelInstance invaderInstance = new ModelInstance(invader);
                invaderInstance.transform.setToTranslation(x, 0, z);
                instances.add(invaderInstance);
                invaders.add(invaderInstance);
            }
        }

        space = new ModelInstance(assets.get("spacesphere.obj", Model.class));
        loading = false;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        if (loading && assets.update())
            doneLoading();
        camController.update();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        modelBatch.render(instances, environment);
        if (space != null)
            modelBatch.render(space);
        modelBatch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        instances.clear();
        assets.dispose();
    }
}
