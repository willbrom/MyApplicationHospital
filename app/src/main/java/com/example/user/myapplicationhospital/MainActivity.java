package com.example.user.myapplicationhospital;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

//http://www.fromtexttospeech.com/

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, RecognitionListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FIRST_STAGE = "first";
    private static final String SECOND_STAGE = "second";
    private static final String THIRD_STAGE = "third";
    private static final String THIRD_STAGE_101 = "third101";
    private static final String FOURTH_STAGE = "fourth";
    private static final String FIFTH_STAGE = "fifth";
    private static final String MR_JOHN = "Mr. John";
    private static final String MR_ALEX = "Mr. Alex";
    private static final String MRS_KATE = "Mrs. Kate";
    private static final String MISS_LILY = "Miss Lily";
    private BottomSheetBehavior bottomSheetBehavior;
    private LottieAnimationView extraAnimation;
    private LottieAnimationView animationRespondButton;
    private FrameLayout bottomSheet;
    private boolean selectCashAsked;
    private boolean selectIdAsked;
    private boolean selectInsuranceAsked;
    private boolean selectPrescriptionAsked;
    private boolean enableMic;
    private boolean isRecording;
    private ImageView profileImageView;
    private ImageView extraImageView;
    private ImageView micImageView;
    private ImageView mainImageView;
    private ImageView greenTickHospitalFile;
    private ImageView emptyTickHospitalFile;
    private ImageView greenTickAppointment;
    private ImageView insuranceCardImageView;
    private TextView insuranceCardTextView;
    private ImageView prescriptionImageView;
    private TextView prescriptionTextView;
    private ImageView playHeadAche;
    private ImageView playChestPain;
    private ImageView playStomachAche;
    private ImageView emptyTickAppointment;
    private TextView profileTextView;
    private TextView recipeDayTextView;
    private TextView recipeTimeOfDayTextView;
    private TextView recipeDocTextView;
    private TextView recipeTimeTextView;
    private TextView extraTextView;
    private Toast errorToast;
    private View includeBottomScreen;
    private View includeBodyProblems;
    private View includeRecipeScreen;
    private ConstraintLayout micWalletContainer;
    private Button nextButton;
    private MediaPlayer mediaPlayer;
    private TextToSpeech textToSpeech;
    private SpeechRecognizer speechRecognizer;
    private Intent speechIntent;
    private String doctorNameSelected;
    private int bodyProblem;
    private int randFever;
    private int randHospitalFile;
    private int randAppointment;
    private int randInsurance;
    private boolean haveFileAsked;
    private boolean haveAppointmentAsked;
    private boolean tellNameAsked;
    private boolean whichDayAsked;
    private String daySelected;
    private boolean morAfterEveAsked;
    private String mAESelected;
    private boolean whatTimeAsked;
    private String timeSelected;
    private boolean haveInsuranceAsked;
    private boolean bodyPartHighlighted;
    private boolean forHowLongAsked;
    private boolean haveAppointment;
    private boolean haveFeverAsked;
    private String userName;
    private Random random = new Random();
    private Bundle params = new Bundle();
    private boolean firstStageCompleted;
    private boolean secondStageCompleted;
    private boolean thirdStageCompleted;
    private boolean startFirstTime;
    private ArrayList<String> waitingRoomNames = new ArrayList<>();
    private ArrayList<Integer> randomCountArrayList = new ArrayList<>();
    private CountDownTimer waitingRoomCountdownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startFirstTime = true;
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        bottomSheet = (FrameLayout) findViewById(R.id.bottom_sheet);
        micWalletContainer = (ConstraintLayout) findViewById(R.id.mic_wallet_container);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        profileTextView = (TextView) findViewById(R.id.profile_textView);
        extraTextView = (TextView) findViewById(R.id.extra_textView);
        recipeDayTextView = (TextView) findViewById(R.id.recipe_day_textView);
        recipeTimeOfDayTextView = (TextView) findViewById(R.id.recipe_time_of_day_textView);
        recipeDocTextView = (TextView) findViewById(R.id.recipe_doc_textView);
        recipeTimeTextView = (TextView) findViewById(R.id.recipe_time_textView);
        profileImageView = (ImageView) findViewById(R.id.profile_imageView);
        extraImageView = (ImageView) findViewById(R.id.extra_imageView);
        mainImageView = (ImageView) findViewById(R.id.main_imageView);
        micImageView = (ImageView) findViewById(R.id.mic_imageView);
        greenTickHospitalFile = (ImageView) findViewById(R.id.green_tick_hospital_file_imageView);
        emptyTickHospitalFile = (ImageView) findViewById(R.id.empty_tick_hospital_file_imageView);
        greenTickAppointment = (ImageView) findViewById(R.id.green_tick_appointment_imageView);
        emptyTickAppointment = (ImageView) findViewById(R.id.empty_tick_appointment_imageView);
        insuranceCardImageView = (ImageView) findViewById(R.id.insurance_card_imageView);
        insuranceCardTextView = (TextView) findViewById(R.id.insurance_card_textView);
        prescriptionImageView = (ImageView) findViewById(R.id.prescription_imageView);
        playHeadAche = (ImageView) findViewById(R.id.play_headache_imageView);
        playChestPain = (ImageView) findViewById(R.id.play_chest_pain_imageView);
        playStomachAche = (ImageView) findViewById(R.id.play_stomachache_imageView);
        prescriptionTextView = (TextView) findViewById(R.id.prescription_textView);
        includeBottomScreen = (View) findViewById(R.id.include_bottom_screen);
        includeBodyProblems = (View) findViewById(R.id.include_body_problems);
        includeRecipeScreen = (View) findViewById(R.id.include_recipe_screen);
        nextButton = (Button) findViewById(R.id.next_button);
        extraAnimation = (LottieAnimationView) findViewById(R.id.extra_animation);
        animationRespondButton = (LottieAnimationView) findViewById(R.id.animation_respond_button);
        micImageView.setOnTouchListener(this);
        speechRecognizer.setRecognitionListener(this);
        initializeTextToSpeech();
        randomize();
    }

    private void randomize() {
        prescriptionTextView.setVisibility(View.GONE);
        prescriptionImageView.setVisibility(View.GONE);

        randHospitalFile = random.nextInt(2);
        randAppointment = random.nextInt(2);
        randInsurance = random.nextInt(2);
        switch (randHospitalFile) {
            case 0:
                emptyTickHospitalFile.setVisibility(View.INVISIBLE);
                break;
            case 1:
                greenTickHospitalFile.setVisibility(View.INVISIBLE);
        }
        switch (randAppointment) {
            case 0:
                emptyTickAppointment.setVisibility(View.INVISIBLE);
                break;
            case 1:
                greenTickAppointment.setVisibility(View.INVISIBLE);

        }
        switch (randInsurance) {
            case 0:
                insuranceCardImageView.setVisibility(View.VISIBLE);
                insuranceCardTextView.setVisibility(View.VISIBLE);
                break;
            case 1:
                insuranceCardImageView.setVisibility(View.GONE);
                insuranceCardTextView.setVisibility(View.GONE);
        }

        waitingRoomNames.add(MR_JOHN);
        waitingRoomNames.add(MR_ALEX);
        waitingRoomNames.add(MRS_KATE);
        waitingRoomNames.add(MISS_LILY);
    }

    private void initializeTextToSpeech() {
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });
    }

    private void initSpeechRecognition() {
        speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-US");
        speechIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 2000);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        speechIntent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"en"});
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
    }

    private void startStage(String stage) {
        switch (stage) {
            case FIRST_STAGE:
                firstStage();
                break;
            case SECOND_STAGE:
                secondStage();
                break;
            case THIRD_STAGE:
                thirdStage();
                break;
            case THIRD_STAGE_101:
                thirdStage101();
                break;
            case FOURTH_STAGE:
                fourthStage();
                break;
            case FIFTH_STAGE:
                fifthStage();
        }
    }

    private void firstStage() {
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                switch ((int) (l/1000)) {
                    case 8:
                        profileImageView.setVisibility(View.VISIBLE);
                        profileTextView.setVisibility(View.VISIBLE);
                        profileImageView.setImageResource(R.drawable.nurse_reception);
                        profileTextView.setText("How can i help you");
//                        textToSpeech.speak("How can i help you?", TextToSpeech.QUEUE_FLUSH, null, null);
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_reception_how_can_i_help_you);
                        mediaPlayer.start();
                        break;
                    case 5:
                        profileImageView.setImageResource(R.drawable.man_hospital);
                        profileTextView.setText("I would like to see a doctor");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_see_doc);
                        mediaPlayer.start();
                        break;
                    case 2:
                        profileImageView.setImageResource(R.drawable.nurse_reception);
                        profileTextView.setText("Which doctor?");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_reception_which_doctor);
                        mediaPlayer.start();
//                        textToSpeech.speak("Which doctor?", TextToSpeech.QUEUE_FLUSH, null, null);
                        extraImageView.setImageResource(R.drawable.doctors);
                        extraImageView.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        enableMic = true;
                        break;
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void secondStage() {
        new CountDownTimer(9000, 1000) {
            @Override
            public void onTick(long l) {
                switch ((int) (l/1000)) {
                    case 8:
                        profileImageView.setVisibility(View.VISIBLE);
                        profileTextView.setVisibility(View.VISIBLE);
                        profileImageView.setImageResource(R.drawable.man_hospital);
                        profileTextView.setText("Here it is");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_here_it_is);
                        mediaPlayer.start();
                        break;
                    case 6:
                        profileImageView.setImageResource(R.drawable.nurse_reception);
                        profileTextView.setText("Please, go to the clinic");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_reception_please_go_to_the_clinic);
                        mediaPlayer.start();
                        break;
                    case 3:
                        firstStageCompleted = true;
                        micWalletContainer.setVisibility(View.GONE);
                        profileTextView.setText("");
                        profileImageView.setVisibility(View.INVISIBLE);
                        nextButton.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void thirdStage() {
        new CountDownTimer(12000, 1000) {
            @Override
            public void onTick(long l) {
                switch ((int) (l/1000)) {
                    case 10:
                        profileImageView.setImageResource(R.drawable.man_hospital);
                        profileTextView.setText("Here it is");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_here_it_is);
                        mediaPlayer.start();
                        break;
                    case 8:
                        profileImageView.setImageResource(R.drawable.nurse_reception_2nd);
                        profileTextView.setText("Please wait, the nurse will call your name");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_sec_reception_please_wait_the_nurse_will_call_your_name);
                        mediaPlayer.start();
                        break;
                    case 5:
//                        includeBottomScreen.setVisibility(View.INVISIBLE);
//                        mainImageView.setImageResource(R.drawable.time_lapse);
                        break;
                    case 2:
                        profileImageView.setImageResource(R.drawable.nurse_waiting_area);
                        profileTextView.setText("");
                        mainImageView.setImageResource(R.drawable.waiting_area);
                        includeBottomScreen.setVisibility(View.VISIBLE);
//                        profileImageView.setImageResource(R.drawable.nurse_waiting_area);
//                        profileTextView.setText("Mr. John");
//                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_male_name_call);
//                        mediaPlayer.start();
                        break;
//                    case 2:
//                        profileImageView.setImageResource(R.drawable.man_hospital_waiting_area);
//                        profileTextView.setText("Yes");
//                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_yes);
//                        mediaPlayer.start();
//                        break;
//                    case 6:
//                        profileImageView.setImageResource(R.drawable.nurse_waiting_area);
//                        profileTextView.setText("Please, come with me");
//                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_male_come_with_me);
//                        mediaPlayer.start();
//                        break;
//                    case 4:
//                        profileImageView.setImageResource(R.drawable.man_hospital_waiting_area);
//                        profileTextView.setText("Ok");
//                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_ok);
//                        mediaPlayer.start();
//                        break;
                }
            }

            @Override
            public void onFinish() {
                animationRespondButton.setVisibility(View.VISIBLE);
                animationRespondButton.playAnimation(0, 170);
                nameCalling();
            }
        }.start();
    }

    private void thirdStage101() {
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                switch ((int) (l/1000)) {
                    case 8:
                        profileImageView.setImageResource(R.drawable.man_hospital_waiting_area);
                        profileTextView.setText("Yes");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_yes);
                        mediaPlayer.start();
                        break;
                    case 6:
                        profileImageView.setImageResource(R.drawable.nurse_waiting_area);
                        profileTextView.setText("Please, come with me");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_male_come_with_me);
                        mediaPlayer.start();
                        break;
                    case 4:
                        profileImageView.setImageResource(R.drawable.man_hospital_waiting_area);
                        profileTextView.setText("Ok");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_ok);
                        mediaPlayer.start();
                        break;
                    case 2:
                        secondStageCompleted = true;
                        micWalletContainer.setVisibility(View.GONE);
                        profileTextView.setText("");
                        profileImageView.setVisibility(View.INVISIBLE);
                        nextButton.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onFinish() {
            }
        }.start();
    }

    private void nameCalling() {
        waitingRoomCountdownTimer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long l) {
                final int randName;
                switch ((int) (l/1000)) {
                    case 13:
                        randName = generateRandomIndex();
                        profileImageView.setImageResource(R.drawable.nurse_waiting_area);
                        profileTextView.setText(waitingRoomNames.get(randName));
                        playNameMp3(waitingRoomNames.get(randName));
                        break;
                    case 10:
                        randName = generateRandomIndex();
                        profileImageView.setImageResource(R.drawable.nurse_waiting_area);
                        profileTextView.setText(waitingRoomNames.get(randName));
                        playNameMp3(waitingRoomNames.get(randName));
                        break;
                    case 7:
                        randName = generateRandomIndex();
                        profileImageView.setImageResource(R.drawable.nurse_waiting_area);
                        profileTextView.setText(waitingRoomNames.get(randName));
                        playNameMp3(waitingRoomNames.get(randName));
                        break;
                    case 4:
                        randName = generateRandomIndex();
                        profileImageView.setImageResource(R.drawable.nurse_waiting_area);
                        profileTextView.setText(waitingRoomNames.get(randName));
                        playNameMp3(waitingRoomNames.get(randName));
                        break;
                }
            }

            @Override
            public void onFinish() {
                mediaPlayer.reset();
                start();
            }
        }.start();
    }

    private int generateRandomIndex() {
        if (randomCountArrayList.size() == 4)
            randomCountArrayList.clear();
        int randomNumber = random.nextInt(4);
        while (randomCountArrayList.contains(randomNumber))
            randomNumber = random.nextInt(4);
        Log.d(TAG, "Random num: " + randomNumber);
        randomCountArrayList.add(randomNumber);
        return randomNumber;
    }

    private void playNameMp3(String name) {
        switch (name) {
            case MR_JOHN:
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_male_mr_john);
                mediaPlayer.start();
                break;
            case MR_ALEX:
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_male_mr_alex);
                mediaPlayer.start();
                break;
            case MRS_KATE:
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_male_mrs_kate);
                mediaPlayer.start();
                break;
            case MISS_LILY:
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_male_miss_lily);
                mediaPlayer.start();
                break;
        }
    }

    private void fourthStage() {
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                switch ((int) (l/1000)) {
                    case 29:
                        extraAnimation.setVisibility(View.GONE);
                        profileImageView.setImageResource(R.drawable.doctor_in_office);
                        profileTextView.setText("I would like to have your blood sample test report.");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.doc_male_want_blood);
                        mediaPlayer.start();
                        break;
                    case 25:
                        profileImageView.setImageResource(R.drawable.doctor_in_office);
                        profileTextView.setText("Kindly go to the lab.");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.doc_male_go_to_lab);
                        mediaPlayer.start();
                        break;
                    case 22:
//                        includeBottomScreen.setVisibility(View.INVISIBLE);
//                        mainImageView.setImageResource(R.drawable.time_lapse);
                        break;
                    case 18:
                        mainImageView.setImageResource(R.drawable.lab);
                        includeBottomScreen.setVisibility(View.VISIBLE);
                        profileImageView.setImageResource(R.drawable.doctor_in_lab);
                        profileTextView.setText("Please extend your arm");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.doc_male_lab_extend_arm);
                        mediaPlayer.start();
                        break;
                    case 16:
                        profileImageView.setImageResource(R.drawable.doctor_in_lab);
                        profileTextView.setText("so that I can take a blood sample");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.doc_male_lab_taking_blood);
                        mediaPlayer.start();
                        break;
                    case 13:
                        profileImageView.setImageResource(R.drawable.man_hospital_lab);
                        profileTextView.setText("Ok");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_ok);
                        mediaPlayer.start();
                        break;
                    case 11:
                        profileImageView.setImageResource(R.drawable.doctor_in_lab);
                        profileTextView.setText("I'll send the report to your doctor");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.doc_male_lab_send_report);
                        mediaPlayer.start();
                        break;
                    case 8:
                        profileImageView.setImageResource(R.drawable.doctor_in_lab);
                        profileTextView.setText("and he will give you the prescription");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.doc_male_lab_he_will_give_pres);
                        mediaPlayer.start();
                        break;
                    case 5:
                        profileImageView.setImageResource(R.drawable.man_hospital_lab);
                        profileTextView.setText("Ok");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_ok);
                        mediaPlayer.start();
                        break;
                    case 3:
                        thirdStageCompleted = true;
                        micWalletContainer.setVisibility(View.GONE);
                        profileTextView.setText("");
                        profileImageView.setVisibility(View.INVISIBLE);
                        nextButton.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void fifthStage() {
        new CountDownTimer(12000, 1000) {
            @Override
            public void onTick(long l) {
                switch ((int) (l/1000)) {
                    case 11:
                        profileImageView.setImageResource(R.drawable.man_pharmacy);
                        profileTextView.setText("Here you go");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_here_you_go);
                        mediaPlayer.start();
                        break;
                    case 9:
                        profileImageView.setImageResource(R.drawable.pharmacist);
                        profileTextView.setText("Here is your medicine");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_phar_medicine);
                        mediaPlayer.start();
                        break;
                    case 7:
                        profileImageView.setImageResource(R.drawable.pharmacist);
                        profileTextView.setText("You have to take it 3 times a day after meal");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_phar_three_times_a_day);
                        mediaPlayer.start();
                        break;
                    case 4:
                        profileImageView.setImageResource(R.drawable.pharmacist);
                        profileTextView.setText("for 2 weeks");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_phar_two_weeks);
                        mediaPlayer.start();
                        break;
                    case 2:
                        profileImageView.setImageResource(R.drawable.man_pharmacy);
                        profileTextView.setText("Thanks");
                        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_thanks);
                        mediaPlayer.start();
                        break;
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public void onClickWallet(View view) {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    public void onClickWalletItem(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.image_anim));
        int viewId = view.getId();
        if (selectCashAsked && viewId == R.id.cash_imageView) {
            Log.d(TAG, "cash");
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            startStage(THIRD_STAGE);
            selectCashAsked = false;
        } else if (selectIdAsked && viewId == R.id.id_card_imageView) {
            Log.d(TAG, "id");
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            startStage(SECOND_STAGE);
            selectIdAsked = false;
        } else if (selectInsuranceAsked && viewId == R.id.insurance_card_imageView) {
            Log.d(TAG, "insurance");
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            startStage(THIRD_STAGE);
            selectInsuranceAsked = false;
        } else if (selectPrescriptionAsked && viewId == R.id.prescription_imageView) {
            Log.d(TAG, "prescription");
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            startStage(FIFTH_STAGE);
            selectPrescriptionAsked = false;
        }
    }

    public void onClickNextButton(View view) {
        nextButton.setEnabled(false);
        nextButton.setText("Please wait...");
        if (firstStageCompleted) {
            firstStageCompleted = false;
            new CountDownTimer(16000, 1000) {
                @Override
                public void onTick(long l) {
                    switch ((int) (l / 1000)) {
                        case 14:
                            includeBottomScreen.setVisibility(View.INVISIBLE);
                            mainImageView.setImageResource(R.drawable.reception_lapse);
                            break;
                        case 10:
                            nextButton.setEnabled(true);
                            nextButton.setText("Next lesson");
                            micWalletContainer.setVisibility(View.VISIBLE);
                            profileImageView.setVisibility(View.VISIBLE);
                            nextButton.setVisibility(View.GONE);
                            includeBottomScreen.setVisibility(View.VISIBLE);
                            mainImageView.setImageResource(R.drawable.reception_2nd);
                            profileImageView.setImageResource(R.drawable.nurse_reception_2nd);
                            profileTextView.setText("Mr. John");
                            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_sec_reception_mr_john);
                            mediaPlayer.start();
                            break;
                        case 8:
                            profileImageView.setImageResource(R.drawable.man_hospital_2nd_reception);
                            profileTextView.setText("Yes");
                            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.male_yes);
                            mediaPlayer.start();
                            break;
                        case 7:
                            profileImageView.setImageResource(R.drawable.nurse_reception_2nd);
                            profileTextView.setText("Do you have insurance?");
                            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_sec_reception_do_you_have_insurance);
                            mediaPlayer.start();
                            haveInsuranceAsked = true;
                            break;
                        case 5:
                            enableMic = true;
                            break;

                    }
                }
                @Override
                public void onFinish() {
                }
            }.start();
        }

        if (secondStageCompleted) {
            secondStageCompleted = false;
            new CountDownTimer(13000, 1000) {
                @Override
                public void onTick(long l) {
                    switch ((int) (l / 1000)) {
                        case 10:
//                            includeBottomScreen.setVisibility(View.INVISIBLE);
//                            mainImageView.setImageResource(R.drawable.time_lapse);
                            break;
                        case 7:
                            nextButton.setEnabled(true);
                            nextButton.setText("Next lesson");
                            nextButton.setVisibility(View.GONE);
                            micWalletContainer.setVisibility(View.VISIBLE);
                            profileImageView.setVisibility(View.VISIBLE);
                            includeBottomScreen.setVisibility(View.VISIBLE);
                            mainImageView.setImageResource(R.drawable.doctors_office);
                            profileImageView.setImageResource(R.drawable.doctor_in_office);
                            profileTextView.setText("What's the problem?");
                            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.doc_male_whats_the_problem);
                            mediaPlayer.start();
                            break;
                        case 4:
                            bodyPartHighlighted = true;
                            includeBodyProblems.setVisibility(View.VISIBLE);
                            extraAnimation.setVisibility(View.VISIBLE);
                            bodyProblem = new Random().nextInt(3);
                            switch (bodyProblem) {
                                case 0:
                                    extraAnimation.setAnimation("head.json");
                                    extraAnimation.playAnimation(1, 35);
                                    extraAnimation.loop(false);
                                    break;
                                case 1:
                                    extraAnimation.setAnimation("chest.json");
                                    extraAnimation.playAnimation(1, 35);
                                    extraAnimation.loop(false);
                                    break;
                                case 2:
                                    extraAnimation.setAnimation("stomach.json");
                                    extraAnimation.playAnimation(1, 35);
                                    extraAnimation.loop(false);
                                    break;
                            }
                            break;
                        case 2:
                            enableMic = true;
                            break;

                    }
                }
                @Override
                public void onFinish() {
                }
            }.start();
        }

        if (thirdStageCompleted) {
            thirdStageCompleted = false;
            new CountDownTimer(7000, 1000) {
                @Override
                public void onTick(long l) {
                    switch ((int) (l / 1000)) {
                        case 5:
//                            includeBottomScreen.setVisibility(View.INVISIBLE);
//                            mainImageView.setImageResource(R.drawable.time_lapse);
                            break;
                        case 2:
                            nextButton.setEnabled(true);
                            nextButton.setText("Next lesson");
                            prescriptionTextView.setVisibility(View.VISIBLE);
                            prescriptionImageView.setVisibility(View.VISIBLE);
                            nextButton.setVisibility(View.GONE);
                            micWalletContainer.setVisibility(View.VISIBLE);
                            profileImageView.setVisibility(View.VISIBLE);
                            includeBottomScreen.setVisibility(View.VISIBLE);
                            mainImageView.setImageResource(R.drawable.pharmacy);
                            profileImageView.setImageResource(R.drawable.pharmacist);
                            profileTextView.setText("May I have the prescription?");
                            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_phar_have_pres);
                            mediaPlayer.start();
                            selectPrescriptionAsked = true;
                            break;
                    }
                }
                @Override
                public void onFinish() {
                }
            }.start();
        }
    }

    private  String getErrorMessage(int errorCode) {
        String errorMessage;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                errorMessage = "Audio isRecording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                errorMessage = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                errorMessage = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                errorMessage = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                errorMessage = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                errorMessage = "Sorry I didn't hear you";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                errorMessage = "I am busy processing your speech";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                errorMessage = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                errorMessage = "Sorry I didn't hear you";
                break;
            default:
                errorMessage = "Didn't understand, please try again.";
        }
        return errorMessage;
    }

    private void checkResult(String result) {
        Log.d(TAG, "checkResult approached!");
        result = result.toLowerCase();
        Log.d(TAG, result);
        String[] doctorNames = new String[]{"Dr Nancy", "Dr Ben", "Doctor Nancy", "Doctor Ben"};
        String[] daysOfWeek = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        String[] timeOfDay = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"};
        String[] whatIsTheProblem = new String[]{"headache", "stomachache", "chest pain", "head ache", "stomach ache", "chestpain"};
        String[] problemForHowLong = new String[]{"one day", "two days", "three days", "four days", "oneday", "tooday", "twodays", "two day", "three day", "today's", "today", "threedays", "four day", "fourdays", "threeday", "toodays", "fourday", "1", "2", "3", "4"};
        String[] morAfterEve = new String[]{"Morning", "Afternoon", "Evening"};

        if (haveAppointmentAsked) {
            Log.d(TAG, "hasappointmentAsked: " + haveAppointmentAsked);
            if (TextUtils.equals(result, "yes") && randAppointment == 0)
                rightAnswer("yes");
            else if (TextUtils.equals(result, "no") && randAppointment == 1)
                rightAnswer("no");
            else
                wrongAnswer("appointment");
        } else if (haveFileAsked) {
            Log.d(TAG, "havefileAsked: " + haveFileAsked);
            if (TextUtils.equals(result, "yes") && randHospitalFile == 0)
                rightAnswer("yes");
            else if (TextUtils.equals(result, "no") && randHospitalFile == 1)
                rightAnswer("no");
            else
                wrongAnswer("file");
        } else if (tellNameAsked) {
            Log.d(TAG, "tellNameAsked: " + tellNameAsked);
            userName = result;
            if (TextUtils.equals(result, "john"))
                rightAnswer(userName);
            else
                wrongAnswer("");
        } else if (selectIdAsked) {
//            micImageView.setVisibility(View.INVISIBLE);

        } else if (whichDayAsked) {
            for (String day : daysOfWeek) {
                daySelected = day;
                if (TextUtils.equals(result, day.toLowerCase())) {
                    rightAnswer(day);
                    return;
                }
            }
            wrongAnswer("day");
        } else if (morAfterEveAsked) {
            for (String mAE : morAfterEve) {
                mAESelected = mAE;
                if (TextUtils.equals(result, mAE.toLowerCase())) {
                    rightAnswer(mAE);
                    return;
                }
            }
            wrongAnswer("mAE");
        } else if (whatTimeAsked) {
            for (String time : timeOfDay) {
                timeSelected = time + " o'clock";
                if (result.contains(time.toLowerCase())) {
                    rightAnswer(time);
                    return;
                }
            }
            wrongAnswer("time");
        } else if (haveInsuranceAsked) {
            Log.d(TAG, "have insurance asked");
            if (TextUtils.equals(result, "yes") && randInsurance == 0)
                rightAnswer("yes");
            else if (TextUtils.equals(result, "no") && randInsurance == 1)
                rightAnswer("no");
            else
                wrongAnswer("");
        } else if (bodyPartHighlighted) {
            Log.d(TAG, "bodyPartHighlighted");
            if (bodyProblem == 0 && (result.equals("head ache") || result.equals("headache"))) {
                rightAnswer("headache");
                return;
            } else if (bodyProblem == 1 && (result.equals("chest pain") || result.equals("chestpain"))) {
                rightAnswer("chest pain");
                return;
            } else if (bodyProblem == 2 && (result.equals("stomach ache") || result.equals("stomachache"))) {
                rightAnswer("stomachache");
                return;
            } else
                wrongAnswer("bodyProblem");
        } else if(forHowLongAsked) {
            for (String s : problemForHowLong) {
                if (result.contains(s)) {
                    rightAnswer("one day");
                    return;
                }
            }
            wrongAnswer("one day");
        } else if(haveFeverAsked) {
            Log.d(TAG, "haveFeverAsked");
            if (randFever == 0 || randFever == 2) {
                if (TextUtils.equals(result, "yes"))
                    rightAnswer("haveFeverAsked");
                else
                    wrongAnswer("haveFeverAsked");
            } else if (randFever == 1) {
                if (TextUtils.equals(result, "no"))
                    rightAnswer("haveFeverAsked");
                else
                    wrongAnswer("haveFeverAsked");
            }
        } else {
            for (String docName : doctorNames) {
                doctorNameSelected = docName;
                if (TextUtils.equals(result, docName.toLowerCase())) {
                    rightAnswer(docName);
                    return;
                }
            }
            wrongAnswer("doc");
        }
    }

    private void wrongAnswer(String check) {
        if (check.equals("doc"))
            Toast.makeText(this, "There is no doctor available with that name", Toast.LENGTH_SHORT).show();
        else if (check.equals("day"))
            Toast.makeText(this, "There is no such day, please try again.", Toast.LENGTH_SHORT).show();
        else if (check.equals("bodyProblem"))
            Toast.makeText(this, "This is not the problem you have, please try again.", Toast.LENGTH_SHORT).show();
        else if (check.equals("haveFeverAsked") ) {
            if (randFever == 1)
                Toast.makeText(this, "You don't have fever, please try again.", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "You have fever, please try again.", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "That was not the right answer", Toast.LENGTH_SHORT).show();
    }

    private void rightAnswer(String check) {
        Log.d(TAG, "rightAnswer approached!");
        if (haveAppointmentAsked) {
            haveAppointmentAsked = false;
            if (check.equals("yes"))
                haveAppointment = true;
            else if (check.equals("no"))
                haveAppointment = false;
            profileImageView.setImageResource(R.drawable.nurse_reception);
            profileTextView.setText("May I have your name?");
            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_reception_may_i_have_your_name);
            mediaPlayer.start();
            tellNameAsked = true;
        } else if (tellNameAsked) {
            tellNameAsked = false;
            profileImageView.setImageResource(R.drawable.nurse_reception);
            if (haveAppointment) {
                profileTextView.setText("Do you have a file?");
                profileTextView.setText("Do you have a file?");
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_reception_do_you_have_a_file);
                mediaPlayer.start();
                haveFileAsked = true;
            } else {
                profileTextView.setText("Which day?");
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_reception_which_day);
                mediaPlayer.start();
                whichDayAsked = true;
            }
        } else if (haveFileAsked) {
            haveFileAsked = false;
            enableMic = false;
            profileImageView.setImageResource(R.drawable.nurse_reception);
            profileTextView.setText("May i have your ID?");
            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_reception_may_i_have_your_id);
            mediaPlayer.start();
            selectIdAsked = true;
        } else if (whichDayAsked) {
            whichDayAsked = false;
            extraImageView.setImageResource(R.drawable.timing);
            extraImageView.setVisibility(View.VISIBLE);
            profileImageView.setImageResource(R.drawable.nurse_reception);
            profileTextView.setText("Morning, afternoon or evening?");
            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_reception_mor_after_eve);
            mediaPlayer.start();
            morAfterEveAsked = true;
        } else if (morAfterEveAsked) {
            morAfterEveAsked = false;
            extraImageView.setVisibility(View.GONE);
            profileImageView.setImageResource(R.drawable.nurse_reception);
            profileTextView.setText("What time?");
            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_reception_what_time);
            mediaPlayer.start();
            whatTimeAsked = true;
        } else if (whatTimeAsked) {
            whatTimeAsked = false;
            enableMic = false;
            recipeDayTextView.setText(daySelected);
            recipeTimeOfDayTextView.setText(mAESelected);
            recipeDocTextView.setText(doctorNameSelected);
            recipeTimeTextView.setText(timeSelected);
            includeRecipeScreen.setVisibility(View.VISIBLE);
            mainImageView.setVisibility(View.INVISIBLE);
            includeBottomScreen.setVisibility(View.INVISIBLE);
//            extraTextView.setText("Your appointment with " + doctorNameSelected + " is on " + daySelected + " at " + timeSelected);
            profileImageView.setImageResource(R.drawable.nurse_reception);
            profileImageView.setVisibility(View.INVISIBLE);
            profileTextView.setText("");
            new CountDownTimer(4000, 1000) {

                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    includeRecipeScreen.setVisibility(View.GONE);
                    mainImageView.setVisibility(View.VISIBLE);
                    includeBottomScreen.setVisibility(View.VISIBLE);
                    enableMic = true;
                    extraTextView.setText("");
                    profileImageView.setVisibility(View.VISIBLE);
                    profileTextView.setText("Do you have a file?");
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_reception_do_you_have_a_file);
                    mediaPlayer.start();
                    haveFileAsked = true;
                }
            }.start();
        } else if (haveInsuranceAsked) {
            haveInsuranceAsked = false;
            enableMic = false;
            profileImageView.setImageResource(R.drawable.nurse_reception_2nd);
            if (check.equals("yes")) {
                profileTextView.setText("May i have your insurance card?");
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_sec_reception_may_i_have_your_insurance_card);
                mediaPlayer.start();
                selectInsuranceAsked = true;
            } else if (check.equals("no")) {
                profileTextView.setText("it's $15");
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_sec_reception_it_is_fifteen);
                mediaPlayer.start();
                selectCashAsked = true;
            }
        } else if (bodyPartHighlighted) {
            bodyPartHighlighted = false;
            includeBodyProblems.setVisibility(View.GONE);
            extraAnimation.setVisibility(View.GONE);
            extraAnimation.loop(false);
            extraAnimation.setAnimation("thermometer.json");
            profileImageView.setImageResource(R.drawable.doctor_in_office);
            profileTextView.setText("For how long?");
            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.doc_male_for_how_long);
            mediaPlayer.start();
            forHowLongAsked = true;
        } else if (forHowLongAsked) {
            forHowLongAsked = false;
            profileImageView.setImageResource(R.drawable.doctor_in_office);
            profileTextView.setText("Do you have fever?");
            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.doc_male_do_you_have_fever);
            mediaPlayer.start();
            extraAnimation.setVisibility(View.VISIBLE);
            randFever = new Random().nextInt(3);
            switch (randFever) {
                case 0:
                    Log.d(TAG, "fever");
                    extraAnimation.playAnimation(0, 19);
                    break;
                case 1:
                    Log.d(TAG, "no fever");
                    extraAnimation.playAnimation(25, 42);
                    break;
                case 2:
                    Log.d(TAG, "fever");
                    extraAnimation.playAnimation(47, 70);
                    break;
            }
            haveFeverAsked = true;
        } else if (haveFeverAsked) {
            Log.d(TAG, "HaveFeverInRightAnswer");
            haveFeverAsked = false;
            enableMic = false;
            extraAnimation.setVisibility(View.GONE);
            startStage(FOURTH_STAGE);
        } else {
            int randomNum = random.nextInt(1);
            switch (randomNum) {
                case 0:
                    profileImageView.setImageResource(R.drawable.nurse_reception);
                    profileTextView.setText("Do you have an appointment?");
                    extraImageView.setVisibility(View.GONE);
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.nurse_reception_do_you_have_appointment);
                    mediaPlayer.start();
                    haveAppointmentAsked = true;
                    break;
                case 1:

                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_start:
                if (startFirstTime) {
                    startFirstTime = false;
                    extraAnimation.setVisibility(View.GONE);
                    extraImageView.setVisibility(View.GONE);
                    profileImageView.setVisibility(View.INVISIBLE);
                    profileTextView.setVisibility(View.INVISIBLE);
                    micWalletContainer.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.GONE);
                    mainImageView.setImageResource(R.drawable.reception);
                    enableMic = false;
                    startStage(FIRST_STAGE);
                }
//                waitingRoomCountdownTimer.cancel();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.mic_imageView:
                if (enableMic && !textToSpeech.isSpeaking() && !mediaPlayer.isPlaying()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            view.setPressed(true);
                            if (speechRecognizer.isRecognitionAvailable(this) && !isRecording) {
                                Log.d(TAG, "recognition available");
                                initSpeechRecognition();
                                speechRecognizer.startListening(speechIntent);
                                micImageView.setImageResource(R.drawable.mic_busy);
                                isRecording = true;
                            } else {
                                Log.d(TAG, "recognition not available");
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            view.setPressed(false);
                            if (isRecording) {
                                speechRecognizer.stopListening();
                                micImageView.setImageResource(R.drawable.mic);
                                isRecording = false;
                            }
                            break;
                    }
                }
                return true;
        }
        return false;
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {
    }

    @Override
    public void onError(int i) {
        micImageView.setImageResource(R.drawable.mic);
        speechRecognizer.cancel();
        isRecording = false;
        String errorMessage = getErrorMessage(i);
        if (errorToast != null) {
            errorToast.cancel();
        }
        errorToast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResults(Bundle bundle) {
        String result = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
        checkResult(result);
        Log.d(TAG, result);
    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }

    public void onClickBodyProblem(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.play_headache_imageView:
               textToSpeech.speak("headache", TextToSpeech.QUEUE_FLUSH, null, null);
                break;
            case R.id.play_chest_pain_imageView:
                textToSpeech.speak("chest pain", TextToSpeech.QUEUE_FLUSH, null, null);
                break;
            case R.id.play_stomachache_imageView:
                textToSpeech.speak("stomach ache", TextToSpeech.QUEUE_FLUSH, null, null);
                break;
        }
    }

    public void onClickResponseButton(View view) {
        if (profileTextView.getText().equals(MR_JOHN)) {
            waitingRoomCountdownTimer.cancel();
            animationRespondButton.playAnimation(170, 220);
            startStage(THIRD_STAGE_101);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
