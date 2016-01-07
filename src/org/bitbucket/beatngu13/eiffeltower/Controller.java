package org.bitbucket.beatngu13.eiffeltower;

import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


public class Controller {
	
	private static final double ROTATE_TICK = 10.0;
	
	private double mouseXOld;
	private double mouseYOld;
	private double mouseXCurr;
	private double mouseYCurr;
	private double mouseXDelta;
	private double mouseYDelta;
	
	private Scene scene;
	private Rotate cameraRotateX = new Rotate(0.0, Rotate.X_AXIS);
	private Rotate cameraRotateY = new Rotate(45.0, Rotate.Y_AXIS);
	private Translate cameraTranslate = new Translate(0.0, -150.0, -700.0);
	//private Translate eiffelTranslate = new Translate(0.0, -20, -20.0);
	private boolean towerMoved;
	int transitionIndex = 0;



	public Controller(Scene scene) {
		this.scene = scene;
		scene.setCamera(setUpCamera());
		
		addKeyHandlers();
		addMouseHandlers();
	}
	
	private final Camera setUpCamera() {
		PerspectiveCamera camera = new PerspectiveCamera(true);
		
		camera.setFieldOfView(40.0);
		camera.setFarClip(10000.0);
		camera.setRotationAxis(Rotate.Z_AXIS);
		camera.getTransforms().addAll(cameraRotateY, cameraRotateX,
				cameraTranslate);
		
		return camera;
	}
	
	private final void addKeyHandlers() {
		scene.setOnKeyPressed(event -> {
			switch(event.getCode()) {
				case PLUS:
				case RIGHT:
					cameraRotateY.setAngle(cameraRotateY.getAngle() + ROTATE_TICK);
					break;
				case MINUS:
				case LEFT:
					cameraRotateY.setAngle(cameraRotateY.getAngle() - ROTATE_TICK);
					break;
				case UP:
					cameraRotateX.setAngle(cameraRotateX.getAngle() + ROTATE_TICK);
					break;
				case DOWN:
					cameraRotateX.setAngle(cameraRotateX.getAngle() - ROTATE_TICK);
					break;
				case SPACE:
					ObservableList<Node> childrenNodes = scene.getRoot().getChildrenUnmodifiable();
					List<TranslateTransition> forwardTransitions = new ArrayList<TranslateTransition>();

					forwardTransitions.add(new TranslateTransition(new Duration(1000), childrenNodes.get(5)));
					forwardTransitions.add(new TranslateTransition(new Duration(1000), childrenNodes.get(1)));
					forwardTransitions.add(new TranslateTransition(new Duration(1000), childrenNodes.get(2)));
					forwardTransitions.add(new TranslateTransition(new Duration(1000), childrenNodes.get(3)));
					forwardTransitions.add(new TranslateTransition(new Duration(1000), childrenNodes.get(4)));

					for (TranslateTransition t: forwardTransitions) {
						t.setAutoReverse(true);
						t.setCycleCount(1);
					}

					forwardTransitions.get(0).setByY(-4);
					forwardTransitions.get(1).setByX(0.3);
					forwardTransitions.get(1).setByZ(0.3);
					forwardTransitions.get(2).setByX(0.3);
					forwardTransitions.get(2).setByZ(0.3);
					forwardTransitions.get(3).setByX(-0.3);
					forwardTransitions.get(3).setByZ(-0.3);
					forwardTransitions.get(4).setByX(-0.3);
					forwardTransitions.get(4).setByZ(-0.3);

					TranslateTransition bt0 = new TranslateTransition(new Duration(1000), childrenNodes.get(5));
					TranslateTransition bt1 = new TranslateTransition(new Duration(1000), childrenNodes.get(1));
					TranslateTransition bt2 = new TranslateTransition(new Duration(1000), childrenNodes.get(2));
					TranslateTransition bt3 = new TranslateTransition(new Duration(1000), childrenNodes.get(3));
					TranslateTransition bt4 = new TranslateTransition(new Duration(1000), childrenNodes.get(4));

					bt0.setByY(4);
					bt1.setByX(-0.3);
					bt1.setByZ(-0.3);
					bt2.setByX(-0.3);
					bt2.setByZ(-0.3);
					bt3.setByX(0.3);
					bt3.setByZ(0.3);
					bt4.setByX(0.3);
					bt4.setByZ(0.3);

					forwardTransitions.get(0).setOnFinished(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {

							bt0.play();
							System.out.println("t0 played");
						}
					});
					forwardTransitions.get(1).setOnFinished(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {

							bt1.play();
							System.out.println("t1 played");
						}
					});
					forwardTransitions.get(2).setOnFinished(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {

							bt2.play();
							System.out.println("t2 played");
						}
					});
					forwardTransitions.get(3).setOnFinished(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {

							bt3.play();
							System.out.println("t3 played");
						}
					});
					forwardTransitions.get(4).setOnFinished(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {

							bt4.play();
							System.out.println("t4 played");
						}
					});

					for (TranslateTransition t: forwardTransitions) {
						t.play();
					}
				default:
				// For the sake of conventions.
				break;
			}
		});
	}

	private final void addMouseHandlers() {
        scene.setOnMousePressed(event -> {
            mouseXCurr = event.getSceneX();
            mouseYCurr = event.getSceneY();
            mouseXOld = event.getSceneX();
            mouseYOld = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            mouseXOld = mouseXCurr;
            mouseYOld = mouseYCurr;
            mouseXCurr = event.getSceneX();
            mouseYCurr = event.getSceneY();
            mouseXDelta = mouseXCurr - mouseXOld;
            mouseYDelta = mouseYCurr - mouseYOld;

            cameraTranslate.setX(cameraTranslate.getX() - mouseXDelta);
            cameraTranslate.setY(cameraTranslate.getY() - mouseYDelta);
        });

        scene.setOnScroll(event -> {
        	cameraTranslate.setZ(cameraTranslate.getZ() + event.getDeltaY());
        });
	}

}
