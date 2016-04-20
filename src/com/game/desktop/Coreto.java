package com.game.desktop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import java.util.ArrayList;

/**
 * Created by Antonio on 23/03/2016.
 */
public class Coreto {
    private ArrayList<ModelInstance> modelInstance;
    private ModelBuilder modelBuilder;
    public Coreto(ArrayList<ModelInstance> modelInstance){
        this.modelInstance = modelInstance;
    }

    public ArrayList<ModelInstance> draw(){

        //TODO: Desenhar a base
        Model cylinder = modelBuilder.createCylinder(10f, 20f, 10f, 20,
                new Material(ColorAttribute.createDiffuse(Color.WHITE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        modelInstance.add(new ModelInstance(cylinder,10, 10, 10));
        Model box;

        //TODO: Desenhar o pilares
        cylinder = modelBuilder.createCylinder(10f, 50f, 10f, 20,
                new Material(ColorAttribute.createDiffuse(Color.PINK)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        float radius = 60f, midpoint = 45;
        modelInstance.add(new ModelInstance(cylinder,0, 27, radius));
        modelInstance.add(new ModelInstance(cylinder,0, 27, -radius));
        modelInstance.add(new ModelInstance(cylinder,radius, 27, 0));
        modelInstance.add(new ModelInstance(cylinder,-radius, 27, 0));
        modelInstance.add(new ModelInstance(cylinder,midpoint, 27, midpoint));
        modelInstance.add(new ModelInstance(cylinder,midpoint, 27, -midpoint));
        modelInstance.add(new ModelInstance(cylinder,-midpoint, 27, midpoint));
        modelInstance.add(new ModelInstance(cylinder,-midpoint, 27, -midpoint));

        //TODO: Desenhar o teto

        return modelInstance;
    }
}
