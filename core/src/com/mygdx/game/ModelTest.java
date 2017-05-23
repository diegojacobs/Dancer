package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.UBJsonReader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationDesc;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class ModelTest implements ApplicationListener {
	private PerspectiveCamera camera;
	private ModelBatch modelBatch;
	private Model model;
    private Model model2;
    private Model audience;
    private Model horse;
    private Model blueBall;
    private Model yellowBall;

	private ModelInstance modelInstance;
    private ModelInstance model2Instance;
    private ModelInstance audienceInstance;
    private ModelInstance horseInstance;
    private ModelInstance blueBallInstance;
    private ModelInstance yellowBallInstance;
	private Environment environment;

	private AnimationController controller;
    private AnimationController controller2;
    private AnimationController prop;
    private AnimationController prop2;

	private Texture texture;
	private Texture BackGround;
	private SpriteBatch batch;
	private Music crankThat;
    private Music ymca;
    private Music gangnamStyle;
    private Music oneDance;
    private Music macarena;

	//private TextButton button;
	private TextButton.TextButtonStyle textButtonStyle;
	private BitmapFont font;
	private Skin skin;
	private TextureAtlas buttonAtlas;
	private Stage stage;
	private Texture dabTexture;
	private TextureRegion myTextureRegion;
	private TextureRegionDrawable myTexRegionDrawable;
	private ImageButton button;
    private Button button2;//play
    private Button button3;//next Animation
    private Button button4;//Change Background
    private Button button5;//Change Dancer
    private int currentSong =0;
    private int currentBackground = 0;
    private int MtoRender = 0;
    private FileHandle imageFileHandle;

	@Override
	public void create() {
		// Create camera sized to screens width/height with Field of View of 75 degrees
		batch = new SpriteBatch();
		camera = new PerspectiveCamera(
				75,
				Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		// Move the camera 5 units back along the z-axis and look at the origin
		camera.position.set(0f,0f,15f);
		camera.lookAt(0f,0f,0f);
		//crankThat = Gdx.audio.newSound(Gdx.files.internal("data/crankThat.mp3"));
		crankThat = Gdx.audio.newMusic(Gdx.files.internal("data/crankThatFinal.mp3"));
        ymca = Gdx.audio.newMusic(Gdx.files.internal("data/ymca.mp3"));
        oneDance = Gdx.audio.newMusic(Gdx.files.internal("data/oneDance.mp3"));
        gangnamStyle = Gdx.audio.newMusic(Gdx.files.internal("data/gangnamStyle.mp3"));
        macarena = Gdx.audio.newMusic(Gdx.files.internal("data/macarena.mp3"));
        //Macarena
        //Gangnam style
        //One Dance
		// Near and Far (plane) represent the minimum and maximum ranges of the camera in, um, units
		camera.near = 0.1f;
		camera.far = 300.0f;

		// A ModelBatch is like a SpriteBatch, just for models.  Use it to batch up geometry for OpenGL
		modelBatch = new ModelBatch();

		// Model loader needs a binary json reader to decode
		UBJsonReader jsonReader = new UBJsonReader();
		// Create a model loader passing in our json reader
		G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
		// Now load the model by name
		// Note, the model (g3db file ) and textures need to be added to the assets folder of the Android proj
		model = modelLoader.loadModel(Gdx.files.getFileHandle("data/DrBones.g3db", FileType.Internal));
        model2 = modelLoader.loadModel(Gdx.files.getFileHandle("data/m2pasito3.g3db", FileType.Internal));
        audience = modelLoader.loadModel(Gdx.files.getFileHandle("data/Audience.g3db", FileType.Internal));
        horse = modelLoader.loadModel(Gdx.files.getFileHandle("data/Horse.g3db", FileType.Internal));
        blueBall = modelLoader.loadModel(Gdx.files.getFileHandle("data/BlueBall.g3db", FileType.Internal));
        yellowBall = modelLoader.loadModel(Gdx.files.getFileHandle("data/YellowBall.g3db", FileType.Internal));
		// Now create an instance.  Instance holds the positioning data, etc of an instance of your model
		modelInstance = new ModelInstance(model);
        model2Instance = new ModelInstance(model2);
        audienceInstance = new ModelInstance(audience);
        horseInstance = new ModelInstance(horse);
        blueBallInstance = new ModelInstance(blueBall);
        yellowBallInstance = new ModelInstance(yellowBall);

		//fbx-conv is supposed to perform this rotation for you... it doesnt seem to
		//modelInstance.transform.rotate(1, 0, 0, -180);
		modelInstance.materials.get(0).set(ColorAttribute.createDiffuse(Color.GOLD));
        modelInstance.transform.translate(0, -10, 0);
        modelInstance.transform.scale(0.05f,0.05f,0.05f);

		//move the model down a bit on the screen ( in a z-up world, down is -z ).

        model2Instance.transform.translate(0, -10, 0);
        model2Instance.transform.translate(0, -3, -4);
        model2Instance.transform.scale(0.03f,0.03f,0.03f);

        audienceInstance.transform.translate(0,-12,0);
        audienceInstance.transform.translate(0,0,-5);
        audienceInstance.transform.translate(14,0,0);
        audienceInstance.transform.scale(0.05f,0.05f,0.05f);

        horseInstance.transform.translate(0,-5,0);
        horseInstance.transform.translate(0,0,6);
        horseInstance.transform.translate(-8,0,0);
        horseInstance.transform.scale(0.01f,0.01f,0.01f);

        yellowBallInstance.transform.translate(0,-12,0);
        yellowBallInstance.transform.translate(0,0,-5);
        yellowBallInstance.transform.translate(14,0,0);
        yellowBallInstance.transform.scale(0.005f,0.005f,0.005f);

        blueBallInstance.transform.translate(0,-7,0);
        blueBallInstance.transform.translate(0,0,6);
        blueBallInstance.transform.translate(-6,0,0);
        blueBallInstance.transform.scale(0.005f,0.005f,0.005f);

		imageFileHandle = Gdx.files.internal("data/bark.jpg");
		texture = new Texture(imageFileHandle);
		imageFileHandle = Gdx.files.internal("data/barn.jpg");
		BackGround = new Texture(imageFileHandle);
		modelInstance.materials.get(0).set(TextureAttribute.createDiffuse(texture));
		// Finally we want some light, or we wont see our color.  The environment gets passed in during
		// the rendering process.  Create one, then create an Ambient ( non-positioned, non-directional ) light.
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1.0f));


		//stage.addActor(button);

		// You use an AnimationController to um, control animations.  Each control is tied to the model instance
		controller = new AnimationController(modelInstance);
        controller.setAnimation("dance2", -1, new AnimationListener() {

            @Override
            public void onEnd(AnimationDesc animation) {
                // this will be called when the current animation is done.
                // queue up another animation called "balloon".
                // Passing a negative to loop count loops forever.  1f for speed is normal speed.
                //controller.queue("ymca",-1,1f,null,0f);
            }

            @Override
            public void onLoop(AnimationDesc animation) {

                // TODO Auto-generated method stub
            }

        });

        crankThat.play();
        crankThat.setLooping(true);

        controller2 = new AnimationController(model2Instance);
        prop = new AnimationController(audienceInstance);
        prop2 = new AnimationController(horseInstance);
        prop2.setAnimation("Horse",-1, new AnimationListener(){

            @Override
            public void onEnd(AnimationDesc animation) {
                // this will be called when the current animation is done.
                // queue up another animation called "balloon".
                // Passing a negative to loop count loops forever.  1f for speed is normal speed.
                //controller.queue("ymca",-1,1f,null,0f);
            }

            @Override
            public void onLoop(AnimationDesc animation) {

                // TODO Auto-generated method stub
                //crankThat.play();
            }

        });
        prop.setAnimation("Take 001",-1, new AnimationListener(){

            @Override
            public void onEnd(AnimationDesc animation) {
                // this will be called when the current animation is done.
                // queue up another animation called "balloon".
                // Passing a negative to loop count loops forever.  1f for speed is normal speed.
                //controller.queue("ymca",-1,1f,null,0f);
            }

            @Override
            public void onLoop(AnimationDesc animation) {

                // TODO Auto-generated method stub
                //crankThat.play();
            }

        });
		// Pick the current animation by name

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        //plays crank that and dances
        button3 = new TextButton("Next Song",skin,"small");
        button3.setSize(300, 160);
        button3.setPosition(200, Gdx.graphics.getHeight()-160);
        button3.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //outputLabel.setText("Press a Button");
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //outputLabel.setText("Pressed Text Button");
                ymca.stop();
                macarena.stop();
                oneDance.stop();
                gangnamStyle.stop();
                crankThat.stop();

                if(MtoRender == 0)
                {
                    if(currentSong == 0) {
                        crankThat.play();
                        crankThat.setLooping(true);
                        controller.setAnimation("dance2", -1, new AnimationListener() {

                            @Override
                            public void onEnd(AnimationDesc animation) {
                                // this will be called when the current animation is done.
                                // queue up another animation called "balloon".
                                // Passing a negative to loop count loops forever.  1f for speed is normal speed.
                                //controller.queue("ymca",-1,1f,null,0f);
                            }

                            @Override
                            public void onLoop(AnimationDesc animation) {

                                // TODO Auto-generated method stub
                            }

                        });
                        currentSong = currentSong + 1;
                    }
                    else
                        if(currentSong == 1)
                        {
                            oneDance.play();
                            oneDance.setLooping(true);
                            controller.setAnimation("Bones_for_Bones|Dance3",-1, new AnimationListener(){

                                @Override
                                public void onEnd(AnimationDesc animation) {
                                    // this will be called when the current animation is done.
                                    // queue up another animation called "balloon".
                                    // Passing a negative to loop count loops forever.  1f for speed is normal speed.
                                    //controller.queue("ymca",-1,1f,null,0f);
                                }

                                @Override
                                public void onLoop(AnimationDesc animation) {

                                    // TODO Auto-generated method stub
                                    //oneDance.play();
                                }

                            });
                            currentSong = currentSong + 1;
                        }
                        else
                            if(currentSong == 2)
                            {
                                ymca.play();
                                ymca.setLooping(true);
                                controller.setAnimation("Bones_for_Bones|YACM",-1, new AnimationListener(){

                                    @Override
                                    public void onEnd(AnimationDesc animation) {
                                        // this will be called when the current animation is done.
                                        // queue up another animation called "balloon".
                                        // Passing a negative to loop count loops forever.  1f for speed is normal speed.
                                        //controller.queue("ymca",-1,1f,null,0f);
                                    }

                                    @Override
                                    public void onLoop(AnimationDesc animation) {

                                        // TODO Auto-generated method stub
                                        //ymca.play();
                                    }

                                });
                                currentSong = 0;
                            }
                }
                else if (MtoRender == 1)
                {
                    if(currentSong == 0)
                    {
                        oneDance.play();
                        oneDance.setLooping(true);
                        controller2.setAnimation("Armature|paso1", -1, new AnimationListener() {
                            //play one dance
                            @Override
                            public void onEnd(AnimationDesc animation) {
                                // this will be called when the current animation is done.
                                // queue up another animation called "balloon".
                                // Passing a negative to loop count loops forever.  1f for speed is normal speed.
                                //controller.queue("ymca",-1,1f,null,0f);
                            }

                            @Override
                            public void onLoop(AnimationDesc animation) {

                                // TODO Auto-generated method stub
                                //oneDance.play();
                                //play one dance
                            }

                        });
                        currentSong = currentSong +1;
                    }
                    else
                        if(currentSong == 1)
                        {
                            macarena.play();
                            macarena.setLooping(true);
                            controller2.setAnimation("Armature|pasito2", -1, new AnimationListener() {
                                //play macarena

                                @Override
                                public void onEnd(AnimationDesc animation) {
                                    // this will be called when the current animation is done.
                                    // queue up another animation called "balloon".
                                    // Passing a negative to loop count loops forever.  1f for speed is normal speed.
                                    //controller.queue("ymca",-1,1f,null,0f);
                                }

                                @Override
                                public void onLoop(AnimationDesc animation) {

                                    // TODO Auto-generated method stub
                                    //crankThat.play();
                                    //play macarena
                                    //macarena.play();
                                }

                            });
                            currentSong = currentSong +1;
                        }
                        else
                            if(currentSong == 2)
                            {
                                gangnamStyle.play();
                                gangnamStyle.setLooping(true);
                                controller2.setAnimation("Armature|pasito3", -1, new AnimationListener() {
                                    //play gangnam style

                                    @Override
                                    public void onEnd(AnimationDesc animation) {
                                        // this will be called when the current animation is done.
                                        // queue up another animation called "balloon".
                                        // Passing a negative to loop count loops forever.  1f for speed is normal speed.
                                        //controller.queue("ymca",-1,1f,null,0f);
                                    }

                                    @Override
                                    public void onLoop(AnimationDesc animation) {

                                        // TODO Auto-generated method stub
                                        //crankThat.play();
                                        //play gangnam style
                                        //gangnamStyle.play();
                                    }

                                });
                                currentSong = 0;
                            }
                }

                return true;
            }
        });// plays Silento, or any in the 5 song playlist
        button4 = new TextButton("Change Background", skin,"small");
        button4.setSize(300, 160);
        button4.setPosition(700, Gdx.graphics.getHeight()-160);
        button4.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //outputLabel.setText("Press a Button");
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Song = 0;
                if(currentBackground == 0) {
                    imageFileHandle = Gdx.files.internal("data/discoteca.jpg");
                    BackGround = new Texture(imageFileHandle);
                    currentBackground = 1;
                    prop = new AnimationController(blueBallInstance);
                    prop2 = new AnimationController(yellowBallInstance);

                    prop.setAnimation("BlueBall",-1, new AnimationListener(){

                        @Override
                        public void onEnd(AnimationDesc animation) {
                            // this will be called when the current animation is done.
                            // queue up another animation called "balloon".
                            // Passing a negative to loop count loops forever.  1f for speed is normal speed.
                            //controller.queue("ymca",-1,1f,null,0f);
                        }

                        @Override
                        public void onLoop(AnimationDesc animation) {

                            // TODO Auto-generated method stub
                            //crankThat.play();
                        }

                    });
                    prop2.setAnimation("Mesh|MeshAction",-1, new AnimationListener(){

                        @Override
                        public void onEnd(AnimationDesc animation) {
                            // this will be called when the current animation is done.
                            // queue up another animation called "balloon".
                            // Passing a negative to loop count loops forever.  1f for speed is normal speed.
                            //controller.queue("ymca",-1,1f,null,0f);
                        }

                        @Override
                        public void onLoop(AnimationDesc animation) {

                            // TODO Auto-generated method stub
                            //crankThat.play();
                        }

                    });
                }
                else
                {
                    imageFileHandle = Gdx.files.internal("data/barn.jpg");
                    BackGround = new Texture(imageFileHandle);
                    currentBackground = 0;

                    prop = new AnimationController(audienceInstance);
                    prop2 = new AnimationController(horseInstance);

                    prop.setAnimation("Take 001",-1, new AnimationListener(){

                        @Override
                        public void onEnd(AnimationDesc animation) {
                            // this will be called when the current animation is done.
                            // queue up another animation called "balloon".
                            // Passing a negative to loop count loops forever.  1f for speed is normal speed.
                            //controller.queue("ymca",-1,1f,null,0f);
                        }

                        @Override
                        public void onLoop(AnimationDesc animation) {

                            // TODO Auto-generated method stub
                            //crankThat.play();
                        }

                    });
                    prop2.setAnimation("Horse",-1, new AnimationListener(){

                        @Override
                        public void onEnd(AnimationDesc animation) {
                            // this will be called when the current animation is done.
                            // queue up another animation called "balloon".
                            // Passing a negative to loop count loops forever.  1f for speed is normal speed.
                            //controller.queue("ymca",-1,1f,null,0f);
                        }

                        @Override
                        public void onLoop(AnimationDesc animation) {

                            // TODO Auto-generated method stub
                            //crankThat.play();
                        }

                    });
                }
                return true;
            }
        });// plays Silento, or any in the 5 song playlist
        button5 = new TextButton("Change Dancer",skin,"small");
        button5.setSize(300, 160);
        button5.setPosition(1200, Gdx.graphics.getHeight()-160);
        button5.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //outputLabel.setText("Press a Button");
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                ymca.stop();
                macarena.stop();
                oneDance.stop();
                gangnamStyle.stop();
                crankThat.stop();

                if(MtoRender == 0)
                {
                    MtoRender = 1;
                    currentSong = 0;
                    oneDance.play();
                    oneDance.setLooping(true);
                    controller2.setAnimation("Armature|paso1", -1, new AnimationListener() {
                        //play one dance
                        @Override
                        public void onEnd(AnimationDesc animation) {
                            // this will be called when the current animation is done.
                            // queue up another animation called "balloon".
                            // Passing a negative to loop count loops forever.  1f for speed is normal speed.
                            //controller.queue("ymca",-1,1f,null,0f);
                        }

                        @Override
                        public void onLoop(AnimationDesc animation) {

                            // TODO Auto-generated method stub
                            //oneDance.play();
                            //play one dance
                        }

                    });
                }
                else
                {
                    MtoRender = 0;
                    currentSong = 0;
                    crankThat.play();
                    crankThat.setLooping(true );
                    controller.setAnimation("dance2", -1, new AnimationListener() {

                        @Override
                        public void onEnd(AnimationDesc animation) {
                            // this will be called when the current animation is done.
                            // queue up another animation called "balloon".
                            // Passing a negative to loop count loops forever.  1f for speed is normal speed.
                            //controller.queue("ymca",-1,1f,null,0f);
                        }

                        @Override
                        public void onLoop(AnimationDesc animation) {

                            // TODO Auto-generated method stub
                            //crankThat.play();
                        }

                    });
                }
                //stop all songs
                return true;
            }
        });
        stage.addActor(button3);
        stage.addActor(button4);
        stage.addActor(button5);
	}

	@Override
	public void dispose() {
		modelBatch.dispose();
		model.dispose();
	}

	@Override
	public void render() {
		// You've seen all this before, just be sure to clear the GL_DEPTH_BUFFER_BIT when working in 3D
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		texture.bind();
		// For some flavor, lets spin our camera around the Y axis by 1 degree each time render is called
		//camera.rotateAround(Vector3.Zero, new Vector3(0,1,0),1f);
		// When you change the camera details, you need to call update();
		// Also note, you need to call update() at least once.
		camera.update();

		// You need to call update on the animation controller so it will advance the animation.  Pass in frame delta
		controller.update(Gdx.graphics.getDeltaTime());
        controller2.update(Gdx.graphics.getDeltaTime());
        prop.update(Gdx.graphics.getDeltaTime());
        prop2.update(Gdx.graphics.getDeltaTime());
        stage.getBatch().begin();
        stage.getBatch().draw(BackGround, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();
        stage.act();
        stage.draw();

        modelBatch.begin(camera);

        if(MtoRender == 0)
        {
            modelBatch.render(modelInstance, environment);
        }
        else if(MtoRender == 1)
        {
            modelBatch.render(model2Instance, environment);
        }

        if(currentBackground == 1) {
            modelBatch.render(blueBallInstance, environment);
            modelBatch.render(yellowBallInstance, environment);
        } else{
            modelBatch.render(horseInstance, environment);
            modelBatch.render(audienceInstance, environment);
        }
		modelBatch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}