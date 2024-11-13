package com.czertilla.project_vinaigrette.utils;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.czertilla.project_vinaigrette.stage.scene.actor.BaseActor;
import com.czertilla.project_vinaigrette.stage.scene.actor.WallActor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class PathFinder {
    public Node destination;
    private HashSet<Node.PosID> expiredNodes;
    public TreeSet<Node> currentNodes;
    private BaseActor actor;
    private Vector2 target;
    private float step;
    private static final float SQRT2 = (float) Math.sqrt(2);
    public class Node extends Vector2 {
        Node previous;

        record PosID(
            int x,
            int y
        ){};

        float path;

        PosID posId;

        Node(float x, float y) {
            super(x, y);
            path = 0;
            posId = new PosID(0, 0);
        }

        Node(Node previous, float dx, float dy){
            super(previous.x + dx, previous.y + dy);
            path = previous.path + dst(previous);
            this.previous = previous;
            posId = new PosID(previous.posId.x + (int)dx, previous.posId.y + (int)dy);
        }


        public Node getPrevious() {
            return previous;
        }
    }

    public PathFinder(BaseActor actor, Vector2 end){
        new PathFinder(actor, end, 20);
    }

    public PathFinder(BaseActor actor, Vector2 end, float step){
        Vector3 start = actor.getCenter();
        target = end;
        currentNodes = new TreeSet<>((n1, n2) ->
            Float.compare(n1.dst(target), n2.dst(target))*2
                +Float.compare(n1.path, n2.path));
        currentNodes.add(new Node(start.x, start.y));
        expiredNodes = new HashSet<>();
        this.step = step;
        this.actor = actor;
    }

    private Polygon getActorBox(Node node){
        Polygon boundingBox = actor.getBoundingBox();
        Polygon box = new Polygon(boundingBox.getVertices());
        box.setOrigin(boundingBox.getOriginX(), boundingBox.getOriginY());
        box.setRotation(boundingBox.getRotation());
        box.setPosition(node.x - box.getOriginX(), node.y - box.getOriginY());
        return box;
    }

    private boolean isNodeCollides(Node node){
        Polygon box = getActorBox(node);
        for (WallActor wall : WallActor.walls){
            if (Intersector.overlapConvexPolygons(box, wall.getBoundingBox()))
                return true;
        }
        return false;
    }

    private void addNodes(Node node){
        for (float x = -step; x <= step; x+=step){
            for (float y = -step; y <= step; y+= step) {
                if (x == 0 && y == 0) continue;
                Node newNode = new Node(node, x, y);
                if (expiredNodes.contains(newNode.posId) || isNodeCollides(newNode)) continue;
                expiredNodes.add(node.posId);
                if (newNode.dst(target) <= step * SQRT2) {
                    destination = newNode;
                    break;
                }
                currentNodes.add(newNode);
            }
        }
    }

    public void calculate(){
        int i = 0;
        int LIMIT = 100;
        while (!currentNodes.isEmpty() && destination == null && i < LIMIT){
            Node node = currentNodes.pollFirst();
            addNodes(node);
            if (destination != null) break;
            i ++;
        }
    }

    public Vector2[] getResult(){
        List<Node> result = new ArrayList<>();
        Node node = destination;
        while(node != null){
            result.add(0, node);
            node = node.previous;
        }
        if (result.isEmpty()) return null;
        return result.toArray(Vector2[]::new);
    }
}
