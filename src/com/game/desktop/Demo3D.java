package com.game.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Demo3D extends ApplicationAdapter implements InputProcessor {
    private PerspectiveCamera camera;
    private ModelBatch modelBatch;
    private ArrayList<ModelInstance> modelInstance;
    private Environment environment;
    CameraInputController camController;
    Model cylinder, box, cone, sphere;
    private Color PINK = new Color(.89f, .41f, .41f, 1f);
    private float soloHeight = 2f, baseHeight = 20f, floorHeight = 5f, varandaHeight = 30f, ceillingHeight = 5f, roofHeight = 7f;
    private float smallCylw = 2f, smallCylH = 5f, bigCylW = 10f, bigCylH = 80f;
    private float Hbase = soloHeight+baseHeight/2, Hfloor = soloHeight+baseHeight+floorHeight/2, Hceil = varandaHeight+baseHeight+bigCylH+floorHeight/2, Hroof = Hceil+roofHeight+floorHeight;
    private float radius = 60f, midpoint = 45;


    @Override
    public void create () {
        camera = new PerspectiveCamera(75,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.position.set(3f, 150f, 250f);
        camera.lookAt(0f,150f,0f);
        camera.near = 1f;
        camera.far = 700f;

        camController = new CameraInputController(camera);
        Gdx.input.setInputProcessor(camController);

        modelBatch = new ModelBatch();
        modelInstance = new ArrayList<ModelInstance>();
        ModelBuilder modelBuilder = new ModelBuilder();

        //Solo
        box = modelBuilder.createBox(800f, soloHeight, 600f,
                new Material(ColorAttribute.createDiffuse(Color.BROWN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        modelInstance.add(new ModelInstance(box, 0, 0, 0));

        //Base
        cylinder = modelBuilder.createCylinder(160f, baseHeight, 160f, 64,
                new Material(ColorAttribute.createDiffuse(PINK)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(cylinder,0, Hbase, 0));

        //Chão
        cylinder = modelBuilder.createCylinder(160f, floorHeight, 160f, 64,
                new Material(ColorAttribute.createDiffuse(Color.WHITE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(cylinder,0, Hfloor, 0));

        //Varanda
        box = modelBuilder.createBox(10f, varandaHeight, 10f,
                new Material(ColorAttribute.createDiffuse(PINK)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);


        //Pilares
        cylinder = modelBuilder.createCylinder(bigCylW, bigCylH, 10f, 20,
                new Material(ColorAttribute.createDiffuse(PINK)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        modelInstance.add(new ModelInstance(cylinder,0, baseHeight+varandaHeight +(bigCylH)/2, radius));
        modelInstance.add(new ModelInstance(box,0, baseHeight +(varandaHeight)/2, radius));
        modelInstance.add(new ModelInstance(cylinder,0, baseHeight+varandaHeight +(bigCylH)/2, -radius));
        modelInstance.add(new ModelInstance(box,0, baseHeight +(varandaHeight)/2, -radius));
        modelInstance.add(new ModelInstance(cylinder,radius, baseHeight+varandaHeight +(bigCylH)/2, 0));
        modelInstance.add(new ModelInstance(box,radius, baseHeight +(varandaHeight)/2, 0));
        modelInstance.add(new ModelInstance(cylinder,-radius, baseHeight+varandaHeight +(bigCylH)/2, 0));
        modelInstance.add(new ModelInstance(box,-radius, baseHeight +(varandaHeight)/2, 0));
        modelInstance.add(new ModelInstance(cylinder,midpoint, baseHeight +varandaHeight+(bigCylH)/2, midpoint));
        modelInstance.add(new ModelInstance(box,midpoint, baseHeight +(varandaHeight)/2, midpoint));
        modelInstance.add(new ModelInstance(cylinder,midpoint, baseHeight+varandaHeight +(bigCylH)/2, -midpoint));
        modelInstance.add(new ModelInstance(box,midpoint, baseHeight +(varandaHeight)/2, -midpoint));
        modelInstance.add(new ModelInstance(cylinder,-midpoint, baseHeight+varandaHeight +(bigCylH)/2, midpoint));
        modelInstance.add(new ModelInstance(box,-midpoint, baseHeight +(varandaHeight)/2, midpoint));
        modelInstance.add(new ModelInstance(cylinder,-midpoint, baseHeight +varandaHeight+(bigCylH)/2, -midpoint));
        modelInstance.add(new ModelInstance(box,-midpoint, baseHeight +(varandaHeight)/2, -midpoint));


        //Teto
        cylinder = modelBuilder.createCylinder(160f, ceillingHeight, 160f, 64,
                new Material(ColorAttribute.createDiffuse(Color.WHITE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(cylinder,0, Hceil, 0));

        cylinder = modelBuilder.createCylinder(165f, ceillingHeight, 160f, 64,
                new Material(ColorAttribute.createDiffuse(Color.WHITE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(cylinder,0, Hceil+floorHeight, 0));

        cylinder = modelBuilder.createCylinder(170f, ceillingHeight, 160f, 64,
                new Material(ColorAttribute.createDiffuse(Color.WHITE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(cylinder,0, Hceil+2*floorHeight, 0));

        //Telhado
        cylinder = modelBuilder.createCylinder(160f, roofHeight, 160f, 64,
                new Material(ColorAttribute.createDiffuse(PINK)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(cylinder,0, Hroof+roofHeight/2, 0));

        cylinder = modelBuilder.createCylinder(165, roofHeight, 160f, 64,
                new Material(ColorAttribute.createDiffuse(PINK)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(cylinder,0, Hroof+roofHeight, 0));

        cylinder = modelBuilder.createCylinder(170, roofHeight, 160f, 64,
                new Material(ColorAttribute.createDiffuse(PINK)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(cylinder,0, Hroof+(float)(1.5*roofHeight), 0));

    //TODO: Lixeira
        box = modelBuilder.createBox(25f, 40f, 25f,
                new Material(ColorAttribute.createDiffuse(Color.BLUE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(box,230, soloHeight+50f, 30));

        box = modelBuilder.createBox(25f, 40f, 25f,
                new Material(ColorAttribute.createDiffuse(Color.YELLOW)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(box,230, soloHeight+50f, 60));

        box = modelBuilder.createBox(25f, 40f, 25f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(box,230, soloHeight+50f, 90));

        box = modelBuilder.createBox(25f, 40f, 25f,
                new Material(ColorAttribute.createDiffuse(Color.RED)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(box,230, soloHeight+50f, 120));

        ///TODO: Barra que segura as 4 lixeiras
        box = modelBuilder.createBox(5f, 5f, 100f,
                new Material(ColorAttribute.createDiffuse(Color.GRAY)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(box,245, soloHeight+50f, 75));

        ///TODO: Haste de suporte do lixeiro
        cylinder = modelBuilder.createCylinder(10f, 60f, 10f, 32,
                new Material(ColorAttribute.createDiffuse(Color.GRAY)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(cylinder,245, soloHeight+30f, 75));

    //TODO: BANCO
        //TODO: PÉS do Banco
        box = modelBuilder.createBox(20f, 20f, 5f,
                new Material(ColorAttribute.createDiffuse(Color.GRAY)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(box, 230, soloHeight+10f, -40));

        box = modelBuilder.createBox(20f, 20f, 5f,
                new Material(ColorAttribute.createDiffuse(Color.GRAY)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(box, 230, soloHeight+10f, 0));
        //TODO: Assento
        box = modelBuilder.createBox(20f, 5f, 60f,
                new Material(ColorAttribute.createDiffuse(Color.GRAY)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(box,230, soloHeight+20f, -20));

    //TODO: Poste
        //TODO:Haste do poste
        cylinder = modelBuilder.createCylinder(10f, 120f, 10f, 32,
                new Material(ColorAttribute.createDiffuse(Color.GRAY)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(cylinder, 200, soloHeight+60f, -100));

        box = modelBuilder.createBox(30f, 8f, 8f,
                new Material(ColorAttribute.createDiffuse(Color.GRAY)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(box,190, soloHeight+125f, -100));

        cone = modelBuilder.createCone(30f, 30f, 30f, 32,
                new Material(ColorAttribute.createDiffuse(Color.GRAY)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(cone ,170, soloHeight+125f, -100));

        sphere = modelBuilder.createSphere(20f, 20f, 20f, 16, 16,
                new Material(ColorAttribute.createDiffuse(Color.YELLOW)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(sphere ,170, soloHeight+115f, -100));


        /*
        MeshBuilder meshBuilder = new MeshBuilder();
        meshBuilder.begin(VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        meshBuilder.part("id1", GL20.GL_TRIANGLES);
        meshBuilder.cylinder(1f, 1f, 1f, 30);
        Mesh mesh1 = meshBuilder.end();

        meshBuilder.begin(VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        meshBuilder.part("id2", GL20.GL_TRIANGLES);
        meshBuilder.cylinder(1f, 1f, 1f, 16);
        Mesh mesh2 = meshBuilder.end();


        modelBuilder.begin();

        modelBuilder.part("part1",
                mesh1,
                GL20.GL_TRIANGLES,
                new Material(ColorAttribute.createDiffuse(Color.RED)));

        modelBuilder.part("part2",
                mesh2,
                GL20.GL_TRIANGLES,
                new Material(ColorAttribute.createDiffuse(Color.RED)))
                .mesh.transform(new Matrix4().translate(1, 0, 0f));
        Model finalCylinder = modelBuilder.end();
        modelInstance.add(new ModelInstance(finalCylinder, 0, 0, 0));
        */
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.2f, 0.2f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(1f, 1f, 1f, -0.2f, -0.8f, -1f));
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);

        camera.update();
        modelBatch.begin(camera);
        modelBatch.render(modelInstance, environment);
        modelBatch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        // In the real world, do not create NEW variables over and over, create
        // a temporary static member instead
        if(keycode == Input.Keys.A)
            camera.rotateAround(new Vector3(0f, 0f, 0f), new Vector3(0f, 1f, 0f), 1f);
        if(keycode == Input.Keys.D)
            camera.rotateAround(new Vector3(0f,0f,0f),new Vector3(0f,1f,0f), -1f);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}