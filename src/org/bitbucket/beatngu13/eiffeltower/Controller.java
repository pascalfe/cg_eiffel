package org.bitbucket.beatngu13.eiffeltower;

import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

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
					// TODO Außernanderfahren
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
